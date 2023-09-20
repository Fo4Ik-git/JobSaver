package com.fo4ik.config;

import java.util.Locale;
import java.util.ResourceBundle;

public class Config {

    public static final String APP_NAME = "Job saver";
    public static final String APP_VERSION = "0.0.1";
    public static final String APP_AUTHOR = "Fo4Ik";
    public static final String APP_DATABASE_NAME = "database.db";

//    public static final String APP_ICON = "src/main/resources/icon.png";

    public static ResourceBundle resourceBundle = ResourceBundle.getBundle("language", Locale.getDefault());

    public static final String[] STATUS_OPTIONS = {
            resourceBundle.getString("JobStatus.New"),
            resourceBundle.getString("JobStatus.Viewed"),
            resourceBundle.getString("JobStatus.Applied"),
            resourceBundle.getString("JobStatus.Interview"),
            resourceBundle.getString("JobStatus.Offer"),
            resourceBundle.getString("JobStatus.Rejected")};
    public static ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public static void setResourceBundle(String language) {
        resourceBundle = ResourceBundle.getBundle("language", new Locale(language));
    }
}
