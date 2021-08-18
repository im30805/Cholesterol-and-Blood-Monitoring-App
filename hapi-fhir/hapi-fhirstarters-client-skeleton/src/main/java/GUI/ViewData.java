package GUI;
import java.awt.*;

import Controller.*;
import com.frontangle.ichart.chart.XYChart;
import com.frontangle.ichart.chart.bar.XYBarDataSeries;
import com.frontangle.ichart.chart.datapoint.DataPointBar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * The ViewData class inherits and extends the Java Swing component JFrame
 */


public class ViewData extends JFrame {

   private JPanel contentPane;
   //private JFrame frame;
   private JTable table;
   private JScrollPane scrollPane;
   private JTextField inputPatientNo;
   private JTextField inputFrequency;
   private JTextField inputSystolicBlood;
   private JTextField inputDiastolicBlood;
   private int frequency = 10;


   /**
    * Constructor
    * To initialize and setup the main display for the practitioner's patient list as well as the mechanism to
    * add/remove patients and change the update frequency
    * @param practitionerName the practitioner's full name
    * @param practitionerIdentifier the practitioner's identifier
    */

   public ViewData(String practitionerName, String practitionerIdentifier) {
      // Setup the panel in the frame of the application window
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      setBounds(0, 0, 1326, 900);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);

      // Print the practitioner's name on the left
      JLabel lblPractitionerName = new JLabel("Practitioner Name: ");
      lblPractitionerName.setFont(new Font("Tahoma", Font.PLAIN, 12));
      lblPractitionerName.setBounds(21, 21, 125, 29);
      contentPane.add(lblPractitionerName);

      JLabel displayName = new JLabel(practitionerName.replaceAll("[0-9]",""));
      displayName.setFont(new Font("Tahoma", Font.PLAIN, 12));
      displayName.setBounds(171, 21, 255, 29);
      contentPane.add(displayName);


      // Label where the practitioner's patients will be printed
      JLabel lblNewLabel = new JLabel("Your Patients: ");
      lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
      lblNewLabel.setBounds(21, 60, 125, 29);
      contentPane.add(lblNewLabel);


      // Print the practitioner's list of patients
      PatientNameController patientNameController = new PatientNameController(practitionerName,practitionerIdentifier);
      JLabel patientDetails = new JLabel(patientNameController.getPatientName());
      patientDetails.setFont(new Font("Tahoma", Font.PLAIN, 12));
      patientDetails.setVerticalTextPosition(SwingConstants.TOP);
      patientDetails.setVerticalAlignment(JLabel.TOP);
      patientDetails.setBounds(21, 96, 255, 495);
      contentPane.add(patientDetails);


      // Section to add a patient
      // Patients are added based on the assigned number in the list
      // E.g. 1. Aaron Le
      //      2. John Doe
      // If a practitioner wants to monitor John Doe, they would enter a 2 into the text field
      JLabel lblPleaseEnterThe = new JLabel("Please enter the position number of the patient (from Your Patients) that you would like to add to monitor: ");
      lblPleaseEnterThe.setFont(new Font("Tahoma", Font.PLAIN, 12));
      lblPleaseEnterThe.setBounds(307, 60, 598, 29);
      contentPane.add(lblPleaseEnterThe);

      inputPatientNo = new JTextField();
      inputPatientNo.setBounds(901, 63, 113, 25);
      contentPane.add(inputPatientNo);
      inputPatientNo.setColumns(10);

      // Section to change the update frequency of the cholesterol and effectiveDateTime values
      inputFrequency = new JTextField();
      inputFrequency.setBounds(572, 103, 113, 23);
      contentPane.add(inputFrequency);
      inputFrequency.setColumns(10);

      JLabel lblPleaseSelectFrequency = new JLabel("Please select frequency (in seconds) to update:");
      lblPleaseSelectFrequency.setFont(new Font("Tahoma", Font.PLAIN, 12));
      lblPleaseSelectFrequency.setBounds(307, 99, 598, 29);
      contentPane.add(lblPleaseSelectFrequency);

