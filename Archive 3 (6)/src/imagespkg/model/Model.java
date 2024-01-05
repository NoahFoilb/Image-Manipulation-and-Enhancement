package imagespkg.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import imagespkg.Imageloader;

/**
 * This class represents the model, this class gets instructions from the controller
 * and performs the necessary operations.
 */
public class Model implements IModel {

  private Map<String, Imageloader> tempMap;

  /**
   * Constructs a new model simulator.
   */
  public Model() {

    tempMap = new HashMap<>();
  }

  /**
   * Brighten the image by the given increment to create a new image, referred to henceforth by the
   * given newname.
   *
   * @param image Image we want to convert into this new image.
   * @param value incremental increase/decrease based on int value.
   * @throws IllegalArgumentException Throws ann exception when it encounters an error for
   *                                  the controller to pick up.
   */
  @Override
  public Map<String, Imageloader> brighten(Imageloader image, int value, String newname) throws
          IllegalArgumentException {
    tempMap.clear();

    int width = image.getWidth();
    int height = image.getHeight();
    Imageloader outputImage = new Imageloader(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Imageloader.Pixel pixel = image.getPixel(j, i);

        int r = clamp(pixel.getRed() + value);
        int g = clamp(pixel.getGreen() + value);
        int b = clamp(pixel.getBlue() + value);

        outputImage.setPixel(j, i, new Imageloader.Pixel(r, g, b));
      }
    }

