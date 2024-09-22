package com.example.groupassessment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField usernameEntry;
    @FXML
    private TextField passwordEntry;
    @FXML
    private TextField emailAddressEntry;
    @FXML
    private TextField roleEntry;



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

    private void checkUserFolderExists() {
        File dir = new File("Users");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void saveUserInfo(String username, String password, String emailAddress, String role) {
        checkUserFolderExists();

        // Create a folder for the user
        File userDir = new File("Users", username);
        if (!userDir.exists()) {
            userDir.mkdirs();
        }

        // Save user info in a file named info.txt inside the user's folder
        File infoFile = new File(userDir, "info.txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(infoFile))) {
            writer.println("Username: " + username);
            writer.println("Password: " + password);
            writer.println("Email: " + emailAddress);
            writer.println("Role: " + role);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void registerInfo() {
        String username = usernameEntry.getText();
        String password = passwordEntry.getText();
        String emailAddress = emailAddressEntry.getText();
        String role = roleEntry.getText();

        saveUserInfo(username, password, emailAddress, role);
    }







}

