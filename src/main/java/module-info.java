module com.example.groupassessment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.groupassessment to javafx.fxml;
    exports com.example.groupassessment;
}