package com.fo4ik.jobsaver.controllers;

import com.fo4ik.jobsaver.config.Config;
import com.fo4ik.jobsaver.engine.PropertiesManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UpdaterController {

    @FXML
    private Label updaterLabel;
    private Stage stage, mainStage;
    private String version;

    public void setStage(Stage stage, Stage mainStage) {
        this.stage = stage;
        this.mainStage = mainStage;
    }

    public void setLabel(String text) {
        updaterLabel.setText(text);
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @FXML
    void cancel(ActionEvent event) {
        stage.close();
    }

    @FXML
    void installUpgrade(ActionEvent event) {
        String updateProperties = "update.properties";
        PropertiesManager.createProperties(updateProperties);
        PropertiesManager.editProperties(updateProperties, "downloadFile", Config.URL_TO_DOWNLOAD_RELEASE + "/" + version + "/" + Config.WIN_ZIP_NAME);
        PropertiesManager.editProperties(updateProperties, "file", Config.WIN_ZIP_NAME);

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "update.jar");
            Process process = processBuilder.start();
            Platform.exit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
