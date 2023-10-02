package com.fo4ik.jobsaver.controllers.settings;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class SettingsController {

    @FXML
    private AnchorPane mainPanel;

    @FXML
    private AnchorPane top;

    public void setSize(int width, int height) {
        mainPanel.setPrefWidth(width);
        mainPanel.setPrefHeight(height);
    }

    public void initialize() {
        //on click on top panel
        top.setOnMouseClicked(event -> {
            System.out.println("top clicked");
        });
    }
}
