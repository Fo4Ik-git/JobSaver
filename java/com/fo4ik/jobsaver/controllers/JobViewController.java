package com.fo4ik.jobsaver.controllers;

import com.fo4ik.jobsaver.config.Config;
import com.fo4ik.jobsaver.database.DBHelper;
import com.fo4ik.jobsaver.engine.SceneSwitcher;
import com.fo4ik.jobsaver.entity.Job;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class JobViewController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField jobCompany;
    @FXML
    private Label jobCompanyLabel;
    @FXML
    private ChoiceBox<String> jobStatusChoice;
    @FXML
    private Label jobStatusLabel;
    @FXML
    private TextField jobTitle;
    @FXML
    private Label jobTitleLabel;
    @FXML
    private WebView jobWebView;
    @FXML
    private Button saveButton;
    private DBHelper dbHelper = DBHelper.getInstance();
    private Job job;

    public void loadJob(Job job) {
        this.job = job;
        jobTitle.setText(job.getJobTitle());
        jobCompany.setText(job.getJobCompany());
        Config config = new Config();

        ObservableList<String> options = FXCollections.observableArrayList(config.STATUS_OPTIONS);
        jobStatusChoice.setItems(options);
        jobStatusChoice.setValue(config.STATUS_OPTIONS[job.getStatus()]);
        jobWebView.getEngine().loadContent(job.getHtml());
    }

    @FXML
    void deleteJob(ActionEvent event) throws IOException {
        dbHelper.connect();
        dbHelper.deleteJob(job);
        dbHelper.close();
        switchToMain(event);
    }

    @FXML
    void save(ActionEvent event) {
        dbHelper.connect();

        try {
            dbHelper.connect();
            job.setJobTitle(jobTitle.getText());
            job.setJobCompany(jobCompany.getText());
            job.setStatus(jobStatusChoice.getSelectionModel().getSelectedIndex());
            dbHelper.updateJob(job);
            dbHelper.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @FXML
    void switchToMain(ActionEvent event) throws IOException {
        SceneSwitcher sceneSwitcher = new SceneSwitcher("/com/fo4ik/jobsaver/main.fxml");
        sceneSwitcher.switchScene(event);
    }

    public void initialize(URL location, ResourceBundle resources) {
    }
}
