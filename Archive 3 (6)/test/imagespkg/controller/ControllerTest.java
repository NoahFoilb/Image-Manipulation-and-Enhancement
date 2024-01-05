package imagespkg.controller;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import imagespkg.Imageloader;
import imagespkg.controller.Controller;
import imagespkg.model.IModel;
import imagespkg.model.Model;
import imagespkg.view.IView;
import imagespkg.view.View;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * This should test all possible invalid and valid inputs a user inputs.
 */
public class ControllerTest {

  @Test
  public void testcomponent() {
    List<String> components = new ArrayList<String>();
    Collections.addAll(components, "red", "blue", "green", "luma", "intensity", "value");
    for (String i : components) {
      MockModel model = new MockModel();
      String input = "load res/test-files/CustomPixels.png image1\n" + i +
              "-component image1 image2\nQ";
      InputStream in = new ByteArrayInputStream(input.getBytes());
      IView view = new View(System.out);
      Controller controller = new Controller(model, in, view);
      controller.run();
      assertEquals(model.getlog(), "Input: " + i + " image2");
    }
  }

  @Test
  public void testflip() {
    List<String> flips = new ArrayList<String>();
    Collections.addAll(flips, "horizontal", "vertical");
    for (String i : flips) {
      MockModel model = new MockModel();
      String input = "load res/test-files/CustomPixels.png image1\n" + i + "-flip image1 image2\nQ";
      InputStream in = new ByteArrayInputStream(input.getBytes());
      IView view = new View(System.out);
      Controller controller = new Controller(model, in, view);
      controller.run();
      assertEquals(model.getlog(), "Input: " + i + " image2");
    }
  }

  @Test
  public void testbrighten() {
    MockModel model = new MockModel();
    String input = "load res/test-files/CustomPixels.png image1\nbrighten 10 image1 image2\nQ";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    IView view = new View(System.out);
    Controller controller = new Controller(model, in, view);
    controller.run();
    assertEquals(model.getlog(), "Input: 10 image2");
  }

  @Test
  public void testrgbsplit() {
    MockModel model = new MockModel();
    String input = "load res/test-files/CustomPixels.png image1\nrgb-split image1 image2 " +
            "image3 image4\nQ";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    IView view = new View(System.out);
    Controller controller = new Controller(model, in, view);
    controller.run();
    assertEquals(model.getlog(), "Input: image2 image3 image4");
  }

  @Test
  public void testrgbcombine() {
    MockModel model = new MockModel();
    String input = "load res/test-files/CustomPixels.png image1\nload " +
            "res/test-files/CustomPixels.png" +
            " image2\n"
            + "load res/test-files/CustomPixels.png image3\nrgb-combine image4 image2 image3 " +
            "image1\nQ";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    IView view = new View(System.out);
    Controller controller = new Controller(model, in, view);
    controller.run();
    assertEquals(model.getlog(), "Input: image4");
  }

  @Test
  public void testblursharpensepia() {
    List<String> tests = new ArrayList<String>();
    Collections.addAll(tests, "blur", "sharpen", "sepia");
    for (String i : tests) {
      MockModel model = new MockModel();
      String input = "load res/test-files/CustomPixels.png image1\n" + i + " image1 image2\nQ";
      InputStream in = new ByteArrayInputStream(input.getBytes());
      IView view = new View(System.out);
      Controller controller = new Controller(model, in, view);
      controller.run();
      assertEquals(model.getlog(), "Input: image2");
    }
  }

  @Test
  public void testsaveandload() {

    IModel model = new Model();
    String input = "load res/test-files/CustomPixels.png image1\nsave " +
            "res/test-files/Test-CustomPixels.png image1\nQ";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    IView view = new View(System.out);
    Controller controller = new Controller(model, in, view);
    controller.run();

    try {
      Imageloader created_image = new Imageloader("res/test-files/Test-CustomPixels.png");
      Imageloader given_image = new Imageloader("res/test-files/CustomPixels.png");

      for (int i = 0; i < created_image.getHeight(); i++) {
        for (int j = 0; j < created_image.getWidth(); j++) {
          assertEquals(created_image.getPixel(j, i).getBlue(),
                  given_image.getPixel(j, i).getBlue());
          assertEquals(created_image.getPixel(j, i).getRed(),
                  given_image.getPixel(j, i).getRed());
          assertEquals(created_image.getPixel(j, i).getGreen(),
                  given_image.getPixel(j, i).getGreen());
        }
      }
    } catch (IOException ignored) {

      fail("save and load fails");
    }
  }

