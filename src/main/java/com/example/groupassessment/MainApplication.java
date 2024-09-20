package com.example.groupassessment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        scene.getRoot().setStyle("-fx-background-color: #91A3B0;");
        stage.setResizable(false);
        stage.setTitle("Art Portfolio Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void changeScene(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        scene.getRoot().setStyle("-fx-background-color: #91A3B0;");
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}