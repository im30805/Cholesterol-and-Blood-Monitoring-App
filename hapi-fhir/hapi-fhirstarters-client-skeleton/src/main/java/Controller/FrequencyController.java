package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.frontangle.ichart.chart.bar.XYBarDataSeries;
import task2.application.EncounterProcessor;
import task2.application.PatientInfo;
import task2.application.PatientProcessor;


/**
 * FrequencyController act as the controller for ViewData when user wants to change the frequency of the updates
 * for the data (cholesterol,time)
 *
 * */
public class FrequencyController implements ActionListener {
   private int frequency;
   private String practitionerName;
   private String practitionerIdentifier;
   private JTextField inputFrequency;
   private DefaultTableModel model;
   private JTable table;
   private JFrame frame;

   private XYBarDataSeries barSeries;
   private JScrollPane pane;

   /*
    * Constructor for FrequencyController Class
    * @param practitionerName the practitioner's name
    * @param practitionerIdentifier the practitioner's identifier
    * @param inputFrequency how often in seconds that a query to a server is called
    * @param table a table display to add the patients into
    * @param model a skeleton for the table
    * */
   public FrequencyController(String practitionerName, String practitionerIdentifier, JTextField inputFrequency,
                              JTable table, DefaultTableModel model, XYBarDataSeries barSeries, JScrollPane pane) {
      this.practitionerName = practitionerName;
      this.practitionerIdentifier = practitionerIdentifier;
      this.inputFrequency = inputFrequency;
      this.table = table;
      this.model = model;

      this.barSeries = barSeries;
      this.pane = pane;

   }
   @Override
   /**
    * Action performed when button is pressed, this gets the frequency input from user, and updates the table with
    * the new frequency
    * */
   public void actionPerformed(ActionEvent e) {

      // get user input
      String getinput = inputFrequency.getText();

      // check if user input is integer
      try {
         int x = Integer.parseInt(getinput);

      }
      catch(NumberFormatException i) {
         // if input is not integer print message
         frame = new JFrame();
         JOptionPane.showMessageDialog(frame, "Input must be integer only!");
         return;
      }


      int x = Integer.parseInt(getinput);
      AddPatientController add = new AddPatientController(practitionerName, practitionerIdentifier, table, model, x,
         barSeries, pane);

      add.update();  //updates the table
   }
   public void setFrequency(int frequency) {
      this.frequency = frequency;
   }

   public int getFrequency() {

      return this.frequency;
   }

}
