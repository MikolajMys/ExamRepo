package com.example.lab1306.server;
import com.example.lab1306.Dot;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{
    private ServerSocket serverSocket;
    static Connection connection;
    private List<ClientThread> clients = new ArrayList<>();

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
                ClientThread thread = new ClientThread(clientSocket, this);
                clients.add(thread);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public void listen() throws SQLException {
        Thread listen = new Thread(() ->{
            while (true)
            {
                try {
                    Socket socket=serverSocket.accept();
                    ClientThread client=new ClientThread(socket,this);
                    clients.add(client);
                    System.out.println("client connected");
                    client.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }});
        listen.start();
    }

    public void removeClient(ClientThread client) {
        clients.remove(client);
    }

    public void broadcast(String message) {
        for (var currentClient : clients)
            currentClient.send(message);
    }

    //pirat
    public static void dropDatabase() throws SQLException {
        PreparedStatement query=connection.prepareStatement("DELETE FROM DOT;");
        query.execute();
    }
    public static void getConnection()
    {
        try {
            connection= DriverManager.getConnection("C:\\Users\\Dell\\Desktop\\oop\\13.06\\Lab13-06\\dot.db");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } {
    }
    }
    public static List<Dot> getSavedDots() throws SQLException {
        List<Dot>dots=new ArrayList<>();
        PreparedStatement query=connection.prepareStatement("SELECT x,y,radius,color from dot;");
        query.execute();
        ResultSet set=query.getResultSet();
        while (set.next())
        {
            double x=set.getDouble("x");
            double y=set.getDouble("y");
            double radius=set.getDouble("radius");
            Color color=Color.valueOf(set.getString("color"));
            dots.add(new Dot(x,y,radius,color));
        }
        return dots;
    }
}