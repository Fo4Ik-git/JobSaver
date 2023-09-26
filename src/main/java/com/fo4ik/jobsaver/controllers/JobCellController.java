package com.fo4ik.jobsaver.controllers;

import com.fo4ik.jobsaver.entity.Job;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.text.SimpleDateFormat;

public class JobCellController {
    @FXML
    private Label titleLabel;
    @FXML
    private HBox jobBox;
    @FXML
    private Label dateLabel;

    public JobCellController() {
    }

    public void setJob(Job job) {
        String var10000 = job.getJobTitle();
        String jobInfo = var10000 + " | " + job.getJobCompany();
        titleLabel.setText(jobInfo);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(job.getAddDate());
        dateLabel.setText(formattedDate);
        setBackgroundForElements(job);
    }

    private void setBackgroundForElements(Job job) {
        switch (job.getStatus()) {
            case 0:
                jobBox.setStyle("-fx-background-color: #00FF00");
                break;
            case 1:
                jobBox.setStyle("-fx-background-color: #7C7CF9");
                break;
            case 2:
                jobBox.setStyle("-fx-background-color: #FFA500");
                break;
            case 3:
                jobBox.setStyle("-fx-background-color: #C102C1");
                break;
            case 4:
                jobBox.setStyle("-fx-background-color: #009E00");
                break;
            case 5:
                jobBox.setStyle("-fx-background-color: #FF0000");
                break;
            default:
                jobBox.setStyle("-fx-background-color: #FFFFFF");
        }

    }
}
