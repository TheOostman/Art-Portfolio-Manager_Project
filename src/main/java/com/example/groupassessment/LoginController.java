package com.example.groupassessment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.Console;
import java.io.IOException;
import javafx.scene.layout.HBox;

public class LoginController {
    @FXML
    private TextField usernameEntry;
    @FXML
    private TextField passwordEntry;


    // THIS SECTION IS DUMMY STATS AND CAN BE DELETED
    public static boolean isLoggedIn = true;        // Please use this boolean for signing and register !!!!
    public String basicUsername = "123";             //Test data, this can be deleted
    public String basicPassword = "123";                   //Test data, this can be deleted
    //----------------------------------


    public void changeToMain() throws IOException{
        MainApplication.changeScene("MainPage.fxml");
    }

    @FXML
    protected void signInBn() {
        String enteredUsername = usernameEntry.getText();
        String enteredPassword = passwordEntry.getText();
        if (enteredUsername.equals(basicUsername)){
            if (enteredPassword.equals(basicPassword)){
                System.out.println("Test Dummy Account" + " " + enteredUsername);
                try {
                    changeToMain();
                } catch (IOException e) {
                    System.out.println("Error happened at changing to main page");
                }
            }
            else{
                System.out.println("Failed Login on " + " " + enteredUsername);
            }

        }
        else{
            System.out.println("Failed Login on " + " " + enteredUsername);
        }

    }

    @FXML
    private void toRegisterPageBn(ActionEvent event) throws IOException {
        MainApplication.changeScene("RegisterPage.fxml");
    }

}
