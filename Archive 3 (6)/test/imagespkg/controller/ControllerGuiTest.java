package imagespkg.controller;

import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import imagespkg.Imageloader;
import imagespkg.model.IModel;
import imagespkg.view.IViewGui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This file tests the controller for valid and invalid inputs provided to the controller
 * by the graphical user interface(GUI).
 */
public class ControllerGuiTest {


  private MockModel mockmodel;
  private MockView mockview;
  private ControllerGui controller;

  @Before
  public void setUp() throws IOException {
    mockmodel = new MockModel();
    mockview = new MockView();
    controller = new ControllerGui(mockmodel, mockview);
    controller.actionPerformed(new ActionEvent("", 0, "Load"));
  }


  @Test
  public void testload() {
    mockmodel.log.delete(0, 100);
    ControllerGui controller = new ControllerGui(mockmodel, mockview);
    controller.actionPerformed(new ActionEvent("", 0, "Load"));
    assertEquals(mockmodel.getlog(), "Input: histogram ");
  }

  @Test
  public void testslideramountunselected() {
    JSlider slider = new JSlider();
    slider.setValue(75);
    controller.stateChanged(new ChangeEvent(slider));
    assertEquals(mockmodel.getlog(), "Input: histogram ");
  }

  @Test
  public void testslideramountselected() {
    JSlider slider = new JSlider();
    slider.setValue(75);
    controller.stateChanged(new ChangeEvent(slider));
    controller.itemStateChanged(new ItemEvent(new JCheckBox("Split"), 0,
            "Split", ItemEvent.SELECTED));
    assertEquals(mockmodel.getlog(), "Input: histogram Input: Split 75.0 Input: histogram ");
  }


  @Test
  public void testtwocomponent() {
    controller.itemStateChanged(new ItemEvent(new JCheckBox("Blue"), 0,
            "Blue", ItemEvent.SELECTED));
    controller.itemStateChanged(new ItemEvent(new JCheckBox("Green"), 0,
            "Green", ItemEvent.SELECTED));
    assertEquals(mockmodel.getlog(), "Input: histogram Input: blank R Input: blank G Input:" +
            " blank B Input: blue Blue Input: Combine Input: histogram Input: blank R " +
            "Input: blank G Input: blank B Input: green Green Input: blue Blue Input: Combine " +
            "Input: histogram ");
  }

  @Test
  public void testbluecomponent() {
    controller.itemStateChanged(new ItemEvent(new JCheckBox("Blue"), 0,
            "Blue", ItemEvent.SELECTED));
    controller.itemStateChanged(new ItemEvent(new JCheckBox("Blue"), 0,
            "Blue", ItemEvent.DESELECTED));
    assertEquals(mockmodel.getlog(), "Input: histogram Input: blank R Input: blank G " +
            "Input: blank B Input: blue Blue Input: Combine Input: histogram Input: histogram ");
  }

  @Test
  public void testgreencomponent() {
    controller.itemStateChanged(new ItemEvent(new JCheckBox("Green"), 0,
            "Green", ItemEvent.SELECTED));
    controller.itemStateChanged(new ItemEvent(new JCheckBox("Green"), 0,
            "Green", ItemEvent.DESELECTED));
    assertEquals(mockmodel.getlog(), "Input: histogram Input: blank R Input: blank G " +
            "Input: blank B Input: green Green Input: Combine Input: histogram Input: histogram ");
  }

  @Test
  public void testredcomponent() {
    controller.itemStateChanged(new ItemEvent(new JCheckBox("Red"), 0,
            "Red", ItemEvent.SELECTED));
    controller.itemStateChanged(new ItemEvent(new JCheckBox("Red"), 0,
            "Red", ItemEvent.DESELECTED));
    assertEquals(mockmodel.getlog(), "Input: histogram Input: blank R Input: blank G " +
            "Input: blank B Input: red Red Input: Combine Input: histogram Input: histogram ");
  }


  @Test
  public void testsplit() {
    controller.itemStateChanged(new ItemEvent(new JCheckBox("Split"), 0,
            "Split", ItemEvent.SELECTED));
    controller.itemStateChanged(new ItemEvent(new JCheckBox("Split"), 0,
            "Split", ItemEvent.DESELECTED));
    assertEquals(mockmodel.getlog(), "Input: histogram Input: Split 50.0 " +
            "Input: histogram Input: histogram ");
  }

  @Test
  public void testUndo() {
    controller.actionPerformed(new ActionEvent("", 0, "Blur"));
    controller.actionPerformed(new ActionEvent("", 0, "Undo"));
    assertEquals(mockmodel.getlog(), "Input: histogram Input: Blur Input: " +
            "histogram Input: histogram ");
  }

  @Test
  public void testcompression() {
    controller.actionPerformed(new ActionEvent("", 0, "Compress"));
    assertEquals(mockmodel.getlog(), "Input: histogram Input: 50.0 Compress " +
            "Input: histogram ");
  }

  @Test
  public void testlvladjustment() {
    controller.actionPerformed(new ActionEvent("", 0, "Level Adjust"));
    assertEquals(mockmodel.getlog(), "Input: histogram Input: Level Adjust 1 2 3 " +
            "Input: histogram ");
  }

