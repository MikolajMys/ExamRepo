package com.example.lab1306.client;
import com.example.lab1306.Dot;
import com.example.lab1306.HelloController;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket;
    private PrintWriter writer;
    private HelloController controller;

    public ServerThread(String address, int port, HelloController controller) {
        this.controller = controller;
        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);
            String message;
            while ((message = reader.readLine()) != null){
                System.out.println("Client: "+message);

                Dot dot = Dot.fromMessage(message);
                this.controller.drawCircle(dot.x(), dot.y(), dot.radius(), dot.color());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message){
        writer.println(message);
    }
    public void draw(String message){
        double x=0,y=0,rad=0;
        long color=0;
        for (String s : message.split(";")) {
            char definer = s.charAt(0);
            switch (definer){
                case'x' -> {
                    String[] values = s.split(":");
                    x = Double.valueOf(values[1]);
                }
                case'y' -> {
                    String[] values = s.split(":");
                    y = Double.valueOf(values[1]);
                }
                case'r' -> {
                    String[] values = s.split(":");
                    rad = Double.valueOf(values[1]);
                }
                case'c' -> {
                    String[] values = s.split(":");
                    color = Long.parseLong(values[1], 16);
                    System.out.println(color);
                }
            }
        }
        controller.drawCircle(x, y, rad, Color.valueOf(Integer.toHexString((int) color)));
    }
}