  @Test
  public void testrun() {

    IModel model = new Model();
    String input = "load res/test-files/CustomPixels.png image1\nrun runscripttestingonly.txt\nQ";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    IView view = new View(System.out);
    Controller controller = new Controller(model, in, view);
    controller.run();

    try {
      Imageloader created_image = new Imageloader("res/test-files/Test-CustomPixels.png");
      Imageloader given_image = new Imageloader("res/test-files/CustomPixels.png");

      for (int i = 0; i < created_image.getHeight(); i++) {
        for (int j = 0; j < created_image.getWidth(); j++) {
          assertEquals(created_image.getPixel(j, i).getBlue(),
                  given_image.getPixel(j, i).getBlue());
          assertEquals(created_image.getPixel(j, i).getRed(),
                  given_image.getPixel(j, i).getRed());
          assertEquals(created_image.getPixel(j, i).getGreen(),
                  given_image.getPixel(j, i).getGreen());
        }
      }

    } catch (IOException ignored) {
      fail("run script fails");
    }

  }


  @Test
  public void testinvalidbrighten() {

    String input1 = "load res/test-files/CustomPixels.png image1\nbrighten 10 " +
            "image1 image2 image2\n"
            + "brighten 10 image1 image2\nQ";
    String input2 = "load res/test-files/CustomPixels.png image1\nbrighten image1 image1 image2\n"
            + "brighten 10 image1 image2\nQ";
    String input3 = "load res/test-files/CustomPixels.png image1\nbrighten 10 image2 image3\n"
            + "brighten 10 image1 image2\nQ";
    List<String> commands = new ArrayList<String>();
    Collections.addAll(commands, input1, input2, input3);

    for (String j : commands) {
      MockModel model = new MockModel();
      InputStream in = new ByteArrayInputStream(j.getBytes());
      IView view = new View(System.out);
      Controller controller = new Controller(model, in, view);
      controller.run();
      assertEquals(model.getlog(), "Input: 10 image2");
    }
  }

  @Test
  public void testinvalidsplits() {
    // List of splits
    String input1 = "load res/test-files/CustomPixels.png image1\nrgb-split image1 image2 image3 " +
            "image4 image5\n"
            + "rgb-split image1 image2 image3 image4\nQ";
    String input2 = "load res/test-files/CustomPixels.png image1\nrgb-split image1 image2 image2 " +
            "image2\n"
            + "rgb-split image1 image2 image3 image4\nQ";
    String input3 = "load res/test-files/CustomPixels.png image1\nrgb-split image2 image2 image3 " +
            "image4\n"
            + "rgb-split image1 image2 image3 image4\nQ";
    List<String> commands = new ArrayList<String>();
    Collections.addAll(commands, input1, input2, input3);

    for (String j : commands) {
      MockModel model = new MockModel();
      InputStream in = new ByteArrayInputStream(j.getBytes());
      IView view = new View(System.out);
      Controller controller = new Controller(model, in, view);
      controller.run();
      assertEquals(model.getlog(), "Input: image2 image3 image4");
    }
  }

  @Test
  public void testinvalidblursharpensepia() {
    List<String> tests = new ArrayList<String>();
    Collections.addAll(tests, "blur", "sharpen", "sepia");
    for (String i : tests) {

      String input1 = "load res/test-files/CustomPixels.png image1\n" + i + " image1 image2 " +
              "image2\n" + i
              + " image1 image2\nQ";
      String input2 = "load res/test-files/CustomPixels.png image1\n" + i + " image2 image2\n" + i
              + " image1 image2\nQ";
      List<String> commands = new ArrayList<String>();
      Collections.addAll(commands, input1, input2);

      for (String j : commands) {
        MockModel model = new MockModel();
        InputStream in = new ByteArrayInputStream(j.getBytes());
        IView view = new View(System.out);
        Controller controller = new Controller(model, in, view);
        controller.run();
        assertEquals(model.getlog(), "Input: image2");
      }
    }
  }

  @Test
  public void testinvalidcomponent() {
    List<String> components = new ArrayList<String>();
    Collections.addAll(components, "red", "blue", "green", "luma", "intensity", "value");
    for (String i : components) {

      String input1 = "load res/test-files/CustomPixels.png image1\nreed-component image1 image2\n"
              + i
              + "-component image1 image2\nQ";
      String input2 = "load res/test-files/CustomPixels.png image1\n" + i + "-component image1 " +
              "image2 image3\n" + i
              + "-component image1 image2\nQ";
      String input3 = "load res/test-files/CustomPixels.png image1\n" + i + "-component image2 " +
              "image3\n" + i
              + "-component image1 image2\nQ";
      List<String> commands = new ArrayList<String>();
      Collections.addAll(commands, input1, input2, input3);

      for (String j : commands) {
        MockModel model = new MockModel();
        InputStream in = new ByteArrayInputStream(j.getBytes());
        IView view = new View(System.out);
        Controller controller = new Controller(model, in, view);
        controller.run();
        assertEquals(model.getlog(), "Input: " + i + " image2");
      }
    }
  }

