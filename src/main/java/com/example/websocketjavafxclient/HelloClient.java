package com.example.websocketjavafxclient;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.awt.event.ActionEvent;

public class HelloClient extends WebSocketClient{

    public HelloClient(URI serverURI) {
        super(serverURI);
    }

    public static WebSocketClient create(URI uri, HelloClient.MessageHandler messageHandler) {
        return new HelloClient(uri) {
            @Override
            public void onMessage(String message) {
                messageHandler.handleMessage(message);
            }
        };
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("opened connection");
        // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient
    }

    @Override
    public void onMessage(String message) {

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // The close codes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println(
                "Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: "
                        + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
        // if the error is fatal then onClose will be called additionally
    }

    public interface MessageHandler {
        void handleMessage(String message);
    }
}