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
    public String basicUsername = "username1";             //Test data, this can be deleted
    public String basicPassword = "123";                   //Test data, this can be deleted
    public int designNumber = 1;


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

    public void watchingDesign1(){
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


    public class User {
        private int id;
        private String username;
        private String email;
        private String password;

        // Constructor
        public User(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }

        // Getters and setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }




    @FXML
    public void initialize() {
        // Iterate through all the HBox elements in the VBox
        if (designNumber == 1){
            watchingDesign1();
        } else if (designNumber == 2) {
            System.out.println("design 2");             //DELETE THIS !!!

        }


    }



}

