package com.example.groupassessment;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class MainController {
    @FXML
    private Button profileButton;
    //@FXML
    //private Button signinRegister;
    @FXML
    private TextField usernameEntry;
    @FXML
    private TextField passwordEntry;
    @FXML
    private Label usernameText;
    @FXML
    private VBox design1RootBox;


    public static boolean isLoggedIn = true;        // Please use this boolean for signing and register !!!!
    String basicUsername = "username1";             //Test data, this can be deleted
    String basicPassword = "123";                   //Test data, this can be deleted


    @FXML
    protected void viewProfileClick() {

        if (isLoggedIn){
            System.out.println(basicUsername);
            usernameText.setText(basicUsername);    //change this !!!
        }
        else{
            profileButton.setText("SIGNIN FIRST");
        }
    }

    @FXML
    protected void signInRegisterBn() {
        System.out.println(usernameEntry);
        System.out.println(passwordEntry);
    }

    @FXML
    public void initialize() {
        // Iterate through all the HBox elements in the VBox
        for (int i = 0; i < design1RootBox.getChildren().size(); i++) {
            HBox hbox = (HBox) design1RootBox.getChildren().get(i);

            // Iterate through all the buttons in each HBox
            for (int j = 0; j < hbox.getChildren().size(); j++) {
                Button button = (Button) hbox.getChildren().get(j);

                // Assign the same event handler to each button
                button.setOnAction(event -> {
                    System.out.println("Hello from " + button.getId());  // Prints "Hello" and the button ID
                });
            }
        }
    }

}

