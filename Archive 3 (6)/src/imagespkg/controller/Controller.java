package imagespkg.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import imagespkg.view.IView;
import imagespkg.Imageloader;
import imagespkg.model.IModel;


/**
 * This class will take in user inputted data or textfile data in order to send data into the model
 * and view in order to perform selected user tasks.
 */
public class Controller implements IController {
  private Scanner in;
  private IView view;
  private IModel model;
  private Map<String, Imageloader> imagemap;
  private Map<String, Imageloader> tempmap;

  /**
   * The controller will take in a model for its calculations, an inputstream for its inputs and a
   * view for its user interface.
   */
  public Controller(IModel model, InputStream in, IView view) {
    this.model = model;
    this.view = view;
    this.in = new Scanner(in);
    this.imagemap = new HashMap<>();
    this.tempmap = new HashMap<>();
  }

  /**
   * Run is what will continue to be looped until the user specifies that its over. This is where
   * the user gets asked to input data and where the data gets sent into the model.
   */
  public void run() {
    boolean quit = false;

    // Make map immutable

    while (!quit) {
      for (int i = 0; i < 3; i += 1) {
        view.showemptyline();
      }
      view.showlistofimages();
      int index = 0;

      for (String key : imagemap.keySet()) {
        index += 1;
        view.showString(key);
        if (index != imagemap.size()) {
          view.showcomma();
        }
      }
      view.showrightbracket();
      view.showhelp();

      String command = in.nextLine();
      String[] result = command.split("\\s+");
      String[] split = result[0].split("-");

      if (split.length > 2) {
        split[split.length - 1] = "No match";
      }

      switch (split[split.length - 1]) {
        case "load":
          if (result.length == 3) {
            try {
              tempmap.clear();
              tempmap.put(result[2], new Imageloader(result[1]));
              immutableobject(tempmap);
              view.successfullyran();
            } catch (IOException e) {
              view.invalidfilelocation(result[1]);
            }
          } else {
            view.showOptionError();
          }
          break;

        case "save":
          savemethod(result);
          break;

        case "component":
          List<String> components = new ArrayList<String>();
          Collections.addAll(components, "red", "blue", "green", "luma", "intensity",
                  "value");
          functionImg1Img2Components(result, "component", components, split);
          break;

        case "flip":
          List<String> flips = new ArrayList<String>();
          Collections.addAll(flips, "horizontal", "vertical");
          functionImg1Img2Components(result, "flip", flips, split);
          break;

        case "brighten":
          functionNumImg1Img2(result, "brighten");
          break;

        case "compress":
          functionNumImg1Img2(result, "compress");
          break;

        case "split":
          splitmethod(result);
          break;

        case "combine":
          combinemethod(result);
          break;

        case "blur":
          functionImg1Img2(result, "blur");
          break;

        case "sharpen":
          functionImg1Img2(result, "sharpen");
          break;

        case "sepia":
          functionImg1Img2(result, "sepia");
          break;

        case "histogram":
          functionImg1Img2(result, "histogram");
          break;

        case "correct":
          functionImg1Img2(result, "color-correct");
          break;

        case "adjust":
          leveladjustmethod(result);
          break;

        case "run":
          runmethod(result);
          break;

        case "help":
          if (result.length == 1) {
            view.showlistofcommands();
          } else {
            view.showOptionError();
          }
          break;

        case "Q":
          quit = true;
          break;

        default:
          view.showOptionError();
      }
    }
  }

  private void split(String[] result, Map<String, Imageloader> temp, int totallength) {
    if (result.length == totallength) {
      try {
        int p = Integer.parseInt(result[totallength - 1]);
        if (Objects.equals(result[totallength - 2], "split")) {
          if (p <= 100 && p >= 0) {
            temp = model.split(temp.get(result[totallength - 3]), imagemap.get(result[totallength
                    - 4]), result[totallength - 3], p);
            immutableobject(temp);
            view.successfullyran();
          } else {
            view.showinvalidpercentage();
          }
        } else {
          view.showOptionError();
        }
      } catch (NumberFormatException ex) {
        view.showOptionError();
      }
    } else if (result.length == totallength - 2) {
      immutableobject(temp);
      view.successfullyran();
    } else {
      view.showOptionError();
    }
  }

