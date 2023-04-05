package com.example.musicturndesktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {

    public static Stage app;

    public static HelloController helloController;

    @Override
    public void start(Stage stage) throws IOException {
        app = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        helloController = fxmlLoader.getController();
        app.setTitle("Hello!");
        app.setScene(scene);
        app.setResizable(false);
        app.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        app.show();
    }

    public static void main(String[] args) {
        launch();
    }
}