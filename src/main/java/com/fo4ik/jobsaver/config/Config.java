package com.fo4ik.jobsaver.config;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Config {
    public static final String APP_NAME = "Job saver";
    public static final String APP_VERSION = "2.0.0";
    public static final String APP_AUTHOR = "Fo4Ik";
    public static final String APP_DATABASE_NAME = "database.db";
    public static final String PROPERTIES_FILE_PATH = "Job saver.properties";
    public static final String APP_ICON = "/img/icon.png";
    public static Map<String, String> SUPPORTED_LANGUAGES_MAP = Map.of("en", "English", "pl", "Polski", "uk", "Українська", "ru", "Русский");
    private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("language", Locale.getDefault());
    public String[] STATUS_OPTIONS;

    public Config() {
        this.STATUS_OPTIONS = new String[]{
                RESOURCE_BUNDLE.getString("JobStatus.New"),
                RESOURCE_BUNDLE.getString("JobStatus.Viewed"),
                RESOURCE_BUNDLE.getString("JobStatus.Applied"),
                RESOURCE_BUNDLE.getString("JobStatus.Interview"),
                RESOURCE_BUNDLE.getString("JobStatus.Offer"),
                RESOURCE_BUNDLE.getString("JobStatus.Rejected")};
    }

    public static ResourceBundle getResourceBundle() {
        return RESOURCE_BUNDLE;
    }

    public static void setResourceBundle(String language) {
        RESOURCE_BUNDLE = ResourceBundle.getBundle("language", new Locale(language));
    }
}
