package com.example.groupassessment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField usernameEntry;
    @FXML
    private TextField passwordEntry;
    @FXML
    private TextField emailAddressEntry;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private Label messageLabel;



    @FXML
    public void initialize() {
        // comboBox with role options
        ObservableList<String> roles = FXCollections.observableArrayList(
                "Artist", "Recruiter", "Community Member"
        );
        roleComboBox.setItems(roles);
        roleComboBox.setValue("Select a role");
    }

    // change to sign in if registration successful
    @FXML
    private void toSignInBn(ActionEvent event) throws IOException {
        MainApplication.changeScene("LoginPage.fxml");
    }

    // SQLite connection URL
    private final String url = "jdbc:sqlite:users.db";

    private boolean isUsernameTaken(String username) {
        String sql = "SELECT username FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if a matching username is found
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean isEmailValid(String email) {
        // email pattern check
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private void saveUserInfo(String username, String password, String email, String role) {
        String sql = "INSERT INTO users(username, password, email, role) VALUES(?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.setString(4, role);

            pstmt.executeUpdate();
            messageLabel.setText("Registration successful! Go to ");
            messageLabel.setText(messageLabel.getText() + "Sign In");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void registerInfo() {
        String username = usernameEntry.getText();
        String password = passwordEntry.getText();
        String email = emailAddressEntry.getText();
        String role = roleComboBox.getValue();

        // Check for empty fields
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            messageLabel.setText("Registration incomplete. All fields must be filled.");
            return;
        }

        // username uniqueness
        if (isUsernameTaken(username)) {
            messageLabel.setText("Username already in use.");
            return;
        }

        // email format
        if (!isEmailValid(email)) {
            messageLabel.setText("Email invalid.");
            return;
        }

        // must select role
        if (role == null || role.equals("Select a role")) {
            messageLabel.setText("Must select a role.");
            return;
        }

        // Save user info and confirm registration
        saveUserInfo(username, password, email, role);
    }

}

