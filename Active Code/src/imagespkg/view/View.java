package imagespkg.view;

import java.io.PrintStream;


/**
 * This class will rarely take in an argument for this implementation and will return print
 * statements when called.
 */
public class View implements IView {
  private PrintStream out;

  /**
   * This initialized the system.out stream.
   */

  public View(PrintStream out) {
    this.out = out;
  }

  /**
   * This will display the String s. This is to display the image names saved.
   *
   * @param s The string printed.
   */
  @Override
  public void showString(String s) {
    out.print(s);
  }

  /**
   * This will list the image names saved.
   */
  @Override
  public void showlistofimages() {
    out.print("Currently loaded images:\n[");
  }

  /**
   * If prompted: This will display comma. Specifically used for displaying the image names.
   */
  @Override
  public void showcomma() {
    out.print(", ");
  }

  /**
   * If prompted: This will display a right bracket when displaying image names.
   */
  @Override
  public void showrightbracket() {
    out.print("]\n");
  }

  /**
   * If prompted: Will display an invalid image text and gives back the given location.
   *
   * @param string The string printed.
   */
  @Override
  public void invalidfilelocation(String string) {
    out.print("Invalid file location: " + string);
  }

  /**
   * If prompted:  Will display an error stating the file path format is incorrect.
   */
  @Override
  public void invalidfilepathformat() {
    out.print("Invalid file format");
  }

  /**
   * If prompted: Will display an error stating invalid image.
   */
  @Override
  public void invalidimage() {
    out.print("Invalid image name");
  }


  /**
   * If prompted: Will display an error stating invalid component.
   */
  @Override
  public void invalidcomponent() {
    out.print("Invalid component type");
  }

  /**
   * If prompted: Will display an error stating duplicate image names in split.
   */
  @Override
  public void duplicateimagenames() {
    out.print("Duplicate names detected: Each new name must be Distinct from another");
  }

  /**
   * If prompted: Will display an error stating images do not match.
   */
  @Override
  public void imagesizesdonotmatch() {
    out.print("Images must share sizes");
  }

  /**
   * If prompted: Will display an error stating show invalid percentage.
   */
  @Override
  public void showinvalidpercentage() {
    out.print("Invalid Percentage");
  }

  @Override
  public void showinvalidbmwinputnotinrangeorinorder()  {
    out.print("b, m, or w input was out of bounds or greater than the next");
  }

  /**
   * If prompted: Will display sucessfully ran.
   */
  @Override
  public void successfullyran() {
    out.print("Task ran successfully");
  }

  /**
   * If prompted: Will display help option.
   */
  @Override
  public void showhelp() {
    out.println("Type [help] for a list of commands");
  }

  /**
   * This is show the options for possible commands.
   */
  @Override
  public void showlistofcommands() {
    out.print("Load: \t\t\tload image-path image-name \n"
            + "Save: \t\t\tsave image-path image-name \n"
            + "Component: \t\tX-component image-name dest-image-name \n"
            + "\t\t\t\t\tX is red, green, blue, value, luma, or intensity \n"
            + "Flip: \t\t\tX-flip image-name dest-image-name\n"
            + "\t\t\t\t\tX is either horizontal or vertical\n"
            + "Brighten: \t\tbrighten increment image-name dest-image-name\n"
            + "Rgb-split: \t\trgb-split image-name dest-image-name-red dest-image-name-green "
            + "dest-image-name-blue\n"
            + "Rgb-combine: \trgb-combine image-name red-image green-image blue-image\n"
            + "Blur: \t\t\tblur image-name dest-image-name\n"
            + "Sharpen: \t\tsharpen image-name dest-image-name\n"
            + "Sepia: \t\t\tsepia image-name dest-image-name\n"
            + "Compress: \t\tcompress percentage image-name dest-image-name\n"
            + "Color-Correct: \tcolor-correct image-name dest-image-name\n"
            + "Level-Adjust: \tlevel-adjust b m w image-name dest-image-name\n"
            + "Run: \t\t\trun script-file\n"
            + "\nAdditional commands to add to the end of blur, sharppen, sepia, luma-component, " +
            "     color-correction, level-adjust\n"
            + "Splice:\t\t\tsplice percentage\n\n"
            + "Q:\t\t\t\tExit program");
  }

  /**
   * This will throw an error "Invalid input".
   */
  @Override
  public void showOptionError() {
    out.print("\nInvalid input. Please try again.");
  }

  /**
   * If prompted: This will create an empty line in the console for visability reasons.
   */
  @Override
  public void showemptyline() {
    out.print("\n");
  }


}
