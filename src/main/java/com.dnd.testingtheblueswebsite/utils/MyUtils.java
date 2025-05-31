package com.dnd.testingtheblueswebsite.utils;

public class MyUtils {
    public static String randomEmail(){
        String prefix = "testuser";
        String suffix = "@gmail.com";
        int randomNum = (int) (Math.random() * 10000);
        return prefix + randomNum + suffix;
    }

    public static String randomLongString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 70; i++) {
            sb.append((char) ('a' + Math.random() * 26));
        }
        return sb.toString();
    }
}
