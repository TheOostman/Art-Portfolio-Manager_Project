package com.example.groupassessment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
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

    //Search for login info
    //Confirm there is info
    //If info correct move to changeToMain()
    //Keep Login Info

    public void changeToMain() throws IOException{
        MainApplication.changeScene("MainPage.fxml");
    }

    @FXML
    protected void signInBn() {
        String enteredUsername = usernameEntry.getText();
        String enteredPassword = passwordEntry.getText();

        if (verifyCredentials(enteredUsername, enteredPassword)) {
            System.out.println("Login successful for user: " + enteredUsername);
            try {
                changeToMain(); // Navigate to the main page
            } catch (IOException e) {
                System.out.println("Error navigating to main page");
            }
        } else {
            System.out.println("Failed login attempt for user: " + enteredUsername);
        }
    }

    private boolean verifyCredentials(String username, String password) {
        File userDir = new File("Users", username); // Folder for the user
        File infoFile = new File(userDir, "info.txt"); // File containing user info

        if (infoFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(infoFile))) {
                String line;
                String savedPassword = null;

                // Read the file line by line and extract the password
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Password: ")) {
                        savedPassword = line.substring(10); // Extract password value
                    }
                }

                // Check if the entered password matches the saved password
                return savedPassword != null && savedPassword.equals(password);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No user found with username: " + username);
        }
        return false; // Return false if the file doesn't exist or passwords don't match
    }

    @FXML
    private void toRegisterPageBn(ActionEvent event) throws IOException {
        MainApplication.changeScene("RegisterPage.fxml");
    }

}

