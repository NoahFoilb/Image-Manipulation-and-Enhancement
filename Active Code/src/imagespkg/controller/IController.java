package imagespkg.controller;

/**
 * This interface will be implemented as the controller for our MVC structure. The only method
 * required by this interface is the "void run()", which will initialize and run our program.
 */
public interface IController {


  /**
   * Run is what will continue to be looped until the user specifies that its over. This is where
   * the user gets asked to input data and where the data gets sent into the model.
   */
  void run();
}
