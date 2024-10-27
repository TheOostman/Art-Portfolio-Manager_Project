package com.example.groupassessment;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class likes {
    @FXML
    private GridPane imagesGridPane;

    private final String url = "jdbc:sqlite:users.db";

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private int getLikeCount(int imageId) {
        String query = "SELECT like_count FROM images WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, imageId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("like_count");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving like count: " + e.getMessage());
        }
        return 0;
    }

    private void updateLikeCount(int imageId, boolean isLiked) {
        String query = "UPDATE images SET like_count = like_count + ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, isLiked ? 1 : -1);
            pstmt.setInt(2, imageId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating like count: " + e.getMessage());
        }
    }

    @FXML
    public void displayImages() {
        imagesGridPane.getChildren().clear();
        String query = "SELECT id, title, image, like_count FROM images";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            int col = 0;
            int row = 0;
            while (rs.next()) {
                int imageId = rs.getInt("id");
                String title = rs.getString("title");
                Image img = new Image(rs.getBinaryStream("image"));
                int likeCount = rs.getInt("like_count");

                ImageView imageView = new ImageView(img);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setPreserveRatio(true);

                Label titleLabel = new Label(title);
                Label likeLabel = new Label("Likes: " + likeCount);

                imageView.setOnMouseClicked(event -> toggleLike(imageId, likeLabel));

                GridPane.setConstraints(imageView, col, row);
                GridPane.setConstraints(titleLabel, col, row + 1);
                GridPane.setConstraints(likeLabel, col, row + 2);

                imagesGridPane.getChildren().addAll(imageView, titleLabel, likeLabel);

                col++;
                if (col > 2) {
                    col = 0;
                    row += 3;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error displaying images: " + e.getMessage());
        }
    }

    private void toggleLike(int imageId, Label likeLabel) {
        String query = "SELECT user_liked FROM image_likes WHERE image_id = ? AND user_id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, imageId);
            pstmt.setInt(2, getCurrentUserId());
            ResultSet rs = pstmt.executeQuery();
            boolean isLiked = rs.next();

            if (isLiked) {
                removeLike(imageId);
            } else {
                addLike(imageId);
            }

            int updatedLikeCount = getLikeCount(imageId);
            likeLabel.setText("Likes: " + updatedLikeCount);
        } catch (SQLException e) {
            System.out.println("Error toggling like: " + e.getMessage());
        }
    }

    private void addLike(int imageId) {
        String insertLike = "INSERT INTO image_likes (image_id, user_id, user_liked) VALUES (?, ?, 1)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(insertLike)) {
            pstmt.setInt(1, imageId);
            pstmt.setInt(2, getCurrentUserId());
            pstmt.executeUpdate();
            updateLikeCount(imageId, true);
        } catch (SQLException e) {
            System.out.println("Error adding like: " + e.getMessage());
        }
    }

    private void removeLike(int imageId) {
        String deleteLike = "DELETE FROM image_likes WHERE image_id = ? AND user_id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(deleteLike)) {
            pstmt.setInt(1, imageId);
            pstmt.setInt(2, getCurrentUserId());
            pstmt.executeUpdate();
            updateLikeCount(imageId, false);
        } catch (SQLException e) {
            System.out.println("Error removing like: " + e.getMessage());
        }
    }

    private int getCurrentUserId() {
        // Placeholder method to get the current user ID
        return 1;  // Replace with actual current user ID retrieval
    }
}
