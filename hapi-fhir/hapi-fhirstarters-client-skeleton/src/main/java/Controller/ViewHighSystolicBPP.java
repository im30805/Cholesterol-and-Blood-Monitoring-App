package Controller;

import task2.application.BloodPressureProcessor;
import task2.application.PatientInfo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.*;

/**
 * The ViewHighSystolicBPP class checks for the patients that are considered to have high systolic blood pressure
 * and generates a textual monitor showing the latest 5 readings for each patient
 */

public class ViewHighSystolicBPP implements ActionListener{
   private JTextField inputSystolicBlood;
   private Vector currentlyMonitoredPatients;
   private JLabel label;
   private AddPatientController addPatientController;
   private ArrayList<String> monitoredPatientNames = new ArrayList<>();
   private ArrayList<ArrayList<ArrayList<String>>> monitoredSystolicBPList = new ArrayList<>();
   private JButton viewSystolicGraphsButton;

   /**
    * Constructor
    * @param inputSystolicBlood the systolic BP value inputted by the practitioner
    * @param currentlyMonitoredPatients the data of patients that are currently monitored in the table view
    * @param label a JLabel component that will be used to display the information
    * @param addPatientController the AddPatientController (used as utility to get full list of patients)
    * @param viewSystolicGraphsButton a JButton component that is set visible once the textual monitor is shown
    */
   public ViewHighSystolicBPP(JTextField inputSystolicBlood, Vector currentlyMonitoredPatients, JLabel label,
                              AddPatientController addPatientController, JButton viewSystolicGraphsButton){
      this.inputSystolicBlood = inputSystolicBlood;
      this.currentlyMonitoredPatients = currentlyMonitoredPatients;
      this.label = label;
      this.addPatientController = addPatientController;
      this.viewSystolicGraphsButton = viewSystolicGraphsButton;

   }

   /**
    * The action to be taken when an event has happened (e.g. clicking a button)
    * In this case the code below splits the Vector of currently monitored patients into and puts the values into
    * an ArrayList dataArrayList for easier access to the values. The systolic BP is checked to see if it is higher
    * than the threshold provided by the practitioner. If yes, the latest 5 readings are obtained and printed for
    * the particular patient
    *
    * After this process, the button to view the graphs version is set visible on the app
    *
    * @param e the event that was fired
    */
   @Override
   public void actionPerformed(ActionEvent e) {
      ArrayList<String[]> dataArrayList = new ArrayList<>();
      ArrayList<ArrayList<String>> patientReadingsStrings = new ArrayList<>();

      for(int i = 0; i < currentlyMonitoredPatients.size(); i++){
         String stringData = currentlyMonitoredPatients.get(i).toString();
         dataArrayList.add(stringData.split(","));

      }

      double inputSystolicValue = Double.parseDouble(inputSystolicBlood.getText());
      List<PatientInfo> patientInfoList = addPatientController.getList();

      //System.out.println("From systolic monitor, input value = " + inputSystolicValue);

      for(int j = 0; j < dataArrayList.size(); j++){
         if(Double.parseDouble(dataArrayList.get(j)[3]) > inputSystolicValue){

            for(PatientInfo patient : patientInfoList){
               BloodPressureProcessor bloodPressureProcessor = new BloodPressureProcessor(patient);
               bloodPressureProcessor.update();

               String fullName = patient.getName() + " " + patient.getSurname();

               //System.out.println(dataArrayList.get(j)[0]);
               //System.out.println(fullName);
               //System.out.println(fullName.equals(dataArrayList.get(j)[0].replace('[', ' ').trim()));

               if(fullName.equals(dataArrayList.get(j)[0].replace('[', ' ').trim())){
                  //System.out.println("Enter 2");
                  ArrayList<String> monitoredPatientDataStrings = new ArrayList<>();
                  monitoredPatientDataStrings.add(fullName);
                  monitoredPatientDataStrings.add(String.valueOf(patient.getBloodPressureReadings()));

                  patientReadingsStrings.add(monitoredPatientDataStrings);

                  monitoredPatientNames.add(fullName);
                  monitoredSystolicBPList.add(patient.getBloodPressureReadings());

               }
            }
         }
      }

      StringBuilder labelData = new StringBuilder("<html>");
      for (ArrayList<String> patientReading : patientReadingsStrings) {
         labelData.append(patientReading.toString());
         labelData.append("<br>");
         System.out.println(patientReading.toString());
      }

      labelData.append("<br></html>");
      label.setText(labelData.toString());

      viewSystolicGraphsButton.setVisible(true);

   }

   /**
    * Utility to return the latest 5 readings for each monitored patient
    * @return the 5 readings of systolic BP for each patient
    */
   public ArrayList<ArrayList<ArrayList<String>>> getMonitoredSystolicBPList(){
      return monitoredSystolicBPList;
   }

   /**
    * Utility to return the names of monitored patients with high systolic BP
    * @return the names of monitored patients with high systolic BP
    */
   public ArrayList<String> getMonitoredPatientNames(){
      return monitoredPatientNames;
   }

}
