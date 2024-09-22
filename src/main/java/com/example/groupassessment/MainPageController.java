package com.example.groupassessment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.stage.FileChooser;

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
    private VBox picA1, picA2, picA3, picA4, picA5, picB1, picB2, picB3, picB4, picB5;

    @FXML
    private VBox DefultA1, DefultA2, DefultA3, DefultA4, DefultA5, DefultB1, DefultB2, DefultB3, DefultB4, DefultB5;
    private ImageView imageViewA1, imageViewA2, imageViewA3, imageViewA4, imageViewA5;
    private ImageView imageViewB1, imageViewB2, imageViewB3, imageViewB4, imageViewB5;

    private boolean isSideBarVisible = false;
    private boolean isProfileEditorVisible = false;
    public boolean hasPictures;


    // -------------------------------
    // THIS SECTION IS DUMMY STATS AND CAN BE DELETED
    public static boolean isLoggedIn = true;        // Please use this boolean for signing and register !!!!
    public String basicUsername = "123";             //Test data, this can be deleted
    public String basicPassword = "123";                   //Test data, this can be deleted

    //----------------------------------


    public void changeToMain() throws IOException{
        MainApplication.changeScene("MainPage.fxml");
    }

    @FXML
    private void toRegisterPageBn(ActionEvent event) throws IOException {
        MainApplication.changeScene("RegisterPage.fxml");
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
        selectAndSaveImagePath(imageViewA1, picA1, "A1");
    }

    @FXML
    public void openImageSelectorForPicA2() {
        selectAndSaveImagePath(imageViewA2, picA2, "A2");
    }

    @FXML
    public void openImageSelectorForPicA3() {
        selectAndSaveImagePath(imageViewA3, picA3, "A3");
    }

    @FXML
    public void openImageSelectorForPicA4() {
        selectAndSaveImagePath(imageViewA4, picA4, "A4");
    }

    @FXML
    public void openImageSelectorForPicA5() {
        selectAndSaveImagePath(imageViewA5, picA5, "A5");
    }

    @FXML
    public void openImageSelectorForPicB1() {
        selectAndSaveImagePath(imageViewB1, picB1, "B1");
    }

    @FXML
    public void openImageSelectorForPicB2() {
        selectAndSaveImagePath(imageViewB2, picB2, "B2");
    }

    @FXML
    public void openImageSelectorForPicB3() {
        selectAndSaveImagePath(imageViewB3, picB3, "B3");
    }

    @FXML
    public void openImageSelectorForPicB4() {
        selectAndSaveImagePath(imageViewB4, picB4, "B4");
    }

    @FXML
    public void openImageSelectorForPicB5() {
        selectAndSaveImagePath(imageViewB5, picB5, "B5");
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

                // Copy the image file to a specific directory
                saveImageFile(selectedFile, imageId);


                // Adjust VBox size
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to copy the image file to a dedicated location (A1 or A2 folder)
    private void saveImageFile(File selectedFile, String imageId) {
        File dir = new File("saved_images"); // Directory for storing images
        if (!dir.exists()) {
            dir.mkdirs(); // Create directory if it doesn't exist
        }

        File outputFile = new File(dir, imageId + getFileExtension(selectedFile.getName())); // Save with original extension
        try (FileInputStream fis = new FileInputStream(selectedFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            System.out.println("Image saved to: " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to get the file extension (like .png or .jpg)
    private String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index > 0) {
            return fileName.substring(index);
        } else {
            return ".png"; // Default to PNG
        }
    }

    // Method to load the saved image during login
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

    public void profileHasPic(){
        loadSavedImageFromDB("A1", DefultA1);
        loadSavedImageFromDB("A2", DefultA2);
        loadSavedImageFromDB("A3", DefultA3);
        loadSavedImageFromDB("A4", DefultA4);
        loadSavedImageFromDB("A5", DefultA5);

        loadSavedImageFromDB("B1", DefultB1);
        loadSavedImageFromDB("B2", DefultB2);
        loadSavedImageFromDB("B3", DefultB3);
        loadSavedImageFromDB("B4", DefultB4);
        loadSavedImageFromDB("B5", DefultB5);

    }
    private void loadSavedImageFromDB(String imageId, VBox vbox) {
        try {
            // Retrieve image data from the database
            byte[] imageData = DatabaseManager.getImageFromDatabase(imageId);

            if (imageData != null) {
                // Convert byte array to InputStream and create an Image
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                Image image = new Image(bis);

                // Create an ImageView, set the image, and add to the VBox
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);
                imageView.setPreserveRatio(true);
                vbox.getChildren().add(imageView);

                // Set flag that pictures exist
                hasPictures = true;
            }
        } catch (Exception e) {
            System.out.println("Error loading image for " + imageId + ": " + e.getMessage());
        }
    }

    @FXML
    public void initialize() {

        hasPictures = false;

        // Directory where images are saved
        File dir = new File("saved_images");

        if (checkForSavedImage("A1") || checkForSavedImage("A2") || checkForSavedImage("A3") || checkForSavedImage("A4") || checkForSavedImage("A5") ||
                checkForSavedImage("B1") || checkForSavedImage("B2") || checkForSavedImage("B3") || checkForSavedImage("B4") || checkForSavedImage("B5")) {
            hasPictures = true;
        }
        if (hasPictures == true) {
            profileHasPic();
        }
        else{
            noImagesSelfProfile.setText("There is no images, please add some");
        }
        sideBar.setVisible(false);
        editPageEditor.setVisible(false);

        imageViewA1 = new ImageView();
        picA1.getChildren().add(imageViewA1);
        loadSavedImage("A1", imageViewA1);

        imageViewA2 = new ImageView();
        picA2.getChildren().add(imageViewA2);
        loadSavedImage("A2", imageViewA2);

        imageViewA3 = new ImageView();
        picA3.getChildren().add(imageViewA3);
        loadSavedImage("A3", imageViewA3);

        imageViewA4 = new ImageView();
        picA4.getChildren().add(imageViewA4);
        loadSavedImage("A4", imageViewA4);

        imageViewA5 = new ImageView();
        picA5.getChildren().add(imageViewA5);
        loadSavedImage("A5", imageViewA5);

        imageViewB1 = new ImageView();
        picB1.getChildren().add(imageViewB1);
        loadSavedImage("B1", imageViewB1);

        imageViewB2 = new ImageView();
        picB2.getChildren().add(imageViewB2);
        loadSavedImage("B2", imageViewB2);

        imageViewB3 = new ImageView();
        picB3.getChildren().add(imageViewB3);
        loadSavedImage("B3", imageViewB3);

        imageViewB4 = new ImageView();
        picB4.getChildren().add(imageViewB4);
        loadSavedImage("B4", imageViewB4);

        imageViewB5 = new ImageView();
        picB5.getChildren().add(imageViewB5);
        loadSavedImage("B5", imageViewB5);


        sideBar.setVisible(false);
        editPageEditor.setVisible(false);
    }

    private boolean checkForSavedImage(String imageId) {
        File dir = new File("saved_images");
        File file = new File(dir, imageId + ".png"); // Assuming PNG format by default

        // Return true if the image file exists
        return file.exists();
    }

}


