package com.scrapper.scraperhtmlbatch.utils;

public class Utils {
    public static String extractBaseUrl(String url) {
        int slashIndex = url.indexOf("/");
        if (slashIndex != -1) {
            int endIndex = url.indexOf("/", slashIndex + 2);
            if (endIndex != -1) {
                return url.substring(0, endIndex);
            } else {
                return url;
            }
        } else {
            return url;
        }
    }

}
