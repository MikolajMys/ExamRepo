package com.example.lab1306;

import javafx.scene.paint.Color;

public record Dot(double x, double y, double radius, Color color) {
    public static String toMessage(Dot dot) {
//        int red = (int) (dot.color.getRed() * 255);
//        int green = (int) (dot.color.getGreen() * 255);
//        int blue = (int) (dot.color.getBlue() * 255);
//
//        String colorString = String.format("#%02X%02X%02X", red, green, blue);

        String message = String.format("%.0f,%.0f,%.0f,%s", dot.x, dot.y, dot.radius, dot.color.toString());
        return message;
    }




    public static Dot fromMessage(String message) {
        String[] parts = message.split(",");
        double centerX = Double.parseDouble(parts[0]);
        double centerY = Double.parseDouble(parts[1]);
        double radius = Double.parseDouble(parts[2]);

        System.out.println(parts[3]);


//        Color color = Color.valueOf(Integer.toHexString((int) Long.parseLong(parts[3], 16)));
        Color color = Color.web(parts[3]);
//        Color color = Color.web("#FF0000");
        return new Dot(centerX, centerY, radius, color);
    }
}