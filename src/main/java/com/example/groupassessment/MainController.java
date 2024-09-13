package com.example.groupassessment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    private Label welcomeText;
    @FXML
    private Button profileButton;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void viewProfileClick() {
        profileButton.setText("NO");
    }
}