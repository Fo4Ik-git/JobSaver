module com.fo4ik.jobsaver {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.media;

    requires org.jsoup;
    requires org.xerial.sqlitejdbc;
    requires java.sql;


    exports com.fo4ik.jobsaver;
    exports com.fo4ik.jobsaver.controllers;
    exports com.fo4ik.jobsaver.window;

    opens com.fo4ik.jobsaver to javafx.fxml;
    opens com.fo4ik.jobsaver.controllers to javafx.fxml;
    opens com.fo4ik.jobsaver.window to javafx.fxml;

}
