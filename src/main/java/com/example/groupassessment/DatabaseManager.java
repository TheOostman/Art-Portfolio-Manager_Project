package com.example.groupassessment;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {

    // SQLite database URL
    private static final String url = "jdbc:sqlite:users.db";

    public boolean deleteExistingImage(String imageId, int userId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = connect();

            // First, check if the image exists
            String query = "SELECT COUNT(*) FROM images WHERE image_id = ? AND user_id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, imageId);
            pstmt.setInt(2, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Image exists, proceed to delete
                String deleteSql = "DELETE FROM images WHERE image_id = ? AND user_id = ?";
                pstmt = conn.prepareStatement(deleteSql);
                pstmt.setString(1, imageId);
                pstmt.setInt(2, userId);
                pstmt.executeUpdate();
                return true;  // Image was deleted
            }

            return false;  // Image does not exist
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    public Map<String, byte[]> getUserImagesByLocation(int userID) {
        Map<String, byte[]> userImages = new HashMap<>();
        String query = "SELECT image_id, image FROM images WHERE user_id = ?";

        try (Connection conn = connect(); PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, userID);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String imageId = rs.getString("image_id");
                byte[] imageData = rs.getBytes("image");
                userImages.put(imageId, imageData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userImages;
    }

    // Method to connect to the database and create the users and images tables
    public static void createTables() {
        // SQL command to create the users table if it doesn't exist
        String usersTableSql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    username TEXT UNIQUE NOT NULL,\n"
                + "    password TEXT NOT NULL,\n"
                + "    email TEXT NOT NULL,\n"
                + "    role TEXT NOT NULL\n"
                + ");";

        // SQL command to create the images table if it doesn't exist
        String imagesTableSql = "CREATE TABLE IF NOT EXISTS images (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    image_id TEXT UNIQUE NOT NULL,\n"
                + "    user_id INTEGER REFERENCES users(id),\n"
                + "    image BLOB\n"
                + ");";

        // Establish connection and execute the SQL commands
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // Execute the SQL commands
            stmt.execute(usersTableSql);
            stmt.execute(imagesTableSql);
            System.out.println("Users and Images tables created successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    // Retrieve user images as a Map with JavaFX Image
    public Map<String, Image> getUserImages(int userID) {
        Map<String, Image> userImages = new HashMap<>();
        String query = "SELECT image_id, image FROM images WHERE user_id = ?";

        try (Connection conn = connect(); PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, userID);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String imageId = rs.getString("image_id");
                byte[] imageData = rs.getBytes("image");

                if (imageData != null) {
                    // Convert byte array to InputStream
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                    // Create JavaFX Image object
                    Image image = new Image(bis);
                    userImages.put(imageId, image);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userImages;
    }

    public static void main(String[] args) {
        // Call the method to create the tables
        createTables();
    }

    public static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:users.db";  // Adjust to your database path
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void saveImage(int userId, String imageId, byte[] imageData) {
        String query = "INSERT INTO images (user_id, image_id, image) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, imageId);
            pstmt.setBytes(3, imageData); // Set the byte array as a BLOB
            pstmt.executeUpdate();
            System.out.println("Image saved successfully for user: " + userId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Map<String, File> getUserImagesAsFiles(int userID) {
        Map<String, File> userImages = new HashMap<>();
        System.out.println("Database UserID: "+userID);
        String query = "SELECT image_id, image FROM images WHERE user_id = ?";

        try (Connection conn = connect(); PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, userID);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String imageId = rs.getString("image_id");
                byte[] imageData = rs.getBytes("image");

                if (imageData != null) {
                    String validPrefix = imageId.length() < 3 ? imageId + "_x" : imageId; // Ensure prefix is >= 3 characters
                    File imageFile = File.createTempFile(validPrefix, ".png"); // Create a temporary file for each image
                    try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                        fos.write(imageData);
                    }
                    userImages.put(imageId, imageFile);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return userImages;
    }
}
