module com.example.gui_client {
    requires javafx.controls;
    requires javafx.fxml;
    requires Client;


    opens com.example.gui_client to javafx.fxml;
    exports com.example.gui_client;
}