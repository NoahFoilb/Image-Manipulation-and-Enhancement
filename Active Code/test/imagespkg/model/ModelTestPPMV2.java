package imagespkg.model;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Objects;

import imagespkg.Imageloader;
import imagespkg.model.IModel;
import imagespkg.model.Model;


/**
 * Test class to test the Model functionality for PPM images.
 */
public class ModelTestPPMV2 {

  private Imageloader image;
  private IModel model;

  @Before
  public void setUp() throws IOException {
    image = new Imageloader("res/test-files/CustomPixels.ppm");
    model = new Model();

  }


  @Test
  public void testcolorcorrection() {
    Map<String, Imageloader> temp = model.colorcorrection(image, "colorcorrectImage");

    Imageloader.Pixel[][] expectedPixels;

    expectedPixels = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(0, 0, 255),
             new Imageloader.Pixel(0, 0, 255),
             new Imageloader.Pixel(0, 0, 255)},
            {new Imageloader.Pixel(255, 0, 0),
             new Imageloader.Pixel(255, 0, 0),
             new Imageloader.Pixel(255, 0, 0)},
            {new Imageloader.Pixel(0, 0, 255),
             new Imageloader.Pixel(0, 255, 0),
             new Imageloader.Pixel(0, 0, 255)}
    };

    assertNotNull(temp);
    assertTrue(temp.containsKey("colorcorrectImage"));

    Imageloader colorcorrectedImage = temp.get("colorcorrectImage");

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expectedPixels[i][j].getRed(), colorcorrectedImage.getPixel(j, i).getRed());
        assertEquals(expectedPixels[i][j].getGreen(),
                colorcorrectedImage.getPixel(j, i).getGreen());
        assertEquals(expectedPixels[i][j].getBlue(), colorcorrectedImage.getPixel(j, i).getBlue());

        Imageloader.Pixel actual = colorcorrectedImage.getPixel(j, i);
        boolean areEqual = actual.equals(expectedPixels[i][j]);
        assertTrue(areEqual);
      }
    }
  }

  @Test
  public void testleveladjustment() {
    Map<String, Imageloader> temp = model.leveladjustment(image, "leveladjustImage",
            20, 100, 255);

    Imageloader.Pixel[][] expectedPixels;

    expectedPixels = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(0, 0, 255),
             new Imageloader.Pixel(0, 0, 255),
             new Imageloader.Pixel(0, 0, 255)},
            {new Imageloader.Pixel(255, 0, 0),
             new Imageloader.Pixel(255, 0, 0),
             new Imageloader.Pixel(255, 0, 0)},
            {new Imageloader.Pixel(0, 0, 255),
             new Imageloader.Pixel(0, 255, 0),
             new Imageloader.Pixel(0, 0, 255)}
    };

    assertNotNull(temp);
    assertTrue(temp.containsKey("leveladjustImage"));

    Imageloader leveladjustImage = temp.get("leveladjustImage");

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expectedPixels[i][j].getRed(), leveladjustImage.getPixel(j, i).getRed());
        assertEquals(expectedPixels[i][j].getGreen(), leveladjustImage.getPixel(j, i).getGreen());
        assertEquals(expectedPixels[i][j].getBlue(), leveladjustImage.getPixel(j, i).getBlue());

        Imageloader.Pixel actual = leveladjustImage.getPixel(j, i);
        boolean areEqual = actual.equals(expectedPixels[i][j]);
        assertTrue(areEqual);
      }
    }
  }


  @Test
  public void testsplitblur() {
    Map<String, Imageloader> temp = model.blur(image, "leveladjustImage");
    temp = model.split(temp.get("leveladjustImage"), image, "LeveladjImagesplit",
            .2);

    Imageloader.Pixel[][] expectedPixels;

    expectedPixels = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(48, 0, 96),
             new Imageloader.Pixel(0, 0, 255),
             new Imageloader.Pixel(0, 0, 255)},
            {new Imageloader.Pixel(96, 16, 80),
             new Imageloader.Pixel(255, 0, 0),
             new Imageloader.Pixel(255, 0, 0)},
            {new Imageloader.Pixel(96, 32, 64),
             new Imageloader.Pixel(0, 255, 0),
             new Imageloader.Pixel(0, 0, 255)}
    };

    assertNotNull(temp);
    assertTrue(temp.containsKey("LeveladjImagesplit"));

    Imageloader leveladjustImageSplit = temp.get("LeveladjImagesplit");

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        assertEquals(expectedPixels[i][j].getRed(), leveladjustImageSplit.getPixel(j, i).getRed());
        assertEquals(expectedPixels[i][j].getGreen(),
                leveladjustImageSplit.getPixel(j, i).getGreen());
        assertEquals(expectedPixels[i][j].getBlue(),
                leveladjustImageSplit.getPixel(j, i).getBlue());

        Imageloader.Pixel actual = leveladjustImageSplit.getPixel(j, i);
        boolean areEqual = actual.equals(expectedPixels[i][j]);
        assertTrue(areEqual);
      }
    }
  }


  @Test
  public void testCompression() {
    int h = image.getHeight();
    int w = image.getWidth();
    Map<String, Imageloader> temp = model.compression(image, 20,
            "compress");
    Imageloader.Pixel[][] actualPixels;
    Imageloader.Pixel[][] expectedPixels;
    expectedPixels = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(0, 4, 251), new Imageloader.Pixel(0,
                    4, 251), new Imageloader.Pixel(0, 4, 251),},
            {new Imageloader.Pixel(247, 4, 0), new Imageloader.Pixel(247,
                    4, 0), new Imageloader.Pixel(247, 4, 0),},
            {new Imageloader.Pixel(0, 4, 251), new Imageloader.Pixel(0,
                    255, 0), new Imageloader.Pixel(0, 4, 251),}
    };
    assertNotNull(temp);
    assertTrue(temp.containsKey("compress"));

    Imageloader compress = temp.get("compress");


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = compress.getPixel(j, i);
        boolean areEqual = actual.equals(expectedPixels[i][j]);
        assertTrue(areEqual);
      }
    }
  }

  @Test
  public void testCompression2() {
    int h = image.getHeight();
    int w = image.getWidth();
    Map<String, Imageloader> temp = model.compression(image, 100,
            "compress");
    Imageloader.Pixel[][] actualPixels;
    Imageloader.Pixel[][] expectedPixels;
    expectedPixels = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0,
                    0, 0), new Imageloader.Pixel(0, 0, 0),},
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0,
                    0, 0), new Imageloader.Pixel(0, 0, 0),},
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0,
                    0, 0), new Imageloader.Pixel(0, 0, 0),}
    };
    assertNotNull(temp);
    assertTrue(temp.containsKey("compress"));

    Imageloader compress = temp.get("compress");


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = compress.getPixel(j, i);
        boolean areEqual = actual.equals(expectedPixels[i][j]);
        assertTrue(areEqual);
      }
    }
  }

  @Test
  public void testhistogrampeaks() {
    Map<String, Imageloader> temp = model.histogram(image, "leveladjustImage");
    assertNotNull(temp);
    assertTrue(temp.containsKey("leveladjustImage"));
    Imageloader leveladjustImageSplit = temp.get("leveladjustImage");

    HashMap<String, HashMap<Integer, Integer>> frequencies = leveladjustImageSplit.getfreqs();
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

    // Test the peaks, they all have the same peaks so the value should have its color at its
    // Position and be blank above it. Except blue as blue has the highest peak of them all.
    assertEquals(leveladjustImageSplit.getPixel(192, 254).getRed(), 255);
    assertEquals(leveladjustImageSplit.getPixel(192, 254).getBlue(), 255);
    assertEquals(leveladjustImageSplit.getPixel(192, 254).getGreen(), 255);
    assertEquals(leveladjustImageSplit.getPixel(192, 255).getRed(), 0);
    assertEquals(leveladjustImageSplit.getPixel(192, 255).getBlue(), 255);
    assertEquals(leveladjustImageSplit.getPixel(192, 255).getGreen(), 0);
  }

}