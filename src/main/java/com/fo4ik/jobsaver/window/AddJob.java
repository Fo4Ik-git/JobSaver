package com.fo4ik.jobsaver.window;

import com.fo4ik.jobsaver.database.DBHelper;
import com.fo4ik.jobsaver.engine.HtmlParser;
import com.fo4ik.jobsaver.entity.Job;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddJob {
    @FXML
    private Button addButton;
    @FXML
    private TextField linkInput;

    public AddJob() {
    }

    public void initialize() {
        addButton.setOnAction((event) -> {
            if (!linkInput.getText().isEmpty()) {
                try {
                    Job job = new Job(HtmlParser.getJobTitle(linkInput.getText()), HtmlParser.getJobCompany(linkInput.getText()), HtmlParser.getHtml(linkInput.getText()), new Date(), 0);
                    DBHelper dbHelper = DBHelper.getInstance();
                    dbHelper.connect();
                    dbHelper.addJob(job);
                    dbHelper.close();
                    Stage stage = (Stage) addButton.getScene().getWindow();
                    stage.close();
                } catch (Exception e) {
                    System.out.println("Error while adding job: " + e.getMessage());
                }
            }

        });
    }
}
