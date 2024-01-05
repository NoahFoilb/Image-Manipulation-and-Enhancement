package imagespkg;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;

import imagespkg.controller.Controller;
import imagespkg.controller.ControllerGui;
import imagespkg.controller.IController;
import imagespkg.controller.IControllerGui;
import imagespkg.model.IModel;
import imagespkg.model.Model;
import imagespkg.view.IView;
import imagespkg.view.IViewGui;
import imagespkg.view.View;
import imagespkg.view.ViewGui;

/**
 * This class aims to initialize the MVC structure and create a new instance of the controller.
 */

public class MVCmain {

  /**
   * This will take in string arguments from the console or text file inputting data into the
   * controller.
   */
  public static void main(String[] args) throws IOException {
    IModel model = new Model();
    IView view = new View(System.out);

    if (args.length == 0) {
      // No command-line arguments provided, open graphical user interface
      IViewGui view2 = new ViewGui();
      IControllerGui controller = new ControllerGui(model, view2);
    } else if (args.length == 1 && "-text".equals(args[0])) {
      // Start in interactive text mode
      IController controller = new Controller(model, System.in, view);
      controller.run();
    } else if (args.length == 3 && "-file".equals(args[0])) {
      // Execute script from a file
      String fileName = args[1];
      File scriptFile = new File(fileName);

      if (scriptFile.exists()) {
        try {
          InputStream fileInput = new FileInputStream(scriptFile);
          String forceQuit = "\nQ";
          InputStream combinedInput = new SequenceInputStream(fileInput,
                  new ByteArrayInputStream(forceQuit.getBytes()));

          // Create a new controller instance for script processing
          IController scriptController = new Controller(model, combinedInput, view);
          scriptController.run();
        } catch (FileNotFoundException e) {
          view.invalidfilelocation(fileName);
        }
      } else {
        view.invalidfilelocation(fileName);
      }
    } else {
      // Invalid command-line arguments provided
      view.showString("Invalid command-line arguments. Usage: \n");
      view.showString("java -jar Program.jar -file path-of-script-file\n");
      view.showString("java -jar Program.jar -text\n");
      view.showString("java -jar Program.jar\n");
    }
  }
}


