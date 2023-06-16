package com.example.lab1306.server;
import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    public Socket getSocket() {
        return socket;
    }

    private Socket socket;
    private Server server;
    private PrintWriter writer;
    public ClientThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }
    public void run(){
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);
            String message;
            while ((message = reader.readLine()) != null){
                System.out.println("Server: "+message);
                server.broadcast(message);
            }
            server.removeClient(this);
        }
        catch (IOException e) {
            server.removeClient(this);
            e.printStackTrace();
        }
    }
    public void send(String message){
        writer.println(message);
    }
}