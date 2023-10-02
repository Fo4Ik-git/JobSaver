package com.fo4ik.jobsaver.engine;

import com.fo4ik.jobsaver.config.Config;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class PropertiesManager {

    public static Map<Object, Object> getProperties(String name) {
        Map<Object, Object> resultProperties = new HashMap();

        try (InputStream input = new FileInputStream(name)) {
            Properties properties = new Properties();
            properties.load(input);
            //load all from properties file to map

            for(String key : properties.stringPropertyNames()) {
                resultProperties.put(key, properties.getProperty(key));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultProperties;
    }

    public static void createProperties(String name) {
        File configFile = new File(name);
        if (!configFile.exists()) {
            try (OutputStream output = new FileOutputStream(name)) {
                Properties properties = new Properties();
                properties.store(output, "Created properties file");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void editProperties(String name, String key, String value) {
        try (InputStream input = new FileInputStream(name)) {
            Properties properties = new Properties();
            properties.load(input);
            properties.setProperty(key, value);

            OutputStream output = new FileOutputStream(name);

            properties.store(output, "Updated Properties: " + key + "=" + value);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
