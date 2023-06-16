package com.example.lab1306;

import com.example.lab1306.client.ServerThread;
import com.example.lab1306.server.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;

import static com.example.lab1306.server.Server.*;

public class HelloController {
    public Slider radiusSlider;
    public Canvas canvas;
    public ColorPicker colorPicker;
    public TextField portField;
    public TextField addressField;
    public Server server;
    public ServerThread serverThread;


    public void onStartServerClicked(ActionEvent actionEvent) throws SQLException {
//        getConnection();
//        System.out.println("connected to database");
//        dropDatabase();
        String address = addressField.getText();
        int port = Integer.parseInt(portField.getText());

        this.server = new Server(port);
        this.server.listen();

        serverThread = new ServerThread(address, port, this);
        serverThread.start();
    }

    public void onConnectClicked(ActionEvent actionEvent) throws SQLException {
        //getConnection();
        String address=addressField.getText();
        int port= Integer.parseInt(portField.getText());
//        for(Dot dot:getSavedDots())
//        {
//            drawCircle(dot.x(),dot.y(),dot.radius(),dot.color());
//        }

        serverThread = new ServerThread(address, port, this);
        serverThread.start();
    }

    public void onMouseClicked(MouseEvent mouseEvent) {
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
        double radius = radiusSlider.getValue();
        Color color = colorPicker.getValue();

//        drawCircle(x,y,radius,color);
        Dot dot = new Dot(x, y, radius, color);
        String message = Dot.toMessage(dot);
        this.serverThread.send(message);

    }

    public void drawCircle(double x, double y, double radius, Color color){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(color);
        graphicsContext.fillOval(x - radius,y - radius,radius * 2 ,radius * 2);
    }
}