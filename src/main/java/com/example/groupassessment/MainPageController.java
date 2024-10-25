package com.example.groupassessment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.stage.FileChooser;

import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Map;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;



public class MainPageController {
    @FXML
    private Label noImagesSelfProfile;
    @FXML
    private VBox sideBar;
    @FXML
    private VBox editPageEditor;
    @FXML
    private VBox picA1, picA2, picA3, picA4, picA5, picB1, picB2, picB3, picB4, picB5;

    @FXML
    private VBox DefultA1, DefultA2, DefultA3, DefultA4, DefultA5, DefultB1, DefultB2, DefultB3, DefultB4, DefultB5;
    @FXML
    private ImageView imageViewA1, imageViewA2, imageViewA3, imageViewA4, imageViewA5;
    @FXML
    private ImageView imageViewB1, imageViewB2, imageViewB3, imageViewB4, imageViewB5;

    private int userID; // This should be set with the logged-in user's ID
    private DatabaseManager databaseManager;

    private boolean isSideBarVisible = false;
    private boolean isProfileEditorVisible = false;
    public boolean hasPictures;

    public MainPageController() {
        // Initialize the database manager
        this.databaseManager = new DatabaseManager();
    }

    // -------------------------------
    // THIS SECTION IS DUMMY STATS AND CAN BE DELETED
    public static boolean isLoggedIn = true;        // Please use this boolean for signing and register !!!!
    public String basicUsername = "123";             //Test data, this can be deleted
    public String basicPassword = "123";                   //Test data, this can be deleted

    //----------------------------------

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
            editPageEditor.setVisible(true);
            noImagesSelfProfile.setText(" ");
            editPageEditor.toFront();
        }
        sideBar.setVisible(false);
        isProfileEditorVisible = !isProfileEditorVisible;
    }
    @FXML
    public void openImageSelectorForPicA1() {
        //selectAndSaveImagePath(imageViewA1, picA1, "A1");
        onSaveImageClick("A1");
    }
    @FXML
    public void openImageSelectorForPicA2() {
        //selectAndSaveImagePath(imageViewA2, picA2, "A2");
        onSaveImageClick("A2");
    }
    @FXML
    public void openImageSelectorForPicA3() {
        //selectAndSaveImagePath(imageViewA3, picA3, "A3");
        onSaveImageClick("A3");
    }
    @FXML
    public void openImageSelectorForPicA4() {
        //selectAndSaveImagePath(imageViewA4, picA4, "A4");
        onSaveImageClick("A4");
    }
    @FXML
    public void openImageSelectorForPicA5() {
        //selectAndSaveImagePath(imageViewA5, picA5, "A5");
        onSaveImageClick("A5");
    }
    @FXML
    public void openImageSelectorForPicB1() {
        //selectAndSaveImagePath(imageViewB1, picB1, "B1");
        onSaveImageClick("B1");
    }
    @FXML
    public void openImageSelectorForPicB2() {
        //selectAndSaveImagePath(imageViewB2, picB2, "B2");
        onSaveImageClick("B2");
    }
    @FXML
    public void openImageSelectorForPicB3() {
        //selectAndSaveImagePath(imageViewB3, picB3, "B3");
        onSaveImageClick("B3");
    }
    @FXML
    public void openImageSelectorForPicB4() {
        //selectAndSaveImagePath(imageViewB4, picB4, "B4");
        onSaveImageClick("B4");
    }
    @FXML
    public void openImageSelectorForPicB5() {
        //selectAndSaveImagePath(imageViewB5, picB5, "B5");
        onSaveImageClick("B5");
    }

    @FXML
    public void deletePicA1(){
        deletePic("A1");
    }
    @FXML
    public void deletePicA2(){
        deletePic("A2");
    }
    @FXML
    public void deletePicA3(){
        deletePic("A3");
    }
    @FXML
    public void deletePicA4(){
        deletePic("A4");
    }
    @FXML
    public void deletePicA5(){
        deletePic("A5");
    }
    @FXML
    public void deletePicB1(){
        deletePic("B1");
    }
    @FXML
    public void deletePicB2(){
        deletePic("B2");
    }
    @FXML
    public void deletePicB3(){
        deletePic("B3");
    }
    @FXML
    public void deletePicB4(){
        deletePic("B4");
    }
    @FXML
    public void deletePicB5(){
        deletePic("B5");
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
    private void selectAndSaveImagePath(ImageView imageView, VBox vBox, String imageId) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(vBox.getScene().getWindow());
        if (selectedFile != null) {
            try {
                // Load and display the image
                Image image = new Image(new FileInputStream(selectedFile));
                imageView.setImage(image);

                // Store the selected image in the map instead of saving it to a directory
                selectedImages.put(imageId, image);

                // Adjust VBox size
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // Helper method to get the file extension (like .png or .jpg)
    private void loadSavedImage(String imageId, ImageView imageView) {
        File dir = new File("saved_images");
        File file = new File(dir, imageId + ".png"); // Load PNG file by default

        if (file.exists()) {
            try (FileInputStream input = new FileInputStream(file)) {
                Image image = new Image(input);
                imageView.setImage(image);
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);
                System.out.println("Loaded saved image: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void finishButton() throws SQLException, IOException {
        //onSaveImageClick();
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


    private void loadSavedImageFromDB(String imageId, ImageView imageView, int userId) {
        try {
            Connection conn = DatabaseManager.connect();
            String sql = "SELECT image FROM images WHERE image_id = ? AND user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, imageId);
            pstmt.setInt(2, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                byte[] imageBytes = rs.getBytes("image");
                if (imageBytes != null) {
                    // Convert byte array back to Image
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                    Image image = new Image(bis);
                    imageView.setImage(image);
                    imageView.setFitWidth(150);  // Adjust ImageView size
                    imageView.setFitHeight(150);
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void saveImageToDatabase(String imageId, byte[] imageBytes, int userId) throws SQLException {
        Connection conn = DatabaseManager.connect();
        String sql = "INSERT INTO images (image_id, user_id, image) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, imageId);
        pstmt.setInt(2, userId);
        pstmt.setBytes(3, imageBytes);  // Saving the image as byte[] (BLOB)

        pstmt.executeUpdate();
        conn.close();
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
    }
    private void setImageView(String location, File imageFile, ImageView imageView, VBox defaultBox) {
        if (imageView == null) {
            System.out.println("ImageView for location " + location + " is null. Please check the FXML file.");
            return;
        }

        if (imageFile != null && imageFile.exists()) {
            try {
                Image image = new Image(imageFile.toURI().toString());
                imageView.setImage(image);

                // Show the image and hide the default box
                imageView.setVisible(true);
                defaultBox.setVisible(false);
            } catch (Exception e) {
                System.out.println("Error loading image for " + location + ": " + e.getMessage());
                e.printStackTrace();
                imageView.setVisible(false);
                defaultBox.setVisible(true);
            }
        } else {
            // No image found, show the default box
            imageView.setVisible(false);
            defaultBox.setVisible(true);
        }
    }
    private boolean checkForSavedImage(String imageId) {
        File dir = new File("saved_images");
        File file = new File(dir, imageId + ".png");

        // Return true if the image file exists
        return file.exists();
    }
}
