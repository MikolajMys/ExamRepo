package com.example.gui_client;

import client.ServerThread;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.List;

public class HelloController {
    private ServerThread serverThread;
    private ClientGuiReceiver receiver;
    public TextArea outputArea;
    public TextField inputField;

    public Button sendButton;
    public Button sendFileButton;
    public ListView clientList;
    public ProgressBar fileProgressBar;
    public GridPane mainPane;

    public HelloController(ServerThread serverThread, ClientGuiReceiver receiver) {
        this.serverThread = serverThread;
        this.receiver = receiver;
        receiver.setController(this);
    }
    public void send(){
        String text = inputField.getText();
        serverThread.broadcast(text);
        inputField.clear();
    }

    public void initialize(){
        serverThread.online();
    }

    public void showBroadcast(String sender, String message){
        outputArea.appendText("\n"+sender+": "+message);
    }
    public void addToClients(String clientName){
        clientList.getItems().add(clientName);
    }
    public void removeFromClients(String clientName){
        clientList.getItems().remove(clientName);
    }
    public void populateOnlineList(List<String> clientNames){
        clientList.getItems().clear();
        clientNames.stream()
            .forEach(name -> clientList.getItems().add(name));
    }

}