  @Test
  public void testinvalidflip() {
    List<String> flips = new ArrayList<String>();
    Collections.addAll(flips, "horizontal", "vertical");
    for (String i : flips) {

      String input1 = "load res/test-files/CustomPixels.png image1\nvertticalll-flip image1 " +
              "image2\n" + i
              + "-flip image1 image2\nQ";
      String input2 = "load res/test-files/CustomPixels.png image1\n" + i + "-flip image1 image2 " +
              "image3\n" + i
              + "-flip image1 image2\nQ";
      String input3 = "load res/test-files/CustomPixels.png image1\n" + i + "-flip image2 image3\n"
              + i
              + "-flip image1 image2\nQ";
      List<String> commands = new ArrayList<String>();
      Collections.addAll(commands, input1, input2, input3);

      for (String j : commands) {
        MockModel model = new MockModel();
        InputStream in = new ByteArrayInputStream(j.getBytes());
        IView view = new View(System.out);
        Controller controller = new Controller(model, in, view);
        controller.run();
        assertEquals(model.getlog(), "Input: " + i + " image2");
      }
    }
  }

  @Test
  public void testinvalidloadsviabrighten() {

    String input1 = "load res/test-files/CustomPixels.png image1 image2\nload " +
            "res/test-files/CustomPixels.png image1\n"
            + "brighten 10 image1 image2\nQ";
    String input2 = "load CustomPixels-sll.png image1 \nload res/test-files/CustomPixels.png " +
            "image1\n"
            + "brighten 10 image1 image2\nQ";
    String invalid_general = "wrong res/test-files/CustomPixels.png image1\nload " +
            "res/test-files/CustomPixels.png image1\n"
            + "brighten 10 image1 image2\nQ";
    List<String> commands = new ArrayList<String>();
    Collections.addAll(commands, input1, input2, invalid_general);

    for (String j : commands) {
      MockModel model = new MockModel();
      InputStream in = new ByteArrayInputStream(j.getBytes());
      IView view = new View(System.out);
      Controller controller = new Controller(model, in, view);
      controller.run();
      assertEquals(model.getlog(), "Input: 10 image2");
    }
  }

  @Test
  public void testinvalidsaveandload() {

    String input1 = "load res/test-files/CustomPixels.png image1\nsave " +
            "res/test-files/Test-CustomPixels.png image1 "
            + "image2\nsave res/test-files/Test-CustomPixels.png image1\nQ";
    String input2 = "load res/test-files/CustomPixels.png image1\nsave " +
            "res/test-files/Test-CustomPixels.png image2\n"
            + "save res/test-files/Test-CustomPixels.png image1\nQ";
    String input3 = "load res/test-files/CustomPixels.png image1\nsave " +
            "res/test-files/Test-CustomPixels.abc image1\n"
            + "save res/test-files/Test-CustomPixels.png image1\nQ";
    String input4 = "load res/test-files/CustomPixels.png image1\nsave " +
            "nowhere/nowhere.png image1\nsave test-files"
            + "/Test-CustomPixels.png image1\nQ";
    List<String> commands = new ArrayList<String>();
    Collections.addAll(commands, input1, input2, input3, input4);

    for (String k : commands) {
      IModel model = new Model();
      InputStream in = new ByteArrayInputStream(k.getBytes());
      IView view = new View(System.out);
      Controller controller = new Controller(model, in, view);
      controller.run();

      try {
        Imageloader created_image = new Imageloader("res/test-files/Test-CustomPixels.png");
        Imageloader given_image = new Imageloader("res/test-files/CustomPixels.png");

        for (int i = 0; i < created_image.getHeight(); i++) {
          for (int j = 0; j < created_image.getWidth(); j++) {
            assertEquals(created_image.getPixel(j, i).getBlue(),
                    given_image.getPixel(j, i).getBlue());
            assertEquals(created_image.getPixel(j, i).getRed(),
                    given_image.getPixel(j, i).getRed());
            assertEquals(created_image.getPixel(j, i).getGreen(),
                    given_image.getPixel(j, i).getGreen());
          }
        }

      } catch (IOException ignored) {
        fail("load and save fails");
      }

    }
  }

