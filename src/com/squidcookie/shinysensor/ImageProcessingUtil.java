package com.squidcookie.shinysensor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageProcessingUtil {
    public static final int WHITE = 0xffffffff;
    public static final int BLACK = 0xff000000;

    public static BufferedImage filterPixels(BufferedImage image, Color upper, Color lower) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage filtered = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color pixel = new Color(image.getRGB(i, j));

                if (pixel.getRed() < upper.getRed() && pixel.getRed() > lower.getRed() &&
                        pixel.getGreen() < upper.getGreen() && pixel.getGreen() > lower.getGreen() &&
                        pixel.getBlue() < upper.getBlue() && pixel.getBlue() > lower.getBlue()) {
                    filtered.setRGB(i, j, WHITE);
                } else {
                    filtered.setRGB(i, j, BLACK);
                }
            }
        }
        return filtered;
    }
}
