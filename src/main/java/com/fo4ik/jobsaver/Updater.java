package com.fo4ik.jobsaver;

import com.fo4ik.jobsaver.config.Config;
import com.fo4ik.jobsaver.controllers.UpdaterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Updater extends Application {
    private String version;
    private Stage mainStage;

    public Updater(String version, Stage mainStage) {
        this.version = version;
        this.mainStage = mainStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Updater.class.getResource("/com/fo4ik/jobsaver/updater.fxml"));
        fxmlLoader.setResources(Config.getResourceBundle());

        Scene scene = new Scene(fxmlLoader.load());

        UpdaterController updateController = fxmlLoader.getController();
        updateController.setLabel(Config.getResourceBundle().getString("updater.label").replace("{0}", version));
        updateController.setStage(stage, mainStage);
        updateController.setVersion(version);

        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/icon.png"))));
        stage.setTitle("Updater");
        stage.setScene(scene);
        stage.show();
    }
}
