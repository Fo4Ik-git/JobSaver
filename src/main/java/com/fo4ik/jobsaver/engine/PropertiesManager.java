package com.fo4ik.jobsaver.engine;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesManager {

    public static Map<Object, Object> getProperties() {
        Map<Object, Object> resultProperties = new HashMap();

        try (InputStream input = new FileInputStream("Job saver.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            resultProperties.put("Language", properties.getProperty("Language"));
            resultProperties.put("Current version", properties.getProperty("Current version"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultProperties;
    }

    public static void createProperties() {
        File configFile = new File("Job saver.properties");
        if (!configFile.exists()) {
            try (OutputStream output = new FileOutputStream("Job saver.properties")) {
                Properties properties = new Properties();
                properties.setProperty("Language", "en");
                properties.setProperty("Current version", "2.0.0");
                properties.store(output, "Created properties file");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void editProperties(String key, String value) {
        try(InputStream input = new FileInputStream("Job saver.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            properties.setProperty(key, value);

            OutputStream output = new FileOutputStream("Job saver.properties");

            properties.store(output, "Updated Properties: " + key + "=" + value);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
