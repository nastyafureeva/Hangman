module org.example.field.game {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.field.game to javafx.fxml;
    exports org.example.field.game;
}