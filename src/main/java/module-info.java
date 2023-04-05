module com.example.musicturndesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.json;
    requires javax.ws.rs.api;
    requires com.google.gson;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;


    opens com.example.musicturndesktop to javafx.fxml;
    exports com.example.musicturndesktop;
    exports com.example.musicturndesktop.Models;
}