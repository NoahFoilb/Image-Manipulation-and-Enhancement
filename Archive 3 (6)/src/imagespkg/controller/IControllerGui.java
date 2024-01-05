package imagespkg.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.event.ChangeEvent;

/**
 * This interface defines a GUI controller within the MVC (Model-View-Controller) application
 * framework. It declares methods to handle various user interactions through action events,
 * item state changes, and general state changes within the GUI. Implementations of this interface
 * should manage the communication between the view and the model in response to these events.
 */
public interface IControllerGui {

  /**
   * Invoked when an action occurs on a GUI component. This method should handle
   * all action-based interactions, such as button clicks, menu selections, or
   * any other event that triggers an ActionEvent.
   *
   * @param event the ActionEvent object that provides information about
   *             the event and its source.
   */
  void actionPerformed(ActionEvent event);

  /**
   * Invoked when an item's state changes on a GUI component. This method should
   * handle changes in items such as checkboxes, radio buttons, or any component
   * that maintains a set of items with selectable or toggleable states.
   *
   * @param itemEvent the ItemEvent object that provides information about
   *             the item event and its source.
   */
  void itemStateChanged(ItemEvent itemEvent);

  /**
   * Invoked when the state changes on a GUI component. This method is typically
   * called in response to a user interacting with a component that maintains
   * a mutable state, such as a slider or a spinner.
   *
   * @param ce the ChangeEvent object that provides information about
   *           the state change event and its source.
   */
  void stateChanged(ChangeEvent ce);
}
