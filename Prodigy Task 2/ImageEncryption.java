import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageEncryption {

    public static void main(String[] args) {
        String imagePath = "image.jpg";
        encryptImageFile(imagePath);
        decryptImageFile("encrypted_image.jpg");
    }

    public static void encryptImageFile(String imagePath) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            int width = img.getWidth();
            int height = img.getHeight();
            int[][][] pixels = new int[width][height][3];


            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixel = img.getRGB(x, y);
                    pixels[x][y][0] = (pixel >> 16) & 0xff; 
                    pixels[x][y][1] = (pixel >> 8) & 0xff; 
                    pixels[x][y][2] = pixel & 0xff; 
                }
            }

            
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int temp = pixels[x][y][0];
                    pixels[x][y][0] = pixels[x][y][1];
                    pixels[x][y][1] = temp;
                }
            }

          
            BufferedImage encryptedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixel = (pixels[x][y][0] << 16) | (pixels[x][y][1] << 8) | pixels[x][y][2];
                    encryptedImg.setRGB(x, y, pixel);
                }
            }

            File encryptedFile = new File("encrypted_image.jpg");
            ImageIO.write(encryptedImg, "jpg", encryptedFile);
        } catch (IOException e) {
            System.err.println("Error encrypting image: " + e.getMessage());
        }
    }

    public static void decryptImageFile(String imagePath) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            int width = img.getWidth();
            int height = img.getHeight();
            int[][][] pixels = new int[width][height][3];

            
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixel = img.getRGB(x, y);
                    pixels[x][y][0] = (pixel >> 16) & 0xff; 
                    pixels[x][y][1] = (pixel >> 8) & 0xff; 
                    pixels[x][y][2] = pixel & 0xff; 
                }
            }

          
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int temp = pixels[x][y][0];
                    pixels[x][y][0] = pixels[x][y][1];
                    pixels[x][y][1] = temp;
                }
            }

            BufferedImage decryptedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixel = (pixels[x][y][0] << 16) | (pixels[x][y][1] << 8) | pixels[x][y][2];
                    decryptedImg.setRGB(x, y, pixel);
                }
            }

            
            File decryptedFile = new File("decrypted_image.jpg");
            ImageIO.write(decryptedImg, "jpg", decryptedFile);
        } catch (IOException e) {
            System.err.println("Error decrypting image: " + e.getMessage());
        }
    }
}