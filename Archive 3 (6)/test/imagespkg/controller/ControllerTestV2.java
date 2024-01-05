package imagespkg.controller;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import imagespkg.Imageloader;
import imagespkg.controller.Controller;
import imagespkg.model.IModel;
import imagespkg.view.IView;
import imagespkg.view.View;

import static org.junit.Assert.assertEquals;

/**
 * This should test all possible invalid and valid inputs a user inputs.
 */
public class ControllerTestV2 {

  @Test
  public void testhistogram() {
    MockModel model = new MockModel();
    String input = "load res/test-files/CustomPixels.png image1\nhistogram image1 image2\nQ";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    IView view = new View(System.out);
    Controller controller = new Controller(model, in, view);
    controller.run();
    assertEquals(model.getlog(), "Input: image2");
  }

  @Test
  public void testcolorcorrect() {
    MockModel model = new MockModel();
    String input = "load res/test-files/CustomPixels.png image1\ncolor-correct image1 image2\nQ";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    IView view = new View(System.out);
    Controller controller = new Controller(model, in, view);
    controller.run();
    assertEquals(model.getlog(), "Input: image2");
  }

  @Test
  public void testleveladj() {
    MockModel model = new MockModel();
    String input = "load res/test-files/CustomPixels.png image1\nlevels-adjust 5 100 250 " +
            "image1 image2\nQ";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    IView view = new View(System.out);
    Controller controller = new Controller(model, in, view);
    controller.run();
    assertEquals(model.getlog(), "Input: image2 5 100 250");
  }

  @Test
  public void testblursharpensepiasplits() {
    List<String> tests = new ArrayList<String>();
    Collections.addAll(tests, "blur", "sharpen", "sepia");
    for (String i : tests) {
      MockModel model = new MockModel();
      String input = "load res/test-files/CustomPixels.png image1\n" + i + " image1 image2 " +
              "split 50\nQ";
      InputStream in = new ByteArrayInputStream(input.getBytes());
      IView view = new View(System.out);
      Controller controller = new Controller(model, in, view);
      controller.run();
      assertEquals(model.getlog(), "Input: image2Input: image2 50.0");
    }
  }

  @Test
  public void testlumasplit() {
    MockModel model = new MockModel();
    String input = "load res/test-files/CustomPixels.png image1\nluma-component image1 image2 " +
            "split 50\nQ";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    IView view = new View(System.out);
    Controller controller = new Controller(model, in, view);
    controller.run();
    assertEquals(model.getlog(), "Input: luma image2Input: image2 50.0");
  }

  @Test
  public void testcolorcorrectionsplit() {
    MockModel model = new MockModel();
    String input = "load res/test-files/CustomPixels.png image1\ncolor-correct image1 image2 " +
            "split 50\nQ";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    IView view = new View(System.out);
    Controller controller = new Controller(model, in, view);
    controller.run();
    assertEquals(model.getlog(), "Input: image2Input: image2 50.0");
  }

  @Test
  public void testleveladjsplits() {
    MockModel model = new MockModel();
    String input = "load res/test-files/CustomPixels.png image1\nlevels-adjust 5 100 250 image1 " +
            "image2 split 50\nQ";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    IView view = new View(System.out);
    Controller controller = new Controller(model, in, view);
    controller.run();
    assertEquals(model.getlog(), "Input: image2 5 100 250Input: image2 50.0");
  }


  @Test
  public void testcompression() {
    MockModel model = new MockModel();
    String input = "load res/test-files/CustomPixels.png image1\ncompress 10 image1 image2\nQ";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    IView view = new View(System.out);
    Controller controller = new Controller(model, in, view);
    controller.run();
    assertEquals(model.getlog(), "Input: 10.0 image2");
  }


  @Test
  public void testinvalidleveladj() {

    String input = "load res/test-files/CustomPixels.png image1\nlevels-adjust -10 100 255 " +
            "image1 image2\nlevels-adjust 5 100 250 image1 image2\nQ";
    String input1 = "load res/test-files/CustomPixels.png image1\nlevels-adjust 10 100 260 " +
            "image1 image2\nlevels-adjust 5 100 250 image1 image2\nQ";
    String input2 = "load res/test-files/CustomPixels.png image1\nlevels-adjust 10 255 250 " +
            "image1 image2\nlevels-adjust 5 100 250 image1 image2\nQ";
    String input3 = "load res/test-files/CustomPixels.png image1\nlevels-adjust 150 100 250 " +
            "image1 image2\nlevels-adjust 5 100 250 image1 image2\nQ";

    List<String> commands = new ArrayList<String>();
    Collections.addAll(commands, input, input1, input2, input3);

    for (String j : commands) {
      MockModel model = new MockModel();
      InputStream in = new ByteArrayInputStream(j.getBytes());
      IView view = new View(System.out);
      Controller controller = new Controller(model, in, view);
      controller.run();
      assertEquals(model.getlog(), "Input: image2 5 100 250");
    }
  }


  @Test
  public void testinvalidsplit() {
    String input1 = "load res/test-files/CustomPixels.png image1\nluma-component image1 image2 " +
            "split -1\nluma-component image1 image2 split 50\nQ";
    String input2 = "load res/test-files/CustomPixels.png image1\nluma-component image1 image2 " +
            "split 101\nluma-component image1 image2 split 50\nQ";

    List<String> commands = new ArrayList<String>();
    Collections.addAll(commands, input1, input2);

    for (String j : commands) {
      MockModel model = new MockModel();
      InputStream in = new ByteArrayInputStream(j.getBytes());
      IView view = new View(System.out);
      Controller controller = new Controller(model, in, view);
      controller.run();
      assertEquals(model.getlog(), "Input: luma image2Input: luma image2Input: image2 50.0");
    }
  }


  @Test
  public void testinvalidcompression() {
    String input1 = "load res/test-files/CustomPixels.png image1\ncompress 101 image1 " +
            "image2\ncompress 10 image1 image2\nQ";
    String input2 = "load res/test-files/CustomPixels.png image1\ncompress -1 image1 " +
            "image2\ncompress 10 image1 image2\nQ";

    List<String> commands = new ArrayList<String>();
    Collections.addAll(commands, input1, input2);

    for (String j : commands) {
      MockModel model = new MockModel();
      InputStream in = new ByteArrayInputStream(j.getBytes());
      IView view = new View(System.out);
      Controller controller = new Controller(model, in, view);
      controller.run();
      assertEquals(model.getlog(), "Input: 10.0 image2");
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
      log.append("Input: " + newname);
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> leveladjustment(Imageloader image, String newname, int b,
                                                    int m, int w) throws IllegalArgumentException {
      log.append("Input: " + newname + " " + b + " " + m + " " + w);
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> histogram(Imageloader image, String newname)
            throws IllegalArgumentException {
      log.append("Input: " + newname);
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> split(Imageloader leftimage, Imageloader rightimage,
                                          String newname, double percent)
            throws IllegalArgumentException {
      log.append("Input: " + newname + " " + percent);
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> compression(Imageloader image, double value, String newname)
            throws IllegalArgumentException {
      log.append("Input: " + value + " " + newname);
      return new HashMap<>();
    }
  }


}