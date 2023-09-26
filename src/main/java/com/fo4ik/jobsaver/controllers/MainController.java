package com.fo4ik.jobsaver.controllers;

import com.fo4ik.jobsaver.config.Config;
import com.fo4ik.jobsaver.database.DBHelper;
import com.fo4ik.jobsaver.engine.SceneSwitcher;
import com.fo4ik.jobsaver.entity.Job;
import com.fo4ik.jobsaver.window.AddJobWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button addButton;
    @FXML
    private ListView<Job> jobList;
    @FXML
    private MenuBar menu;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField searchField;
    @FXML
    private HBox searchPanel;
    @FXML
    private Button actionButton;
    private int width;
    private int height;
    private DBHelper dbHelper = DBHelper.getInstance();
    private ObservableList<Job> selectedJobs = FXCollections.observableArrayList();

    public void getSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void loadJobList() {
        dbHelper.connect();
        List<Job> jobs = this.dbHelper.getAllJobs();
        ObservableList<Job> observableJobs = FXCollections.observableArrayList(jobs);
        jobList.setItems(observableJobs);
        dbHelper.close();
    }

    @FXML
    void openAddWindow(ActionEvent event) {
        try {
            new AddJobWindow();
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    @FXML
    void openJobView(MouseEvent event) throws IOException {
        Job selectedJob = (Job) jobList.getSelectionModel().getSelectedItem();
        if (selectedJob != null && event.getClickCount() == 2) {
            ActionEvent actionEvent = new ActionEvent(event.getTarget(), (EventTarget) null);
            SceneSwitcher sceneSwitcher = new SceneSwitcher("/com/fo4ik/jobsaver/job/jobView.fxml");

            try {
                sceneSwitcher.switchSceneToJobView(actionEvent, selectedJob);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void search(ActionEvent event) {
        search(searchField.getText());
    }

    @FXML
    void actionButton(ActionEvent event) {
        if (searchField.getText().isEmpty()) {
            search(event);
        } else {
            searchField.clear();
            loadJobList();
        }

    }

    @FXML
    void handleDeleteKey(KeyEvent event) {
        switch (event.getCode()) {
            case DELETE:
                deleteSelectedJob();
                break;
            case F5:
                if (event.isShiftDown()) {
                    loadJobList();
                }
        }

    }

    @FXML
    void selectAll(ActionEvent event) {
        selectedJobs.addAll(jobList.getItems());
        jobList.getSelectionModel().selectAll();
    }

    @FXML
    void unselectAll(ActionEvent event) {
        selectedJobs.clear();
        jobList.getSelectionModel().clearSelection();
    }

    @FXML
    void delete(ActionEvent event) {
        deleteSelectedJob();
    }

    public void initialize(URL location, ResourceBundle resources) {
        jobList.setPrefWidth((double) width);
        scrollPane.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            jobList.setPrefWidth(newWidth.doubleValue());
        });
        scrollPane.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            jobList.setPrefHeight(newHeight.doubleValue());
        });
        jobList.setCellFactory(new Callback<ListView<Job>, ListCell<Job>>() {
            public ListCell<Job> call(ListView<Job> param) {
                return new JobListCell();
            }
        });
        jobList.setFixedCellSize(50.0);
        jobList.getStyleClass().add("list-cell");
        jobList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        jobList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedJobs.add(newValue);
        });
        scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent;");
        dbHelper.registerDatabaseChangeListener(this::loadJobList);


        dbHelper.connect();
        loadJobList();
        dbHelper.close();
    }

    private void search(String searchQuery) {
        if (!searchQuery.isEmpty()) {
            dbHelper.connect();
            List<Job> jobs = dbHelper.searchJobsByTitleOrCompany(searchQuery);
            ObservableList<Job> observableJobs = FXCollections.observableArrayList(jobs);
            jobList.setItems(observableJobs);
            dbHelper.close();
        } else {
            loadJobList();
        }

    }

    private void deleteSelectedJob() {
        if (!selectedJobs.isEmpty()) {
            dbHelper.connect();
            Iterator<Job> iterator = selectedJobs.iterator();

            while (iterator.hasNext()) {
                Job selectedJob = (Job) iterator.next();
                dbHelper.deleteJob(selectedJob);
            }

            dbHelper.close();
            selectedJobs.clear();
            loadJobList();
        }
    }

    private class JobListCell extends ListCell<Job> {

        @Override
        protected void updateItem(Job item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fo4ik/jobsaver/job/jobCell.fxml"));
                HBox content;
                try {
                    content = loader.load();
                    JobCellController controller = loader.getController();
                    controller.setJob(item);
                    setGraphic(content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
