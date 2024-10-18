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

    public void finishButton() throws SQLException, IOException {
        for (Map.Entry<String, Image> entry : selectedImages.entrySet()) {
            String imageId = entry.getKey();
            Image image = entry.getValue();

            // Convert the Image to byte array by wrapping it in an ImageView
            ImageView imageView = new ImageView(image);
            byte[] imageBytes = convertImageToByteArray(imageView.getImage());

            // Assuming you have the userId available
            LoginController loginController = new LoginController();
            int userId = getUserIDFromDoc();

            // Save image to the database
            saveImageToDatabase(imageId, imageBytes, userId);
        }

        // Optionally, clear the map after saving
        selectedImages.clear();
    }

    public static byte[] convertFileToBytes(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
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


    private void saveImageToDatabase(String imageId, File imageFile) throws IOException, SQLException {
        System.out.println("asd");
        // Convert the image file to byte[]
        byte[] imageBytes = convertFileToBytes(imageFile);
        LoginController loginController = new LoginController();

        // Database connection and insertion logic
        Connection conn = DatabaseManager.connect(); // Assuming you have a connect() method
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO images (image_id, user_id, image) VALUES (?, ?, ?)");

        pstmt.setString(1, imageId);
        int userId = getUserIDFromDoc();
        pstmt.setBytes(3, imageBytes);  // Set the byte[] for the image

        pstmt.executeUpdate();
        conn.close();
    }

    private void uploadImageFromImageView(ImageView imageView, String imageId) throws IOException, SQLException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(imageView.getScene().getWindow());

        if (file != null) {
            // Save the image to the database
            saveImageToDatabase(imageId, file);

            // Optionally, update the ImageView with the new image
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        } else {
            System.out.println("No file selected");
        }
    }
    private byte[] convertImageToByteArray(Image image) {
        try {
            // Convert the JavaFX Image to WritableImage
            WritableImage writableImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
            SnapshotParameters params = new SnapshotParameters();

            // Draw the Image on the WritableImage
            writableImage.getPixelWriter().setPixels(0, 0, (int) image.getWidth(), (int) image.getHeight(),
                    image.getPixelReader(), 0, 0);

            // Use ByteArrayOutputStream to store the image data
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            // You can use ImageIO to write the image as PNG to the output stream
            BufferedImage bufferedImage = new BufferedImage((int) writableImage.getWidth(), (int) writableImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);  // Using PNG format

            // Return byte array
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            System.out.println("Error converting Image to byte array: " + e.getMessage());
            return null;
        }
    }

    public void uploadImage(File file) {
        File UserDatafile = new File("src/main/resources/userData/UserData.txt");
        int userId = readUserIdFromFile(UserDatafile);
        try {
            // Convert image file to byte array
            byte[] imageData = Files.readAllBytes(UserDatafile.toPath());
            String imageId = UUID.randomUUID().toString(); // Generate a unique ID for the image

            // Get connection from DatabaseManager
            Connection conn = DatabaseManager.connect();

            // Save image to the database
            DatabaseManager dbManager = new DatabaseManager();
            dbManager.saveImage(imageId, imageData, userId);

            System.out.println("Image uploaded successfully!");
        } catch (IOException | SQLException e) {
            System.out.println("Error uploading image: " + e.getMessage());
        }
    }

    private void loadSavedImageFromDB(String imageId, VBox vBox) {
        try {
            // Connect to the database
            Connection conn = DatabaseManager.connect();
            String sql = "SELECT image FROM images WHERE image_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, imageId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Retrieve the image as a byte array
                byte[] imageBytes = rs.getBytes("image");
                if (imageBytes != null) {
                    // Convert byte[] to Image
                    Image image = new Image(new ByteArrayInputStream(imageBytes));

                    // Create an ImageView and add it to the VBox
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(150); // Adjust size if needed
                    imageView.setFitHeight(150);
                    vBox.getChildren().add(imageView);
                }
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error loading image from database: " + e.getMessage());
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
    private void saveImageToDatabase(String imageId, byte[] imageBytes, int userId) throws SQLException {
        Connection conn = DatabaseManager.connect();
        String sql = "INSERT INTO images (image_id, user_id, image) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, imageId);
        pstmt.setInt(2, userId);
        pstmt.setBytes(3, imageBytes);  // Save the byte array

        pstmt.executeUpdate();
        conn.close();
    }
    public static int readUserIdFromFile(File file) {
        int userId = -1;  // Default value if no user ID is found

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String theLine;
            while ((theLine = reader.readLine()) != null) {
                if (theLine.startsWith("User ID:")) {
                    String[] parts = theLine.split(":");
                    if (parts.length == 2) {
                        userId = Integer.parseInt(parts[1].trim());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return userId;
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
        File file = new File(dir, imageId + ".png");

        // Return true if the image file exists
        return file.exists();
    }
}
