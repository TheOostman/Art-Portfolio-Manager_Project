package com.example.groupassessment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.io.IOException;

public class MainPageController {
    @FXML
    private Button profileButton;
    @FXML
    private Label noImagesSelfProfile;
    @FXML
    private VBox sideBar;

    private boolean isSideBarVisible = false;


    // -------------------------------
    // THIS SECTION IS DUMMY STATS AND CAN BE DELETED
    public static boolean isLoggedIn = true;        // Please use this boolean for signing and register !!!!
    public String basicUsername = "123";             //Test data, this can be deleted
    public String basicPassword = "123";                   //Test data, this can be deleted
    public boolean hasPictures;
    //----------------------------------



    @FXML
    protected void viewProfileClick() {

    }

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
        System.out.println("asd");
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
    }




}

