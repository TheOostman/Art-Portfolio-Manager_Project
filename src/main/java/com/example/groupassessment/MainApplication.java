package com.example.groupassessment;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.IOException;

import static com.example.groupassessment.DatabaseManager.createTables;

public class MainApplication extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        scene.getRoot().setStyle("-fx-background-color: #ffffff;");
        stage.setResizable(false);
        stage.setTitle("Art Portfolio Manager");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void changeScene(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        scene.getRoot().setStyle("-fx-background-color: #ffffff;");
        primaryStage.setScene(scene);
        createTables();
    }

    public static void main(String[] args) {
        launch();
    }


}