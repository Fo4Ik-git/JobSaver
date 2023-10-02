package com.fo4ik.jobsaver;

import com.fo4ik.jobsaver.config.Config;
import com.fo4ik.jobsaver.controllers.MainController;
import com.fo4ik.jobsaver.database.DBHelper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    protected Scene scene;
    private int width = 720;
    private int height = 405;

    public Main() {
    }

    public static void main(String[] args) {
        launch(new String[0]);
    }

    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        fxmlLoader.setResources(Config.getResourceBundle());
        createFiles();
        scene = new Scene((Parent)fxmlLoader.load(), (double)this.width, (double)this.height);
        stage.setMinWidth((double)width);
        stage.setMinHeight((double)height);
        MainController mainController = (MainController)fxmlLoader.getController();
        mainController.getSize(width, height);
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/img/icon.png")));
        stage.setTitle("Job saver");
        stage.setScene(scene);
        stage.show();
    }

    private void createFiles() throws IOException {
        Path properties = Paths.get("Job saver.properties");
        Path database = Paths.get("database.db");
        if (!properties.toFile().exists()) {
            Files.createFile(properties);
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

    }
}