  @Test
  public void testCC() {
    controller.actionPerformed(new ActionEvent("", 0, "Color Correct"));
    assertEquals(mockmodel.getlog(), "Input: histogram Input: Color Correct Input: " +
            "histogram ");
  }

  @Test
  public void testSepia() {
    controller.actionPerformed(new ActionEvent("", 0, "Sepia"));
    assertEquals(mockmodel.getlog(), "Input: histogram Input: Sepia Input: histogram ");
  }

  @Test
  public void testblur() {
    controller.actionPerformed(new ActionEvent("", 0, "Blur"));
    assertEquals(mockmodel.getlog(), "Input: histogram Input: Blur Input: histogram ");
  }

  @Test
  public void testsharpen() {
    controller.actionPerformed(new ActionEvent("", 0, "Sharpen"));
    assertEquals(mockmodel.getlog(), "Input: histogram Input: Sharpen Input: histogram ");
  }

  @Test
  public void testverticalflip() {
    controller.actionPerformed(new ActionEvent("", 0, "Blur"));
    assertEquals(mockmodel.getlog(), "Input: histogram Input: Blur Input: histogram ");
  }

  @Test
  public void testhorixontalflip() {
    controller.actionPerformed(new ActionEvent("", 0, "Blur"));
    assertEquals(mockmodel.getlog(), "Input: histogram Input: Blur Input: histogram ");
  }

