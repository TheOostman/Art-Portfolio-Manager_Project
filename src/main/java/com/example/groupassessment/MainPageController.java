package com.example.groupassessment;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class MainPageController {
    @FXML
    private Label noImagesSelfProfile;
    @FXML
    private VBox sideBar;
    @FXML
    private VBox editPageEditor;
    @FXML
    private Label usernameText;
    @FXML
    private ImageView imageViewA1, imageViewA2, imageViewA3, imageViewA4, imageViewA5;
    @FXML
    private ImageView imageViewB1, imageViewB2, imageViewB3, imageViewB4, imageViewB5;
    @FXML
    private ImageView selectedImageViewA1, selectedImageViewA2, selectedImageViewA3, selectedImageViewA4, selectedImageViewA5;
    @FXML
    private ImageView selectedImageViewB1, selectedImageViewB2, selectedImageViewB3, selectedImageViewB4, selectedImageViewB5;

    private int userID; // This should be set with the logged-in user's ID
    private DatabaseManager databaseManager;

    private boolean isSideBarVisible = false;
    private boolean isProfileEditorVisible = false;
    public boolean hasPictures;

    public MainPageController() {
        // Initialize the database manager
        this.databaseManager = new DatabaseManager();
    }

    // change pages
    public void changeToMain() throws IOException{
        MainApplication.changeScene("MainPage.fxml");
    }
    @FXML
    private void toFeedPage(ActionEvent event) throws IOException {
        MainApplication.changeScene("FeedPage.fxml");
    }
    @FXML
    private void toSearchPage(ActionEvent event) throws IOException {
        MainApplication.changeScene("ProfileSearch.fxml");
    }
    @FXML
    private void changeToInbox(ActionEvent event) throws IOException {
        MainApplication.changeScene("InboxPage.fxml");
    }
    @FXML
    private void logout(ActionEvent event) throws IOException {
        try {
            int userId = -1; // default value indicating no user is logged in

            // Delete user data file if it exists
            File fileToDelete = new File("src/main/resources/userData/UserData.txt");
            if (fileToDelete.exists()) {
                boolean deleted = fileToDelete.delete();
                if (deleted) {
                    System.out.println("User data file deleted successfully");
                } else {
                    System.out.println("Failed to delete user data file");
                }
            }

            System.out.println("Session data cleared");
            userID = -1; // Indicates no user is logged in
            usernameText.setText(""); // Clear username display

            // Redirect to the login page
            MainApplication.changeScene("LoginPage.fxml"); // Change to your actual login page scene
            // Log the logout action for monitoring purposes
            System.out.println("User logged out successfully.");
            // Display success message
            showMessage("Logout successful. You can now sign in with a different user ID.");
        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("Logout failed: " + e.getMessage());
            // Display error message
            showMessage("Logout failed. Please try again.");
        }
    }
    private void showMessage(String message) {
        // This method should display messages to the user
        // Consider using a dialog for better user experience
        System.out.println(message); // Example implementation
        // You can also use JavaFX Alert for a pop-up message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
    // load images and info
    public void loadUserImages(int userId) {
        DatabaseManager dbManager = new DatabaseManager();
        Map<String, byte[]> userImages = dbManager.getUserImages(userId);

        for (Map.Entry<String, byte[]> entry : userImages.entrySet()) {
            String imageId = entry.getKey();
            byte[] imageData = entry.getValue();

            // Convert byte array to Image
            ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
            Image image = new Image(bis);

            // Retrieve title and description from the database
            String title = dbManager.getImageTitle(userId, imageId);
            String description = dbManager.getImageDescription(userId, imageId);

            // Set image and add click event to open it in an edit pop-up
            switch (imageId) {
                case "A1":
                    imageViewA1.setImage(image);
                    imageViewA1.setOnMouseClicked(e -> EditImage(image, title, description, imageId, userId));
                    break;
                case "A2":
                    imageViewA2.setImage(image);
                    imageViewA2.setOnMouseClicked(e -> EditImage(image, title, description, imageId, userId));
                    break;
                case "A3":
                    imageViewA3.setImage(image);
                    imageViewA3.setOnMouseClicked(e -> EditImage(image, title, description, imageId, userId));
                    break;
                case "A4":
                    imageViewA4.setImage(image);
                    imageViewA4.setOnMouseClicked(e -> EditImage(image, title, description, imageId, userId));
                    break;
                case "A5":
                    imageViewA5.setImage(image);
                    imageViewA5.setOnMouseClicked(e -> EditImage(image, title, description, imageId, userId));
                    break;
                case "B1":
                    imageViewB1.setImage(image);
                    imageViewB1.setOnMouseClicked(e -> EditImage(image, title, description, imageId, userId));
                    break;
                case "B2":
                    imageViewB2.setImage(image);
                    imageViewB2.setOnMouseClicked(e -> EditImage(image, title, description, imageId, userId));
                    break;
                case "B3":
                    imageViewB3.setImage(image);
                    imageViewB3.setOnMouseClicked(e -> EditImage(image, title, description, imageId, userId));
                    break;
                case "B4":
                    imageViewB4.setImage(image);
                    imageViewB4.setOnMouseClicked(e -> EditImage(image, title, description, imageId, userId));
                    break;
                case "B5":
                    imageViewB5.setImage(image);
                    imageViewB5.setOnMouseClicked(e -> EditImage(image, title, description, imageId, userId));
                    break;
            }
        }
    }

    public String getUsername(){
        userID = getUserIDFromDoc();
        DatabaseManager dbManager = new DatabaseManager();
        String username = dbManager.getUsernameDB(userID);

        if (username != null) {
            System.out.println("Username: " + username);
            return username;
        } else {
            System.out.println("User not found for ID: " + userID);
            return null;
        }
    }

    @FXML
    public void initialize() {
        sideBar.setVisible(false);
        editPageEditor.setVisible(false);
        System.out.println("Initializing MainPage...");
        System.out.println("imageViewA1: " + (imageViewA1 != null ? "Initialized" : "Null"));
        System.out.println("imageViewA2: " + (imageViewA2 != null ? "Initialized" : "Null"));
        userID = getUserIDFromDoc();
        System.out.println("Initializing MainPage...");
        loadUserImages(userID);
        String username = getUsername();
        if (username != null){
            usernameText.setText(username);
        }

    }
    // Edit image (title and comments)
    private void EditImage(Image image, String title, String description, String imageId, int userId) {
        Stage editStage = new Stage();
        editStage.setTitle("Edit Image");

        ImageView fullImageView = new ImageView(image);
        fullImageView.setFitWidth(500);
        fullImageView.setPreserveRatio(true);
        DatabaseManager dbManager = new DatabaseManager();

        // title and comments
        TextField titleField = new TextField(title);
        titleField.setPromptText("Enter new title");
        titleField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        TextArea descriptionArea = new TextArea(description);
        descriptionArea.setWrapText(true);
        descriptionArea.setPromptText("Enter Description");

        // Save button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String newTitle = titleField.getText();
            String newDescription = descriptionArea.getText();

            // Update the title and description in the database
            dbManager.updateImageMetadata(userId, imageId, newTitle, newDescription);
            loadUserImages(userId);

            editStage.close(); // close window after saving
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(
                new Label("Edit Title:"), titleField, fullImageView,
                new Label("Edit Description:"), descriptionArea, saveButton
        );

        Scene scene = new Scene(layout, 700, 900);
        editStage.setScene(scene);
        editStage.initModality(Modality.APPLICATION_MODAL);
        editStage.showAndWait();
    }

    //edit pages
    public void editPageLoadUp(int userId) {
        DatabaseManager dbManager = new DatabaseManager();
        Map<String, byte[]> userImages = dbManager.getUserImages(userId);

        for (Map.Entry<String, byte[]> entry : userImages.entrySet()) {
            String imageId = entry.getKey();
            byte[] imageData = entry.getValue();

            if (imageId.equals("A1")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA1.setImage(image);
            }
            if (imageId.equals("A2")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA2.setImage(image);
            }
            if (imageId.equals("A3")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA3.setImage(image);
            }
            if (imageId.equals("A4")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA4.setImage(image);
            }
            if (imageId.equals("A5")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA5.setImage(image);
            }
            if (imageId.equals("B1")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB1.setImage(image);
            }
            if (imageId.equals("B2")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB2.setImage(image);
            }
            if (imageId.equals("B3")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB3.setImage(image);
            }
            if (imageId.equals("B4")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB4.setImage(image);
            }
            if (imageId.equals("B5")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB5.setImage(image);
            }
        }
    }
    public void editPageDisplay(int userId, String imageID) {
        DatabaseManager dbManager = new DatabaseManager();
        Map<String, byte[]> userImagesEditPage = dbManager.getUserImagesForEditPage(userId, imageID);

        for (Map.Entry<String, byte[]> entry : userImagesEditPage.entrySet()) {
            String imageId = entry.getKey();
            byte[] imageData = entry.getValue();

            if (imageId.equals("A1")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA1.setImage(image);
            }
            if (imageId.equals("A2")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA2.setImage(image);
            }
            if (imageId.equals("A3")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA3.setImage(image);
            }
            if (imageId.equals("A4")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA4.setImage(image);
            }
            if (imageId.equals("A5")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA5.setImage(image);
            }
            if (imageId.equals("B1")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB1.setImage(image);
            }
            if (imageId.equals("B2")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB2.setImage(image);
            }
            if (imageId.equals("B3")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB3.setImage(image);
            }
            if (imageId.equals("B4")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB4.setImage(image);
            }
            if (imageId.equals("B5")) {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB5.setImage(image);
            }
        }
    }
    @FXML
    public void editPage(ActionEvent action){
        if (isProfileEditorVisible) {
            editPageEditor.setVisible(false);
            try{
                changeToMain();
            } catch (IOException e){
                e.printStackTrace();
            }

        } else {
            editPageLoadUp(getUserIDFromDoc());
            editPageEditor.setVisible(true);
            noImagesSelfProfile.setText(" ");
            editPageEditor.toFront();
        }
        sideBar.setVisible(false);
        isProfileEditorVisible = !isProfileEditorVisible;
    }
    @FXML
    public void openImageSelectorForPicA1() {
        onSaveImageClick("A1");
        editPageDisplay(getUserIDFromDoc(),"A1");
    }
    @FXML
    public void openImageSelectorForPicA2() {
        onSaveImageClick("A2");
        editPageDisplay(getUserIDFromDoc(),"A2");
    }
    @FXML
    public void openImageSelectorForPicA3() {
        onSaveImageClick("A3");
        editPageDisplay(getUserIDFromDoc(),"A3");
    }
    @FXML
    public void openImageSelectorForPicA4() {
        onSaveImageClick("A4");
        editPageDisplay(getUserIDFromDoc(),"A4");
    }
    @FXML
    public void openImageSelectorForPicA5() {
        onSaveImageClick("A5");
        editPageDisplay(getUserIDFromDoc(),"A5");
    }
    @FXML
    public void openImageSelectorForPicB1() {
        onSaveImageClick("B1");
        editPageDisplay(getUserIDFromDoc(),"B1");
    }
    @FXML
    public void openImageSelectorForPicB2() {
        onSaveImageClick("B2");
        editPageDisplay(getUserIDFromDoc(),"B2");
    }
    @FXML
    public void openImageSelectorForPicB3() {
        onSaveImageClick("B3");
        editPageDisplay(getUserIDFromDoc(),"B3");
    }
    @FXML
    public void openImageSelectorForPicB4() {
        onSaveImageClick("B4");
        editPageDisplay(getUserIDFromDoc(),"B4");
    }
    @FXML
    public void openImageSelectorForPicB5() {
        onSaveImageClick("B5");
        editPageDisplay(getUserIDFromDoc(),"B5");
    }

    @FXML
    private void onDeleteButtonClick(String imageId) {
        int userId = getUserIDFromDoc();
        DatabaseManager dbManager = new DatabaseManager();

        // Delete the image from the database
        boolean isDeleted = dbManager.deleteImageFromDatabase(imageId, userId);

        if (isDeleted) {
            System.out.println("Image deleted successfully from database.");

            // Clear the corresponding ImageView
            if (imageId.equals("A1")) {
                selectedImageViewA1.setImage(null);
            }
            if (imageId.equals("A2")) {
                selectedImageViewA2.setImage(null);
            }
            if (imageId.equals("A3")) {
                selectedImageViewA3.setImage(null);
            }
            if (imageId.equals("A4")) {
                selectedImageViewA4.setImage(null);
            }
            if (imageId.equals("A5")) {
                selectedImageViewA5.setImage(null);
            }
            if (imageId.equals("B1")) {
                selectedImageViewB1.setImage(null);
            }
            if (imageId.equals("B2")) {
                selectedImageViewB2.setImage(null);
            }
            if (imageId.equals("B3")) {
                selectedImageViewB3.setImage(null);
            }
            if (imageId.equals("B4")) {
                selectedImageViewB4.setImage(null);
            }
            if (imageId.equals("B5")) {
                selectedImageViewB5.setImage(null);
            }

        } else {
            System.out.println("Failed to delete the image.");
        }
    }
    @FXML
    public void deletePicA1(){
        onDeleteButtonClick("A1");
    }
    @FXML
    public void deletePicA2(){
        onDeleteButtonClick("A2");
    }
    @FXML
    public void deletePicA3(){
        onDeleteButtonClick("A3");
    }
    @FXML
    public void deletePicA4(){
        onDeleteButtonClick("A4");
    }
    @FXML
    public void deletePicA5(){
        onDeleteButtonClick("A5");
    }
    @FXML
    public void deletePicB1(){
        onDeleteButtonClick("B1");
    }
    @FXML
    public void deletePicB2(){
        onDeleteButtonClick("B2");
    }
    @FXML
    public void deletePicB3(){
        onDeleteButtonClick("B3");
    }
    @FXML
    public void deletePicB4(){
        onDeleteButtonClick("B4");
    }
    @FXML
    public void deletePicB5(){
        onDeleteButtonClick("B5");
    }
    public void deletePic(String picID) {
        // Directory where images are saved
        File dir = new File("saved_images");

        // Construct the file path for the image to be deleted (assuming PNG format)
        File file = new File(dir, picID + ".png");

        // Check if the file exists
        if (file.exists()) {
            // Attempt to delete the file
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println("Deleted image: " + file.getAbsolutePath());

                // Clear the corresponding ImageView after deletion
                clearImageView(picID);
            } else {
                System.out.println("Failed to delete image: " + file.getAbsolutePath());
            }
        } else {
            System.out.println("No image found for " + picID);
        }
    }
    // clear the ImageView after deletion
    private void clearImageView(String picID) {
        switch (picID) {
            case "A1":
                imageViewA1.setImage(null);
                break;
            case "A2":
                imageViewA2.setImage(null);
                break;
            case "A3":
                imageViewA3.setImage(null);
                break;
            case "A4":
                imageViewA4.setImage(null);
                break;
            case "A5":
                imageViewA5.setImage(null);
                break;
            case "B1":
                imageViewB1.setImage(null);
                break;
            case "B2":
                imageViewB2.setImage(null);
                break;
            case "B3":
                imageViewB3.setImage(null);
                break;
            case "B4":
                imageViewB4.setImage(null);
                break;
            case "B5":
                imageViewB5.setImage(null);
                break;
            default:
                System.out.println("Invalid picID: " + picID);
        }
    }
    @FXML
    public void finishButton() throws SQLException, IOException {
        changeToMain();
    }
    private int getUserIDFromDoc() {
        int userId = -1;  // Default value if no user ID is found

        try {
            // class loader to load the file from resources
            InputStream inputStream = getClass().getResourceAsStream("/userData/UserData.txt");

            if (inputStream == null) {
                throw new FileNotFoundException("UserData.txt file not found in resources.");
            }

            // Read the user ID from the file
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("User ID:")) {
                    userId = Integer.parseInt(line.split(":")[1].trim());
                    break;
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading UserData.txt: " + e.getMessage());
        }

        return userId;
    }
    private byte[] convertImageFileToByteArray(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void onSaveImageClick(String InputimageID) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(null); // Assuming you use a JavaFX window reference here
        if (file != null) {
            byte[] imageData = convertImageFileToByteArray(file);
            if (imageData != null) {
                int userId = getUserIDFromDoc(); // Replace with your user ID logic
                DatabaseManager dbManager = new DatabaseManager();
                String imageId = InputimageID;
                dbManager.saveImage(userId, imageId, imageData);

                System.out.println("Image saved successfully.");
            } else {
                System.out.println("Failed to convert image to byte array.");
            }
        } else {
            System.out.println("No image selected.");
        }
    }
}
