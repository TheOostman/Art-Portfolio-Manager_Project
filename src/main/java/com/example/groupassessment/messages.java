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

public class messages {
    @FXML
    private TextField senderEntry;
    @FXML
    private TextField recipientEntry;
    @FXML
    private TextArea messageContentEntry;
    @FXML
    private ComboBox<String> recipientComboBox;
    @FXML
    private Label messageLabel;

    // SQLite connection URL
    private final String url = "jdbc:sqlite:messages.db";

    @FXML
    public void initialize() {
        // ComboBox with recipient usernames (for example)
        ObservableList<String> recipients = FXCollections.observableArrayList(
                "User1", "User2", "User3" // Populate with actual usernames
        );
        recipientComboBox.setItems(recipients);
        recipientComboBox.setValue("Select a recipient");
    }

    private List<String> getMessages(String recipient) {
        List<String> messages = new ArrayList<>();
        String sql = "SELECT sender, message_content FROM messages WHERE recipient = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, recipient);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String sender = rs.getString("sender");
                String message = rs.getString("message_content");
                messages.add(sender + ": " + message);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving messages: " + e.getMessage());
        }
        return messages;
    }

    private void saveMessage(String sender, String recipient, String messageContent) {
        String sql = "INSERT INTO messages(sender, recipient, message_content) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sender);
            pstmt.setString(2, recipient);
            pstmt.setString(3, messageContent);

            pstmt.executeUpdate();
            messageLabel.setText("Message sent successfully.");
        } catch (SQLException e) {
            System.out.println("Error saving message: " + e.getMessage());
        }
    }

    @FXML
    private void sendMessage(ActionEvent event) {
        String sender = senderEntry.getText();
        String recipient = recipientComboBox.getValue();
        String messageContent = messageContentEntry.getText();

        // Check for empty fields
        if (sender.isEmpty() || recipient == null || recipient.equals("Select a recipient") || messageContent.isEmpty()) {
            messageLabel.setText("All fields must be filled.");
            return;
        }

        // Save message to database
        saveMessage(sender, recipient, messageContent);

        // Clear the fields after sending
        senderEntry.clear();
        recipientComboBox.setValue("Select a recipient");
        messageContentEntry.clear();
    }

    @FXML
    private void viewMessages(ActionEvent event) {
        String recipient = recipientComboBox.getValue();

        // Check for a selected recipient
        if (recipient == null || recipient.equals("Select a recipient")) {
            messageLabel.setText("Please select a recipient to view messages.");
            return;
        }

        // Retrieve messages from the database
        List<String> messages = getMessages(recipient);

        if (messages.isEmpty()) {
            messageLabel.setText("No messages available for this recipient.");
        } else {
            StringBuilder messagesDisplay = new StringBuilder();
            for (String message : messages) {
                messagesDisplay.append(message).append("\n");
            }
            messageLabel.setText(messagesDisplay.toString());
        }
    }
}
