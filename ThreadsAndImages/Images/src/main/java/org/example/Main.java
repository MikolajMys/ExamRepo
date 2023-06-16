package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        Runtime.getRuntime().availableProcessors();
//
//        Thread thread = new Thread(() -> {
//
//        });

        //thread.start();

        //thread.join();



        ImageProcessor imageProcessor1 = new ImageProcessor();
        imageProcessor1.loadImage("res/pope.jpg");
        imageProcessor1.setBrightness(100);
        imageProcessor1.saveImage("res/out.png");

        ImageProcessor imageProcessor2 = new ImageProcessor();
        imageProcessor2.loadImage("res/pope.jpg");
        imageProcessor2.setBrightness(100);
        imageProcessor2.saveImage("res/out.png");


    }
}