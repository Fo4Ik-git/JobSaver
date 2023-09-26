package com.fo4ik.config;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Config {

    public static final String APP_NAME = "Job saver";
    public static final String APP_VERSION = "0.0.1";
    public static final String APP_AUTHOR = "Fo4Ik";
    public static final String APP_DATABASE_NAME = "database.db";

//    public static final String APP_ICON = "src/main/resources/icon.png";

    public static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("language", new Locale("ru"));

    public static final String[] STATUS_OPTIONS = {
            RESOURCE_BUNDLE.getString("JobStatus.New"),
            RESOURCE_BUNDLE.getString("JobStatus.Viewed"),
            RESOURCE_BUNDLE.getString("JobStatus.Applied"),
            RESOURCE_BUNDLE.getString("JobStatus.Interview"),
            RESOURCE_BUNDLE.getString("JobStatus.Offer"),
            RESOURCE_BUNDLE.getString("JobStatus.Rejected")};

    public static ResourceBundle getResourceBundle() {
        return RESOURCE_BUNDLE;
    }

    public static void setResourceBundle(String language) {
        RESOURCE_BUNDLE = ResourceBundle.getBundle("language", new Locale(language));
    }

    public static Map<String, String> SUPPORTED_LANGUAGES_MAP = Map.of(
            "en", "English",
            "pl", "Polski",
            "uk", "??????????",
            "ru", "???????"
    );
}
