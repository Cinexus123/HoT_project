package com.api.utils;

import ij.IJ;
import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageProcessor;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
public class MapDrawer {
    private ImagePlus img;
    private int diameter = 380;
    private int radius = diameter / 2;

    public MapDrawer open(String path) {
        img = IJ.openImage(path);
        return this;
    }

    public MapDrawer drawDot(Integer x, Integer y, String name) {
        BufferedImage bufferedImage = img.getBufferedImage();
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setColor(Color.RED);
        graphics.setStroke(new BasicStroke(10));
        graphics.drawOval(x - radius, y - radius, diameter, diameter);

        graphics.setComposite(AlphaComposite.SrcOver.derive(0.8f));
        graphics.setColor(new Color(255, 0, 0, 88));
        graphics.fillOval(x - radius, y - radius, diameter, diameter);
        graphics.setComposite(AlphaComposite.SrcOver);

        img = new ImagePlus(img.getTitle(), bufferedImage);
        ImageProcessor ip = img.getProcessor();
        ip.setColor(Color.RED);
        ip.setLineWidth(15);
        ip.drawDot(x, y);
        ip.setColor(Color.BLACK);
        ip.setFont(ip.getFont().deriveFont(30f));
        ip.drawString(name, x + 25, y + 15);
        return this;
    }

    private void extendImage() {
        BufferedImage image = img.getBufferedImage();
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight() + 250, image.getType());

        Graphics graphics = newImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
        graphics.drawImage(image, 0, 0, null);

        img = new ImagePlus(img.getTitle(), newImage);
    }

    public MapDrawer drawDescription(String username, String building, String floor, String description, String timeStamp) {
        extendImage();
        ImageProcessor ip = img.getProcessor();
        ip.createImage();
        ip.setFont(ip.getFont().deriveFont(30f));
        ip.setColor(Color.BLACK);

        ip.drawString("Location of: " + username, 50, ip.getHeight() - 220);
        ip.drawString("Building: " + building + "  " + "Floor: " + floor.substring(0,floor.length()-4), 50, ip.getHeight() - 180);
        ip.drawString("Description: " + description, 50, ip.getHeight()-100);
        ip.drawString("Last seen: " + timeStamp.replace("T"," "), 50, ip.getHeight()-60);
        return this;
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();

        try {
            ImageIO.write(img.getBufferedImage(), "png", bao);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bao.toByteArray();
    }

    public void saveAsPng(String path) {
        new FileSaver(img).saveAsPng(path);
    }
}
