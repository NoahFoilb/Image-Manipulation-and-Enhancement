package imagespkg.view;

import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.JTextField;

import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import imagespkg.Imageloader;

/**
 * This class implements the iView interface and adds the graphical user interface capabilities.
 */
public class ViewGui extends JFrame implements IViewGui {
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;
  private JButton fileOpenButton;
  private JButton fileSaveButton;
  private JButton[] buttons;
  private JCheckBox[] rbarray;
  private JCheckBox preview_check;
  private JSlider preview_slider;
  private JButton undoButton;
  private ImageIcon image_icon;
  private JLabel mainimage;
  private ImageIcon image_icon_hist;
  private JLabel mainimage_hist;

  /**
   * Constructor which initializes the graphical user interface.
   */
  public ViewGui() {
    super();
    setTitle("PDP Image Manipulator");
    setSize(1300, 650);
    JScrollPane mainScrollPane;
    JPanel mainPanel;
    mainPanel = new JPanel();
    mainScrollPane = new JScrollPane(mainPanel);
    this.add(mainScrollPane);

    // Set up three main column panels
    JPanel left_panel = new JPanel();
    JPanel center_panel = new JPanel();
    JPanel right_panel = new JPanel();
    mainPanel.add(left_panel);
    mainPanel.add(center_panel);
    mainPanel.add(right_panel);
    left_panel.setLayout(new BoxLayout(left_panel, BoxLayout.PAGE_AXIS));
    center_panel.setLayout(new BoxLayout(center_panel, BoxLayout.PAGE_AXIS));
    right_panel.setLayout(new BoxLayout(right_panel, BoxLayout.PAGE_AXIS));
    left_panel.setBorder(new TitledBorder(""));
    left_panel.setPreferredSize(new Dimension(new Dimension(400, 560)));

    // Undo
    JPanel undo_redo_panel = new JPanel();
    undo_redo_panel.setLayout(new BoxLayout(undo_redo_panel, BoxLayout.LINE_AXIS));
    left_panel.add(undo_redo_panel);
    undoButton = new JButton("Undo previous action");
    undoButton.setActionCommand("Undo");
    undo_redo_panel.add(undoButton);
    undo_redo_panel.setBorder(new TitledBorder("Undo"));

    left_panel.add(Box.createVerticalStrut(30));
    // Preview
    JPanel preview = new JPanel();
    preview_check = new JCheckBox("Preview Mode");
    preview_check.setActionCommand("Split");
    preview.add(preview_check);
    preview_slider = new JSlider(0, 100, 50);
    preview_slider.setMajorTickSpacing(10);
    preview_slider.setPaintTicks(true);
    preview_slider.setPaintLabels(true);
    preview_slider.setLabelTable(preview_slider.createStandardLabels(10));
    preview.add(preview_slider);
    preview.setLayout(new BoxLayout(preview, BoxLayout.LINE_AXIS));
    preview.setBorder(new TitledBorder("Preview transformations in a split view"));
    left_panel.add(preview);
    left_panel.add(Box.createVerticalStrut(30));

    // Add visualize red, blue, green as a "selection"
    JPanel component_radio = new JPanel();
    List<String> components = new ArrayList<String>();
    Collections.addAll(components, "Red", "Green", "Blue");
    rbarray = new JCheckBox[3];
    for (int i = 0; i < rbarray.length; i++) {
      String str = components.get(i);
      rbarray[i] = new JCheckBox(str);
      rbarray[i].setActionCommand(str);
      component_radio.add(rbarray[i]);
    }
    component_radio.setLayout(new BoxLayout(component_radio, BoxLayout.LINE_AXIS));
    component_radio.setBorder(new TitledBorder("Visualize R,G,B"));
    left_panel.add(component_radio);
    left_panel.add(Box.createVerticalStrut(30));

    // Commands
    JPanel commandGroup = new JPanel();
    List<String> commands = new ArrayList<String>();
    Collections.addAll(commands, "Horizontal Flip", "Vertical Flip", "Blur", "Sharpen",
            "Greyscale", "Sepia", "Color Correct", "Compress", "Level Adjust");
    buttons = new JButton[9]; //
    for (int i = 0; i < buttons.length; i++) {
      String str = commands.get(i);
      buttons[i] = new JButton(str);
      buttons[i].setActionCommand(str);
      commandGroup.add(buttons[i]);
    }
    commandGroup.setLayout(new BoxLayout(commandGroup, BoxLayout.PAGE_AXIS));
    //commandGroup.setBorder( BorderFactory.createTitledBorder("Transformations"));
    TitledBorder titledBorder = BorderFactory.createTitledBorder("Transformations");

    // Get the current font from the titled border or use a default one
    Font currentFont = titledBorder.getTitleFont();
    if (currentFont == null) {
      // Use a default font if the current font is not set
      currentFont = UIManager.getFont("TitledBorder.font");
    }

    // Increase the font size, keeping the same font family and style
    float newSize = currentFont.getSize() + 2.0f; // Increase the font size by 2
    Font newFont = currentFont.deriveFont(newSize);

    // Set the new font to the titled border
    titledBorder.setTitleFont(newFont);

    // Set the custom border to the panel
    commandGroup.setBorder(titledBorder);
    commandGroup.setAlignmentX(CENTER_ALIGNMENT);
    left_panel.add(commandGroup);

    // Open file
    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new BoxLayout(fileopenPanel, BoxLayout.LINE_AXIS));
    fileOpenButton = new JButton("Load"); //
    fileOpenButton.setToolTipText("Click to open a dialog to load an image file.");
    fileOpenButton.setActionCommand("Load");
    fileOpenButton.setPreferredSize(new Dimension(70, 20));
    fileopenPanel.add(fileOpenButton);

