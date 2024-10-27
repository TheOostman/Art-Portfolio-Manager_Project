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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.groupassessment.DatabaseManager.connect;

public class ViewProfileController {
    @FXML
    private VBox sideBar;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private HBox imagesContainer; // VBox to hold images dynamically

    private boolean isSideBarVisible = false;

    // SQLite connection URL
    private final String url = "jdbc:sqlite:users.db";

    private int userID; // This should be set with the logged-in user's ID

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

    // Method to set user information
    public void setUserInfo(String username, String role) {
        usernameLabel.setText(username);
        roleLabel.setText(role);
        loadUserImages(username);
    }

    // Load user images into the imagesContainer
    private void loadUserImages(String username) {
        List<ImageData> userImages = grabUserImages(username);
        imagesContainer.getChildren().clear(); // Clear previous images

        for (ImageData imageData : userImages) {
            ImageView imageView = new ImageView(imageData.getImage());
            imageView.setFitWidth(200); // Set width of the image view
            imageView.setFitHeight(200); // Optionally set height if needed
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true); // Enables smoother rendering
            imageView.setOnMouseClicked(event -> openImagePopup(imageData)); // Open popup on click

            // Add margin for spacing
            HBox.setMargin(imageView, new Insets(0, 10, 0, 10)); // Adjust margins as necessary

            imagesContainer.getChildren().add(imageView); // Add image view to container
        }

        if (userImages.isEmpty()) {
            Label noImagesLabel = new Label("No images to display.");
            imagesContainer.getChildren().add(noImagesLabel);
        }
    }


    // Grab user images from the database
    private List<ImageData> grabUserImages(String username) {
        List<ImageData> userImages = new ArrayList<>();
        String query = """
                SELECT images.title, images.description, images.image 
                FROM images 
                JOIN users ON images.user_id = users.id 
                WHERE users.username = ?
                """;

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                byte[] imgBytes = rs.getBytes("image");
                String title = rs.getString("title");
                String description = rs.getString("description");

                if (imgBytes != null) {
                    Image image = new Image(new ByteArrayInputStream(imgBytes));
                    userImages.add(new ImageData(image, title, description, username, null)); // role is not needed
                }
            }
        } catch (SQLException e) {
            System.out.println("Error grabbing user images: " + e.getMessage());
        }

        return userImages;
    }

    // Open image in a pop-up
    private void openImagePopup(ImageData imageData) {
        Stage imageStage = new Stage();
        imageStage.setTitle(imageData.getTitle());

        ImageView fullImageView = new ImageView(imageData.getImage());
        fullImageView.setFitWidth(500);
        fullImageView.setPreserveRatio(true);

        Label titleLabel = new Label("Title: " + imageData.getTitle());
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label userLabel = new Label("User: " + imageData.getUsername());
        Label descriptionLabel = new Label("Description: " + imageData.getDescription());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(titleLabel, userLabel, fullImageView, descriptionLabel);

        Scene scene = new Scene(layout, 700, 900);
        imageStage.setScene(scene);
        imageStage.initModality(Modality.APPLICATION_MODAL);
        imageStage.showAndWait();
    }

    // Side bar toggle
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

    // Navigation methods
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

    // ImageData class
    public class ImageData {
        private Image image;
        private String title;
        private String description;
        private String username;

        public ImageData(Image image, String title, String description, String username, String role) {
            this.image = image;
            this.title = title;
            this.description = description;
            this.username = username;
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
    }
}
