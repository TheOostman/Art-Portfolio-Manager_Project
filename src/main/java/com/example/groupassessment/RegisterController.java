package com.example.groupassessment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField usernameEntry;
    @FXML
    private TextField passwordEntry;



    //Entered Info Saved (Save the info into UserData file)
    //Maybe make folder
    //
    // We also need ID for each person
    // -How to do this-
    // Count the amount of users stroed in file
    // add 1 and make that the ID.                  (eg. files: adam, max. Thats 2 people. Therfore if adding another it will have id of 3.)
    // ---------



    // THIS SECTION IS DUMMY STATS AND CAN BE DELETED
    public static boolean isLoggedIn = true;        // Please use this boolean for signing and register !!!!
    public String basicUsername = "123";             //Test data, this can be deleted
    public String basicPassword = "123";                   //Test data, this can be deleted
    //----------------------------------

    public void changeToMain() throws IOException{
        MainApplication.changeScene("MainPage.fxml");
    }

    @FXML
    private void toSignInBn(ActionEvent event) throws IOException {
        MainApplication.changeScene("LoginPage.fxml");
    }

    @FXML
    private void registerInfo() {

    }


}

