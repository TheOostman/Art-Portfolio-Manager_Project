package com.example.groupassessment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.stage.FileChooser;

import java.sql.SQLException;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.util.Map;
import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.animation.TranslateTransition;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.FileChooser;

import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;

import java.io.ByteArrayOutputStream;
import java.io.*;

import javafx.scene.image.ImageView;
import java.awt.image.BufferedImage;
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
    private VBox picA1, picA2, picA3, picA4, picA5, picB1, picB2, picB3, picB4, picB5;

    @FXML
    private VBox DefultA1, DefultA2, DefultA3, DefultA4, DefultA5, DefultB1, DefultB2, DefultB3, DefultB4, DefultB5;
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

    private Map<String, Image> selectedImages = new HashMap<>();
    public void changeToMain() throws IOException{
        MainApplication.changeScene("MainPage.fxml");
    }

    @FXML
    private void toSignInBn(ActionEvent event) throws IOException {
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
    private void toSearchPage(ActionEvent event) throws IOException {
        MainApplication.changeScene("ProfileSearch.fxml");
    }
    public void editPageLoadUp(int userId) {
        DatabaseManager dbManager = new DatabaseManager();
        Map<String, byte[]> userImages = dbManager.getUserImages(userId);

        for (Map.Entry<String, byte[]> entry : userImages.entrySet()) {
            String imageId = entry.getKey();
            byte[] imageData = entry.getValue();

            if (imageId.equals("A1")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA1.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("A2")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA2.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("A3")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA3.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("A4")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA4.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("A5")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA5.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("B1")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB1.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("B2")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB2.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("B3")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB3.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("B4")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB4.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("B5")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB5.setImage(image); // Set the image to ImageView
            }
        }
    }
    public void editPageDisplay(int userId, String imageID) {
        DatabaseManager dbManager = new DatabaseManager();
        Map<String, byte[]> userImagesEditPage = dbManager.getUserImagesForEditPage(userId, imageID);

        for (Map.Entry<String, byte[]> entry : userImagesEditPage.entrySet()) {
            String imageId = entry.getKey();
            byte[] imageData = entry.getValue();

            if (imageId.equals("A1")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA1.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("A2")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA2.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("A3")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA3.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("A4")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA4.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("A5")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewA5.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("B1")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB1.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("B2")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB2.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("B3")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB3.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("B4")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB4.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("B5")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                selectedImageViewB5.setImage(image); // Set the image to ImageView
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
        int userId = getUserIDFromDoc(); // Replace with your logic to get the current user ID
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
    // Helper method to clear the ImageView after deletion
    private void clearImageView(String picID) {
        // Clear the image from the corresponding ImageView based on the picID
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
            // Use the class loader to load the file from resources
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
    public void loadUserImages(int userId) {
        DatabaseManager dbManager = new DatabaseManager();
        Map<String, byte[]> userImages = dbManager.getUserImages(userId);

        for (Map.Entry<String, byte[]> entry : userImages.entrySet()) {
            String imageId = entry.getKey();
            byte[] imageData = entry.getValue();

            if (imageId.equals("A1")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                imageViewA1.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("A1")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                imageViewA1.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("A2")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                imageViewA2.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("A3")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                imageViewA3.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("A4")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                imageViewA4.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("A5")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                imageViewA5.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("B1")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                imageViewA1.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("B1")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                imageViewB1.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("B2")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                imageViewB2.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("B3")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                imageViewB3.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("B4")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                imageViewB4.setImage(image); // Set the image to ImageView
            }
            if (imageId.equals("B5")) { // Replace with the appropriate logic to identify ImageViews
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);
                imageViewB5.setImage(image); // Set the image to ImageView
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
    // pop up view image
    private void OpenImage(Image image, String title, String comments) {
        //stage
        Stage imageStage = new Stage();
        imageStage.setTitle("Image Title");

        ImageView fullImageView = new ImageView(image);
        fullImageView.setPreserveRatio(true);

        // title form database
        //Label titleLabel = new Label("Title: " + title);
        //titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        //comments
        //TextArea commentsArea = new TextArea(comments);
        //commentsArea.setWrapText(true);
        //commentsArea.setEditable(false);
        // need to be able to write in comments
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.getChildren().addAll(fullImageView);

        Scene scene = new Scene(layout, 500, 500);

        imageStage.setScene(scene);
        imageStage.initModality(Modality.APPLICATION_MODAL);
        imageStage.showAndWait();

    }

}