  private void functionNumImg1Img2(String[] result, String functionname) {
    if (result.length == 4) {
      double value;
      try {
        value = Double.parseDouble(result[1]);
        if (imagemap.containsKey(result[2])) {
          if (functionname.equals("brighten")) {
            Map<String, Imageloader> temp = model.brighten(imagemap.get(result[2]),
                    (int) value, result[3]);
            immutableobject(temp);
            view.successfullyran();
          } else if (functionname.equals("compress")) {
            if (value >= 0 && value <= 100) {
              Map<String, Imageloader> temp = model.compression(imagemap.get(result[2]), value,
                      result[3]);
              immutableobject(temp);
              view.successfullyran();
            } else {
              view.showinvalidpercentage();
            }
          }
        } else {
          view.invalidimage();
        }
      } catch (NumberFormatException ex) {
        view.showOptionError();
      }
    } else {
      view.showOptionError();
    }
  }

  private void functionImg1Img2Components(String[] result, String functionname,
                                          List<String> componentsflips, String[] split) {
    if (result.length == 3 || result.length == 5) {
      if (imagemap.containsKey(result[1])) {
        if (componentsflips.contains(split[0])) {
          if (functionname.equals("component")) {
            //switch (functionname) {
            //case "component":
            Map<String, Imageloader> temp = model.component(imagemap.get(result[1]),
                    split[0], result[2]);
            if (Objects.equals(split[0], "luma")) {
              split(result, temp, 5);
            } else if (result.length == 3) {
              immutableobject(temp);
              view.successfullyran();
            } else {
              view.showOptionError();
            }
          } else if (functionname.equals("flip")) {
            if (result.length == 3) {
              Map<String, Imageloader> temp = model.flip(imagemap.get(result[1]), split[0],
                      result[2]);
              immutableobject(temp);
              view.successfullyran();
            } else {
              view.showOptionError();
            }
          }
        } else {
          view.invalidcomponent();
        }
      } else {
        view.invalidimage();
      }
    } else {
      view.showOptionError();
    }
  }

  private void functionImg1Img2(String[] result, String functionname) {
    if (result.length == 3 || result.length == 5) {
      if (imagemap.containsKey(result[1])) {
        Map<String, Imageloader> temp;
        switch (functionname) {
          case "sepia":
            temp = model.sepia(imagemap.get(result[1]), result[2]);
            split(result, temp, 5);
            break;
          case "histogram":
            if (result.length == 3) {
              temp = model.histogram(imagemap.get(result[1]), result[2]);
              immutableobject(temp);
              view.successfullyran();
            } else {
              view.showOptionError();
            }
            break;
          case "sharpen":
            temp = model.sharpen(imagemap.get(result[1]), result[2]);
            split(result, temp, 5);
            break;
          case "color-correct":
            temp = model.colorcorrection(imagemap.get(result[1]), result[2]);
            split(result, temp, 5);
            break;
          case "blur":
            temp = model.blur(imagemap.get(result[1]), result[2]);
            split(result, temp, 5);
            break;
          default:
            System.out.println();
        }
      } else {
        view.invalidimage();
      }
    } else {
      view.showOptionError();
    }
  }

  private void leveladjustmethod(String[] result) {
    if (result.length == 6 || result.length == 8) {
      if (imagemap.containsKey(result[4])) {
        try {
          int b = Integer.parseInt(result[1]);
          int m = Integer.parseInt(result[2]);
          int w = Integer.parseInt(result[3]);


          if (b < m && m < w && b >= 0 && b <= 255 && m <= 255 && w <= 255) {
            Map<String, Imageloader> temp = model.leveladjustment(imagemap.get(result[4]),
                    result[5], b, m, w);
            split(result, temp, 8);
          } else {
            view.showinvalidbmwinputnotinrangeorinorder();
          }


        } catch (NumberFormatException ex) {
          view.showOptionError();
        }
      } else {
        view.invalidimage();
      }
    } else {
      view.showOptionError();
    }
  }

