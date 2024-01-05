package imagespkg;

import java.util.HashMap;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * The purpose of this class is to be able to create and hold data on an image.
 */
public class Imageloader {
  private int width;
  private int height;
  private Pixel[][] pixels;


  /**
   * The purpose of this contructor is to create a new empty image inorder to do calculations later.
   *
   * @param width  The width of the new image.
   * @param height The height of the new image.
   */
  public Imageloader(int width, int height) {
    this.width = width;
    this.height = height;
    this.pixels = new Pixel[height][width];
  }

  /**
   * This is to load an image with filepath filename.
   *
   * @param filename This should be the name of the file path for the image to be loaded.
   * @throws IOException This should be thrown when an invalid filepath is used.
   */
  public Imageloader(String filename) throws IOException {
    if (filename.endsWith(".ppm")) {
      loadFromPPM(filename);
    } else {
      loadFromBufferedImage(filename);
    }
  }

  /**
   * This is to load an image from a provided bufferedImage needed for histograms.
   */
  public Imageloader(BufferedImage bufferedImage) {


    this.width = bufferedImage.getWidth();
    this.height = bufferedImage.getHeight();
    this.pixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int rgb = bufferedImage.getRGB(j, i);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        this.pixels[i][j] = new Pixel(red, green, blue);
      }
    }
  }


  private void loadFromPPM(String filename) {
    Scanner sc;
    int maxValue;
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return;
    }

    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    if (!sc.next().equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
      return;
    }

    this.width = sc.nextInt();
    this.height = sc.nextInt();
    //this.maxValue = sc.nextInt();
    maxValue = sc.nextInt();
    this.pixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        this.pixels[i][j] = new Pixel(r, g, b);
      }
    }
  }

  private void loadFromBufferedImage(String filename) throws IOException {


    BufferedImage bufferedImage = ImageIO.read(new File(filename));
    this.width = bufferedImage.getWidth();
    this.height = bufferedImage.getHeight();
    this.pixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int rgb = bufferedImage.getRGB(j, i);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        this.pixels[i][j] = new Pixel(red, green, blue);
      }
    }


  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public Pixel getPixel(int x, int y) {
    return pixels[y][x];
  }

  public void setPixel(int x, int y, Pixel pixel) {
    this.pixels[y][x] = pixel;
  }

  /**
   * The purpose of this method is get frequencies for the histogram.
   */
  public HashMap<String, HashMap<Integer, Integer>> getfreqs() {
    HashMap<String, HashMap<Integer, Integer>> finalfreqs =
            new HashMap<String, HashMap<Integer, Integer>>();
    HashMap<Integer, Integer> greenfreq = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> bluefreq = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> redfreq = new HashMap<Integer, Integer>();

    for (int i = 0; i < this.getHeight(); i++) {
      for (int j = 0; j < this.getWidth(); j++) {
        if (!redfreq.containsKey(this.getPixel(j, i).getRed())) {
          redfreq.put(this.getPixel(j, i).getRed(), 1);
        } else {
          redfreq.put(this.getPixel(j, i).getRed(), redfreq.get(this.getPixel(j, i).getRed()) + 1);
        }
        if (!greenfreq.containsKey(this.getPixel(j, i).getGreen())) {
          greenfreq.put(this.getPixel(j, i).getGreen(), 1);
        } else {
          greenfreq.put(this.getPixel(j, i).getGreen(),
                  greenfreq.get(this.getPixel(j, i).getGreen()) + 1);
        }
        if (!bluefreq.containsKey(this.getPixel(j, i).getBlue())) {
          bluefreq.put(this.getPixel(j, i).getBlue(), 1);
        } else {
          bluefreq.put(this.getPixel(j, i).getBlue(), bluefreq.get(this.getPixel(j, i).getBlue())
                  + 1);
        }
      }
    }

    finalfreqs.put("Red", redfreq);
    finalfreqs.put("Green", greenfreq);
    finalfreqs.put("Blue", bluefreq);

    return finalfreqs;
  }


  /**
   * This class aims to hold the red, green, and blue values at a given pixel.
   */
  public static class Pixel {
    private int red;
    private int green;
    private int blue;

    /**
     * This class is created when inputted a red, green, blue value.
     */
    public Pixel(int red, int green, int blue) {
      this.red = red;
      this.green = green;
      this.blue = blue;
    }

    public int getRed() {
      return red;
    }

    public void setRed(int red) {
      this.red = red;
    }

    public int getGreen() {
      return green;
    }

    public void setGreen(int green) {
      this.green = green;
    }

    public int getBlue() {
      return blue;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      Imageloader.Pixel pixel = (Imageloader.Pixel) obj;
      return red == pixel.red &&
              green == pixel.green &&
              blue == pixel.blue;
    }

    @Override
    public int hashCode() {
      return java.util.Objects.hash(red, green, blue);
    }


  }
}