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

    private static Connection connect() {
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
