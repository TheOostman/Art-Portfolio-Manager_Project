package com.example.groupassessment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainController {
    @FXML
    private Label welcomeText;
    @FXML
    private Button profileButton;
    @FXML
    private Button signinRegister;
    private TextField usernameEntry;
    private TextField passwordEntry;



    @FXML
    protected void viewProfileClick() {
        profileButton.setText("SIGNIN FIRST");
    }

    @FXML
    protected void signInRegisterBn() {
        System.out.println(usernameEntry);
        System.out.println(passwordEntry);
    }

}