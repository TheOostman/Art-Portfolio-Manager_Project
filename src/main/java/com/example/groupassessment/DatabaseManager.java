package com.example.groupassessment;

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
                + "    image_id TEXT NOT NULL,\n"
                + "    user_id INTEGER REFERENCES users(id),\n"
                + "    image BLOB,\n"
                + "    title TEXT,\n"
                + "    description TEXT\n"
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
    public Map<String, byte[]> getUserImages(int userId) {
        Map<String, byte[]> userImages = new HashMap<>();
        String query = "SELECT image_id, image FROM images WHERE user_id = ?";

        try (Connection conn = connect(); PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String imageId = rs.getString("image_id");
                byte[] imageData = rs.getBytes("image");

                if (imageData != null) {
                    userImages.put(imageId, imageData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userImages;
    }
    public Map<String, byte[]> getUserImagesForEditPage(int userId, String imageID) {
        Map<String, byte[]> userImagesEditPage = new HashMap<>();
        String query = "SELECT image_id, image FROM images WHERE user_id = ? AND image_id = ?";

        try (Connection conn = connect(); PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setString(2, imageID);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String imageId = rs.getString("image_id");
                byte[] imageData = rs.getBytes("image");

                if (imageData != null) {
                    userImagesEditPage.put(imageId, imageData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userImagesEditPage;
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
        try {
            // First, delete the existing image if it exists
            if (deleteExistingImage(imageId, userId)) {
                System.out.println("Previous image deleted successfully.");
            }

            // Insert the new image
            String query = "INSERT INTO images (user_id, image_id, image) VALUES (?, ?, ?)";
            try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, userId);
                pstmt.setString(2, imageId);
                pstmt.setBytes(3, imageData);
                pstmt.executeUpdate();
                System.out.println("New image saved successfully for user: " + userId);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error while deleting existing image: " + e.getMessage());
        }
    }

    public boolean deleteImageFromDatabase(String imageId, int userId) {
        String query = "DELETE FROM images WHERE image_id = ? AND user_id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, imageId);
            pstmt.setInt(2, userId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting image from database: " + e.getMessage());
            return false;
        }
    }

    public String getUsernameDB(int userId) {
        String query = "SELECT username FROM users WHERE id = ?";
        String username = null;

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                username = rs.getString("username");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching username from database: " + e.getMessage());
        }

        return username;
    }

    // Update the title and comments of an image in the database
    public void updateImageMetadata(int userId, String imageId, String newTitle, String newDescription) {
        String query = "UPDATE images SET title = ?, description = ? WHERE user_id = ? AND image_id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newTitle);
            pstmt.setString(2, newDescription);
            pstmt.setInt(3, userId);
            pstmt.setString(4, imageId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Image metadata updated successfully for imageId: " + imageId);
            } else {
                System.out.println("No image found to update for imageId: " + imageId);
            }
        } catch (SQLException e) {
            System.out.println("Error updating image metadata: " + e.getMessage());
        }
    }

    // Method to get the title of an image from the database
    public String getImageTitle(int userId, String imageId) {
        String title = null;
        String query = "SELECT title FROM images WHERE user_id = ? AND image_id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, imageId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                title = rs.getString("title"); // Get title from result set
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving image title: " + e.getMessage());
        }
        return title; // Return the retrieved title
    }

    // Method to get the description of an image from the database
    public String getImageDescription(int userId, String imageId) {
        String description = null;
        String query = "SELECT description FROM images WHERE user_id = ? AND image_id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, imageId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                description = rs.getString("description"); // Get description from result set
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving image description: " + e.getMessage());
        }
        return description; // Return the retrieved description
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
                    File imageFile = File.createTempFile(validPrefix, ".png");
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
