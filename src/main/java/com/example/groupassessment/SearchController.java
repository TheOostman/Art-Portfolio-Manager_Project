package com.example.groupassessment;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.IOException;

public class SearchController {
    @FXML
    private VBox sideBar;
    @FXML
    private TextField searchField;
    @FXML
    private ListView<String> usernamesListView;
    @FXML
    private GridPane imagesGridPane;

    private boolean isSideBarVisible = false;
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

    // search for usernames
    private List<String> searchUsernames(String searchText) {
        List<String> matchedUsernames = new ArrayList<>();
        String query = "SELECT username FROM users WHERE username LIKE ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + searchText + "%"); //wildcard
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                matchedUsernames.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println("Error searching usernames: " + e.getMessage());
        }

        return matchedUsernames;
    }

    public class ImageData {
        private Image image;
        private String title;
        private String description;
        private String username;
        private String role;

        public ImageData(Image image, String title, String description, String username, String role) {
            this.image = image;
            this.title = title;
            this.description = description;
            this.username = username;
            this.role = role;
        }

        public Image getImage() {
            return image;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getUsername() {
            return username;
        }

        public String getRole() {
            return role;
        }
    }
    private List<ImageData> searchImages(String searchText) {
        List<ImageData> matchedImages = new ArrayList<>();
        String query = """
        SELECT images.title, images.description, images.image, users.username, users.role 
        FROM images 
        JOIN users ON images.user_id = users.id 
        WHERE images.title LIKE ? OR images.description LIKE ?
        """;

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + searchText + "%");
            pstmt.setString(2, "%" + searchText + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                byte[] imgBytes = rs.getBytes("image");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String username = rs.getString("username");
                String role = rs.getString("role");

                if (imgBytes != null) {
                    Image image = new Image(new ByteArrayInputStream(imgBytes));
                    matchedImages.add(new ImageData(image, title, description, username, role));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching images: " + e.getMessage());
        }

        return matchedImages;
    }

    @FXML
    private void searchUsername() {
        String searchText = searchField.getText().toLowerCase();
        List<String> matchedUsernames = searchUsernames(searchText);
        List<ImageData> matchedImages = searchImages(searchText);

        // Update the ListView for usernames
        ObservableList<String> userResults = FXCollections.observableArrayList(matchedUsernames);
        if (matchedUsernames.isEmpty()) {
            userResults.add("No Usernames Found");
        }
        usernamesListView.setItems(userResults);

        // Clear the GridPane before adding new images
        imagesGridPane.getChildren().clear();

        if (matchedImages.isEmpty()) {
            // Display "No Titles Found" if no images were found
            Label noImagesLabel = new Label("No Titles Found");

            imagesGridPane.add(noImagesLabel, 0, 0);
        } else {
            // Add images to the GridPane if any matched images were found
            int col = 0;
            int row = 0;
            for (ImageData imageData : matchedImages) {
                ImageView imageView = new ImageView(imageData.getImage());
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setPreserveRatio(true);

                // Open image popup with title, description, username, and role
                imageView.setOnMouseClicked(event -> OpenImage(
                        imageData.getImage(),
                        imageData.getTitle(),
                        imageData.getDescription(),
                        imageData.getUsername(),
                        imageData.getRole()
                ));

                imagesGridPane.add(imageView, col, row);

                col++;
                if (col > 2) {
                    col = 0;
                    row++;
                }
            }
        }
        // Clear any previous selection
        usernamesListView.getSelectionModel().clearSelection();
    }
    private String getUserRole(String username) {
        String role = null;
        String query = "SELECT role FROM users WHERE username = ?"; // Example query

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                role = rs.getString("role");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user role: " + e.getMessage());
        }

        return role;
    }

    @FXML
    private void handleUserSelection() {
        String selectedUsername = usernamesListView.getSelectionModel().getSelectedItem();
        String userRole = getUserRole(selectedUsername);

        if (selectedUsername != null && userRole != null) {
            System.out.println("Selected Username: " + selectedUsername + ", Role: " + userRole);
            // Load the profile view for the selected username
            loadProfileView(selectedUsername, userRole);
        }
    }

    private void loadProfileView(String username, String role) {
        try {
            // Load the profile view FXML file (e.g., "ViewProfilePage.fxml")
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewProfilePage.fxml"));
            Parent profileView = loader.load();

            // Pass the selected username and role to the ProfileController
            ViewProfileController profileController = loader.getController();
            profileController.setUserInfo(username, role);  // Updated to use setUserInfo method

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

    // pop up view image
    private void OpenImage(Image image, String title, String comments, String username, String role) {
        Stage imageStage = new Stage();
        imageStage.setTitle(title);

        ImageView fullImageView = new ImageView(image);
        fullImageView.setFitWidth(500);
        fullImageView.setPreserveRatio(true);

        Label titleLabel = new Label("Title: " + title);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label userLabel = new Label("User: " + username);
        Label roleLabel = new Label("Role: " + role);

        TextArea commentsArea = new TextArea(comments);
        commentsArea.setWrapText(true);
        commentsArea.setEditable(false);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(titleLabel, userLabel, roleLabel, fullImageView, commentsArea);

        Scene scene = new Scene(layout, 700, 900);
        imageStage.setScene(scene);
        imageStage.initModality(Modality.APPLICATION_MODAL);
        imageStage.showAndWait();
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
    // Page Changes
    @FXML
    public void changeToMain() throws IOException {
        MainApplication.changeScene("MainPage.fxml");
    }
    public void toSearchPage() throws IOException {
        MainApplication.changeScene("ProfileSearch.fxml");
    }
    public void toFeedPage() throws IOException {
        MainApplication.changeScene("FeedPage.fxml");
    }
    public void changeToInbox() throws IOException {
        MainApplication.changeScene("InboxPage.fxml");
    }
    public void toSignInBn() throws IOException {
        MainApplication.changeScene("LoginPage.fxml");
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