  private void combinemethod(String[] result) {
    if (result.length == 5) {
      if (imagemap.containsKey(result[2]) && imagemap.containsKey(result[3])
              && imagemap.containsKey(result[4])) {
        if (imagemap.get(result[2]).getWidth() == imagemap.get(result[3]).getWidth()
                && imagemap.get(result[3]).getWidth() == imagemap.get(result[4]).getWidth()
                && imagemap.get(result[2]).getHeight() == imagemap.get(result[3]).getHeight()
                && imagemap.get(result[3]).getHeight() == imagemap.get(result[4]).
                getHeight()) {
          Map<String, Imageloader> temp = model.rgbcombine(result[1],
                  imagemap.get(result[2]), imagemap.get(result[3]), imagemap.get(result[4]));
          immutableobject(temp);
          view.successfullyran();
        } else {
          view.imagesizesdonotmatch();
        }
      } else {
        view.invalidimage();
      }
    } else {
      view.showOptionError();
    }
  }

  private void runmethod(String[] result) {
    if (result.length == 2) {
      File text = new File(result[1]);
      try {
        InputStream new_input = new FileInputStream(text);
        String force_quit = "\nQ";
        new_input = new SequenceInputStream(new_input,
                new ByteArrayInputStream(force_quit.getBytes()));
        Controller new_controller = new Controller(model, new_input, view);
        new_controller.imagemap = this.imagemap;
        new_controller.run();
        immutableobject(new_controller.imagemap);
        view.successfullyran();
      } catch (FileNotFoundException e) {
        view.invalidfilelocation(result[1]);
      }
    } else {
      view.showOptionError();
    }
  }

  private void splitmethod(String[] result) {
    if (result.length == 5) {
      String[] test_duplicates = new String[3];
      int j = 0;
      for (int i = 4; i > 1; i--) {
        test_duplicates[j] = result[i];
        j += 1;
      }
      if (noDupes(test_duplicates)) {
        if (imagemap.containsKey(result[1])) {
          Map<String, Imageloader> temp = model.rgbsplit(imagemap.get(result[1]),
                  result[2], result[3], result[4]);
          immutableobject(temp);
          view.successfullyran();
        } else {
          view.invalidimage();
        }
      } else {
        view.duplicateimagenames();
      }
    } else {
      view.showOptionError();
    }
  }

  private void savemethod(String[] result) {
    if (result.length == 3) {
      if (imagemap.containsKey(result[2])) {
        this.imagemap = new HashMap<>(imagemap);
        List<String> filetypes = new ArrayList<String>();
        Collections.addAll(filetypes, "png", "ppm", "jpg");
        String[] format;
        format = result[1].split("[.]");
        String format_string = format[format.length - 1];
        if (filetypes.contains(format_string)) {
          try {
            Save saveimage = new Save(); // This is wrong
            saveimage.saveImage(imagemap.get(result[2]), result[1], format_string);
            view.successfullyran();
            imagemap = Collections.unmodifiableMap(imagemap);
          } catch (IOException e) {
            view.invalidfilelocation(result[1]);
          }
        } else {
          view.invalidfilepathformat();
        }
      } else {
        view.invalidimage();
      }
    } else {
      view.showOptionError();
    }
  }

  private void immutableobject(Map<String, Imageloader> temp) {
    this.imagemap = new HashMap<>(imagemap);
    imagemap.putAll(temp);
    imagemap = Collections.unmodifiableMap(imagemap);
  }

  private boolean noDupes(Object[] array) {
    return Arrays.stream(array).allMatch(new HashSet<>()::add);
  }


}





