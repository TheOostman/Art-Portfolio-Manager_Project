package com.example.groupassessment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.io.IOException;

public class MainController {
    @FXML
    private Button profileButton;
    //@FXML
    //private Button signinRegister;
    @FXML
    private TextField usernameEntry;
    @FXML
    private TextField passwordEntry;


    public static boolean isLoggedIn = true;        // Please use this boolean for signing and register !!!!
    String basicUsername = "username1";             //Test data, this can be deleted
    String basicPassword = "123";                   //Test data, this can be deleted

    @FXML
    protected void viewProfileClick() {

        if (isLoggedIn){
            System.out.println(basicUsername);
        }
        else{
            profileButton.setText("SIGNIN FIRST");
        }
    }

    @FXML
    protected void signInBn() {
        System.out.println(usernameEntry);
        System.out.println(passwordEntry);
    }

    @FXML
    private void toRegisterPageBn(ActionEvent event) throws IOException {
        MainApplication.changeScene("RegisterPage.fxml");
    }

    @FXML
    private void toSignInBn(ActionEvent event) throws IOException {
        MainApplication.changeScene("LoginPage.fxml");
    }

    @FXML
    private void registerInfo() {

    }



}

