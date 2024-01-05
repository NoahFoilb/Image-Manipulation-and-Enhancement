package imagespkg.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;

import imagespkg.Imageloader;

/**
 * This class aims to take in an image loaded on the controller and download it on the user's
 * computer.
 */
public class Save {

  /**
   * The purpose of this class it to take in an image, a file path, and what type an image we want
   * it downloaded as (format). Should throw an exception when there is an invalid filepath.
   */
  public void saveImage(Imageloader image, String path, String format) throws IOException {
    if (format.equalsIgnoreCase("ppm")) {
      saveAsPPM(image, path);
    } else if (format.equalsIgnoreCase("png") ||
            format.equalsIgnoreCase("jpg") ||
            format.equalsIgnoreCase("jpeg")) {
      saveAsJPGorPNG(image, path, format);
    } else {
      throw new IOException("Unsupported file format: " + format);
    }
  }

  private void saveAsPPM(Imageloader image, String path) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(path));
    writer.write("P3\n");
    writer.write(image.getWidth() + " " + image.getHeight() + "\n");
    writer.write("255\n");

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        Imageloader.Pixel pixel = image.getPixel(j, i);
        writer.write(pixel.getRed() + " " + pixel.getGreen() + " " + pixel.getBlue() + " ");
      }
      writer.write("\n");
    }

    writer.close();
  }


  private void saveAsJPGorPNG(Imageloader image, String path, String format) throws IOException {
    BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(),
            BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        Imageloader.Pixel pixel = image.getPixel(j, i);
        int rgb = (pixel.getRed() << 16) | (pixel.getGreen() << 8) | pixel.getBlue();
        bufferedImage.setRGB(j, i, rgb);
      }
    }


    javax.imageio.ImageIO.write(bufferedImage, format, new File(path));

  }
}