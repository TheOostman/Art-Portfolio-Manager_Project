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

    public void finishButton() throws SQLException, IOException {
        for (Map.Entry<String, Image> entry : selectedImages.entrySet()) {
            String imageId = entry.getKey();
            Image image = entry.getValue();

            // Convert the Image to byte array
            byte[] imageBytes = convertImageToByteArray(image);

            // Assuming you have the userId available
            LoginController loginController = new LoginController();
            int userId = getUserIDFromDoc();

            // Check if an image with the same imageId exists for the user, and delete it
            DatabaseManager dbManager = new DatabaseManager();
            boolean imageExists = dbManager.deleteExistingImage(imageId, userId);

            if (imageExists) {
                System.out.println("Existing image with imageId " + imageId + " was replaced.");
            }

            // Save the new image to the database
            saveImageToDatabase(imageId, imageBytes, userId);
        }

        // Optionally, clear the map after saving
        selectedImages.clear();
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

    private byte[] convertImageToByteArray(Image image) {
        if (image == null) {
            return null;
        }

        // Get image dimensions
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        // Prepare a ByteArrayOutputStream to hold the image bytes
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            // Get PixelReader from the image
            PixelReader pixelReader = image.getPixelReader();

            // Prepare a byte buffer to store pixel data
            byte[] buffer = new byte[width * height * 4];  // Assuming 4 bytes per pixel (RGBA)
            WritablePixelFormat<ByteBuffer> format = WritablePixelFormat.getByteBgraInstance();

            // Read pixel data into the buffer
            pixelReader.getPixels(0, 0, width, height, format, buffer, 0, width * 4);

            // Write the buffer to the ByteArrayOutputStream
            byteArrayOutputStream.write(buffer);

            // Return byte array
            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
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
    public void loadUserImages() throws SQLException {
        LoginController loginController = new LoginController();
        int userId = getUserIDFromDoc();  // Retrieve the logged-in user's ID

        // Retrieve the images associated with the logged-in user
        DatabaseManager dbManager = new DatabaseManager();
        Map<String, byte[]> userImages = dbManager.getUserImages(userId);

        // Display images in their respective ImageView based on imageId (e.g., A1, A2, B1, etc.)
        for (Map.Entry<String, byte[]> entry : userImages.entrySet()) {
            String imageId = entry.getKey();
            byte[] imageData = entry.getValue();

            if (imageData != null && imageData.length > 0) {
                // Convert the byte array into an Image object
                Image image = new Image(new ByteArrayInputStream(imageData));

                // Display the image in the corresponding ImageView and add it to the VBox
                switch (imageId) {
                    case "A1":
                        imageViewA1 = new ImageView(image);  // Create a new ImageView with the image
                        DefultA1.getChildren().clear();  // Clear the VBox
                        DefultA1.getChildren().add(imageViewA1);  // Add the ImageView to the VBox
                        break;
                    case "A2":
                        imageViewA2 = new ImageView(image);
                        DefultA2.getChildren().clear();
                        DefultA2.getChildren().add(imageViewA2);
                        break;
                    case "A3":
                        imageViewA3 = new ImageView(image);
                        DefultA3.getChildren().clear();
                        DefultA3.getChildren().add(imageViewA3);
                        break;
                    case "A4":
                        imageViewA4 = new ImageView(image);
                        DefultA4.getChildren().clear();
                        DefultA4.getChildren().add(imageViewA4);
                        break;
                    case "A5":
                        imageViewA5 = new ImageView(image);
                        DefultA5.getChildren().clear();
                        DefultA5.getChildren().add(imageViewA5);
                        break;
                    case "B1":
                        imageViewB1 = new ImageView(image);
                        DefultB1.getChildren().clear();
                        DefultB1.getChildren().add(imageViewB1);
                        break;
                    case "B2":
                        imageViewB2 = new ImageView(image);
                        DefultB2.getChildren().clear();
                        DefultB2.getChildren().add(imageViewB2);
                        break;
                    case "B3":
                        imageViewB3 = new ImageView(image);
                        DefultB3.getChildren().clear();
                        DefultB3.getChildren().add(imageViewB3);
                        break;
                    case "B4":
                        imageViewB4 = new ImageView(image);
                        DefultB4.getChildren().clear();
                        DefultB4.getChildren().add(imageViewB4);
                        break;
                    case "B5":
                        imageViewB5 = new ImageView(image);
                        DefultB5.getChildren().clear();
                        DefultB5.getChildren().add(imageViewB5);
                        break;
                    default:
                        System.out.println("Unknown imageId: " + imageId);
                }
            }
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
            //profileHasPic();
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
        File file = new File(dir, imageId + ".png");

        // Return true if the image file exists
        return file.exists();
    }
}
