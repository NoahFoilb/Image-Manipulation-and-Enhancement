package imagespkg.view;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.event.ChangeListener;

import imagespkg.Imageloader;

/**
 * The IView_Gui interface defines the graphical user interface (GUI) view
 * component in an MVC (Model-View-Controller) architecture. It specifies methods for setting image
 * icons, registering event listeners, displaying the GUI, and managing user inputs and feedback.
 */
public interface IViewGui {

  /**
   * Sets the image icons for the display and histogram visualizations within the GUI.
   *
   * @param displayimage The Imageloader instance containing the image to be displayed.
   * @param histogram    The Imageloader instance containing the histogram image data.
   */
  void setIconImages(Imageloader displayimage, Imageloader histogram);

  /**
   * Registers the action, item, and change listeners that will handle corresponding
   * user interaction events within the GUI.
   *
   * @param listener  The ActionListener for handling action events.
   * @param listener2 The ItemListener for handling item events.
   * @param listener3 The ChangeListener for handling change events.
   */
  void setListener(ActionListener listener, ItemListener listener2, ChangeListener listener3);

  /**
   * Displays the main GUI window to the user.
   */
  void display();

  /**
   * Retrieves the user input for the compression percentage from the GUI.
   *
   * @return A String representing the compression percentage.
   */
  String viewCompressionPercent();

  /**
   * Retrieves the user inputs for level adjustments from the GUI.
   *
   * @return An array of String containing the level adjustment values.
   */
  String[] levelAdjInputs();

  /**
   * Displays an error message to the user when an invalid manual input is detected.
   *
   * @param s The error message to be displayed.
   */
  void invalidManualInput(String s);

  /**
   * Provides feedback to the user that an image is currently loading.
   *
   * @return A message indicating the loading process.
   */
  String loading();

  /**
   * Provides feedback to the user that an image is being saved.
   *
   * @return A message indicating the saving process.
   */
  String saving();
}