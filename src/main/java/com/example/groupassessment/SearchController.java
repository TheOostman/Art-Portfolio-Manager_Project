package com.example.groupassessment;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchController {
    @FXML
    private TextField usernameEntry;
    @FXML
    private TextField passwordEntry;
    @FXML
    private VBox sideBar;

    private boolean isSideBarVisible = false;


    // THIS SECTION IS DUMMY STATS AND CAN BE DELETED
    public static boolean isLoggedIn = true;        // Please use this boolean for signing and register !!!!
    public String basicUsername = "123";             //Test data, this can be deleted
    public String basicPassword = "123";                   //Test data, this can be deleted
    //----------------------------------

    public void changeToMain() throws IOException {
        MainApplication.changeScene("MainPage.fxml");
    }

    @FXML
    private void toSignInBn(ActionEvent event) throws IOException {
        MainApplication.changeScene("LoginPage.fxml");
    }

    @FXML
    private void onFilterClick() {
        // Create a new Stage (window)
        Stage popupStage = new Stage();

        // Set the window to be modal, so it blocks interaction with the main window
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Filter Options");

        // Create the layout for the popup
        VBox layout = new VBox(15);
        layout.setStyle("-fx-padding: 10;");

        // Create Labels and ComboBoxes for the filters
        Label filterLabel1 = new Label("Art Medium:");
        ComboBox<String> comboBox1 = comboBox1();

        Label filterLabel2 = new Label("Art Style:");
        ComboBox<String> comboBox2 = comboBox2();

        Label filterLabel3 = new Label("Date Created:");
        ComboBox<String> comboBox3 = comboBox3();  // Month selection
        ComboBox<String> comboBox4 = comboBox4();  // Year selection

        // Create HBox to display month and year ComboBoxes side by side
        HBox dateHBox = new HBox(10);  // Horizontal box with spacing
        dateHBox.getChildren().addAll(comboBox3, comboBox4);  // Add month and year ComboBoxes side by side

        // Create an "Apply" button to apply the filters
        Button applyButton = new Button("Apply");
        applyButton.setOnAction(e -> {
            // Logic to apply the filter (for now, just close the popup)
            String selectedFilter1 = comboBox1.getValue();
            String selectedFilter2 = comboBox2.getValue();
            String selectedFilter3 = comboBox3.getValue();
            String selectedFilter4 = comboBox4.getValue();

            System.out.println("Selected Filter 1: " + selectedFilter1);
            System.out.println("Selected Filter 2: " + selectedFilter2);
            System.out.println("Selected Filter 3: " + selectedFilter3);
            System.out.println("Selected Filter 4: " + selectedFilter4);

            popupStage.close();  // Close the popup after applying the filter
        });

        // Add the components to the layout
        layout.getChildren().addAll(filterLabel1, comboBox1, filterLabel2, comboBox2, filterLabel3, dateHBox, applyButton);

        // Set the scene and show the popup
        Scene scene = new Scene(layout, 300, 250);
        popupStage.setScene(scene);
        popupStage.showAndWait();  // Wait until the popup is closed
    }

    // create Art medium options
    private ComboBox<String> comboBox1() {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Oil Painting", "Water Colours", "Photography", "Digital Art", "Painting", "Drawing", "Graphite"
        );
        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.setValue("Select an option");  // Default value
        return comboBox;
    }
    // create Art style options
    private ComboBox<String> comboBox2() {
        ObservableList<String> options = FXCollections.observableArrayList(
                "Pop Art", "Realism", "Impression", "Abstract", "Surrealism", "Expressionism"
        );
        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.setValue("Select an option");  // Default value
        return comboBox;
    }
    // create month options
// Month selection options (from 01 to 12)
    private ComboBox<String> comboBox3() {
        ObservableList<String> options = FXCollections.observableArrayList();

        // Generate months from 01 to 12
        for (int month = 1; month <= 12; month++) {
            options.add(String.format("%02d", month));  // Format month as "01", "02", ..., "12"
        }

        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.setValue("Month");  // Default value
        return comboBox;
    }

    // create year options
    private ComboBox<String> comboBox4() {
        ObservableList<String> options = FXCollections.observableArrayList();
                // Generate years from 2001 to 2024
        for (int year = 2001; year <= 2024; year++) {
            options.add(String.valueOf(year));
        }
        ComboBox<String> comboBox = new ComboBox<>(options);
        comboBox.setValue("Year");  // Default value
        return comboBox;
    }

    @FXML
    private void registerInfo() {

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
    public void initialize() {
        sideBar.setVisible(false);
    }
}
