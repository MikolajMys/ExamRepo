package org.example;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageProcessor {
    BufferedImage image;
    int height, width;
    public void loadImage(String path) throws IOException {
        File file = new File(path);
        image = ImageIO.read(file);
        height = image.getHeight();
        width = image.getWidth();
    }
    public void saveImage(String path) throws IOException {
        ImageIO.write(image, "png", new File(path));
    }
    private int clamp(int val){
        if(val > 255)
            val = 255;
        else if(val<0)
            val = 0;
        return val;
    }
    private int getPixelAfterBright(int color,int factor){
        Color actColor = new Color(color);

        int r = actColor.getRed();
        int g = actColor.getGreen();
        int b = actColor.getBlue();

        int r_bright = clamp(r + factor);
        int g_bright = clamp(g + factor);
        int b_bright = clamp(b + factor);

        Color newColor = new Color(r_bright, g_bright, b_bright);

        return newColor.getRGB();
    }
    public void setBrightness(int factor){
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                image.setRGB(x, y, getPixelAfterBright(image.getRGB(x, y), factor));

//                int rgb = image.getRGB(x,y);
//                int r = (0xFF0000 & rgb)>>16;
//                int g = (0xFF00 & rgb)>>8;
//                int b = 0xFF & rgb;
//                r = getPixelAfterBright(offset,r);
//                g = getPixelAfterBright(offset,g);
//                b = getPixelAfterBright(offset,b);
//                rgb = b | (g << 8) | (r << 16);
//                image.setRGB(x, y, rgb);

//                Color color1 = new Color(rgb);
//                color1.getRed();
//                Color color2 = new Color(color1.getRed() +100, ...);
//                color2.getRGB();
            }
        }
    }
    public void setBrightnessWithThreads(int factor) throws InterruptedException {
        int cores = Runtime.getRuntime().availableProcessors();
        int newHeight = height/cores;
        Thread threads[] = new Thread[cores];
        for (int i = 0; i < cores; i++) {
            int index = i;
            Thread thread = new Thread(() -> {
                for(int x = 0; x < width; x++) {
                    int start = newHeight * index;
                    int end = newHeight * index + newHeight;
                    if(end > newHeight) {
                        end = newHeight;
                    }
                    for (int y = start; y < end; y++) {
                        image.setRGB(x, y, getPixelAfterBright(image.getRGB(x, y), factor));
                    }
                }
            });
            threads[i]=thread;
            threads[i].start();
        }
        for (int i = 0; i < cores; i++) {
            threads[i].join();
        }
    }
}