    // Display
    fileOpenDisplay = new JLabel(" File path will appear here");
    JScrollPane fileOpenDisplayScrollable = new JScrollPane(fileOpenDisplay);
    fileOpenDisplayScrollable.setPreferredSize(new Dimension(100, 40));
    fileopenPanel.add(fileOpenDisplayScrollable);
    fileopenPanel.setBorder(new TitledBorder("Load / Open a file"));
    center_panel.add(fileopenPanel);

    // Save File
    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new BoxLayout(filesavePanel, BoxLayout.LINE_AXIS));
    fileSaveButton = new JButton("Save"); //
    fileSaveButton.setActionCommand("Save");
    fileSaveButton.setPreferredSize(new Dimension(70, 20));
    filesavePanel.add(fileSaveButton);
    fileSaveDisplay = new JLabel(" File path will appear here");
    JScrollPane fileSaveDisplayScrollable = new JScrollPane(fileSaveDisplay);
    fileSaveDisplayScrollable.setPreferredSize(new Dimension(100, 40));
    filesavePanel.add(fileSaveDisplayScrollable);
    filesavePanel.setBorder(new TitledBorder("Save / Download the image"));
    right_panel.add(filesavePanel);

    // First Image
    image_icon = new ImageIcon();
    mainimage = new JLabel(image_icon);
    mainimage.setLayout(new GridLayout(1, 0, 10, 10));
    mainimage.setIcon(image_icon);
    JScrollPane mainimagescrollable = new JScrollPane(mainimage);
    mainimagescrollable.setPreferredSize(new Dimension(500, 500));
    mainimagescrollable.setBorder(new TitledBorder(""));
    center_panel.add(mainimagescrollable);

    // Seconds Image
    image_icon_hist = new ImageIcon();
    mainimage_hist = new JLabel(image_icon_hist);
    mainimage_hist.setLayout(new GridLayout(1, 0, 10, 10));
    JScrollPane mainimagescrollablehist = new JScrollPane(mainimage_hist);
    mainimage_hist.setIcon(image_icon_hist);
    mainimagescrollablehist.setPreferredSize(new Dimension(300, 500));
    mainimagescrollablehist.setBorder(new TitledBorder("Pixel Frequencies"));
    right_panel.add(mainimagescrollablehist);
  }


  private BufferedImage tobufferedimage(Imageloader image) {
    BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(),
            BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        Imageloader.Pixel pixel = image.getPixel(j, i);
        int rgb = (pixel.getRed() << 16) | (pixel.getGreen() << 8) | pixel.getBlue();
        bufferedImage.setRGB(j, i, rgb);
      }
    }
    return bufferedImage;
  }

  /**
   * Sets the image icons for the display and histogram visualizations within the GUI.
   *
   * @param displayimage The Imageloader instance containing the image to be displayed.
   * @param histogram    The Imageloader instance containing the histogram image data.
   */
  @Override
  public void setIconImages(Imageloader displayimage, Imageloader histogram) {
    image_icon = new ImageIcon(tobufferedimage(displayimage));
    mainimage.setIcon(image_icon);
    image_icon_hist = new ImageIcon(tobufferedimage(histogram));
    mainimage_hist.setIcon(image_icon_hist);
  }

  /**
   * Displays an error message to the user when an invalid manual input is detected.
   *
   * @param s The error message to be displayed.
   */
  @Override
  public void invalidManualInput(String s) {
    JOptionPane.showMessageDialog(this, "Invalid Input: " + s,
            "Invalid Entry", JOptionPane.ERROR_MESSAGE);
  }

  private JFileChooser filters() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG, PPM & PNG Images", "jpg", "png", "ppm");
    fchooser.setFileFilter(filter);
    return fchooser;
  }

  /**
   * Provides feedback to the user that an image is currently loading.
   *
   * @return A message indicating the loading process.
   */
  @Override
  public String loading() {
    final JFileChooser fchooser = filters();
    int retvalue = fchooser.showOpenDialog(null);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      fileOpenDisplay.setText(fchooser.getSelectedFile().getAbsolutePath());
      return fchooser.getSelectedFile().getAbsolutePath();
    }
    return null;
  }

  /**
   * Provides feedback to the user that an image is being saved.
   *
   * @return A message indicating the saving process.
   */
  @Override
  public String saving() {
    final JFileChooser fchooser = filters();
    int retvalue = fchooser.showSaveDialog(null);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      fileSaveDisplay.setText(fchooser.getSelectedFile().getAbsolutePath());
      return fchooser.getSelectedFile().getAbsolutePath();
    }
    return null;
  }

  /**
   * Registers the action, item, and change listeners that will handle corresponding
   * user interaction events within the GUI.
   *
   * @param listener  The ActionListener for handling action events.
   * @param listener2 The ItemListener for handling item events.
   * @param listener3 The ChangeListener for handling change events.
   */
  @Override
  public void setListener(ActionListener listener, ItemListener listener2,
                          ChangeListener listener3) {
    this.fileOpenButton.addActionListener(listener);
    this.fileSaveButton.addActionListener(listener);
    this.undoButton.addActionListener(listener);

    for (int i = 0; i < buttons.length; i++) {
      this.buttons[i].addActionListener(listener);
    }
    this.preview_check.addItemListener(listener2);
    for (int i = 0; i < rbarray.length; i++) {
      this.rbarray[i].addItemListener(listener2);
    }
    preview_slider.addChangeListener(listener3);
  }

  /**
   * Displays the main GUI window to the user.
   */
  @Override
  public void display() {
    setVisible(true);
  }

  /**
   * Retrieves the user input for the compression percentage from the GUI.
   *
   * @return A String representing the compression percentage.
   */
  @Override
  public String viewCompressionPercent() {
    String num = JOptionPane.showInputDialog("Percent compressed by (Must be between 0 - 100)");
    return num;
  }

  /**
   * Retrieves the user inputs for level adjustments from the GUI.
   *
   * @return An array of String containing the level adjustment values.
   */
  @Override
  public String[] levelAdjInputs() {
    JTextField bField = new JTextField(5);
    JTextField mField = new JTextField(5);
    JTextField wField = new JTextField(5);
    JLabel top = new JLabel("Please Enter Valid B,M,W values where\n\t 0 <= B < M < W < 256");
    JPanel myPanel = new JPanel();
    JPanel toppanel = new JPanel();
    JPanel bottom = new JPanel();
    myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));
    myPanel.add(toppanel);
    myPanel.add(bottom);
    toppanel.add(top);
    bottom.add(new JLabel("B:"));
    bottom.add(bField);
    bottom.add(Box.createHorizontalStrut(15)); // a spacer
    bottom.add(new JLabel("M:"));
    bottom.add(mField);
    bottom.add(Box.createHorizontalStrut(15)); // a spacer
    bottom.add(new JLabel("W:"));
    bottom.add(wField);

    JOptionPane.showConfirmDialog(null, myPanel,
            "Level Adjustment", JOptionPane.OK_CANCEL_OPTION);


    return new String[]{bField.getText(), mField.getText(), wField.getText()};
  }


}
