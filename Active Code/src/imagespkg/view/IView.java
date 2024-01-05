package imagespkg.view;

/**
 * This interface will be implemented as the view for our MVC structure. The class that implements
 * this interface's purpose is simple: create the user interface that will appear in the console of
 * IntelliJ. This has a list of commands that should return print's and println's.
 */
public interface IView {

  /**
   * This will display the String s. This is to display the image names saved.
   *
   * @param s The string printed.
   */
  void showString(String s);

  /**
   * This will throw an error "Invalid input".
   */
  void showOptionError();

  /**
   * This will list the image names saved.
   */
  void showlistofimages();

  /**
   * If prompted: this will show the help option for the user.
   */
  void showhelp();

  /**
   * If prompted: This will show the list of commands for the user.
   */
  void showlistofcommands();

  /**
   * If prompted: This will create an empty line in the console for visability reasons.
   */
  void showemptyline();

  /**
   * If prompted: This will display comma. Specifically used for displaying the image names.
   */
  void showcomma();

  /**
   * If prompted: This will display a right bracket when displaying image names.
   */
  void showrightbracket();

  /**
   * If prompted: Will display an invalid image text and gives back the given location.
   *
   * @param string The string printed.
   */
  void invalidfilelocation(String string);

  /**
   * If prompted:  Will display an error stating the file path format is incorrect.
   */
  void invalidfilepathformat();

  /**
   * If prompted: Will display an error stating invalid image.
   */
  void invalidimage();

  /**
   * If prompted: Will display an error stating invalid component.
   */
  void invalidcomponent();

  /**
   * If prompted: Will display an error stating duplicate image names in split.
   */
  void duplicateimagenames();

  /**
   * If prompted: Will display an error stating images do not match.
   */
  void imagesizesdonotmatch();

  /**
   * If prompted: Will display sucessfully ran.
   */
  void successfullyran();

  /**
   * If prompted: Will display an error stating show invalid percentage.
   */
  void showinvalidpercentage();

  /**
   * If prompted: Will display an error stating show invalid percentage.
   */
  void showinvalidbmwinputnotinrangeorinorder();
}
