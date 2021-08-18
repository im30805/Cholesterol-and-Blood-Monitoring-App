package Controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import GUI.ButtonColumn;
import GUI.ViewPatientDetails;
import com.frontangle.ichart.chart.XYChart;
import com.frontangle.ichart.chart.bar.XYBarDataSeries;
import com.frontangle.ichart.chart.datapoint.DataPointBar;

import task2.application.*;


/**
 * The AddPatientController is an action listener for the add patient feature
 */

public class AddPatientController implements ActionListener {
   private String practitionerName;
   private String practitionerIdentifier;
   private JTextField inputPatientNo;
   private List<PatientInfo> patientInfoList;
   private double averageTotalCholesterol;
   private DefaultTableModel model;
   private int count;
   private JTable table;
   private int[] arr;
   private int frequency;
   private JFrame frame;
   private RemovePatientController remove;
   private XYBarDataSeries barSeries;
   private JScrollPane pane;

   private boolean cholesterolIsEnabled = false;
   private boolean bloodPressureIsEnabled = false;
   private boolean defaultViewIsEnabled = true;

   /**
    * Constructors
    *
    * @param practitionerName the practitioner's name
    * @param practitionerIdentifier the practitioner's identifier
    * @param inputPatientNo the number that the practitioner inputted based on their generated patient list
    * @param table a table display to add the patients into
    * @param model a skeleton for the table
    * @param frequency how often in seconds that a query to a server is called
    */
   public AddPatientController(String practitionerName, String practitionerIdentifier, JTextField inputPatientNo,
                               JTable table, DefaultTableModel model, int frequency, XYBarDataSeries barSeries, JScrollPane pane) {
      this.practitionerName = practitionerName;
      this.practitionerIdentifier = practitionerIdentifier;
      this.inputPatientNo = inputPatientNo;
      this.table = table;
      this.model = model;
      this.frequency = frequency;
      getList();
      arr = new int[patientInfoList.size()];
      getAverage();
      this.barSeries = barSeries;
      this.pane = pane;
   }

   public AddPatientController(String practitionerName,String practitionerIdentifier, JTable table,
                               DefaultTableModel model, int frequency, XYBarDataSeries barSeries, JScrollPane pane) {
      this.practitionerName = practitionerName;
      this.practitionerIdentifier = practitionerIdentifier;
      this.table = table;
      this.model = model;
      this.frequency = frequency;
      this.barSeries = barSeries;
      this.pane = pane;


   }