  @Test
  public void testinvalidrgbcombine() {
    String inputstart = "load res/test-files/CustomPixels.png image1\nload " +
            "res/test-files/CustomPixels.png image2\n" +
            "load res/test-files/CustomPixels.png image3\n load CustomPixelsWrong.png " +
            "imageincorrect\n";
    String input1 = inputstart + "rgb-combine image4 image2 image3 image1 image5\nrgb-combine " +
            "image4 image2 image3 image1\nQ";
    String input2 = inputstart + "rgb-combine image4 image50000 image3 image1\nrgb-combine image4 "
            + "image2 image3 image1\nQ";
    String input3 = inputstart + "rgb-combine image4 image2 image3 imageincorrect\nrgb-combine " +
            "image4 image2 image3 image1\nQ";
    List<String> commands = new ArrayList<String>();
    Collections.addAll(commands, input1, input2, input3);

    for (String k : commands) {
      MockModel model = new MockModel();
      InputStream in = new ByteArrayInputStream(k.getBytes());
      IView view = new View(System.out);
      Controller controller = new Controller(model, in, view);
      controller.run();
      assertEquals(model.getlog(), "Input: image4");
    }
  }


  @Test
  public void testinvalidrun() {
    String input1 = "load res/test-files/CustomPixels.png image1\nrun runstripttestingonly.txt " +
            "runthis" +
            "\nrun runstripttestingonly.txt\nQ";
    String input2 = "load res/test-files/CustomPixels.png image1\nrun " +
            "runstripttestingonlyyyyy.txt" +
            "\nrun runstripttestingonly.txt\nQ";


    List<String> commands = new ArrayList<String>();
    Collections.addAll(commands, input1, input2);
    for (String k : commands) {
      IModel model = new Model();
      InputStream in = new ByteArrayInputStream(k.getBytes());
      IView view = new View(System.out);
      Controller controller = new Controller(model, in, view);
      controller.run();

      try {
        Imageloader created_image = new Imageloader("res/test-files/Test-CustomPixels.png");
        Imageloader given_image = new Imageloader("res/test-files/CustomPixels.png");

        for (int i = 0; i < created_image.getHeight(); i++) {
          for (int j = 0; j < created_image.getWidth(); j++) {
            assertEquals(created_image.getPixel(j, i).getBlue(),
                    given_image.getPixel(j, i).getBlue());
            assertEquals(created_image.getPixel(j, i).getRed(),
                    given_image.getPixel(j, i).getRed());
            assertEquals(created_image.getPixel(j, i).getGreen(),
                    given_image.getPixel(j, i).getGreen());
          }
        }

      } catch (IOException ignored) {
        fail("invalid run");
      }

    }

  }


  class MockModel implements IModel {

    private final StringBuilder log;

    public MockModel() {
      this.log = new StringBuilder();
    }

    public String getlog() {
      return this.log.toString();
    }

    @Override
    public Map<String, Imageloader> component(Imageloader image, String componenttype,
                                              String newname) throws IllegalArgumentException {
      log.append("Input: " + componenttype + " " + newname);
      return new HashMap<>();
    }
    // Ignore adding "image" to the log.

    @Override
    public Map<String, Imageloader> flip(Imageloader image, String fliptype, String newname)
            throws IllegalArgumentException {
      log.append("Input: " + fliptype + " " + newname);
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> brighten(Imageloader image, int value, String newname)
            throws IllegalArgumentException {
      log.append("Input: " + value + " " + newname);
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> rgbsplit(Imageloader originalimage, String redimagename,
                                             String greenimagename, String blueimagename)
            throws IllegalArgumentException {
      log.append("Input: " + redimagename + " " + greenimagename + " " + blueimagename);
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> rgbcombine(String newname, Imageloader redimage,
                                               Imageloader greenimage, Imageloader blueimage)
            throws IllegalArgumentException {
      log.append("Input: " + newname);
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> blur(Imageloader image, String newname)
            throws IllegalArgumentException {
      log.append("Input: " + newname);
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> sharpen(Imageloader image, String newname)
            throws IllegalArgumentException {
      log.append("Input: " + newname);
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> sepia(Imageloader image, String newname)
            throws IllegalArgumentException {
      log.append("Input: " + newname);
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> colorcorrection(Imageloader image, String newname)
            throws IllegalArgumentException {
      return null;
    }

    @Override
    public Map<String, Imageloader> leveladjustment(Imageloader image, String newname, int b,
                                                    int m, int w) throws IllegalArgumentException {
      return null;
    }

    @Override
    public Map<String, Imageloader> histogram(Imageloader image, String newname)
            throws IllegalArgumentException {
      return null;
    }

    @Override
    public Map<String, Imageloader> split(Imageloader leftimage, Imageloader rightimage,
                                          String newname, double percent)
            throws IllegalArgumentException {
      return null;
    }

    @Override
    public Map<String, Imageloader> compression(Imageloader image, double value, String newname)
            throws IllegalArgumentException {
      log.append("Input: " + value + " " + newname);
      return new HashMap<>();
    }
  }


}