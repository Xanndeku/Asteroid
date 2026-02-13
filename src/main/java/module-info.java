module com.example.asteroid {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.asteroid to javafx.fxml;
    exports com.example.asteroid;
}