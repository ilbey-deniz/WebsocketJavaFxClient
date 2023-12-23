package com.example.websocketjavafxclient;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;




public class HelloController {
    @FXML
    private TextField nicknameTextField;
    @FXML
    private TextField messageTextField;
    @FXML
    private ScrollPane chatScrollPane;
    @FXML
    private TextFlow chatFlow;
    private WebSocketClient client;


    @FXML
    public void initialize() {
        URI uri = null;
        try {
            uri = new URI("ws://localhost:8080/simple");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 20; i++) {
            writeToChatBox("");
        }
        client = HelloClient.create(uri, this::onMessage);
        client.connect();
    }

    @FXML
    protected void onSendButtonClick() {
        String message = messageTextField.getText();
        if(!message.isEmpty() || !nicknameTextField.getText().isEmpty()) {
            client.send(nicknameTextField.getText() + ": " + message);
        }
    }

    @FXML
    protected void onEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            onSendButtonClick();
            messageTextField.setText("");
        }
    }

    protected void onMessage(String message) {
        Platform.runLater(() -> writeToChatBox(message));
    }

    private void writeToChatBox(String message) {
        chatFlow.getChildren().add(new Text(message + "\n"));
        chatScrollPane.applyCss();
        chatScrollPane.layout();
        chatScrollPane.setVvalue(1.0);
    }

    @FXML
    protected void shutdown() {
        client.close();
        Platform.exit();
    }
}