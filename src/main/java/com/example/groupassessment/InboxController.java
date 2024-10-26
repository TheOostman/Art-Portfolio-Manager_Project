package com.example.groupassessment;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InboxController {
    @FXML
    private VBox sideBar;
    private boolean isSideBarVisible = false;
    private Map<String, Image> selectedImages = new HashMap<>();

    // change pages
    @FXML
    public void changeToMain() throws IOException {
        MainApplication.changeScene("MainPage.fxml");
    }
    public void toSearchPage() throws IOException {
        MainApplication.changeScene("ProfileSearch.fxml");
    }
    public void toFeedPage() throws IOException {
        MainApplication.changeScene("FeedPage.fxml");
    }
    public void toSignInBn() throws IOException {
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
}
