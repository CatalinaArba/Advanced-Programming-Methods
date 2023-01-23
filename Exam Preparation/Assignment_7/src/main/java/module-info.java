module com.example.assignment_7 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.logging;

    opens GUI to javafx.fxml;
    exports GUI;
}