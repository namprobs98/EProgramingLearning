package com.eprogramming.learning.util;

public final class LocaleUtils {

    public static final String VI = "vi";
    public static final String EN = "en";

    private LocaleUtils() {
    }

    public static String resolveLanguage(String acceptLanguage) {
        if (acceptLanguage == null || acceptLanguage.isBlank()) {
            return VI;
        }
        String lang = acceptLanguage.trim().toLowerCase();
        if (lang.startsWith(EN) || lang.contains("en")) {
            return EN;
        }
        return VI;
    }

    public static boolean isEnglish(String lang) {
        return EN.equals(resolveLanguage(lang));
    }
}
