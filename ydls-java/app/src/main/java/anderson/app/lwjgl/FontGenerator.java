package anderson.app.lwjgl;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FontGenerator {
    public static void main(String[] args) {
        String outputDir = "app/src/main/resources/anderson";
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.,!?- ";
        int charWidth = 32;
        int charHeight = 32;
        
        // Create output directory if it doesn't exist
        new File(outputDir).mkdirs();
        
        // Create a font
        Font font = new Font("Arial", Font.BOLD, 24);
        
        for (char c : chars.toCharArray()) {
            // Create image for the character
            BufferedImage image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            
            // Set rendering hints for better quality
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            // Set background to transparent
            g2d.setColor(new Color(0, 0, 0, 0));
            g2d.fillRect(0, 0, charWidth, charHeight);
            
            // Draw the character
            g2d.setColor(Color.WHITE);
            g2d.setFont(font);
            
            // Center the character
            FontMetrics metrics = g2d.getFontMetrics();
            int x = (charWidth - metrics.charWidth(c)) / 2;
            int y = ((charHeight - metrics.getHeight()) / 2) + metrics.getAscent();
            
            g2d.drawString(String.valueOf(c), x, y);
            g2d.dispose();
            
            // Save the image
            try {
                ImageIO.write(image, "PNG", new File(outputDir + "/font_" + c + ".png"));
                System.out.println("Generated font_" + c + ".png");
            } catch (IOException e) {
                System.err.println("Error saving font_" + c + ".png: " + e.getMessage());
            }
        }
    }
} 