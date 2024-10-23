package com.example.groupassessment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    public void initialize() {
        // Set up ComboBox with role options
        ObservableList<String> roles = FXCollections.observableArrayList(
                "Artist", "Recruiter", "Community Member"
        );
        roleComboBox.setItems(roles);
        roleComboBox.setValue("Select a role");
    }

    public void changeToMain() throws IOException{
        MainApplication.changeScene("MainPage.fxml");
    }

    @FXML
    private void toSignInBn(ActionEvent event) throws IOException {
        MainApplication.changeScene("LoginPage.fxml");
    }

    // SQLite connection URL
    private final String url = "jdbc:sqlite:users.db";

    private void saveUserInfo(String username, String password, String email, String role) {
        String sql = "INSERT INTO users(username, password, email, role) VALUES(?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.setString(4, role);

            pstmt.executeUpdate();
            System.out.println("User registered successfully!");

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

        if (role == null || role.equals("Select a role")) {
            System.out.println("Please select a valid role.");
            return;
        }

        saveUserInfo(username, password, email, role);
    }

}

