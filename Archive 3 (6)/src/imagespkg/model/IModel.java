package imagespkg.model;

import java.util.Map;

import imagespkg.Imageloader;

/**
 * This interface will be implemented as the model for our MVC structure. The purpose of this class
 * is to act as the "image manipulator" and to make the changes the controller requests it to do.
 * The methods will typically take in an assortment of class "imageloader" objects and string names
 * in order to return hashmaps of string, imageloader.
 */
public interface IModel {


  /**
   * The purpose of this method is to take an image and convert it into a new image with only the
   * selected components transformation within the image.
   *
   * @param image         Image we want to convert into its specific part.
   * @param componenttype Needs to be of string "red, blue, green, luma, value,
   *                      intensity.
   * @throws IllegalArgumentException If component type doesnt exist AND when image is not
   *                                  found, throw an exception.
   */
  Map<String, Imageloader> component(Imageloader image, String componenttype, String newname)
          throws IllegalArgumentException;


  /**
   * Flip an image horizontally or vertically to create a new image, referred to henceforth by
   * newname.
   *
   * @param image    Image we want to convert into its specific part.
   * @param fliptype Needs to be of string "horizontal or vertical".
   * @throws IllegalArgumentException When it is not hor or vert AND when image is not found.
   */
  Map<String, Imageloader> flip(Imageloader image, String fliptype, String newname)
          throws IllegalArgumentException;


  /**
   * Brighten the image by the given increment to create a new image, referred to henceforth by the
   * given newname.
   *
   * @param image Image we want to convert into this new image.
   * @param value incremental increase/decrease based on int value.
   * @throws IllegalArgumentException Throws ann exception when it encounters an error for
   *                                  the controller to pick up.
   */
  Map<String, Imageloader> brighten(Imageloader image, int value, String newname)
          throws IllegalArgumentException;


  /**
   * Split the given image into three images containing its red, green and blue components
   * respectively.
   *
   * @param originalimage  Image that should be split.
   * @param blueimagename  Blue image component new name.
   * @param greenimagename Green image component new name
   * @param redimagename   Red image component new name.
   * @throws IllegalArgumentException Throws ann exception when it encounters an error for
   *                                  the controller to pick up.
   */
  Map<String, Imageloader> rgbsplit(Imageloader originalimage, String redimagename, String
          greenimagename, String blueimagename) throws IllegalArgumentException;


  /**
   * Combine the three greyscale images into a single image that gets its red, green and blue
   * components from the three images respectively.
   *
   * @param newname    New image name.
   * @param redimage   Image we want to red component of.
   * @param greenimage Image we want to green component of.
   * @param blueimage  Image we want to blue component of.
   * @throws IllegalArgumentException Throws ann exception when it encounters an error for
   *                                  the controller to pick up.
   */
  Map<String, Imageloader> rgbcombine(String newname, Imageloader redimage, Imageloader
          greenimage, Imageloader blueimage) throws IllegalArgumentException;


  /**
   * blur the given image and store the result in another image with the given name.
   *
   * @param image   Image that should be blurred.
   * @param newname New image name that should be saved to the blurred image.
   * @throws IllegalArgumentException Throws ann exception when it encounters an error for
   *                                  the controller to pick up.
   */
  Map<String, Imageloader> blur(Imageloader image, String newname)
          throws IllegalArgumentException;


  /**
   * sharpen the given image and store the result in another image with the given name.
   *
   * @param image   Image that should be sharpened.
   * @param newname New image name that should be saved to the sharpened image
   * @throws IllegalArgumentException Throws ann exception when it encounters an error for
   *                                  the controller to pick up.
   */
  Map<String, Imageloader> sharpen(Imageloader image, String newname)
          throws IllegalArgumentException;


  /**
   * produce a sepia-toned version of the given image and store the result in another image with
   * the given name.
   *
   * @param image   Image that should be sepia'd.
   * @param newname New image name that should be saved to the sepia'd image.
   * @throws IllegalArgumentException Throws ann exception when it encounters an error for
   *                                  the controller to pick up.
   */
  Map<String, Imageloader> sepia(Imageloader image, String newname)
          throws IllegalArgumentException;


  /**
   * produce a sepia-toned version of the given image and store the result in another image with
   * the given name.
   *
   * @param image   Image that should be color corrected
   * @param newname New image name that should be saved to the sepia'd image.
   * @throws IllegalArgumentException Throws ann exception when it encounters an error for
   *                                  the controller to pick up.
   */
  Map<String, Imageloader> colorcorrection(Imageloader image, String newname)
          throws IllegalArgumentException;


  /**
   * Adjust the image based on the inputs of b, m, w.
   *
   * @param image   The image being transformed.
   * @param newname The new name for the created image.
   * @param b       The min cap for black.
   * @param m       The middle cap.
   * @param w       The min cap for white.
   * @return Returns type Map holding image and name.
   * @throws IllegalArgumentException Throws ann exception when it encounters an error for
   *                                  the controller to pick up.
   */
  Map<String, Imageloader> leveladjustment(Imageloader image, String newname, int b, int m, int w)
          throws IllegalArgumentException;


  /**
   * This should create a histogram of the requested image.
   *
   * @param image   The image being transformed.
   * @param newname The new name for the created image.
   * @return Returns type Map holding image and name.
   * @throws IllegalArgumentException Throws ann exception when it encounters an error for
   *                                  the controller to pick up.
   */
  Map<String, Imageloader> histogram(Imageloader image, String newname)
          throws IllegalArgumentException;


  /**
   * This splits an image into 2 different operations.
   *
   * @param leftimage  The left image being transformed.
   * @param rightimage The right image being transformed.
   * @param newname    The new name for the created image.
   * @param percent    Percent of image being changed.
   * @return Returns type Map holding image and name.
   * @throws IllegalArgumentException Throws ann exception when it encounters an error for
   *                                  the controller to pick up.
   */
  Map<String, Imageloader> split(Imageloader leftimage, Imageloader rightimage, String newname,
                                 double percent)
          throws IllegalArgumentException;

  /**
   * This performs a compression on the image, by the percentaage provided.
   *
   * @param image               The image being transformed.
   * @param newname             The new name for the created image.
   * @param thresholdPercentage Percent of image being changed.
   * @return Returns type Map holding image and name.
   * @throws IllegalArgumentException Throws ann exception when it encounters an error for
   *                                  the controller to pick up.
   */
  Map<String, Imageloader> compression(Imageloader image, double thresholdPercentage,
                                       String newname)
          throws IllegalArgumentException;
}
