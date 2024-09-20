package com.example.groupassessment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class MainPageController {
    @FXML
    private Button profileButton;
    @FXML
    private Label noImagesSelfProfile;


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
    private void dropDownBn(ActionEvent event){
        System.out.println("menu button pressed");
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
    }




}

