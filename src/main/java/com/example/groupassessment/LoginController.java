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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usernameEntry;
    @FXML
    private TextField passwordEntry;

    public void changeToMain() throws IOException{
        MainApplication.changeScene("MainPage.fxml");
    }

    @FXML
    private void toRegisterPageBn(ActionEvent event) throws IOException {
        MainApplication.changeScene("RegisterPage.fxml");
    }

    // SQLite connection URL
    private final String url = "jdbc:sqlite:users.db";

    // Verify the entered credentials by querying the database
    private boolean verifyCredentials(String username, String password) {
        String sql = "SELECT password FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the username parameter
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return password.equals(storedPassword);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
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

}

