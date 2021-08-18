package Controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * The DefaultViewController sets the initial view the practitioner sees upon logging in
 */

public class DefaultViewController implements ItemListener {

   private AddPatientController addPatientController;

   /**
    * Constructor
    * @param addPatientController  the AddPatientController (used as utility to set the boolean flag)
    */
   public DefaultViewController(AddPatientController addPatientController){
      this.addPatientController = addPatientController;
   }

   /**
    * The action to be taken when an event has happened (e.g. clicking a button)
    * In this case the code sets the boolean flags in AddPatientController
    *
    * When the button is selected (initially set to be selected) the cholesterol and blood pressure views are
    * set to false in AddPatientController
    *
    * When the button is unselected the boolean flag is set to false
    *
    * @param e the event that was fired
    */
   @Override
   public void itemStateChanged(ItemEvent e) {
      if(e.getStateChange() == ItemEvent.SELECTED) {
         //pane.setViewportView(table);

         addPatientController.defaultViewEnabled(true);
         addPatientController.cholesterolEnabled(false);
         addPatientController.bloodPressureEnabled(false);

      }
      else if(e.getStateChange() == ItemEvent.DESELECTED){
         System.out.println("Default view disabled");
         addPatientController.defaultViewEnabled(false);
      }
   }
}
