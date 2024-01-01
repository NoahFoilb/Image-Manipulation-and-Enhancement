README file:
	The purpose of this file is to explain the design. This should give the grader an overview of the purpose of every class, interface, design choices and justifications.


Interfaces
	IController: This interface will be implemented as the controller for our MVC structure. The only method required by this interface is the "void go()," which will initialize and run our program.

	IModel: This interface will be implemented as the model for our MVC structure. The purpose of this class is to act as the "image manipulator" and make the changes the controller requests. The methods will typically take in an assortment of class "imagespkg.Imageloader" objects and string names to return hashmaps of <string, imageloader>.
	
	IView: This interface will be implemented as the view for our MVC structure. The class that implements this interface's purpose is simple: create the user interface that will appear in the console of IntelliJ. This has a list of commands that should return print's and println'.

    IView_Gui: This interface will be implemented as the view for our Graphical user interface. This view supports Java swing functionalities.

    IControllerGui:


Classes
	MVCmain: This class aims to initialize the MVC structure and create a new controller instance.

	Imageloader: The purpose of this class is to be able to create and hold data on an image.
		Innerclass Pixel: This class aims to hold the red, green, and blue values at a given pixel.
	
	Save: This class aims to take in an image loaded on the controller and download it on the user's computer.

	Controller: This class will take in user-inputted data or textfile data to send data into the model and view to perform selected user tasks.

	View: This class will rarely take in an argument for this implementation and will return print statements when called.

	View_Gui: This class aims to create a Graphical user interface for the program. It implements panels, frames, buttons, sliders, checkboxes etc.

	Controller_Gui: This class interacts with the model and the view_gui. It waits for actions and state changes in the gui and interacts with the model.


The model, view and the controller are complete.

Assignment 5:

Some Design changes we made for assignment 5 are:
1. move logic from long switch cases into their own separate helper methods.
        Justification: To reduce long switch cases and make the code more readable.
2. Create a lambda function to reduce code duplication for the component function.
        Justification: To reduce code duplication
3. Make our hashmap returned by the model to the controller immutable. so that the controller
cannot modify the images.
        Justification: Only the model should be allowed to modify and perform operations.
4. Reorganized the structure by moving the model classes into a model package, controller classes
into a controller package and the view classes into a view package.
        Justification: To keep the code organized and for better encapsulation.


Assignment 6:
Some Design changes we made for assignment 6 are:
1. Implemented a new Interface for the GUI controller and view. The view and controller for the gui required methods not required by the previous implementaton of the controller and view.
        Justification: The new GUI controller and view require methods different from the previous implementations.
2. Implemented new classes for the GUI's controller and view.
        Justification: The GUI requires a controller which handles state changes, action listerners etc. The view implements GUI features such as jframes, panes, buttons, sliders etc.

Image Credits and Usage Authorization:
All images utilized in this project are original creations by our team. We hold the full rights to
these images and hereby authorize their use within the context of this project. Redistribution or
use outside the scope of this project is not permitted without explicit consent from our team.
