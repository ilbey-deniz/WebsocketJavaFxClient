module com.example.websocketjavafxclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.java_websocket;
    requires java.desktop;


    opens com.example.websocketjavafxclient to javafx.fxml;
    exports com.example.websocketjavafxclient;
}