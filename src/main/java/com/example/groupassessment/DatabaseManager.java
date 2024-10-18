package com.example.groupassessment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

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

    public void saveImage(String imageId, byte[] imageData, int userId) throws SQLException {
        String sql = "INSERT INTO images (image_id, image, user_id) VALUES (?, ?, ?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, imageId);
            pstmt.setBytes(2, imageData);
            pstmt.setInt(3, userId); // associate the image with the user
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static byte[] getImageFromDatabase(String imageId) {
        String query = "SELECT image FROM images WHERE image_id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, imageId);
            ResultSet rs = pstmt.executeQuery();

            // If an image exists, return it as a byte array
            if (rs.next()) {
                return rs.getBytes("image");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;  // No image found
    }
}
