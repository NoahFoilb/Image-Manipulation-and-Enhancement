package imagespkg.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import imagespkg.Imageloader;
import imagespkg.model.IModel;
import imagespkg.view.IViewGui;


/**
 * This class will take in user inputted data or textfile data in order to send data into the model
 * and view in order to perform selected user tasks.
 */
public class ControllerGui implements IControllerGui, ActionListener, ItemListener, ChangeListener {
  private IViewGui view;
  private IModel model;
  private Imageloader theimage;
  private Imageloader thesplitimage;
  private ArrayList<String> listofcommands;
  private Imageloader theoriginalimage;
  private boolean undo;
  private boolean red;
  private boolean blue;
  private boolean green;
  private boolean split;
  private Imageloader displayimage;
  private int slider_value;
  private ArrayList<Double> listofinputvalues_compress;
  private int[] lvl_nums;
  private double percent;
  private ArrayList<int[]> listofinputvalues_lvl;
  private ArrayList<int[]> listlvl;
  private ArrayList<Double> listcomp;
  private ArrayList<String> listcommands;

  /**
   * The controller will take in a model for its calculations, an inputstream for its inputs and a
   * view for its user interface.
   */
  public ControllerGui(IModel model, IViewGui view) {
    this.model = model;
    this.view = view;
    view.setListener(this, this, this);
    view.display();
    listofcommands = new ArrayList<>();
    listofinputvalues_compress = new ArrayList<>();
    this.slider_value = 50;
    listofinputvalues_lvl = new ArrayList<>();
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("Application is shutting down.");
    }));
  }


  /**
   * Invoked when an action occurs on a GUI component. This method should handle
   * all action-based interactions, such as button clicks, menu selections, or
   * any other event that triggers an ActionEvent.
   *
   * @param event the ActionEvent object that provides information about
   *              the event and its source.
   */
  @Override
  public void actionPerformed(ActionEvent event) {
    actionPerformedSwitches(event.getActionCommand());
  }

  /**
   * Invoked when an item's state changes on a GUI component. This method should
   * handle changes in items such as checkboxes, radio buttons, or any component
   * that maintains a set of items with selectable or toggleable states.
   *
   * @param itemEvent the ItemEvent object that provides information about
   *                  the item event and its source.
   */
  @Override
  public void itemStateChanged(ItemEvent itemEvent) {
    String who = ((JCheckBox) itemEvent.getItemSelectable()).getActionCommand();
    switch (who) {
      case "Split":
        split = itemEvent.getStateChange() == ItemEvent.SELECTED;
        if (theoriginalimage != null) {
          if (split) {
            thesplitimage = theimage;
            listlvl = (ArrayList<int[]>) listofinputvalues_lvl.clone();
            listcomp = (ArrayList<Double>) listofinputvalues_compress.clone();
            listcommands = (ArrayList<String>) listofcommands.clone();
          } else {
            theimage = thesplitimage;
            listofinputvalues_lvl = (ArrayList<int[]>) listlvl.clone();
            listofinputvalues_compress = (ArrayList<Double>) listcomp.clone();
            listofcommands = (ArrayList<String>) listcommands.clone();
          }
        } else {
          ((JCheckBox) itemEvent.getItemSelectable()).setSelected(false);
        }
        break;
      case "Red":
        red = itemEvent.getStateChange() == ItemEvent.SELECTED;
        break;
      case "Green":
        green = itemEvent.getStateChange() == ItemEvent.SELECTED;
        break;
      case "Blue":
        blue = itemEvent.getStateChange() == ItemEvent.SELECTED;
        break;
      default:
        System.out.println();
    }
    createImageIcon(theimage);
  }

  /**
   * Invoked when the state changes on a GUI component. This method is typically
   * called in response to a user interacting with a component that maintains
   * a mutable state, such as a slider or a spinner.
   *
   * @param ce the ChangeEvent object that provides information about
   *           the state change event and its source.
   */
  @Override
  public void stateChanged(ChangeEvent ce) {
    JSlider source = (JSlider) ce.getSource();
    slider_value = source.getValue();
    if (split) {
      createImageIcon(this.theimage);
    }
  }

  private void actionPerformedSwitches(String action) {

    if (theoriginalimage != null || action.equals("Load")) {
      switch (action) {
        case "Undo":
          undoMethod();
          break;

        case "Horizontal Flip":
          actionSelected(model.flip(theimage, "horizontal",
                  "Horizontal Flip").get("Horizontal Flip"), action);
          break;

        case "Vertical Flip":
          actionSelected(model.flip(theimage, "vertical",
                  "Vertical Flip").get("Vertical Flip"), action);
          break;

        case "Blur":
          actionSelected(model.blur(theimage, "Blur").get("Blur"), action);
          break;

        case "Sharpen":
          actionSelected(model.sharpen(theimage, "Sharpen").get("Sharpen"), action);
          break;

        case "Greyscale":
          actionSelected(model.component(theimage, "luma",
                  "Greyscale").get("Greyscale"), action);
          break;

        case "Sepia":
          actionSelected(model.sepia(theimage, "Sepia").get("Sepia"), action);
          break;

        case "Compress":
          if (!undo) {

            String num = view.viewCompressionPercent();
            if (num.equals("")) {
              percent = -1;
            } else {
              try {
                double d = Double.parseDouble(num);
                if (d < 0 || d > 100) {
                  view.invalidManualInput("Value >= 100 or Value <= 0");
                  percent = -1;
                } else {
                  percent = d;
                }
              } catch (NullPointerException e) {
                percent = -1;
              } catch (NumberFormatException nfe) {
                view.invalidManualInput("Please only include numbers");
                percent = -1;
              }
            }

          }
          if (percent != -1) {
            actionSelected(model.compression(theimage, percent,
                    "Compress").get("Compress"), action);
            if (!undo) {
              listofinputvalues_compress.add(percent);
            }
          }
          break;

        case "Color Correct":
          actionSelected(model.colorcorrection(theimage,
                  "Color Correct").get("Color Correct"), action);
          break;

        case "Level Adjust":
          if (!undo) {

            String[] inputs = view.levelAdjInputs();

            if (isnum(inputs[0]) && isnum(inputs[1]) && isnum(inputs[2])) {
              int b = Integer.parseInt(inputs[0]);
              int m = Integer.parseInt(inputs[1]);
              int w = Integer.parseInt(inputs[2]);

              if (b < m && m < w && b >= 0 && b <= 255 && m <= 255 && w <= 255) {
                lvl_nums = new int[]{b, m, w};
              } else {

                if (b > m) {
                  view.invalidManualInput("b has to be less than m");
                } else if (m > w) {
                  view.invalidManualInput("m has to be less than w");
                } else if (b <= 0) {
                  view.invalidManualInput("b has to be greater than 0");
                } else if (b > 255) {
                  view.invalidManualInput("b has to be less or equal to 255");
                } else if (m > 255) {
                  view.invalidManualInput("m has to be less or equal to 255");
                } else if (w > 255) {
                  view.invalidManualInput("w has to be less or equal to 255");
                }
                lvl_nums = new int[]{-1, -1, -1};
              }
            } else {
              if (inputs[0].hashCode() != 0 || inputs[1].hashCode() != 0
                      || inputs[2].hashCode() != 0) {
                view.invalidManualInput("Please only include numbers / Fill out all Fields");
              }
              lvl_nums = new int[]{-1, -1, -1};
            }


          }
          if (lvl_nums[0] != -1) {
            actionSelected(model.leveladjustment(theimage, "Level Adjust",
                    lvl_nums[0], lvl_nums[1], lvl_nums[2]).get("Level Adjust"), action);
            if (!undo) {
              listofinputvalues_lvl.add(lvl_nums);
            }
          }
          break;

        case "Save": {
          String path = view.saving();
          Save save = new Save();
          String[] format = path.split("[.]");
          String format_string = format[format.length - 1];
          if (!format_string.equalsIgnoreCase("ppm") &&
                  !format_string.equalsIgnoreCase("png") &&
                  !format_string.equalsIgnoreCase("jpg") &&
                  !format_string.equalsIgnoreCase("jpeg")) {
            view.invalidManualInput("Invalid file extension. Only PPM, PNG, and " +
                    "JPG are allowed.");
            return;
          }
          try {
            save.saveImage(displayimage, path, format_string);
          } catch (IOException | NullPointerException ee) {
            view.invalidManualInput("Error saving the file: " + ee.getMessage());
          }
        }
        break;

        case "Load":
          String f = view.loading();
          try {
            this.theimage = new Imageloader(f);
            this.theoriginalimage = new Imageloader(f);
          } catch (IOException | NullPointerException e) {
            view.invalidManualInput("Invalid File Location");
          }
          createImageIcon(theimage);
          break;
        default:
          System.out.println();
      }
    } else {
      view.invalidManualInput("No image loaded yet!");
    }
  }

  private void undoMethod() {
    this.undo = true;
    List<String> simplecommands = new ArrayList<>();
    Collections.addAll(simplecommands, "Blur", "Sharpen", "Greyscale", "Sepia",
            "Color Correct", "Horizontal Flip", "Vertical Flip");

    int compression_index = 0;
    int lvl_index = 0;

    if (!listofcommands.isEmpty()) {
      if (listofcommands.get(listofcommands.size() - 1).equals("Compress")) {
        listofinputvalues_compress.remove(listofinputvalues_compress.size() - 1);

      } else if (listofcommands.get(listofcommands.size() - 1).equals("Level Adjust")) {
        listofinputvalues_lvl.remove(listofinputvalues_lvl.size() - 1);
      }

      listofcommands.remove(listofcommands.size() - 1);
      theimage = theoriginalimage;

      for (int i = 0; i < listofcommands.size(); i++) {
        String[] commandi = inarray(listofcommands.get(i), simplecommands);
        if (commandi[0].equals("Included")) {
          actionPerformedSwitches(commandi[1]);
        } else if (listofcommands.get(i).equals("Compress")) {
          percent = listofinputvalues_compress.get(compression_index);
          compression_index += 1;
          actionPerformedSwitches("Compress");
        } else if (listofcommands.get(i).equals("Level Adjust")) {
          lvl_nums = listofinputvalues_lvl.get(lvl_index);
          lvl_index += 1;
          actionPerformedSwitches("Level Adjust");
        }
      }

    }
    createImageIcon(theimage);
    this.undo = false;
  }

  private void createImageIcon(Imageloader image) {

    if (theoriginalimage != null) {
      displayimage = image;

      if (red || green || blue) {
        Imageloader red_image = model.component(displayimage, "blank",
                "R").get("R");
        Imageloader green_image = model.component(displayimage, "blank",
                "G").get("G");
        Imageloader blue_image = model.component(displayimage, "blank",
                "B").get("B");
        if (red) {
          red_image = model.component(displayimage, "red", "Red").get("Red");
        }
        if (green) {
          green_image = model.component(displayimage, "green",
                  "Green").get("Green");
        }
        if (blue) {
          blue_image = model.component(displayimage, "blue",
                  "Blue").get("Blue");
        }

        displayimage = model.rgbcombine(
                "Combine", red_image, green_image, blue_image).get("Combine");

      }
      if (split) {
        displayimage = model.split(displayimage, thesplitimage, "Split",
                slider_value).get("Split");
      }

      Imageloader histogram = model.histogram(displayimage, "histogram").get("histogram");
      view.setIconImages(displayimage, histogram);

    }
  }

  private void actionSelected(Imageloader image, String s) {
    this.theimage = image;
    createImageIcon(image);
    if (!undo) {
      listofcommands.add(s);
    }
  }

  private String[] inarray(String str, List<String> tobein) {
    for (String s : tobein) {
      if (s == str) {
        String[] string = {"Included", str};
        return string;
      }
    }
    return new String[]{"Not Included"};
  }

  private boolean isnum(String s) {
    if (s == null) {
      return false;
    } else {
      try {
        double d = Double.parseDouble(s);
        return true;
      } catch (NumberFormatException nfe) {
        return false;
      }
    }
  }

}





