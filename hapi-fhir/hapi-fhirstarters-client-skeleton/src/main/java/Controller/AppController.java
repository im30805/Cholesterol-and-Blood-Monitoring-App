package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.hl7.fhir.r4.model.Practitioner;

import GUI.ViewData;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;
import task2.application.ServerBase;

/**
 * AppController will act as the controller class for View (GUI.AppLogin) and the model (task2.application)
 * */
public class AppController implements ActionListener {
	private JTextField idInput;
	private JFrame frame;
	public AppController(JTextField idInput,JFrame frame ) {
		super();
		this.idInput = idInput;
	}
	@Override
	/**
	 * Performs operation when button clicked.
	 * This is used to run and get data from server, by retrieving valid practitioner ID
	 * User input of practitioner ID must be valid
	 * @return: None
	 **/
	public void actionPerformed(ActionEvent e) {
		
		// get user input text
		String getinput = idInput.getText();
	      Practitioner practitioner;
	      try {
	         // check if user input id is valid
	         practitioner = ServerBase.getClient().read()
	            .resource(Practitioner.class)
	            .withId(getinput)
	            .execute();
		        JOptionPane.showMessageDialog(frame, "Login Success!");
		        
	      } catch (ResourceNotFoundException i) {
	    	  // if not valid, print message
	    	  JOptionPane.showMessageDialog(frame,"Practitioner ID input was invalid!");
	         return;
	      }
	      
	      // if valid, get the practitioner name and identifier
	      String pracFamilyName = practitioner.getName().get(0).getFamily();
		  String pracGivenName = String.valueOf(practitioner.getName().get(0).getGiven().get(0));
		  String practitionerName = "Dr. " + pracGivenName + " " + pracFamilyName;
		  String practitionerIdentifier = practitioner.getIdentifier().get(0).getValue();
		  // go to next GUI
	      ViewData viewData = new ViewData(practitionerName,practitionerIdentifier);
	      viewData.setVisible(true);

		
	}
	
}
