package com.example.groupassessment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.io.*;
import java.sql.*;

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
    private ImageView imageViewA1, imageViewA2, imageViewA3, imageViewA4, imageViewA5, imageViewB1, imageViewB2, imageViewB3, imageViewB4, imageViewB5;
    @FXML
    private VBox DefultA1, DefultA2, DefultA3, DefultA4, DefultA5, DefultB1, DefultB2, DefultB3, DefultB4, DefultB5;

    private boolean isSideBarVisible = false;
    private boolean isProfileEditorVisible = false;
    public boolean hasPictures;


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
    public void deletePicA1() {
        deletePic("A1");
    }
    @FXML
    public void deletePicA2() {
        deletePic("A2");
    }
    @FXML
    public void deletePicA3() {
        deletePic("A3");
    }
    @FXML
    public void deletePicA4() {
        deletePic("A4");
    }
    @FXML
    public void deletePicA5() {
        deletePic("A5");
    }
    @FXML
    public void deletePicB1() {
        deletePic("B1");
    }
    @FXML
    public void deletePicB2() {
        deletePic("B2");
    }
    @FXML
    public void deletePicB3() {
        deletePic("B3");
    }
    @FXML
    public void deletePicB4() {
        deletePic("B4");
    }
    @FXML
    public void deletePicB5() {
        deletePic("B5");
    }

    public void deletePic(String picID) {
        String sql = "DELETE FROM images WHERE image_id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:users.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, picID);
            pstmt.executeUpdate();
            System.out.println("Image deleted from database for " + picID);
            clearImageView(picID); // Clear the corresponding ImageView after deletion
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Helper method to clear the ImageView after deletion
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

    // Method to save the image to the database
    private void saveImageToDatabase(File selectedFile, String imageId) {
        String sql = "INSERT OR REPLACE INTO images(image_id, image) VALUES(?, ?)";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:users.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, imageId);
            try (FileInputStream fis = new FileInputStream(selectedFile)) {
                pstmt.setBytes(2, fis.readAllBytes());
            }
            pstmt.executeUpdate();
            System.out.println("Image saved to database for " + imageId);
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to load the image from the database
    private void loadImageFromDatabase(String imageId, ImageView imageView) {

        String sql = "SELECT image FROM images WHERE image_id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:users.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, imageId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                byte[] imgBytes = rs.getBytes("image");
                if (imgBytes != null) {
                    Image image = new Image(new ByteArrayInputStream(imgBytes));
                    imageView.setImage(image);
                    imageView.setFitWidth(150);
                    imageView.setFitHeight(150);
                    System.out.println("Image loaded from database for " + imageId);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Select and save image path from file chooser, save image to database
    private void selectAndSaveImagePath(ImageView imageView, VBox vBox, String imageId) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(vBox.getScene().getWindow());
        if (selectedFile != null) {
            try {
                Image image = new Image(new FileInputStream(selectedFile));
                imageView.setImage(image);
                saveImageToDatabase(selectedFile, imageId);  // Save image to database
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void initialize() {
        hasPictures = false;

        // Check if any images are saved in the database
        if (checkForSavedImage("A1") || checkForSavedImage("A2") || checkForSavedImage("A3") ||
                checkForSavedImage("A4") || checkForSavedImage("A5") ||
                checkForSavedImage("B1") || checkForSavedImage("B2") ||
                checkForSavedImage("B3") || checkForSavedImage("B4") || checkForSavedImage("B5")) {
            hasPictures = true;
        }

        if (hasPictures) {
            profileHasPic();
        } else {
            noImagesSelfProfile.setText("There is no images, please add some");
        }

        sideBar.setVisible(false);
        editPageEditor.setVisible(false);

        // Initialize image views and load saved images from database
        imageViewA1 = new ImageView();
        picA1.getChildren().add(imageViewA1);
        loadImageFromDatabase("A1", imageViewA1);

        imageViewA2 = new ImageView();
        picA2.getChildren().add(imageViewA2);
        loadImageFromDatabase("A2", imageViewA2);

        imageViewA3 = new ImageView();
        picA3.getChildren().add(imageViewA3);
        loadImageFromDatabase("A3", imageViewA3);

        imageViewA4 = new ImageView();
        picA4.getChildren().add(imageViewA4);
        loadImageFromDatabase("A4", imageViewA4);

        imageViewA5 = new ImageView();
        picA5.getChildren().add(imageViewA5);
        loadImageFromDatabase("A5", imageViewA5);

        imageViewB1 = new ImageView();
        picB1.getChildren().add(imageViewB1);
        loadImageFromDatabase("B1", imageViewB1);

        imageViewB2 = new ImageView();
        picB2.getChildren().add(imageViewB2);
        loadImageFromDatabase("B2", imageViewB2);

        imageViewB3 = new ImageView();
        picB3.getChildren().add(imageViewB3);
        loadImageFromDatabase("B3", imageViewB3);

        imageViewB4 = new ImageView();
        picB4.getChildren().add(imageViewB4);
        loadImageFromDatabase("B4", imageViewB4);

        imageViewB5 = new ImageView();
        picB5.getChildren().add(imageViewB5);
        loadImageFromDatabase("B5", imageViewB5);
    }

    private boolean checkForSavedImage(String imageId) {
        String sql = "SELECT image FROM images WHERE image_id = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:users.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, imageId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();  // Returns true if an image exists for the given ID
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Method to load all saved profile images from the database
    public void profileHasPic() {
        loadImageFromDatabase("A1", imageViewA1);
        loadImageFromDatabase("A2", imageViewA2);
        loadImageFromDatabase("A3", imageViewA3);
        loadImageFromDatabase("A4", imageViewA4);
        loadImageFromDatabase("A5", imageViewA5);
        loadImageFromDatabase("B1", imageViewB1);
        loadImageFromDatabase("B2", imageViewB2);
        loadImageFromDatabase("B3", imageViewB3);
        loadImageFromDatabase("B4", imageViewB4);
        loadImageFromDatabase("B5", imageViewB5);
    }
}
