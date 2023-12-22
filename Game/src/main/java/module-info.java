module java{
    requires javafx.controls;
    requires javafx.fxml;



    exports client;
    opens client to javafx.fxml;
    exports server;
    opens server to javafx.fxml;
}