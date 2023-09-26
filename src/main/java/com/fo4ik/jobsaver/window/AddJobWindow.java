package com.fo4ik.jobsaver.window;

import com.fo4ik.jobsaver.Main;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddJobWindow {
    public AddJobWindow() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Add job");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("window/addJob.fxml"));
        Scene scene = new Scene((Parent)fxmlLoader.load(), 300.0, 200.0);
        stage.setScene(scene);
        stage.show();
    }
}
