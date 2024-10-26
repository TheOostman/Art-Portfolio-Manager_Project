package com.example.groupassessment;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class Feed {

    @FXML
    private VBox feedContainer; // The container where posts will be dynamically added

    @FXML
    private TextField inputField;

    private File selectedImageFile; // To store the selected image

    // Handle the "Add Image" button
    @FXML
    protected void handleAddImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        selectedImageFile = fileChooser.showOpenDialog(null);
    }

    // Add a new post to the VBox feedContainer
    @FXML
    protected void handleAddPost(ActionEvent event) {
        String postContent = inputField.getText();

        // If there's text, create a new post
        if (!postContent.isEmpty()) {
            // Create a VBox to represent a single post (comment -> image -> buttons)
            VBox post = new VBox(10); // 10px spacing between elements
            post.setAlignment(Pos.CENTER); // Center everything inside the VBox

            // Add the post text (comment)
            TextField postText = new TextField(postContent);
            postText.setEditable(false);
            postText.setStyle("-fx-font-size: 14px; -fx-alignment: center;"); // Center text alignment

            // Add the post image if one is selected
            if (selectedImageFile != null) {
                Image image = new Image(selectedImageFile.toURI().toString(), 300, 300, true, true); // Resize image
                ImageView imageView = new ImageView(image);
                post.getChildren().add(imageView); // Add the image below the comment
            }

            // Create an HBox for the buttons (Like and Request Use)
            HBox buttonsBox = new HBox(10); // 10px spacing between buttons
            buttonsBox.setAlignment(Pos.CENTER); // Center the buttons in the HBox
            Button likeButton = new Button("Like");
            Button requestUseButton = new Button("Request Use");
            buttonsBox.getChildren().addAll(likeButton, requestUseButton); // Add buttons to the HBox

            // Add the components to the post VBox (Comment -> Image -> Buttons)
            post.getChildren().addAll(postText, buttonsBox);

            // Add the post VBox to the feed container
            feedContainer.getChildren().add(post);

            // Clear input and image selection for the next post
            inputField.clear();
            selectedImageFile = null;
        }
    }

    @FXML
    private VBox sideBar;
    private boolean isSideBarVisible = false;
    // change pages
    @FXML
    public void changeToMain() throws IOException {
        MainApplication.changeScene("MainPage.fxml");
    }
    public void toSearchPage() throws IOException {
        MainApplication.changeScene("ProfileSearch.fxml");
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
}
