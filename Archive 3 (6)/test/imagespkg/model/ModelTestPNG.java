package imagespkg.model;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import imagespkg.Imageloader;
import imagespkg.model.IModel;
import imagespkg.model.Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class to test the Model functionality for PNG and JPG images.
 */
public class ModelTestPNG {

  private Imageloader image;
  private IModel model;

  @Before
  public void setUp() throws IOException {
    image = new Imageloader("res/test-files/CustomPixels.png");
    model = new Model();

  }

  @Test
  public void testIfValidConstructor() {
    int h = image.getHeight();
    int w = image.getWidth();
    assertEquals(h, 10);
    assertEquals(w, 10);
  }

  @Test
  public void testIfValid() {
    int h = image.getHeight();
    int w = image.getWidth();
    Imageloader.Pixel[][] expectedPixels;
    expectedPixels = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(0, 0, 255), new Imageloader.Pixel(0, 0,
                    255), new Imageloader.Pixel(0, 0, 255)},
            {new Imageloader.Pixel(255, 0, 0), new Imageloader.Pixel(255,
                    0, 0), new Imageloader.Pixel(255, 0, 0)},
            {new Imageloader.Pixel(0, 0, 255), new Imageloader.Pixel(0,
                    255, 0), new Imageloader.Pixel(0, 0, 255)}
    };

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = image.getPixel(j, i);
        boolean areEqual = actual.equals(expectedPixels[i][j]);
        assertTrue(areEqual);
      }
    }
  }

  @Test
  public void testBrighten() {
    int h = image.getHeight();
    int w = image.getWidth();
    Map<String, Imageloader> temp = model.brighten(image, 1, "brightenedImage");
    Imageloader.Pixel[][] actualPixels;
    Imageloader.Pixel[][] expectedPixels;
    expectedPixels = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(1, 1, 255), new Imageloader.Pixel(1, 1,
                    255), new Imageloader.Pixel(1, 1, 255)},
            {new Imageloader.Pixel(255, 1, 1), new Imageloader.Pixel(255,
                    1, 1), new Imageloader.Pixel(255, 1, 1)},
            {new Imageloader.Pixel(1, 1, 255), new Imageloader.Pixel(1,
                    255, 1), new Imageloader.Pixel(1, 1, 255)}
    };


    assertNotNull(temp);
    assertTrue(temp.containsKey("brightenedImage"));

    Imageloader brightenedImage = temp.get("brightenedImage");


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = brightenedImage.getPixel(j, i);
        boolean areEqual = actual.equals(expectedPixels[i][j]);
        assertTrue(areEqual);
      }
    }
  }

  @Test
  public void testDarken() {
    int h = image.getHeight();
    int w = image.getWidth();
    Map<String, Imageloader> temp = model.brighten(image, -1, "darkenedImage");
    Imageloader.Pixel[][] actualPixels;
    Imageloader.Pixel[][] expectedPixels;
    expectedPixels = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(0, 0, 254), new Imageloader.Pixel(0, 0,
                    254), new Imageloader.Pixel(0, 0, 254)},
            {new Imageloader.Pixel(254, 0, 0), new Imageloader.Pixel(254,
                    0, 0), new Imageloader.Pixel(254, 0, 0)},
            {new Imageloader.Pixel(0, 0, 254), new Imageloader.Pixel(0,
                    254, 0), new Imageloader.Pixel(0, 0, 254)}
    };
    assertNotNull(temp);
    assertTrue(temp.containsKey("darkenedImage"));

    Imageloader darkenedImage = temp.get("darkenedImage");


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = darkenedImage.getPixel(j, i);
        boolean areEqual = actual.equals(expectedPixels[i][j]);
        assertTrue(areEqual);
      }
    }
  }

  @Test
  public void testRedComponent() {
    int h = image.getHeight();
    int w = image.getWidth();
    Map<String, Imageloader> temp = model.component(image, "red", "red");
    Imageloader.Pixel[][] actualPixels;
    Imageloader.Pixel[][] expectedPixels;
    expectedPixels = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0, 0,
                    0), new Imageloader.Pixel(0, 0, 0)},
            {new Imageloader.Pixel(255, 0, 0), new Imageloader.Pixel(255,
                    0, 0), new Imageloader.Pixel(255, 0, 0)},
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0, 0,
                    0), new Imageloader.Pixel(0, 0, 0)}
    };
    assertNotNull(temp);
    assertTrue(temp.containsKey("red"));

    Imageloader red = temp.get("red");


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = red.getPixel(j, i);
        boolean areEqual = actual.equals(expectedPixels[i][j]);
        assertTrue(areEqual);
      }
    }
  }

  @Test
  public void testGreenComponent() {
    int h = image.getHeight();
    int w = image.getWidth();
    Map<String, Imageloader> temp = model.component(image, "green",
            "green");
    Imageloader.Pixel[][] actualPixels;
    Imageloader.Pixel[][] expectedPixels;
    expectedPixels = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0, 0,
                    0), new Imageloader.Pixel(0, 0, 0)},
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0, 0,
                    0), new Imageloader.Pixel(0, 0, 0)},
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0, 255,
                    0), new Imageloader.Pixel(0, 0, 0)}
    };
    assertNotNull(temp);
    assertTrue(temp.containsKey("green"));

    Imageloader green = temp.get("green");


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = green.getPixel(j, i);
        boolean areEqual = actual.equals(expectedPixels[i][j]);
        assertTrue(areEqual);
      }
    }
  }

  @Test
  public void testBlueComponent() {
    int h = image.getHeight();
    int w = image.getWidth();
    Map<String, Imageloader> temp = model.component(image, "blue",
            "blue");
    Imageloader.Pixel[][] actualPixels;
    Imageloader.Pixel[][] expectedPixels;
    expectedPixels = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(0, 0, 255), new Imageloader.Pixel(0,
                    0, 255), new Imageloader.Pixel(0, 0, 255)},
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0,
                    0, 0), new Imageloader.Pixel(0, 0, 0)},
            {new Imageloader.Pixel(0, 0, 255), new Imageloader.Pixel(0,
                    0, 0), new Imageloader.Pixel(0, 0, 255)}
    };
    assertNotNull(temp);
    assertTrue(temp.containsKey("blue"));

    Imageloader blue = temp.get("blue");


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = blue.getPixel(j, i);
        boolean areEqual = actual.equals(expectedPixels[i][j]);
        assertTrue(areEqual);
      }
    }
  }

  @Test
  public void testValueComponent() {
    int h = image.getHeight();
    int w = image.getWidth();
    Map<String, Imageloader> temp = model.component(image, "value",
            "value");
    Imageloader.Pixel[][] actualPixels;
    Imageloader.Pixel[][] expectedPixels;
    expectedPixels = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(255, 255, 255), new Imageloader.Pixel(255,
                    255, 255), new Imageloader.Pixel(255, 255, 255)},
            {new Imageloader.Pixel(255, 255, 255), new Imageloader.Pixel(255,
                    255, 255), new Imageloader.Pixel(255, 255, 255)},
            {new Imageloader.Pixel(255, 255, 255), new Imageloader.Pixel(255,
                    255, 255), new Imageloader.Pixel(255, 255, 255)}
    };
    assertNotNull(temp);
    assertTrue(temp.containsKey("value"));

    Imageloader value = temp.get("value");


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = value.getPixel(j, i);
        boolean areEqual = actual.equals(expectedPixels[i][j]);
        assertTrue(areEqual);
      }
    }
  }

  @Test
  public void testLumaComponent() {
    int h = image.getHeight();
    int w = image.getWidth();
    Map<String, Imageloader> temp = model.component(image, "luma",
            "luma");
    Imageloader.Pixel[][] actualPixels;
    Imageloader.Pixel[][] expectedPixels;
    expectedPixels = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(18, 18, 18), new Imageloader.Pixel(18,
                    18, 18), new Imageloader.Pixel(18, 18, 18)},
            {new Imageloader.Pixel(54, 54, 54), new Imageloader.Pixel(54,
                    54, 54), new Imageloader.Pixel(54, 54, 54)},
            {new Imageloader.Pixel(18, 18, 18), new Imageloader.Pixel(182,
                    182, 182), new Imageloader.Pixel(18, 18, 18)}
    };
    assertNotNull(temp);
    assertTrue(temp.containsKey("luma"));

    Imageloader luma = temp.get("luma");


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = luma.getPixel(j, i);
        boolean areEqual = actual.equals(expectedPixels[i][j]);
        assertTrue(areEqual);
      }
    }
  }

  @Test
  public void testIntensityComponent() {
    int h = image.getHeight();
    int w = image.getWidth();
    Map<String, Imageloader> temp = model.component(image, "intensity",
            "intensity");
    Imageloader.Pixel[][] actualPixels;
    Imageloader.Pixel[][] expectedPixels;
    expectedPixels = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(85, 85, 85), new Imageloader.Pixel(85,
                    85, 85), new Imageloader.Pixel(85, 85, 85),},
            {new Imageloader.Pixel(85, 85, 85), new Imageloader.Pixel(85,
                    85, 85), new Imageloader.Pixel(85, 85, 85),},
            {new Imageloader.Pixel(85, 85, 85), new Imageloader.Pixel(85,
                    85, 85), new Imageloader.Pixel(85, 85, 85),},
    };
    assertNotNull(temp);
    assertTrue(temp.containsKey("intensity"));

    Imageloader intensity = temp.get("intensity");


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = intensity.getPixel(j, i);
        boolean areEqual = actual.equals(expectedPixels[i][j]);
        assertTrue(areEqual);
      }
    }
  }

  @Test
  public void testVerticalFlip() {
    int h = image.getHeight();
    int w = image.getWidth();
    Map<String, Imageloader> temp = model.flip(image, "vertical", "flip");


    assertNotNull(temp);
    assertTrue(temp.containsKey("flip"));

    Imageloader flip = temp.get("flip");


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = image.getPixel(j, i);
        Imageloader.Pixel vert = flip.getPixel(j, 9 - i);
        boolean areEqual = actual.equals(vert);

      }

    }
  }

  @Test
  public void testHorizontalFlip() {
    int h = image.getHeight();
    int w = image.getWidth();
    Map<String, Imageloader> temp = model.flip(image, "horizontal", "flip");

    assertNotNull(temp);
    assertTrue(temp.containsKey("flip"));

    Imageloader flip = temp.get("flip");

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = image.getPixel(j, i);
        Imageloader.Pixel horizontal = flip.getPixel(9 - j, i);
        boolean areEqual = actual.equals(horizontal);

      }

    }
  }

  @Test
  public void testBlur() {
    int h = image.getHeight();
    int w = image.getWidth();
    Map<String, Imageloader> temp = model.blur(image, "blur");
    Imageloader.Pixel[][] actualPixels;
    Imageloader.Pixel[][] expectedPixels;
    expectedPixels = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(48, 0, 96), new Imageloader.Pixel(64,
                    0, 128), new Imageloader.Pixel(48, 0, 143)},
            {new Imageloader.Pixel(96, 16, 80), new Imageloader.Pixel(128,
                    32, 96), new Imageloader.Pixel(112, 16, 128)},
            {new Imageloader.Pixel(96, 32, 64), new Imageloader.Pixel(112,
                    80, 64), new Imageloader.Pixel(96, 80, 80),}
    };
    assertNotNull(temp);
    assertTrue(temp.containsKey("blur"));

    Imageloader blur = temp.get("blur");


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = blur.getPixel(j, i);
        boolean areEqual = actual.equals(expectedPixels[i][j]);
        assertTrue(areEqual);
      }
    }
  }

  @Test
  public void testSharpen() {
    int h = image.getHeight();
    int w = image.getWidth();
    Map<String, Imageloader> temp = model.sharpen(image, "sharpen");
    Imageloader.Pixel[][] actualPixels;
    Imageloader.Pixel[][] expectedPixels;
    expectedPixels = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(96, 0, 223), new Imageloader.Pixel(159,
                    0, 255), new Imageloader.Pixel(0, 0, 255)},
            {new Imageloader.Pixel(223, 32, 128), new Imageloader.Pixel(255,
                    0, 255), new Imageloader.Pixel(223, 0, 191)},
            {new Imageloader.Pixel(191, 32, 64), new Imageloader.Pixel(255,
                    255, 0), new Imageloader.Pixel(96, 159, 0)},
    };
    assertNotNull(temp);
    assertTrue(temp.containsKey("sharpen"));

    Imageloader sharpen = temp.get("sharpen");


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = sharpen.getPixel(j, i);
        boolean areEqual = actual.equals(expectedPixels[i][j]);
        assertTrue(areEqual);
      }
    }
  }

  @Test
  public void testSepia() {
    int h = image.getHeight();
    int w = image.getWidth();
    Map<String, Imageloader> temp = model.sepia(image, "sepia");
    Imageloader.Pixel[][] actualPixels;
    Imageloader.Pixel[][] expectedPixels;
    expectedPixels = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(48, 42, 33), new Imageloader.Pixel(48,
                    42, 33), new Imageloader.Pixel(48, 42, 33),},
            {new Imageloader.Pixel(100, 88, 69), new Imageloader.Pixel(100,
                    88, 69), new Imageloader.Pixel(100, 88, 69),},
            {new Imageloader.Pixel(48, 42, 33), new Imageloader.Pixel(196,
                    174, 136), new Imageloader.Pixel(48, 42, 33),},};
    assertNotNull(temp);
    assertTrue(temp.containsKey("sepia"));

    Imageloader sepia = temp.get("sepia");


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = sepia.getPixel(j, i);
        boolean areEqual = actual.equals(expectedPixels[i][j]);
        assertTrue(areEqual);
      }
    }
  }

  @Test
  public void testRGBSplit() {
    int h = image.getHeight();
    int w = image.getWidth();
    Map<String, Imageloader> temp = model.rgbsplit(image, "red",
            "green", "blue");
    Imageloader.Pixel[][] expectedGreen;
    Imageloader.Pixel[][] expectedRed;
    Imageloader.Pixel[][] expectedBlue;
    expectedRed = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0, 0,
                    0), new Imageloader.Pixel(0, 0, 0)},
            {new Imageloader.Pixel(255, 0, 0), new Imageloader.Pixel(255,
                    0, 0), new Imageloader.Pixel(255, 0, 0)},
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0, 0,
                    0), new Imageloader.Pixel(0, 0, 0)}
    };
    expectedGreen = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0, 0,
                    0), new Imageloader.Pixel(0, 0, 0)},
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0, 0,
                    0), new Imageloader.Pixel(0, 0, 0)},
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0, 255,
                    0), new Imageloader.Pixel(0, 0, 0)}
    };
    expectedBlue = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(0, 0, 255), new Imageloader.Pixel(0, 0,
                    255), new Imageloader.Pixel(0, 0, 255)},
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0, 0,
                    0), new Imageloader.Pixel(0, 0, 0)},
            {new Imageloader.Pixel(0, 0, 255), new Imageloader.Pixel(0, 0,
                    0), new Imageloader.Pixel(0, 0, 255)}
    };

    assertNotNull(temp);
    assertTrue(temp.containsKey("red"));
    assertTrue(temp.containsKey("green"));
    assertTrue(temp.containsKey("blue"));

    Imageloader red = temp.get("red");
    Imageloader green = temp.get("green");
    Imageloader blue = temp.get("blue");


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actualred = red.getPixel(j, i);
        Imageloader.Pixel actualgreen = green.getPixel(j, i);
        Imageloader.Pixel actualblue = blue.getPixel(j, i);
        boolean areEqual = actualred.equals(expectedRed[i][j]);
        assertTrue(areEqual);
        boolean areEqual2 = actualgreen.equals(expectedGreen[i][j]);
        assertTrue(areEqual2);
        boolean areEqual3 = actualblue.equals(expectedBlue[i][j]);
        assertTrue(areEqual3);
      }
    }
  }

  @Test
  public void testRGBCombine() {
    int h = image.getHeight();
    int w = image.getWidth();

    Imageloader.Pixel[][] expectedGreen;
    Imageloader.Pixel[][] expectedRed;
    Imageloader.Pixel[][] expectedBlue;
    Imageloader red = new Imageloader(3, 3);
    Imageloader green = new Imageloader(3, 3);
    Imageloader blue = new Imageloader(3, 3);
    expectedRed = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0, 0,
                    0), new Imageloader.Pixel(0, 0, 0)},
            {new Imageloader.Pixel(255, 0, 0), new Imageloader.Pixel(255,
                    0, 0), new Imageloader.Pixel(255, 0, 0)},
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0, 0,
                    0), new Imageloader.Pixel(0, 0, 0)}
    };
    expectedGreen = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0, 0,
                    0), new Imageloader.Pixel(0, 0, 0)},
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0, 0,
                    0), new Imageloader.Pixel(0, 0, 0)},
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0, 255,
                    0), new Imageloader.Pixel(0, 0, 0)}
    };
    expectedBlue = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(0, 0, 255), new Imageloader.Pixel(0,
                    0, 255), new Imageloader.Pixel(0, 0, 255)},
            {new Imageloader.Pixel(0, 0, 0), new Imageloader.Pixel(0, 0,
                    0), new Imageloader.Pixel(0, 0, 0)},
            {new Imageloader.Pixel(0, 0, 255), new Imageloader.Pixel(0,
                    0, 0), new Imageloader.Pixel(0, 0, 255)}
    };

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        red.setPixel(j, i, expectedRed[i][j]);
      }
    }
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        green.setPixel(j, i, expectedGreen[i][j]);
      }
    }
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        blue.setPixel(j, i, expectedBlue[i][j]);
      }
    }


    Map<String, Imageloader> temp = model.rgbcombine("newimage", red, green, blue);
    assertNotNull(temp);
    assertTrue(temp.containsKey("newimage"));

    Imageloader newi = temp.get("newimage");


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = newi.getPixel(j, i);
        Imageloader.Pixel imageo = image.getPixel(j, i);
        boolean areEqual = actual.equals(imageo);
        assertTrue(areEqual);

      }
    }
  }

  @Test
  public void testBrighten2() {
    int h = image.getHeight();
    int w = image.getWidth();
    Map<String, Imageloader> temp = model.brighten(image, 1, "brightenedImage");
    assertNotNull(temp);
    assertTrue(temp.containsKey("brightenedImage"));

    Imageloader brightenedImage = temp.get("brightenedImage");
    Map<String, Imageloader> temp2 = model.brighten(brightenedImage, 1,
            "brightenedImage2");
    Imageloader brightenedImage2 = temp2.get("brightenedImage2");
    Imageloader.Pixel[][] actualPixels;
    Imageloader.Pixel[][] expectedPixels;
    expectedPixels = new Imageloader.Pixel[][]{
            {new Imageloader.Pixel(2, 2, 255), new Imageloader.Pixel(2, 2,
                    255), new Imageloader.Pixel(2, 2, 255)},
            {new Imageloader.Pixel(255, 2, 2), new Imageloader.Pixel(255,
                    2, 2), new Imageloader.Pixel(255, 2, 2)},
            {new Imageloader.Pixel(2, 2, 255), new Imageloader.Pixel(2,
                    255, 2), new Imageloader.Pixel(2, 2, 255)}
    };

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = brightenedImage2.getPixel(j, i);
        boolean areEqual = actual.equals(expectedPixels[i][j]);
        assertTrue(areEqual);
      }
    }
  }

  @Test
  public void testFlipTwice() {

    Map<String, Imageloader> temp = model.flip(image, "vertical", "flip");
    assertNotNull(temp);
    assertTrue(temp.containsKey("flip"));

    Imageloader flip = temp.get("flip");
    Map<String, Imageloader> temp2 = model.flip(flip, "vertical", "flip2");
    Imageloader flip2 = temp.get("flip2");


    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Imageloader.Pixel actual = image.getPixel(j, i);
        Imageloader.Pixel vert = flip2.getPixel(j, i);
        boolean areEqual = actual.equals(vert);
        assertTrue(areEqual);

      }

    }
  }


}
