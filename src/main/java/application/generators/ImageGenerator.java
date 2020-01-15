package application.generators;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ImageGenerator {

    public static File generateImage(String path, int height, int width) throws IOException {
        BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB );
        Random random = new Random();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int randomValue = random.nextInt(255);
                image.setRGB(x, y, new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
            }
        }
        File outputFile = new File(path);
        ImageIO.write(image, "jpg", outputFile);
        return outputFile;
    }
}

