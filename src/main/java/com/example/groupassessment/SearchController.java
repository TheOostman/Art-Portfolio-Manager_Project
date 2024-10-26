package com.example.groupassessment;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.scene.control.ListView;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;


public class SearchController {
    @FXML
    private TextField usernameEntry;
    @FXML
    private TextField passwordEntry;
    @FXML
    private VBox sideBar;

    private boolean isSideBarVisible = false;

    // THIS SECTION IS DUMMY STATS AND CAN BE DELETED
    public static boolean isLoggedIn = true;        // Please use this boolean for signing and register !!!!
    public String basicUsername = "123";             //Test data, this can be deleted
    public String basicPassword = "123";                   //Test data, this can be deleted
    //----------------------------------

    @FXML
    private TextField searchField;  // For user input
    @FXML
    private ListView<String> usernamesListView;  // For displaying matched usernames
    @FXML
    private GridPane imagesGridPane;  // For displaying matched images
    // SQLite connection URL
    private final String url = "jdbc:sqlite:users.db";

    // Method to connect to the database
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Method to search for usernames
    private List<String> searchUsernames(String searchText) {
        List<String> matchedUsernames = new ArrayList<>();
        String query = "SELECT username FROM users WHERE username LIKE ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + searchText + "%");  // Use wildcard for partial match
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                matchedUsernames.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println("Error searching usernames: " + e.getMessage());
        }

