package com.example.groupassessment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseManager {

    // SQLite database URL
    private static final String url = "jdbc:sqlite:users.db";

    // Method to connect to the database and create the users table
    public static void createUsersTable() {
        // SQL command to create the users table if it doesn't exist
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    username TEXT UNIQUE NOT NULL,\n"
                + "    password TEXT NOT NULL,\n"
                + "    email TEXT NOT NULL,\n"
                + "    role TEXT NOT NULL\n"
                + ");";

        // Establish connection and execute the SQL command
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            // Execute the SQL command
            stmt.execute(sql);
            System.out.println("Users table created successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Call the method to create the table
        createUsersTable();
    }
}
