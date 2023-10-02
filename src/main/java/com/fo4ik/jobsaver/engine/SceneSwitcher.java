package com.fo4ik.jobsaver.engine;

import com.fo4ik.jobsaver.Main;
import com.fo4ik.jobsaver.config.Config;
import com.fo4ik.jobsaver.controllers.JobViewController;
import com.fo4ik.jobsaver.entity.Job;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwitcher {
    private String urlToSwitchFXML;
    private int width;
    private int height;

    public SceneSwitcher(String urlToSwitchFXML, int width, int height) {
        this.urlToSwitchFXML = urlToSwitchFXML;
        this.width = width;
        this.height = height;
    }

    public SceneSwitcher(String urlToSwitchFXML) {
        this.urlToSwitchFXML = urlToSwitchFXML;
    }

    public void switchScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(urlToSwitchFXML));
        fxmlLoader.setResources(Config.getResourceBundle());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene((Parent)fxmlLoader.load(), stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.show();
    }

    public void switchSceneToJobView(ActionEvent event, Job job) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneSwitcher.class.getResource(urlToSwitchFXML));
        fxmlLoader.setResources(Config.getResourceBundle());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene((Parent)fxmlLoader.load(), stage.getWidth(), stage.getHeight());
        JobViewController jobViewController = (JobViewController)fxmlLoader.getController();
        jobViewController.loadJob(job);
        stage.setScene(scene);
        stage.show();
    }
}