      // Generate the table display for monitored patients
      scrollPane = new JScrollPane();
      scrollPane.setBounds(307, 220, 900, 270);
      contentPane.add(scrollPane);
      String column[]={"NAME","TOTAL CHOLESTEROL [mg/DL]","TIME(C)", "SYSTOLIC BP [mm[Hg]]", "DIASTOLIC BP [mm[Hg]]","TIME(BP)",
         "DETAILS++"};
      DefaultTableModel model = new DefaultTableModel(column, 0);
      table = new JTable (model);

      // Initial graph view
      XYBarDataSeries barSeries = new XYBarDataSeries();
      barSeries.add(new DataPointBar("[Empty]", 0, Color.BLUE));
      barSeries.add(new DataPointBar("[Empty]", 10, Color.BLUE));
      barSeries.add(new DataPointBar("[Empty]", 20, Color.BLUE));

      XYChart barChart = new XYChart("Total Cholesterol (mg/DL)", "Patient", "", barSeries);

      JScrollPane scrollPaneGraph = new JScrollPane(barChart);
      scrollPaneGraph.setBounds(307, 500, 900, 300);
      contentPane.add(scrollPaneGraph);


      // Create the button and add an action listener to add patients to the table
      AddPatientController addPatientController = new AddPatientController(practitionerName, practitionerIdentifier,
         inputPatientNo, table, model, frequency, barSeries, scrollPaneGraph);
      JButton btnNewButton_1 = new JButton("Add Patient");
      btnNewButton_1.setBounds(1039, 59, 120, 33);
      btnNewButton_1.addActionListener(addPatientController);
      contentPane.add(btnNewButton_1);
      scrollPane.setViewportView(table);

      // Create the button and add an action listener to change the update frequency
      JButton btnNewButton = new JButton("Confirm");
      FrequencyController frequencyController = new FrequencyController(practitionerName, practitionerIdentifier,
         inputFrequency, table, model, barSeries, scrollPaneGraph);
      btnNewButton.addActionListener(frequencyController);
      btnNewButton.setBounds(708, 96, 90, 33);
      contentPane.add(btnNewButton);

      // Create the button and add an action listener to delete patients from the table
      JButton btnNewButton_2 = new JButton("Delete");
      btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
      RemovePatientController removePatientController = new RemovePatientController(table,model);
      btnNewButton_2.addActionListener(removePatientController);
      btnNewButton_2.setBounds(1250, 211, 146, 56);
      contentPane.add(btnNewButton_2);

      // Add the button to only view blood pressure in the table
      BloodPressureOnlyViewController bloodPressureViewOnlyController = new BloodPressureOnlyViewController(table, scrollPane, model, addPatientController,
         removePatientController);
      JRadioButton bloodPressureOnlyButton = new JRadioButton("Blood pressure only", false);
      bloodPressureOnlyButton.setBounds(1250, 311, 146, 56);
      bloodPressureOnlyButton.addItemListener(bloodPressureViewOnlyController);
      contentPane.add(bloodPressureOnlyButton);

      // Add the button to only view the cholesterol in the table
      CholesterolOnlyViewController CholesterolOnlyViewController = new CholesterolOnlyViewController(table, scrollPane, model, addPatientController,
         removePatientController);
      JRadioButton cholesterolOnlyButton = new  JRadioButton("Cholesterol only", false);
      cholesterolOnlyButton.setBounds(1250, 411, 146, 56);
      cholesterolOnlyButton.addItemListener(CholesterolOnlyViewController);
      contentPane.add(cholesterolOnlyButton);

      // Add the button to view both observations (initial view)
      DefaultViewController defaultViewController = new DefaultViewController(addPatientController);
      JRadioButton defaultViewButton = new JRadioButton("Default view", true);
      defaultViewButton.setBounds(1250, 511, 146, 56);
      defaultViewButton.addItemListener(defaultViewController);
      contentPane.add(defaultViewButton);

      // Group the buttons together into a ButtonGroup object to ensure that only one of the buttons can be selected
      // at a time
      ButtonGroup buttonGroup = new ButtonGroup();
      buttonGroup.add(bloodPressureOnlyButton);
      buttonGroup.add(cholesterolOnlyButton);
      buttonGroup.add(defaultViewButton);

