package com.example.groupassessment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class comments {
    @FXML
    private TextField usernameEntry;
    @FXML
    private TextArea commentEntry;
    @FXML
    private ComboBox<String> imageTitleComboBox;
    @FXML
    private Label messageLabel;

    // SQLite connection URL
    private final String url = "jdbc:sqlite:comments.db";

    @FXML
    public void initialize() {
        // ComboBox with image titles (for example)
        ObservableList<String> imageTitles = FXCollections.observableArrayList(
                "Image 1", "Image 2", "Image 3" // Populate with actual image titles
        );
        imageTitleComboBox.setItems(imageTitles);
        imageTitleComboBox.setValue("Select an image");
    }

    private List<String> getComments(String imageTitle) {
        List<String> comments = new ArrayList<>();
        String sql = "SELECT username, comment FROM comments WHERE image_title = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, imageTitle);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                String comment = rs.getString("comment");
                comments.add(username + ": " + comment);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving comments: " + e.getMessage());
        }
        return comments;
    }

    private void saveComment(String imageTitle, String username, String comment) {
        String sql = "INSERT INTO comments(image_title, username, comment) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, imageTitle);
            pstmt.setString(2, username);
            pstmt.setString(3, comment);

            pstmt.executeUpdate();
            messageLabel.setText("Comment added successfully.");
        } catch (SQLException e) {
            System.out.println("Error saving comment: " + e.getMessage());
        }
    }

    @FXML
    private void addComment(ActionEvent event) {
        String username = usernameEntry.getText();
        String comment = commentEntry.getText();
        String imageTitle = imageTitleComboBox.getValue();

        // Check for empty fields
        if (username.isEmpty() || comment.isEmpty() || imageTitle == null || imageTitle.equals("Select an image")) {
            messageLabel.setText("All fields must be filled.");
            return;
        }

        // Save comment to database
        saveComment(imageTitle, username, comment);

        // Clear the fields after saving
        usernameEntry.clear();
        commentEntry.clear();
        imageTitleComboBox.setValue("Select an image");
    }

    @FXML
    private void viewComments(ActionEvent event) {
        String imageTitle = imageTitleComboBox.getValue();

        // Check for a selected image title
        if (imageTitle == null || imageTitle.equals("Select an image")) {
            messageLabel.setText("Please select an image to view comments.");
            return;
        }

        // Retrieve comments from the database
        List<String> comments = getComments(imageTitle);

        if (comments.isEmpty()) {
            messageLabel.setText("No comments available for this image.");
        } else {
            StringBuilder commentsDisplay = new StringBuilder();
            for (String comment : comments) {
                commentsDisplay.append(comment).append("\n");
            }
            messageLabel.setText(commentsDisplay.toString());
        }
    }
}