    tempMap.put(newname, outputImage);
    return tempMap;

  }

  /**
   * The purpose of this method is to take an image and convert it into a new image with only the
   * selected components transformation within the image.
   *
   * @param image         Image we want to convert into its specific part.
   * @param componenttype Needs to be of string "red, blue, green, luma, value,
   *                      intensity.
   * @throws IllegalArgumentException If component type doesnt exist AND when image is not
   *                                  found, throw an exception.
   */

  @Override
  public Map<String, Imageloader> component(Imageloader image, String componenttype,
                                            String newname) throws IllegalArgumentException {
    tempMap.clear();
    int width = image.getWidth();
    int height = image.getHeight();

    switch (componenttype) {
      case "red":
        processImage(image, newname, pixel -> new
                Imageloader.Pixel(pixel.getRed(), 0, 0));

        break;

      case "green":
        processImage(image, newname, pixel ->
                new Imageloader.Pixel(0, pixel.getGreen(), 0));

        break;

      case "blue":
        processImage(image, newname, pixel ->
                new Imageloader.Pixel(0, 0, pixel.getBlue()));
        break;

      case "blank":
        processImage(image, newname, pixel ->
                new Imageloader.Pixel(0, 0, 0));
        break;

      case "value":
        processImage(image, newname, pixel -> {
          int value = Math.max(pixel.getRed(), Math.max(pixel.getGreen(), pixel.getBlue()));
          return new Imageloader.Pixel(value, value, value);
        });
        break;

      case "luma":
        processImage(image, newname, pixel -> {
          int luma = (int) (0.2126 * pixel.getRed() +
                  0.7152 * pixel.getGreen() +
                  0.0722 * pixel.getBlue());
          return new Imageloader.Pixel(luma, luma, luma);
        });
        break;

      case "intensity":

        processImage(image, newname, pixel -> {
          int intensity = (pixel.getRed() + pixel.getGreen() +
                  pixel.getBlue()) / 3;
          return new Imageloader.Pixel(intensity, intensity,
                  intensity);
        });
        break;

      default:
        System.out.print("not valid");
    }

    return tempMap;
  }

  private void processImage(Imageloader image, String newname, Function<Imageloader.Pixel,
          Imageloader.Pixel> pixelProcessor) {
    int width = image.getWidth();
    int height = image.getHeight();
    Imageloader processedImage = new Imageloader(width, height);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Imageloader.Pixel originalPixel = image.getPixel(j, i);
        Imageloader.Pixel newPixel = pixelProcessor.apply(originalPixel);
        processedImage.setPixel(j, i, newPixel);
      }
    }
    tempMap.put(newname, processedImage);
  }


  /**
   * Flip an image horizontally or vertically to create a new image, referred to henceforth by
   * newname.
   *
   * @param image    Image we want to convert into its specific part.
   * @param fliptype Needs to be of string "horizontal or vertical".
   * @throws IllegalArgumentException When it is not hor or vert AND when image is not found.
   */
  @Override
  public Map<String, Imageloader> flip(Imageloader image, String fliptype, String newname)
          throws IllegalArgumentException {
    tempMap.clear();
    int width = image.getWidth();
    int height = image.getHeight();

    if (fliptype.equals("vertical")) {
      Imageloader flippedImageV = new Imageloader(width, height);

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          flippedImageV.setPixel(j, (height - 1) - i, image.getPixel(j, i));
        }
      }
      tempMap.put(newname, flippedImageV);
    } else if (fliptype.equals("horizontal")) {
      Imageloader flippedImageH = new Imageloader(width, height);

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          flippedImageH.setPixel((width - 1) - j, i, image.getPixel(j, i));
        }
      }
      tempMap.put(newname, flippedImageH);
    }
    return tempMap;
  }


  /**
   * Split the given image into three images containing its red, green and blue components
   * respectively.
   *
   * @param originalimage  Image that should be split.
   * @param blueimagename  Blue image component new name.
   * @param greenimagename Green image component new name
   * @param redimagename   Red image component new name.
   * @throws IllegalArgumentException Throws ann exception when it encounters an error for
   *                                  the controller to pick up.
   */
  @Override
  public Map<String, Imageloader> rgbsplit(Imageloader originalimage, String redimagename,
                                           String greenimagename, String blueimagename) throws
          IllegalArgumentException {
    tempMap.clear();
    HashMap<String, Imageloader> tempMap2;
    tempMap2 = new HashMap<String, Imageloader>();
    Map<String, Imageloader> red_image = component(originalimage, "red",
            redimagename);
    tempMap2.putAll(red_image);
    Map<String, Imageloader> blue_image = component(originalimage, "blue",
            blueimagename);
    tempMap2.putAll(blue_image);
    Map<String, Imageloader> green_image = component(originalimage, "green",
            greenimagename);

    tempMap2.putAll(green_image);
    tempMap.putAll(tempMap2);


    return tempMap;
  }

  /**
   * Combine the three greyscale images into a single image that gets its red, green and blue
   * components from the three images respectively.
   *
   * @param newname    New image name.
   * @param redimage   Image we want to red component of.
   * @param greenimage Image we want to green component of.
   * @param blueimage  Image we want to blue component of.
   * @throws IllegalArgumentException Throws ann exception when it encounters an error for
   *                                  the controller to pick up.
   */
  @Override
  public Map<String, Imageloader> rgbcombine(String newname, Imageloader redimage, Imageloader
          greenimage, Imageloader blueimage) throws IllegalArgumentException {
    tempMap.clear();
    Imageloader temp_image = new Imageloader(redimage.getWidth(), redimage.getHeight());

    for (int i = 0; i < temp_image.getHeight(); i++) {
      for (int j = 0; j < temp_image.getWidth(); j++) {
        temp_image.setPixel(j, i, new Imageloader.Pixel(redimage.getPixel(j, i).getRed(),
                greenimage.getPixel(j, i).getGreen(),
                blueimage.getPixel(j, i).getBlue()));
      }
    }

    tempMap.put(newname, temp_image);

    return tempMap;
  }


  /**
   * blur the given image and store the result in another image with the given name.
   *
   * @param image   Image that should be blurred.
   * @param newname New image name that should be saved to the blurred image.
   * @throws IllegalArgumentException Throws ann exception when it encounters an error for
   *                                  the controller to pick up.
   */
  @Override
  public Map<String, Imageloader> blur(Imageloader image, String newname) throws
          IllegalArgumentException {
    tempMap.clear();
    Imageloader outputImage = new Imageloader(image.getWidth(), image.getHeight());

    double[][] filter = {
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {


        double red_return = 0;
        double blue_return = 0;
        double green_return = 0;


        for (int i_new = -1; i_new <= 1; i_new++) {

          for (int j_new = -1; j_new <= 1; j_new++) {

            int pixelX = j + j_new;
            int pixelY = i + i_new;

            // if out of boundary
            if (pixelX < 0 || pixelX >= image.getWidth() || pixelY < 0 || pixelY >=
                    image.getHeight()) {
              continue;
            }
            Imageloader.Pixel pixel = image.getPixel(pixelX, pixelY);
            red_return += filter[i_new + 1][j_new + 1] * pixel.getRed();
            green_return += filter[i_new + 1][j_new + 1] * pixel.getGreen();
            blue_return += filter[i_new + 1][j_new + 1] * pixel.getBlue();
          }
        }

        // Clamp the values and set the pixel in the output image
        outputImage.setPixel(j, i, new Imageloader.Pixel(
                (int) Math.round(clampDouble(red_return)),
                (int) Math.round(clampDouble(green_return)),
                (int) Math.round(clampDouble(blue_return))
        ));
      }

    }
    tempMap.put(newname, outputImage);


    return tempMap;
  }


  /**
   * sharpen the given image and store the result in another image with the given name.
   *
   * @param image   Image that should be sharpened.
   * @param newname New image name that should be saved to the sharpened image
   * @throws IllegalArgumentException Throws ann exception when it encounters an error for
   *                                  the controller to pick up.
   */
  @Override
  public Map<String, Imageloader> sharpen(Imageloader image, String newname) throws
          IllegalArgumentException {
    tempMap.clear();

    Imageloader outputImage = new Imageloader(image.getWidth(), image.getHeight());

    double[][] filter = {
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    };

    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {


        double red_return = 0;
        double blue_return = 0;
        double green_return = 0;


        for (int i_new = -2; i_new <= 2; i_new++) {

          for (int j_new = -2; j_new <= 2; j_new++) {

            int pixelX = j + j_new;
            int pixelY = i + i_new;

            // if out of boundary
            if (pixelX < 0 || pixelX >= image.getWidth() || pixelY < 0 || pixelY >=
                    image.getHeight()) {
              continue;
            }
            Imageloader.Pixel pixel = image.getPixel(pixelX, pixelY);
            red_return += filter[i_new + 2][j_new + 2] * pixel.getRed();
            green_return += filter[i_new + 2][j_new + 2] * pixel.getGreen();
            blue_return += filter[i_new + 2][j_new + 2] * pixel.getBlue();
          }
        }

        // Clamp the values and set the pixel in the output image
        outputImage.setPixel(j, i, new Imageloader.Pixel(
                (int) Math.round(clampDouble(red_return)),
                (int) Math.round(clampDouble(green_return)),
                (int) Math.round(clampDouble(blue_return))
        ));
      }

    }
    tempMap.put(newname, outputImage);

    return tempMap;
  }

  /**
   * produce a sepia-toned version of the given image and store the result in another image with
   * the given name.
   *
   * @param image   Image that should be sepia'd.
   * @param newname New image name that should be saved to the sepia'd image.
   * @throws IllegalArgumentException Throws ann exception when it encounters an error for
   *                                  the controller to pick up.
   */
  @Override
  public Map<String, Imageloader> sepia(Imageloader image, String newname) throws
          IllegalArgumentException {
    tempMap.clear();


    int width = image.getWidth();
    int height = image.getHeight();
    Imageloader temp_image = new Imageloader(width, height);


    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Imageloader.Pixel originalPixel = image.getPixel(j, i);


        int red_return = (int) (0.393 * originalPixel.getRed() +
                0.769 * originalPixel.getGreen() +
                0.189 * originalPixel.getBlue());

        int green_return = (int) (0.349 * originalPixel.getRed() +
                0.686 * originalPixel.getGreen() +
                0.168 * originalPixel.getBlue());

        int blue_return = (int) (0.272 * originalPixel.getRed() +
                0.534 * originalPixel.getGreen() +
                0.131 * originalPixel.getBlue());

        Imageloader.Pixel new_pixel = new Imageloader.Pixel(clamp(red_return), clamp(green_return),
                clamp(blue_return));

        temp_image.setPixel(j, i, new_pixel);


      }
    }
    tempMap.put(newname, temp_image);

    return tempMap;
  }

  @Override
  public Map<String, Imageloader> colorcorrection(Imageloader image, String newname)
          throws IllegalArgumentException {
    tempMap.clear();


    HashMap<String, HashMap<Integer, Integer>> frequencies = image.getfreqs();

    // get rid of these by putting them into the .put
    List<Integer> redpeaks = new ArrayList<Integer>();
    List<Integer> bluepeaks = new ArrayList<Integer>();
    List<Integer> greenpeaks = new ArrayList<Integer>();

    HashMap<String, List<Integer>> peaks = new HashMap<>();
    peaks.put("Red", redpeaks);
    peaks.put("Blue", bluepeaks);
    peaks.put("Green", greenpeaks);

    for (String str : frequencies.keySet()) {

      for (int i : frequencies.get(str).keySet()) {
        if (i < 245 && i > 11) {


          if (peaks.get(str).isEmpty()) {
            peaks.get(str).add(i);
          } else if (frequencies.get(str).get(i) >
                  frequencies.get(str).get(peaks.get(str).get(0))) {
            peaks.get(str).clear();
            peaks.get(str).add(i);
          } else if (Objects.equals(frequencies.get(str).get(i),
                  frequencies.get(str).get(peaks.get(str).get(0)))) {
            peaks.get(str).add(i);
          }
        }
      }

    }

    int denominator = 3;

    // What if there are more than one peak?
    for (String str : frequencies.keySet()) {
      if (peaks.get(str).size() > 1) {
        int sum = 0;
        for (int i = 0; i < peaks.get(str).size(); i++) {
          sum += peaks.get(str).get(i);
        }
        sum = (int) sum / peaks.get(str).size();
        peaks.get(str).clear();
        peaks.get(str).add(sum);

        // if
      } else if (peaks.get(str).isEmpty()) {
        peaks.get(str).add(0);
        denominator -= 1;
      }
    }

    if (denominator != 0) {
      int average = (int) (peaks.get("Red").get(0) + peaks.get("Blue").get(0) +
              peaks.get("Green").get(0)) / denominator;

      Map<String, Imageloader> split = rgbsplit(image, "Red", "Green",
              "Blue");

      Model modelR = new Model();
      Map<String, Imageloader> redchange = modelR.brighten(split.get("Red"),
              average - peaks.get("Red").get(0), "Red");

      Model modelG = new Model();
      Map<String, Imageloader> greenchange = modelG.brighten(split.get("Green"),
              average - peaks.get("Green").get(0), "Green");

      Model modelB = new Model();
      Map<String, Imageloader> bluechange = modelB.brighten(split.get("Blue"),
              average - peaks.get("Blue").get(0), "Blue");


      return rgbcombine(newname, redchange.get("Red"), greenchange.get("Green"),
              bluechange.get("Blue"));
    } else {
      // if there are no

      tempMap.clear();
      tempMap.put(newname, image);
      return tempMap;

    }
  }

  @Override
  public Map<String, Imageloader> leveladjustment(Imageloader image, String newname, int b, int m,
                                                  int w) throws IllegalArgumentException {
    tempMap.clear();

    double z = b * b * (m - w) - b * (m * m - w * w) + w * m * m - m * w * w;
    double aA = -b * (128 - 255) + 128 * w - 255 * m;
    double bA = b * b * (128 - 255) + 255 * m * m - 128 * w * w;
    double cA = b * b * (255 * m - 128 * w) - b * (255 * m * m - 128 * w * w);
    double a = aA / z;
    double bb = bA / z;
    double c = cA / z;


    int width = image.getWidth();
    int height = image.getHeight();
    Imageloader outputImage = new Imageloader(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Imageloader.Pixel pixel = image.getPixel(j, i);

        double r = Math.round(clampDouble(Math.pow(pixel.getRed(), 2) * a +
                pixel.getRed() * bb + c));
        double g = Math.round(clampDouble(Math.pow(pixel.getGreen(), 2) * a +
                pixel.getGreen() * bb + c));
        double blue = Math.round(clampDouble(Math.pow(pixel.getBlue(), 2) * a +
                pixel.getBlue() * bb + c));

        outputImage.setPixel(j, i, new Imageloader.Pixel((int) r, (int) g, (int) blue));
      }
    }

    tempMap.put(newname, outputImage);
    return tempMap;

  }

  @Override
  public Map<String, Imageloader> histogram(Imageloader image, String newname)
          throws IllegalArgumentException {
    tempMap.clear();

    BufferedImage imagemap = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);

    Graphics2D graph = imagemap.createGraphics();
    graph.setBackground(Color.WHITE);
    graph.fillRect(0, 0, 256, 256);
    graph.setColor(Color.LIGHT_GRAY);
    BasicStroke bs = new BasicStroke(1);
    graph.setStroke(bs);

    for (int i = 0; i < 17; i++) {
      graph.drawLine(i * 15, 0, i * 15, 256);
      graph.drawLine(0, i * 15, 256, i * 15);
    }

    HashMap<String, HashMap<Integer, Integer>> frequencies = image.getfreqs();


    // Max peak
    int max = 0;
    for (String str : frequencies.keySet()) {
      for (int i = 0; i < 256; i++) {
        if (frequencies.get(str).containsKey(i)) {
          if (frequencies.get(str).get(i) > max) {
            max = frequencies.get(str).get(i);
          }
        }
      }
    }


    for (int i = 0; i < 256; i++) {
      int green = 0;
      int red = 0;
      int blue = 0;

      if (frequencies.get("Red").containsKey(i)) {
        red = (int) (((double) frequencies.get("Red").get(i) / max) * 255);
      }

      if (frequencies.get("Green").containsKey(i)) {
        green = (int) (((double) frequencies.get("Green").get(i) / max) * 255);
      }

      if (frequencies.get("Blue").containsKey(i)) {
        blue = (int) (((double) frequencies.get("Blue").get(i) / max) * 255);
      }

      int[] values = {red, green, blue};
      Arrays.sort(values);


      if (blue == values[0]) {
        graph.setColor(Color.BLUE);
      } else if (green == values[0]) {
        graph.setColor(Color.GREEN);
      } else if (red == values[0]) {
        graph.setColor(Color.RED);
      }

      graph.drawLine(i, 255, i, 255 - values[0]);

      if (blue == values[1]) {
        graph.setColor(Color.BLUE);
      } else if (green == values[1]) {
        graph.setColor(Color.GREEN);
      } else if (red == values[1]) {
        graph.setColor(Color.RED);
      }

      if (values[0] == values[1]) {
        graph.drawLine(i, 255 - values[0], i, 255 - values[1]);
      } else {
        graph.drawLine(i, 255 - values[0] - 1, i, 255 - values[1]);
      }

      if (blue == values[2]) {
        graph.setColor(Color.BLUE);
      } else if (green == values[2]) {
        graph.setColor(Color.GREEN);
      } else if (red == values[2]) {
        graph.setColor(Color.RED);
      }

      if (values[1] == values[2]) {
        graph.drawLine(i, 255 - values[1], i, 255 - values[2]);
      } else {
        graph.drawLine(i, 255 - values[1] - 1, i, 255 - values[2]);
      }
    }

    graph.dispose();
    tempMap.put(newname, new Imageloader(imagemap));

    return tempMap;
  }

  @Override
  public Map<String, Imageloader> split(Imageloader leftimage, Imageloader rightimage, String
          newname, double percent) throws IllegalArgumentException {

    tempMap.clear();

    int width = leftimage.getWidth();
    int height = leftimage.getHeight();
    Imageloader outputImage = new Imageloader(width, height);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        Imageloader.Pixel pixel;


        double cond = (j * 100 / width);
        if (cond < percent) {
          pixel = leftimage.getPixel(j, i);
        } else {
          pixel = rightimage.getPixel(j, i);
        }

        int r = pixel.getRed();
        int g = pixel.getGreen();
        int b = pixel.getBlue();

        outputImage.setPixel(j, i, new Imageloader.Pixel(r, g, b));

      }
    }

    tempMap.put(newname, outputImage);
    return tempMap;
  }

  @Override
  public Map<String, Imageloader> compression(Imageloader image, double thresholdPercentage,
                                              String newname) throws IllegalArgumentException {
    tempMap.clear();

    // get the square power of 2 size
    int origheight = image.getHeight();
    int origwidth = image.getWidth();
    int size = nextPowerOfTwo(Math.max(image.getHeight(), image.getWidth()));

    // Step 1: Pad the image to a square of power of 2 dimensions
    Imageloader padded = padImage(image, size);

    // Apply the transformation to each channel
    double[][] redChannel = extractChannel(padded, Channel.RED, size);
    double[][] greenChannel = extractChannel(padded, Channel.GREEN, size);
    double[][] blueChannel = extractChannel(padded, Channel.BLUE, size);

    // Step 2: Transform it using the 2D Haar
    double[][] red = haarTransform(redChannel, size);
    double[][] green = haarTransform(greenChannel, size);
    double[][] blue = haarTransform(blueChannel, size);

    // Thresholding
    threshold(red, green, blue, thresholdPercentage);


    // Inverting the 2D Haar transform
    inverseTransform(red, size);
    inverseTransform(green, size);
    inverseTransform(blue, size);

    // Reconstruct the pixel matrix after transformation

    Imageloader newImage = reconstructAndUnpadImage(red, green, blue, size, origwidth, origheight);


    tempMap.put(newname, newImage);

    return tempMap;

  }

  private static int nextPowerOfTwo(int number) {
    int power = 1;
    while (power < number) {
      power *= 2;
    }
    return power;
  }

  private static Imageloader padImage(Imageloader imageLoader, int squareSize) {
    // Create a new ImageLoader instance with the new square size
    Imageloader paddedImage = new Imageloader(squareSize, squareSize);

    // Copy existing pixels and fill the rest with a default color
    for (int y = 0; y < squareSize; y++) {
      for (int x = 0; x < squareSize; x++) {
        if (y < imageLoader.getHeight() && x < imageLoader.getWidth()) {
          paddedImage.setPixel(x, y, imageLoader.getPixel(x, y));
        } else {
          paddedImage.setPixel(x, y, new Imageloader.Pixel(0, 0, 0));
        }
      }
    }

    return paddedImage;

  }

  private enum Channel {
    RED, GREEN, BLUE
  }

  // Function to extract a specific channel from an ImageLoader instance
  private static double[][] extractChannel(Imageloader imageLoader, Channel channel,
                                           int paddedSize) {
    //int paddedSize = nextPowerOfTwo(Math.max(imageLoader.getWidth(), imageLoader.getHeight()));
    double[][] channelData = new double[paddedSize][paddedSize];

    for (int i = 0; i < imageLoader.getHeight(); i++) {
      for (int j = 0; j < imageLoader.getWidth(); j++) {
        Imageloader.Pixel pixel = imageLoader.getPixel(j, i);
        switch (channel) {
          case RED:
            channelData[i][j] = pixel.getRed();
            break;
          case GREEN:
            channelData[i][j] = pixel.getGreen();
            break;
          case BLUE:
            channelData[i][j] = pixel.getBlue();
            break;
          default:
            System.out.println("invalid channel");
        }
      }
    }
    return channelData;
  }

  private static void applyThreshold(double[][] channelData, double thresholdValue) {

    for (int i = 0; i < channelData.length; i++) {
      for (int j = 0; j < channelData[i].length; j++) {
        if (Math.abs(channelData[i][j]) <= thresholdValue) {
          channelData[i][j] = 0; // Zero out values below the threshold
        }
      }
    }
  }

  private static void addAbsoluteValues(Set<Double> valuesSet, double[][] channelData) {
    for (double[] row : channelData) {
      for (double value : row) {
        // Add only unique absolute values
        valuesSet.add(Math.abs(value));
      }
    }
  }

  private static void threshold(double[][] redChannel, double[][] greenChannel,
                                double[][] blueChannel, double thresholdPercentage) {
    Set<Double> uniqueValuesSet = new HashSet<>();

    // Collect unique absolute values from all channels
    addAbsoluteValues(uniqueValuesSet, redChannel);
    addAbsoluteValues(uniqueValuesSet, greenChannel);
    addAbsoluteValues(uniqueValuesSet, blueChannel);

    // Convert the set to a list and sort to find the threshold value
    List<Double> allValues = new ArrayList<>(uniqueValuesSet);
    Collections.sort(allValues);

    // Calculate the threshold index and value
    int thresholdIndex = (int) (allValues.size() * (thresholdPercentage / 100.0));

    // Make sure the index is within the list size after multiplying by the percentage
    thresholdIndex = Math.min(thresholdIndex, allValues.size() - 1);
    double thresholdValue = allValues.get(thresholdIndex);


    // Apply the threshold
    applyThreshold(redChannel, thresholdValue);
    applyThreshold(greenChannel, thresholdValue);
    applyThreshold(blueChannel, thresholdValue);

  }


  private static double[] transform(double[] s) {
    double[] transformed = new double[s.length];
    int halfLength = s.length / 2;

    for (int i = 0; i < halfLength; i++) {
      transformed[i] = (s[2 * i] + s[2 * i + 1]) / Math.sqrt(2); // Average
      transformed[halfLength + i] = (s[2 * i] - s[2 * i + 1]) / Math.sqrt(2); // Difference
    }

    return transformed;
  }

  // Haar wavelet transform on a 2D array
  private static double[][] haarTransform(double[][] x, int s) {
    int c = s;

    while (c > 1) {
      // Transform rows
      for (int i = 0; i < c; i++) {
        double[] rowTransformed = transform(Arrays.copyOfRange(x[i], 0, c));
        System.arraycopy(rowTransformed, 0, x[i], 0, c);
      }

      // Transform columns
      for (int j = 0; j < c; j++) {
        double[] column = new double[c];
        for (int i = 0; i < c; i++) {
          column[i] = x[i][j];
        }
        double[] colTransformed = transform(column);
        for (int i = 0; i < c; i++) {
          x[i][j] = colTransformed[i];
        }
      }

      c = c / 2;
    }

    return x;
  }


  private static double[] inverseT(double[] transformed) {
    double[] original = new double[transformed.length];
    int halfLength = transformed.length / 2;

    for (int i = 0; i < halfLength; i++) {
      original[2 * i] = (transformed[i] + transformed[halfLength + i]) / Math.sqrt(2);
      original[2 * i + 1] = (transformed[i] - transformed[halfLength + i]) / Math.sqrt(2);
    }

    return original;
  }

  private static void inverseTransform(double[][] x, int s) {
    int c = 2;
    while (c <= s) {
      // Inverse transform columns
      for (int j = 0; j < c; j++) {
        double[] column = new double[c];
        for (int i = 0; i < c; i++) {
          column[i] = x[i][j];
        }
        double[] colTransformed = inverseT(column);
        for (int i = 0; i < c; i++) {
          x[i][j] = colTransformed[i];
        }
      }

      // Inverse transform rows
      for (int i = 0; i < c; i++) {
        double[] rowTransformed = inverseT(Arrays.copyOfRange(x[i], 0, c));
        System.arraycopy(rowTransformed, 0, x[i], 0, c);
      }

      c = c * 2;
    }
  }

  private Imageloader reconstructAndUnpadImage(double[][] redChannel, double[][] greenChannel,
                                               double[][] blueChannel, int paddedSize,
                                               int originalWidth, int originalHeight) {

    Imageloader newImage = new Imageloader(originalWidth, originalHeight);
    // Create a new array for the unpadded and reconstructed pixels
    Imageloader.Pixel[][] originalPixels = new Imageloader.Pixel[originalHeight][originalWidth];

    // Reconstruct the pixels from the channels and unpad to the original size
    for (int y = 0; y < originalHeight; y++) {
      for (int x = 0; x < originalWidth; x++) {
        int red = (int) Math.round(clampDouble(redChannel[y][x]));
        int green = (int) Math.round(clampDouble(greenChannel[y][x]));
        int blue = (int) Math.round(clampDouble(blueChannel[y][x]));

        originalPixels[y][x] = new Imageloader.Pixel(red, green, blue);
        newImage.setPixel(x, y, originalPixels[y][x]);
      }
    }
    return newImage;
  }


  private static int clamp(int value) {
    if (value < 0) {
      return 0;
    }
    if (value > 255) {
      return 255;
    }
    return value;
  }

  private static double clampDouble(double value) {
    if (value < 0.0) {
      return 0.0;
    }
    if (value > 255.0) {
      return 255.0;
    }
    return value;
  }

}



