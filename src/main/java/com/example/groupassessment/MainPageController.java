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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.image.ImageView;

import java.io.IOException;



public class MainPageController {
    @FXML
    private Label noImagesSelfProfile;
    @FXML
    private VBox sideBar;
    @FXML
    private HBox editPageEditor;
    @FXML
    private VBox picA1, picA2;
    private ImageView imageViewA1, imageViewA2;

    private boolean isSideBarVisible = false;
    private boolean isProfileEditorVisible = false;


    // -------------------------------
    // THIS SECTION IS DUMMY STATS AND CAN BE DELETED
    public static boolean isLoggedIn = true;        // Please use this boolean for signing and register !!!!
    public String basicUsername = "123";             //Test data, this can be deleted
    public String basicPassword = "123";                   //Test data, this can be deleted
    public boolean hasPictures;
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
        } else {
            editPageEditor.setVisible(true);
            editPageEditor.toFront();
        }
        sideBar.setVisible(false);
        isProfileEditorVisible = !isProfileEditorVisible;
        System.out.println("asd");
    }

    @FXML
    public void openImageSelectorForPicA1() {
        selectImageForVBox(imageViewA1, picA1);
    }
    @FXML
    public void openImageSelectorForPicA2() {
        selectImageForVBox(imageViewA2, picA2);
    }

    // Helper method to select and display image in VBox
    private void selectImageForVBox(ImageView imageView, VBox vBox) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(vBox.getScene().getWindow());

        if (selectedFile != null) {
            try (FileInputStream input = new FileInputStream(selectedFile)) {
                Image image = new Image(input);
                imageView.setImage(image);

                // Adjust VBox size slightly larger than the image
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);
                vBox.setPrefWidth(imageView.getFitWidth() + 20);
                vBox.setPrefHeight(imageView.getFitHeight() + 20);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void initialize() {
        hasPictures = false;
        if (hasPictures == true) {
            noImagesSelfProfile.setText("");
        }
        else{
            noImagesSelfProfile.setText("There is no images, please add some");
        }
        sideBar.setVisible(false);
        editPageEditor.setVisible(false);

        imageViewA1 = new ImageView();
        picA1.getChildren().add(imageViewA1);

        imageViewA2 = new ImageView();
        picA2.getChildren().add(imageViewA2);



        sideBar.setVisible(false);
        editPageEditor.setVisible(false);
    }







}