      // Section for practitioner to enter the systolic BP value they want to observe
      JLabel lblSystolicBlood = new JLabel("Please enter value for systolic blood pressure to be observed:");
      lblSystolicBlood.setFont(new Font("Tahoma", Font.PLAIN, 12));
      lblSystolicBlood.setBounds(307, 138, 500, 29);
      contentPane.add(lblSystolicBlood);

      inputSystolicBlood = new JTextField();
      inputSystolicBlood.setBounds(650,140, 113, 25);
      contentPane.add(inputSystolicBlood);
      inputSystolicBlood.setColumns(10);

      // Systolic blood pressure textual monitor button
      // Initially not visible, only visible once a systolic BP value has been entered
      JButton viewmorebtn = new JButton("Monitor High Systolic BP Patient");
      viewmorebtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
      viewmorebtn.setBounds(1250, 611, 250, 56);
      viewmorebtn.setVisible(false);

      // Systolic blood pressure graphical monitor button
      // Initially not visible, only visible once the textual monitor is generated
      JButton viewSystolicGraphButton = new JButton("View Graphs for High Systolic BP Patient");
      viewSystolicGraphButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
      viewSystolicGraphButton.setBounds(1250, 711, 270, 56);
      viewSystolicGraphButton.setVisible(false);

      // Label for the textual monitor and attaching an action listener to it
      JLabel labelMonitor = new JLabel();
      labelMonitor.setText("<html>Monitors will be displayed here once <br> the [Monitor High Systolic BP Patient] button appears " +
         "and is clicked..." +
         "<br><br> It will take some time to load..." +
         "</html>");
      labelMonitor.setBounds(21, 200, 270, 700);
      contentPane.add(labelMonitor);
      ViewHighSystolicBPP viewhigh = new ViewHighSystolicBPP(inputSystolicBlood,
         model.getDataVector(), labelMonitor, addPatientController, viewSystolicGraphButton);
      viewmorebtn.addActionListener(viewhigh);
      contentPane.add(viewmorebtn);

      // Attaching an action listener to the graphical monitor button
      ViewHighSystolicBPPGraphs viewHighSystolicBPPGraphs = new ViewHighSystolicBPPGraphs(viewhigh.getMonitoredPatientNames(), viewhigh.getMonitoredSystolicBPList());
      viewSystolicGraphButton.addActionListener(viewHighSystolicBPPGraphs);
      contentPane.add(viewSystolicGraphButton);

      // Button to confirm the value of systolic blood pressure to be monitored
      JButton SystolicBloodbtn = new JButton("Confirm");
      SystolicBloodbtn.setBounds(785, 136, 90, 33);
      SystolicBloodController systolicBloodController = new SystolicBloodController(inputSystolicBlood,table, model,viewmorebtn,bloodPressureOnlyButton,defaultViewButton);
      SystolicBloodbtn.addActionListener(systolicBloodController);
      contentPane.add(SystolicBloodbtn);

      // Section for practitioner to enter the diastolic BP value they want to observe
      JLabel lblDiastolicBlood = new JLabel("Please enter value for diastolic blood pressure to be observed:");
      lblDiastolicBlood.setFont(new Font("Tahoma", Font.PLAIN, 12));
      lblDiastolicBlood.setBounds(307, 177, 500, 29);
      contentPane.add(lblDiastolicBlood);

      inputDiastolicBlood = new JTextField();
      inputDiastolicBlood.setBounds(650,179, 113, 25);
      contentPane.add(inputDiastolicBlood);
      inputDiastolicBlood.setColumns(10);

      JButton DiastolicBloodbtn = new JButton("Confirm");
      DiastolicBloodbtn.setBounds(785, 177, 90, 33);
      DiastolicBloodController diastolicBloodController = new DiastolicBloodController(inputDiastolicBlood,table,model,cholesterolOnlyButton,bloodPressureOnlyButton,defaultViewButton);
      DiastolicBloodbtn.addActionListener(diastolicBloodController);
      contentPane.add(DiastolicBloodbtn);


   }
}

