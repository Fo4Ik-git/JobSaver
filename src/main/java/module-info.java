module com.fo4ik.jobsaver {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;
    requires javafx.media;

    requires java.base;
    requires java.net.http;
    requires java.sql;

    requires jdk.crypto.cryptoki;
    requires jdk.crypto.ec;
    requires jdk.jsobject;
    requires jdk.unsupported;

    requires org.jsoup;
    requires org.xerial.sqlitejdbc;
    requires org.apache.commons.io;

    exports com.fo4ik.jobsaver;
    exports com.fo4ik.jobsaver.controllers;
    exports com.fo4ik.jobsaver.controllers.settings;

    opens com.fo4ik.jobsaver to javafx.fxml;
    opens com.fo4ik.jobsaver.controllers to javafx.fxml;
    opens com.fo4ik.jobsaver.controllers.settings to javafx.fxml;
}
