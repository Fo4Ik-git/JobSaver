package com.fo4ik.jobsaver;

import com.fo4ik.jobsaver.config.Config;
import com.fo4ik.jobsaver.controllers.MainController;
import com.fo4ik.jobsaver.database.DBHelper;
import com.fo4ik.jobsaver.engine.PropertiesManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Objects;

public class Main extends Application {
    private final int width = 720;
    private final int height = 405;

    private Stage primaryStage;

    public static void main(String[] args) {
        launch();
    }


    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/fo4ik/jobsaver/main.fxml"));
            fxmlLoader.setResources(Config.getResourceBundle());

            createFiles();
            Scene scene = new Scene(fxmlLoader.load(), width, height);
            stage.setMinWidth(width);
            stage.setMinHeight(height);

            MainController mainController = fxmlLoader.getController();
            mainController.getSize(width, height);

            primaryStage = stage;

            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/icon.png"))));
            stage.setTitle("Job saver");
            stage.setScene(scene);
            stage.show();

            //check updates
            checkUpdates();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkUpdates() throws Exception {
        try {
            String finalRealeseVersion = getFinalRealeseVersion(Config.URL_TO_LAST_RELEASE);
            //finalRealeseVersion get last / in url
            String version = finalRealeseVersion.substring(finalRealeseVersion.lastIndexOf('/') + 1);
            //if version > current version
            if (version.compareTo(Config.APP_VERSION) > 0) {
                Updater updater = new Updater(version, primaryStage);
                updater.start(new Stage());
            }
        } catch (Exception e) {
            System.out.println("Error while getting final realese version");
        }
    }

    private void createFiles() throws IOException {
        Path properties = Paths.get(Config.PROPERTIES_FILE_PATH);
        Path database = Paths.get(Config.APP_DATABASE_NAME);
        Path update = Paths.get("update.jar");
        if (!properties.toFile().exists()) {
            PropertiesManager.createProperties(Config.PROPERTIES_FILE_PATH);
            PropertiesManager.editProperties(Config.PROPERTIES_FILE_PATH, "Language", Locale.getDefault().getLanguage());
            PropertiesManager.editProperties(Config.PROPERTIES_FILE_PATH, "Current version", Config.APP_VERSION);
        }
        if (!database.toFile().exists()) {
            Files.createFile(database);
        }
        if (database.toFile().exists()) {
            DBHelper dbHelper = DBHelper.getInstance();
            dbHelper.connect();
            dbHelper.createTable();
            dbHelper.close();
        }
        if (!update.toFile().exists()) {
            String currentDirectory = System.getProperty("user.dir");

            Path destinationPath = Path.of(currentDirectory, "update.jar");
            try {
                //copy update.jar from resources to current directory
                InputStream inputStream = getClass().getResourceAsStream("/updater/update.jar");
                Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("update.jar created");
            } catch (Exception e) {
                System.out.println("Error while creating update.jar " + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    private String getFinalRealeseVersion(String url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.connect();

        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode >= 300 && responseCode < 400) {
            String redirectUrl = httpURLConnection.getHeaderField("Location");
            if (redirectUrl != null) {
                // If the response code is a redirect, recursively check the new URL.
                return getFinalRealeseVersion(redirectUrl);
            }
        }

        return url;
    }
}
