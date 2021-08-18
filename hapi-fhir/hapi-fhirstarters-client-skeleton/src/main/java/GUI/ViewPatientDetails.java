package GUI;

import javax.swing.*;
import task2.application.*;

import java.awt.Font;
import java.util.List;

/**
 * ViewPatientDetails will display selected patient's more detailed data
 * */
public class ViewPatientDetails {
   private List<PatientInfo> patientInfoList;
   private int index;

   /**
    * Constructor class
    * */
   public ViewPatientDetails(List<PatientInfo> patientInfoList, int index){
      this.patientInfoList = patientInfoList;
      this.index = index;
   }
   
   /**
    * This method will print the patient's details when selected
    * */
   public void generateDetails() {
      String patientName = patientInfoList.get(index).getName() + patientInfoList.get(index).getSurname();
      JFrame patientDetailFrame = new JFrame(patientName + "'s Details");

      String patientDetails [][] = {{patientInfoList.get(index).getName(), patientInfoList.get(index).getSurname(), patientInfoList.get(index).getId(),
         patientInfoList.get(index).getBirthDate(), patientInfoList.get(index).getGender(), patientInfoList.get(index).getCity(),
         patientInfoList.get(index).getState(), patientInfoList.get(index).getCountry()}};

      String patientDetailsColumn [] = {"Name", "Surname", "ID", "Birthdate", "Gender", "City", "State", "Country"};

      final JTable tableDetail = new JTable(patientDetails, patientDetailsColumn);

      JScrollPane scrollPaneDetail = new JScrollPane(tableDetail);
      patientDetailFrame.add(scrollPaneDetail);
      patientDetailFrame.setSize(600, 200);
      patientDetailFrame.setVisible(true);
      
      
   }
}

