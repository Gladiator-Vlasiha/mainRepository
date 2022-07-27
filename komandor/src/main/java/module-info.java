module com.example.komandor {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.komandor to javafx.fxml;
    exports com.example.komandor;
}