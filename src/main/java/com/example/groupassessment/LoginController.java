package com.example.groupassessment;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.*;

import javafx.scene.control.Label;
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
    @FXML
    private Label feedbackLabel;


    public void registerUser(String username, String password, Connection connection) throws SQLException {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        statement.executeUpdate();
    }

    public void changeToMain() throws IOException{
        MainApplication.changeScene("MainPage.fxml");
    }

    @FXML
    private void toRegisterPageBn(ActionEvent event) throws IOException {
        MainApplication.changeScene("RegisterPage.fxml");
    }

    // SQLite connection URL
    private final String url = "jdbc:sqlite:users.db";

    // Verify credentials
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
    public int getUserId(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        int userId = -1; // Default value if user is not found

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the username in the query
            pstmt.setString(1, username);

            // Execute the query and retrieve the userId
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return userId;
    }

    @FXML
    protected void signInBn() {
        String enteredUsername = usernameEntry.getText();
        String enteredPassword = passwordEntry.getText();

        if (verifyCredentials(enteredUsername, enteredPassword)) {
            System.out.println("Login successful for user: " + enteredUsername);
            feedbackLabel.setText("");  // Clear feedback message

            // Write current user data immediately
            int userId = getUserId(enteredUsername);
            writeUserSessionData(userId);

            // Change scene after successfully saving user session
            try {
                changeToMain();
            } catch (IOException e) {
                System.out.println("Error navigating to main page: " + e.getMessage());
            }
        } else {
            System.out.println("Failed login attempt for user: " + enteredUsername);
            feedbackLabel.setText("Invalid username or password");  // Set error message
        }
    }

    // Helper method to handle writing session data
    private void writeUserSessionData(int userId) {
        File dir = new File("src/main/resources/userData");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File outputFile = new File(dir, "UserData.txt");

        // Ensure file is overwritten each time
        try (FileWriter writer = new FileWriter(outputFile, false)) {
            writer.write("User ID: " + userId);
            System.out.println("User ID saved to: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error saving user session data: " + e.getMessage());
        }
    }
}

