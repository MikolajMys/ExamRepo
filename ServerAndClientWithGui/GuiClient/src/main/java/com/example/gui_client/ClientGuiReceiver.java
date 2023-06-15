package com.example.gui_client;

import client.ClientReceiver;
import javafx.application.Platform;

import java.util.Arrays;

public class ClientGuiReceiver implements ClientReceiver {
    HelloController controller = null;

    public void setController(HelloController controller) {
        this.controller = controller;
    }

    @Override
    public void receiveBroadcast(String sender, String message) {
        Platform.runLater(()->controller.showBroadcast(sender, message));
    }

    @Override
    public void receiveWhisper(String s, String s1) {

    }

    @Override
    public void receiveFile(String s, long l, String s1) {

    }

    @Override
    public void receiveLoginBroadcast(String sender) {
        Platform.runLater(()->controller.addToClients(sender));
    }

    @Override
    public void receiveLogoutBroadcast(String sender) {
        Platform.runLater(()->controller.removeFromClients(sender));
    }

    @Override
    public void receiveOnline(String[] strings) {
        Platform.runLater(()->controller.populateOnlineList(Arrays.stream(strings).toList()));
    }

    @Override
    public void receiveFileProgress(int i) {

    }
}