        return matchedUsernames;
    }

    // Method to search for image titles and descriptions
    private List<Image> searchImages(String searchText) {
        List<Image> matchedImages = new ArrayList<>();
        String query = "SELECT title, image FROM images WHERE title LIKE ? OR description LIKE ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + searchText + "%");  // Use wildcard for partial match
            pstmt.setString(2, "%" + searchText + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Retrieve image data (assuming the image is stored as a BLOB in the database)
                byte[] imgBytes = rs.getBytes("image");
                if (imgBytes != null) {
                    Image image = new Image(new ByteArrayInputStream(imgBytes));
                    matchedImages.add(image);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching images: " + e.getMessage());
        }

        return matchedImages;
    }

    @FXML
    private void searchUsername() {
        String searchText = searchField.getText().toLowerCase();  // Get the search text from the TextField

        // Search for usernames and images
        List<String> matchedUsernames = searchUsernames(searchText);
        List<Image> matchedImages = searchImages(searchText);

        // Update the ListView for usernames
        ObservableList<String> userResults = FXCollections.observableArrayList(matchedUsernames);
        usernamesListView.setItems(userResults);

        // Clear the GridPane before adding new images
        imagesGridPane.getChildren().clear();

        // Add images to the GridPane
        int col = 0;
        int row = 0;
        for (Image image : matchedImages) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100);  // Set a desired width
            imageView.setFitHeight(100);  // Set a desired height
            imageView.setPreserveRatio(true);

            imagesGridPane.add(imageView, col, row);  // Add ImageView to GridPane

            col++;  // Move to the next column
            if (col > 2) {  // Change this number to set how many images per row
                col = 0;  // Reset column count
                row++;  // Move to the next row
            }
        }

        // Clear any previous selection
        usernamesListView.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleUserSelection() {
        String selectedUsername = usernamesListView.getSelectionModel().getSelectedItem(); // Get the selected item
        if (selectedUsername != null) {
            System.out.println("Selected Username: " + selectedUsername);
            // Load the profile view for the selected username
            loadProfileView(selectedUsername);
        }
    }

    private void loadProfileView(String username) {
        try {
            // Load the profile view FXML file (e.g., "ProfileView.fxml")
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewProfilePage.fxml"));
            Parent profileView = loader.load();

            // Pass the selected username to the ProfileController if necessary
            ViewProfileController profileController = loader.getController();
            profileController.setUsername(username);  // Assume you have a setUsername method in ProfileController

            // Create a new scene and set it to the stage
            Scene scene = new Scene(profileView);
            Stage stage = (Stage) usernamesListView.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading profile view: " + e.getMessage());
        }
    }
    // Page Changes
    @FXML
    private void toSignInBn(ActionEvent event) throws IOException {
        MainApplication.changeScene("LoginPage.fxml");
    }

    public void toViewProfile() throws IOException {
        MainApplication.changeScene("ViewProfilePage.fxml");
    }

    public void changeToMain() throws IOException {
        MainApplication.changeScene("MainPage.fxml");
    }

    // Filter button
    public void onFilterClick() {
        // new Stage (window)
        Stage popupStage = new Stage();
        popupStage.setTitle("Filter Options");

        // Set the window to be modal, so it blocks interaction with the main window
        popupStage.initModality(Modality.APPLICATION_MODAL);

        // Create the layout for the popup
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 10; -fx-background-color: #D7F6FD; -fx-border-color: transparent;");

        // filter labels
        Label filterLabel1 = new Label("Art Medium:");
        ComboBox<String> comboBox1 = comboBox1();

        Label filterLabel2 = new Label("Art Style:");
        ComboBox<String> comboBox2 = comboBox2();

        Label filterLabel3 = new Label("Date Created:");
        ComboBox<String> comboBox3 = comboBox3();  // Month
        ComboBox<String> comboBox4 = comboBox4();  // Year
        HBox dateHBox = new HBox(10);
        dateHBox.getChildren().addAll(comboBox3, comboBox4);

        // "Apply" button
        Button applyButton = new Button("Apply");
        applyButton.setOnAction(e -> {
            // Logic to apply the filter (for now, just close the popup)
            String selectedFilter1 = comboBox1.getValue();
            String selectedFilter2 = comboBox2.getValue();
            String selectedFilter3 = comboBox3.getValue();
            String selectedFilter4 = comboBox4.getValue();

            System.out.println("Selected Filter 1: " + selectedFilter1);
            System.out.println("Selected Filter 2: " + selectedFilter2);
            System.out.println("Selected Filter 3: " + selectedFilter3);
            System.out.println("Selected Filter 4: " + selectedFilter4);

            popupStage.close();  // Close the popup after applying the filter
        });

        // components
        layout.getChildren().addAll(filterLabel1, comboBox1, filterLabel2, comboBox2, filterLabel3, dateHBox, applyButton);

        // show the popup
        Scene scene = new Scene(layout, 300, 300);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    // create Art medium options
    private ComboBox<String> comboBox1() {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Oil Painting", "Water Colours", "Photography", "Digital Art", "Painting", "Drawing", "Graphite"
        );
        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.setValue("Select an option");
        return comboBox;
    }
    // create Art style options
    private ComboBox<String> comboBox2() {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Pop Art", "Realism", "Impression", "Abstract", "Surrealism", "Expressionism"
        );
        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.setValue("Select an option");
        return comboBox;
    }
    // create month options
    private ComboBox<String> comboBox3() {
        ObservableList<String> options = FXCollections.observableArrayList();
        for (int month = 1; month <= 12; month++) {
            options.add(String.format("%02d", month));
        }

        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.setValue("Month");
        return comboBox;
    }
    // create year options
    private ComboBox<String> comboBox4() {
        ObservableList<String> options = FXCollections.observableArrayList();
                // Generate years from 2001 to 2024
        for (int year = 2001; year <= 2024; year++) {
            options.add(String.valueOf(year));
        }
        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.setValue("Year");
        return comboBox;
    }

    @FXML
    private void registerInfo() {

    }

    @FXML
    private void dropDownBn(ActionEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(300), sideBar);
        if (isSideBarVisible) {
            transition.setToX(200);  // Slide out to hide
            sideBar.setVisible(false);
        } else {
            sideBar.setVisible(true);
            sideBar.toFront();
            transition.setToX(0);  // Slide in to show
        }
        transition.play();
        isSideBarVisible = !isSideBarVisible;
    }

    @FXML
    public void initialize() {
        sideBar.setVisible(false);
    }
}