   /**
    * The action to be taken when an event has happened (e.g. clicking a button)
    * In this case, the code below checks if a patient has been added or not before adding an entry
    * Once the check has passed CholesterolProcessor and BloodPressureProcessor from the task2.application package
    * is called to get the patient's cholesterol and blood pressure values as well as their additional details
    *
    * @param e the event that was fired
    */
   @Override
   public void actionPerformed(ActionEvent e) {
      // Get the patient's assigned number
      String patientNo = inputPatientNo.getText();

      // Setup the table's properties
      // Check if the practitioner has added a valid number and also if they have already added the patient
      table.setDefaultEditor(Object.class, null);

      try {
         int x = Integer.parseInt(patientNo);
         if ((x > (Integer.valueOf(patientInfoList.size()))) || (x <= 0)) {
            JOptionPane.showMessageDialog(frame, "Please select the numbers displayed on 'Your Patient' list only!");
            return;
         }

         remove = new RemovePatientController(table, model);
         System.out.println(remove.getPosition());
         if (remove.getPosition() != 0) {
            for (int i = 0; i < arr.length; i++) {
               if (arr[i] == (remove.getPosition() - 1)) {
                  for (int j = i; j <arr.length - 1; j++) {
                     arr[j] = arr[j+1];
                  }
               }
            }
         }

         for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) {
               JOptionPane.showMessageDialog(frame, "Patient is already added!");
               return;
            }
         }
         arr[count] = x;

      }
      catch(NumberFormatException i) {
         JOptionPane.showMessageDialog(frame, "Input must be integer only!");
         return;
      }

      // Populate the table with the values of the added patient depending
      int x = Integer.parseInt(patientNo);
      PatientInfo patient = patientInfoList.get(x - 1);

      CholesterolProcessor cholesterolProcessor = new CholesterolProcessor(patient);
      BloodPressureProcessor bloodPressureProcessor = new BloodPressureProcessor(patient);
      cholesterolProcessor.update();
      bloodPressureProcessor.update();

      String patientName = patient.getName() + " " + patient.getSurname();
      String patientCholesterol;
      String cholesterolDateTime;
      String patientSystolicBP;
      String patientDiastolicBP;
      String bloodPressureDateTime;

      table.setAutoCreateColumnsFromModel(true);

      if (patient.getCholesterolDateTime() == null){ // check if they have a cholesterolDateTime value
         cholesterolDateTime = "0.0";
      }
      else {
         cholesterolDateTime = patient.getCholesterolDateTime();
      }

      if (patient.getBloodPressureDateTime() == null){ // check if they have a bloodPressureDateTime value
         bloodPressureDateTime = "0.0";
      }
      else {
         bloodPressureDateTime = patient.getBloodPressureDateTime();
      }

      count++;	// this is to help go through the patient array

      if (count == 1) { // for the first patient added
         if (patient.getCholesterol() == null){    // check if they have a cholesterol value
            patientCholesterol = "0.0";
         }
         else {
            patientCholesterol = patient.getCholesterol();
         }

         if (patient.getSystolicBloodPressure() == null){ // check if they have a systolic BP value
            patientSystolicBP = "0.0";
         }
         else {
            patientSystolicBP = patient.getSystolicBloodPressure() + " mm[Hg]";
         }

         if (patient.getDiastolicBloodPressure() == null){ // check if they have a diastolic BP value
            patientDiastolicBP = "0.0";
         }
         else {
            patientDiastolicBP = patient.getDiastolicBloodPressure();
         }

         if (patient.getBloodPressureDateTime() == null){ // check if they have a bloodPressureDateTime value
            bloodPressureDateTime = "0.0";
         }
         else {
            bloodPressureDateTime = patient.getBloodPressureDateTime();
         }

         if(defaultViewIsEnabled) { // if default view is enabled, all observations are added
            System.out.println("Default view enabled .. 1");
            model.addRow(new Object[]{patientName, patientCholesterol, cholesterolDateTime, patientSystolicBP,
               patientDiastolicBP, bloodPressureDateTime}); // add the info into the table
         }
         else if(bloodPressureIsEnabled){ // blood pressure observations only
            System.out.println("BP only .. 1");
            model.addRow(new Object[]{patientName, patientSystolicBP, patientDiastolicBP, bloodPressureDateTime});
         }
         else if(cholesterolIsEnabled){ // cholesterol observations only
            System.out.println("Cholesterol only .. 1");
            model.addRow(new Object[]{patientName, patientCholesterol, cholesterolDateTime});
         }

         // Add the patient's name and cholesterol to the graph view
         addDataToGraph(patientName, patientCholesterol);


      }
      else if (count > 1) { // for subsequent patients
         if (patient.getSystolicBloodPressure() == null){ // check if they have a systolic BP value
            patientSystolicBP = "0.0";
         }
         else {
            patientSystolicBP = patient.getSystolicBloodPressure();
         }

         if (patient.getDiastolicBloodPressure() == null){ // check if they have a diastolic BP value
            patientDiastolicBP = "0.0";
         }
         else {
            patientDiastolicBP = patient.getDiastolicBloodPressure();
         }

         if (patient.getCholesterol() == null){ // check if they have a cholesterol value
            patientCholesterol = "0.0";

            if(defaultViewIsEnabled) {  // if default view is enabled, all observations are added
               System.out.println("Default view enabled .. 2");
               model.addRow(new Object[]{patientName, patientCholesterol, cholesterolDateTime, patientSystolicBP,
                  patientDiastolicBP, bloodPressureDateTime}); // add the info into the table
            }
            else if(bloodPressureIsEnabled){ // blood pressure observations only
               System.out.println("BP only .. 2");
               model.addRow(new Object[]{patientName, patientSystolicBP, patientDiastolicBP, bloodPressureDateTime});
            }
            else if(cholesterolIsEnabled){ // cholesterol observations only
               System.out.println("Cholesterol only .. 2");
               model.addRow(new Object[]{patientName, patientCholesterol, cholesterolDateTime});
            }

            // Add the patient's name and cholesterol to the graph view
            addDataToGraph(patientName, patientCholesterol);

         }
         else {
            patientCholesterol = patient.getCholesterol();

            if(defaultViewIsEnabled) {  // if default view is enabled, all observations are added
               System.out.println("Default view enabled .. 3");
               model.addRow(new Object[]{patientName, patientCholesterol, cholesterolDateTime, patientSystolicBP,
                  patientDiastolicBP, bloodPressureDateTime}); // add the info into the table

               RowColorRenderer rowRenderer = new RowColorRenderer(1); // to enable the red highlighting feature
               TableColumn column = table.getColumnModel().getColumn(1);
               column.setCellRenderer(rowRenderer);
            }
            else if(bloodPressureIsEnabled){  // blood pressure observations only
               System.out.println("BP only .. 3");
               model.addRow(new Object[]{patientName, patientSystolicBP, patientDiastolicBP, bloodPressureDateTime});
            }
            else if(cholesterolIsEnabled){ // cholesterol observations only
               System.out.println("Cholesterol only .. 3");
               model.addRow(new Object[]{patientName, patientCholesterol, cholesterolDateTime});

               RowColorRenderer rowRenderer = new RowColorRenderer(1); // to enable the red highlighting feature
               TableColumn column = table.getColumnModel().getColumn(1);
               column.setCellRenderer(rowRenderer);
            }

            // Add the patient's name and cholesterol to the graph view
            addDataToGraph(patientName, patientCholesterol);

         }


      }

      // Run the updates to the values in the background
      execute(cholesterolProcessor, bloodPressureProcessor, patient, this.frequency);

      // Call the method to add the view more details buttons into the table
      // The column by default is col 6
      addDetailsButton(6);

   }

   /**
    * Another action listener, used in conjunction with the ButtonColumn class
    * This is used to implement the view for the patient's additional details
    * The method uses the ButtonColumn class, taken from http://www.camick.com/java/source/ButtonColumn.java
    *
    * @param columnNumber the column that the buttons will be placed in
    */

   private void addDetailsButton(int columnNumber){
      Action view = new AbstractAction()
      {
         public void actionPerformed(ActionEvent e)
         {
            //JTable table = (JTable)e.getSource();
            int modelRow = Integer.valueOf(e.getActionCommand());

            ViewPatientDetails view = new ViewPatientDetails(patientInfoList, modelRow);
            view.generateDetails();
         }
      };

      ButtonColumn buttonColumn = new ButtonColumn(table, view, columnNumber);
      buttonColumn.setMnemonic(KeyEvent.VK_D);
   }


   /**
    * The execute method is used to update the observation values every n seconds
    *
    * @param cholesterolProcessor an cholesterolProcessor instance to process new values in the Observations
    * @param patient the patient's info
    * @param frequency how often the update will be performed (in seconds)
    */
   private void execute(CholesterolProcessor cholesterolProcessor, BloodPressureProcessor bloodPressureProcessor,
                        PatientInfo patient, int frequency) {
      Runnable observationRunnable = () -> {
         cholesterolProcessor.update();
         bloodPressureProcessor.update();

         String patientName = patient.getName() + " " + patient.getSurname();
         String newCholesterolDateTime;
         String newPatientCholesterol;

         String newSystolicBP;
         String newDiastolicBP;
         String newBPDateTime;

         if (patient.getCholesterol() == null){
            newPatientCholesterol = "0.0";
         }
         else {
            newPatientCholesterol = patient.getCholesterol();
         }

         if (patient.getCholesterolDateTime() == null){
            newCholesterolDateTime = "0.0";
         }
         else {
            newCholesterolDateTime = patient.getCholesterolDateTime();
         }

         if (patient.getSystolicBloodPressure() == null){
            newSystolicBP = "0.0";
         }
         else {
            newSystolicBP = patient.getSystolicBloodPressure();
         }

         if (patient.getDiastolicBloodPressure() == null){
            newDiastolicBP = "0.0";
         }
         else {
            newDiastolicBP = patient.getDiastolicBloodPressure();
         }

         if (patient.getBloodPressureDateTime() == null){
            newBPDateTime = "0.0";
         }
         else {
            newBPDateTime = patient.getBloodPressureDateTime();
         }

         // Update the table view
         for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 0).equals(patientName) && defaultViewIsEnabled) {
               System.out.println("Default view");
               table.setValueAt(newPatientCholesterol, i, 1);
               table.setValueAt(newCholesterolDateTime, i, 2);
               table.setValueAt(newSystolicBP, i, 3);
               table.setValueAt(newDiastolicBP, i, 4);
               table.setValueAt(newBPDateTime, i, 5);

				   RowColorRenderer rowRenderer = new RowColorRenderer(1); // to enable the red highlighting feature
		         TableColumn column = table.getColumnModel().getColumn(1);
		         column.setCellRenderer(rowRenderer);


            }
            else if(table.getValueAt(i, 0).equals(patientName) && bloodPressureIsEnabled){
               System.out.println("BP only enabled");
               table.setValueAt(newSystolicBP, i, 1);
               table.setValueAt(newDiastolicBP, i, 2);
               table.setValueAt(newBPDateTime, i, 3);

               addDetailsButton(4);


            }
            else if(table.getValueAt(i, 0).equals(patientName) && cholesterolIsEnabled){
               System.out.println("Cholesterol only enabled");
               table.setValueAt(newPatientCholesterol, i, 1);
               table.setValueAt(newCholesterolDateTime, i, 2);

               RowColorRenderer rowRenderer = new RowColorRenderer(1); // to enable the red highlighting feature
               TableColumn column = table.getColumnModel().getColumn(1);
               column.setCellRenderer(rowRenderer);

               addDetailsButton(3);

            }

         }

         // Update the graph view as well
         for (int j = 0; j < barSeries.dataPoints.size(); j++){
            String dataPoint = String.valueOf(barSeries.dataPoints.get(j));
            if (dataPoint.contains("name=" + patientName)){
               barSeries.dataPoints.set(j, new DataPointBar(patientName, Double.parseDouble(newPatientCholesterol),
                  Color.BLUE));
            }
         }

         System.out.println("update!");
      };

      ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
      executor.scheduleAtFixedRate(observationRunnable, 0, frequency, TimeUnit.SECONDS); // update every n seconds
   }

   /**
    * Adds a new entry of patient into the bar graph view
    *
    * @param patientName the name of the patient to add
    * @param patientCholesterol the patient's cholesterol value
    */
   private void addDataToGraph(String patientName, String patientCholesterol){
      barSeries.add(new DataPointBar(patientName, Double.parseDouble(patientCholesterol), Color.BLUE));
      XYChart barChartNew = new XYChart("Total Cholesterol (mg/DL)", "Patient", "", barSeries);
      pane.setViewportView(barChartNew);

      for (int i = 0; i < barSeries.dataPoints.size(); i++){
         if(String.valueOf(barSeries.dataPoints.get(i)).contains("name=[Empty]")){
            barSeries.dataPoints.remove(barSeries.dataPoints.get(i));
         }
      }

   }

   /**
    * Nested class
    * The RowColorRenderer is used to highlight the patient's cholesterol if it exceeds the average total
    * cholesterol level
    */
   private class RowColorRenderer extends DefaultTableCellRenderer {

      private int colNo = 0;

      RowColorRenderer(int col) {
         colNo = col;
      }

      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
         Component comp = super.getTableCellRendererComponent(table, value,isSelected, hasFocus, row, column);
         JComponent jc = (JComponent) comp;

         if (table.getValueAt(row, colNo) != null) {
            String str = table.getValueAt(row, colNo).toString();
            String digits = str.replaceAll("[^0-9.]", "");
            Double theStr = Double.parseDouble(digits);

            if (theStr > averageTotalCholesterol) {
               jc.setForeground(Color.RED);
            }
            else if (theStr == 0) {
               jc.setForeground(table.getForeground());
            }
         }

         return jc;

      }
   }

   /**
    * Method to get the patient list
    * @return a list of PatientInfo
    */
   public List<PatientInfo> getList(){
      EncounterProcessor encounters = new EncounterProcessor(practitionerIdentifier);
      LinkedHashSet<String> patientIDs = encounters.retrievePatientIDs(practitionerName);
      PatientProcessor patientProcessor = new PatientProcessor(patientIDs);

      LinkedHashSet<PatientInfo> patients = patientProcessor.processPatients();
      List<PatientInfo> patientInfoList = new ArrayList<>(patients);

      this.patientInfoList = patientInfoList;
      return patientInfoList;
   }

   /**
    * Method to calculate the average total cholesterol between patients
    * @return the average total cholesterol
    */
   public double getAverage() {
      for (int i = 0; i < patientInfoList.size(); i ++) {
         PatientInfo patientCount = patientInfoList.get(i);
         CholesterolProcessor cholesterolProcessor2 = new CholesterolProcessor(patientCount);

         cholesterolProcessor2.update();
         averageTotalCholesterol += 0;

         if (patientCount.getCholesterol() == null){
            averageTotalCholesterol += 0;
         }
         else {
            averageTotalCholesterol += Double.parseDouble(patientCount.getCholesterol());
         }
      }

      averageTotalCholesterol /= patientInfoList.size();

      System.out.println(averageTotalCholesterol);

      return averageTotalCholesterol;
   }

   /**
    * Public method to update cholesterol and blood pressure values if the frequency is changed
    */
   public void update() {
      getList();
      for (int i = 0; i < patientInfoList.size(); i ++) {
         PatientInfo patient = patientInfoList.get(i);
         CholesterolProcessor cholesterolProcessor = new CholesterolProcessor(patient);
         BloodPressureProcessor bloodPressureProcessor = new BloodPressureProcessor(patient);


         Runnable observationRunnable = () -> {
            cholesterolProcessor.update();
            bloodPressureProcessor.update();

            String patientName = patient.getName() + " " + patient.getSurname();
            String newCholesterolDateTime;
            String newPatientCholesterol;

            String newSystolicBP;
            String newDiastolicBP;
            String newBPDateTime;

            if (patient.getCholesterol() == null) {
               newPatientCholesterol = "0.0";
            } else {
               newPatientCholesterol = patient.getCholesterol();
            }

            if (patient.getCholesterolDateTime() == null) {
               newCholesterolDateTime = "0.0";
            } else {
               newCholesterolDateTime = patient.getCholesterolDateTime();
            }

            if (patient.getSystolicBloodPressure() == null) {
               newSystolicBP = "0.0";
            } else {
               newSystolicBP = patient.getSystolicBloodPressure();
            }

            if (patient.getDiastolicBloodPressure() == null) {
               newDiastolicBP = "0.0";
            } else {
               newDiastolicBP = patient.getDiastolicBloodPressure();
            }

            if (patient.getBloodPressureDateTime() == null) {
               newBPDateTime = "0.0";
            } else {
               newBPDateTime = patient.getBloodPressureDateTime();
            }

            for (int j = 0; j < table.getRowCount(); j++) {
               if (table.getValueAt(j, 0).equals(patientName) && defaultViewIsEnabled) {
                  System.out.println("Default view");
                  table.setValueAt(newPatientCholesterol, j, 1);
                  table.setValueAt(newCholesterolDateTime, j, 2);
                  table.setValueAt(newSystolicBP, j, 3);
                  table.setValueAt(newDiastolicBP, j, 4);
                  table.setValueAt(newBPDateTime, j, 5);
                  RowColorRenderer rowRenderer = new RowColorRenderer(1); // to enable the red highlighting feature
                  TableColumn column = table.getColumnModel().getColumn(1);
                  column.setCellRenderer(rowRenderer);


               } else if (table.getValueAt(j, 0).equals(patientName) && bloodPressureIsEnabled) {
                  System.out.println("BP only enabled");
                  table.setValueAt(newSystolicBP, j, 1);
                  table.setValueAt(newDiastolicBP, j, 2);
                  table.setValueAt(newBPDateTime, j, 3);
                  addDetailsButton(4);


               } else if (table.getValueAt(j, 0).equals(patientName) && cholesterolIsEnabled) {
                  System.out.println("Cholesterol only enabled");
                  table.setValueAt(newPatientCholesterol, j, 1);
                  table.setValueAt(newCholesterolDateTime, j, 2);
                  RowColorRenderer rowRenderer = new RowColorRenderer(1); // to enable the red highlighting feature
                  TableColumn column = table.getColumnModel().getColumn(1);
                  column.setCellRenderer(rowRenderer);
                  addDetailsButton(3);
               }

            }
         };

         System.out.println("Freq update");
         ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
         executor.scheduleAtFixedRate(observationRunnable, 0, frequency, TimeUnit.SECONDS); // update every n seconds

      }
   }

   /**
    * Method to flag if the cholesterol view is enabled
    * @param bool set to true if cholesterol view is chosen
    */
   public void cholesterolEnabled(boolean bool){
      cholesterolIsEnabled = bool;
      //System.out.println("Cholesterol toggled - " + bool);
   }

   /**
    * Method to flag if the blood pressure view is enabled
    * @param bool set to true if blood pressure view is chosen
    */
   public void bloodPressureEnabled(boolean bool){
      bloodPressureIsEnabled = bool;
      //System.out.println("Blood pressure toggled - " + bool);
   }

   /**
    * Method to flag if the default view is enabled
    * @param bool set to true if default view is chosen
    */
   public void defaultViewEnabled(boolean bool){
      defaultViewIsEnabled = bool;
      //System.out.println("Default view enabled - " + bool);
   }

   /**
    * Setter to update the table's model
    * @param model the model to use as the table's present view
    */
   public void setNewModel(DefaultTableModel model){
      this.model = model;
      table.setModel(model);
   }

}