  @Test
  public void testsave() {
    controller.actionPerformed(new ActionEvent("", 0, "Save"));
    assertEquals(mockmodel.getlog(), "Input: histogram ");

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
  public void testcontrollerconsturctor() {
    mockview.log.delete(0, 100);
    ControllerGui controller = new ControllerGui(mockmodel, mockview);
    assertEquals(mockview.getlog(), "Listeners Display ");
  }

  @Test
  public void testviewloading() {
    assertEquals(mockview.getlog(), "Listeners Display Loading Icons ");
  }

  @Test
  public void testcontrollercompress() {
    mockview.setcompress("50");
    controller.actionPerformed(new ActionEvent("", 0, "Compress"));
    assertEquals(mockview.getlog(), "Listeners Display Loading Icons Compress Icons ");
  }

  @Test
  public void testcontrollercompressinvalid() {
    mockview.setcompress("-1");
    controller.actionPerformed(new ActionEvent("", 0, "Compress"));
    assertEquals(mockview.getlog(), "Listeners Display Loading Icons Compress Input: " +
            "Value >= 100 or Value <= 0");
  }

  @Test
  public void testcontrollercompressinvalid2() {
    mockview.setcompress("101");
    controller.actionPerformed(new ActionEvent("", 0, "Compress"));
    assertEquals(mockview.getlog(), "Listeners Display Loading Icons Compress Input: " +
            "Value >= 100 or Value <= 0");
  }

  @Test
  public void testcontrollercompressinvalid3() {
    mockview.setcompress("fdjasfh kjdsah");
    controller.actionPerformed(new ActionEvent("", 0, "Compress"));
    assertEquals(mockview.getlog(), "Listeners Display Loading Icons Compress Input: " +
            "Please only include numbers");
  }

  @Test
  public void testcontrollercompressinvalid4() {
    mockview.setcompress("");
    controller.actionPerformed(new ActionEvent("", 0, "Compress"));
    assertEquals(mockview.getlog(), "Listeners Display Loading Icons Compress ");
  }


  @Test
  public void testcontrollerlvladjustment() {
    mockview.setlvladj(new String[]{"1", "2", "3"});
    controller.actionPerformed(new ActionEvent("", 0, "Level Adjust"));
    assertEquals(mockview.getlog(), "Listeners Display Loading Icons Lvladj Icons ");
  }

  @Test
  public void testcontrollerlvladjustmentempty() {
    mockview.setlvladj(new String[]{"", "", ""});
    controller.actionPerformed(new ActionEvent("", 0, "Level Adjust"));
    assertEquals(mockview.getlog(), "Listeners Display Loading Icons Lvladj ");
  }

  @Test
  public void testlvladjustmentbgreaterneg1() {
    mockview.setlvladj(new String[]{"-1", "2", "3"});
    controller.actionPerformed(new ActionEvent("", 0, "Level Adjust"));
    assertEquals(mockview.getlog(), "Listeners Display Loading Icons Lvladj Input: " +
            "b has to be greater than 0");
  }

  @Test
  public void testlvladjustmentMgreaterB() {
    mockview.setlvladj(new String[]{"2", "1", "3"});
    controller.actionPerformed(new ActionEvent("", 0, "Level Adjust"));
    assertEquals(mockview.getlog(), "Listeners Display Loading Icons Lvladj Input: " +
            "b has to be less than m");
  }

  @Test
  public void testlvladjustmentWgreaterM() {
    mockview.setlvladj(new String[]{"1", "3", "2"});
    controller.actionPerformed(new ActionEvent("", 0, "Level Adjust"));
    assertEquals(mockview.getlog(), "Listeners Display Loading Icons Lvladj Input: " +
            "m has to be less than w");
  }

  @Test
  public void testlvladjustmentWgreater255() {
    mockview.setlvladj(new String[]{"1", "3", "256"});
    controller.actionPerformed(new ActionEvent("", 0, "Level Adjust"));
    assertEquals(mockview.getlog(), "Listeners Display Loading Icons Lvladj Input: w has " +
            "to be less or equal to 255");
  }

  @Test
  public void testlvladjustmentstrings() {
    mockview.setlvladj(new String[]{"1dd", "3", "256"});
    controller.actionPerformed(new ActionEvent("", 0, "Level Adjust"));
    assertEquals(mockview.getlog(), "Listeners Display Loading Icons Lvladj Input: Please " +
            "only include numbers / Fill out all Fields");
  }

  @Test
  public void testinvalidloads() {
    mockview.log.delete(0, 100);
    mockview.setload("notvalidlocation/whazo.nonya");
    ControllerGui controller = new ControllerGui(mockmodel, mockview);
    controller.actionPerformed(new ActionEvent("", 0, "Load"));
    assertEquals(mockview.getlog(), "Listeners Display Loading Input: Invalid File Location");
  }

  @Test
  public void testinvalidsavings() {
    mockview.setsave("notvalidlocation/whazo.nonya");
    controller.actionPerformed(new ActionEvent("", 0, "Save"));
    assertEquals(mockview.getlog(), "Listeners Display Loading Icons Saving Input: " +
            "Invalid file extension. Only PPM, PNG, and JPG are allowed.");
  }


  class MockView implements IViewGui {

    private final StringBuilder log;
    private String compress;
    private String[] lvladj;
    private String load;
    private String save;

    public MockView() {
      this.log = new StringBuilder();
      this.save = "Test-CustomPixels.png";
      load = "res/test-files/CustomPixels.png";
      lvladj = new String[]{"1", "2", "3"};
      compress = "50";
    }

    public String getlog() {
      return this.log.toString();
    }

    public void setcompress(String compressvalue) {
      this.compress = compressvalue;
    }

    public void setload(String compressvalue) {
      this.load = compressvalue;
    }

    public void setsave(String compressvalue) {
      this.save = compressvalue;
    }

    public void setlvladj(String[] lvladjvalue) {
      this.lvladj = lvladjvalue;
    }


    @Override
    public void setIconImages(Imageloader displayimage, Imageloader histogram) {
      log.append("Icons ");
    }

    @Override
    public void setListener(ActionListener listener, ItemListener listener2,
                            ChangeListener listener3) {
      log.append("Listeners ");
    }

    @Override
    public void display() {
      log.append("Display ");
    }

    @Override
    public String viewCompressionPercent() {
      log.append("Compress ");
      return compress;
    }

    @Override
    public String[] levelAdjInputs() {
      log.append("Lvladj ");
      return lvladj;
    }

    @Override
    public void invalidManualInput(String s) {
      log.append("Input: " + s);
    }

    @Override
    public String loading() {
      log.append("Loading ");
      return load;
    }

    @Override
    public String saving() {
      log.append("Saving ");
      return save;
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
      log.append("Input: " + componenttype + " " + newname + " ");
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> flip(Imageloader image, String fliptype, String newname)
            throws IllegalArgumentException {
      log.append("Input: " + fliptype + " " + newname + " ");
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> brighten(Imageloader image, int value, String newname)
            throws IllegalArgumentException {
      log.append("Input: " + value + " " + newname + " ");
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> rgbsplit(Imageloader originalimage, String redimagename,
                                             String greenimagename, String blueimagename)
            throws IllegalArgumentException {
      log.append("Input: " + redimagename + " " + greenimagename + " " + blueimagename + " ");
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> rgbcombine(String newname, Imageloader redimage,
                                               Imageloader greenimage, Imageloader blueimage)
            throws IllegalArgumentException {
      log.append("Input: " + newname + " ");
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> blur(Imageloader image, String newname)
            throws IllegalArgumentException {
      log.append("Input: " + newname + " ");
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> sharpen(Imageloader image, String newname)
            throws IllegalArgumentException {
      log.append("Input: " + newname + " ");
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> sepia(Imageloader image, String newname)
            throws IllegalArgumentException {
      log.append("Input: " + newname + " ");
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> colorcorrection(Imageloader image, String newname)
            throws IllegalArgumentException {
      log.append("Input: " + newname + " ");
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> leveladjustment(Imageloader image, String newname, int b,
                                                    int m, int w) throws IllegalArgumentException {
      log.append("Input: " + newname + " " + b + " " + m + " " + w + " ");
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> histogram(Imageloader image, String newname)
            throws IllegalArgumentException {
      log.append("Input: " + newname + " ");
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> split(Imageloader leftimage, Imageloader rightimage,
                                          String newname, double percent)
            throws IllegalArgumentException {
      log.append("Input: " + newname + " " + percent + " ");
      return new HashMap<>();
    }

    @Override
    public Map<String, Imageloader> compression(Imageloader image, double value, String newname)
            throws IllegalArgumentException {
      log.append("Input: " + value + " " + newname + " ");
      return new HashMap<>();
    }

  }
}