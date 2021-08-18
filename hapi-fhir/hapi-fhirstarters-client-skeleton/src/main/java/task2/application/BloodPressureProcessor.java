package task2.application;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * The BloodPressureProcessor class is a concrete implementation of the abstract class ObservationSubject for the
 * purpose of implementing the Observer design pattern
 *
 * It receives a Bundle of Observations from the FHIR server by using the patient's ID and sorts the data by latest
 * updated. To get the total BP values and effectiveDateTime, code=55284-4 is used in the query string
 */

public class BloodPressureProcessor extends ObservationSubject {
   private PatientInfo patientInfo; // a patient's information
   private String systolicBloodPressure;
   private String diastolicBloodPressure;
   private String bloodPressureDateTime;

   private ArrayList<ArrayList<String>> bloodPressureReadings = new ArrayList<>();

   /**
    * Constructor
    * @param patientInfo the patient's information
    */
   public BloodPressureProcessor(PatientInfo patientInfo){
      this.patientInfo = patientInfo;
   }

   /**
    * The processObservation method here goes through the retrieved Bundle of Observations and retrieves the BP values
    * and effectiveDateTime for the particular patient. This method is called in the update method below
    *
    * The latest 5 readings of systolic blood pressure is also retrieved and updated
    */

   @Override
   public void processObservation() {
      attach(patientInfo);

      String searchStringBloodLevel = "Observation?patient=" + patientInfo.getId() + "&code=55284-4&_sort=date&_count=13";
      Bundle bloodLevelBundle = ServerBase.getClient().search()
         .byUrl(searchStringBloodLevel)
         .returnBundle(Bundle.class)
         .execute();

      for(int i = 0; i < bloodLevelBundle.getEntry().size(); i++){
         String urlBloodLevel = bloodLevelBundle.getEntry().get(i).getFullUrl();

         Observation bloodLevelObservation = ServerBase.getClient().read()
            .resource(Observation.class)
            .withUrl(urlBloodLevel)
            .execute();

         systolicBloodPressure = String.valueOf(bloodLevelObservation.getComponent().get(1).getValueQuantity().getValue());
         diastolicBloodPressure = String.valueOf(bloodLevelObservation.getComponent().get(0).getValueQuantity().getValue());
         bloodPressureDateTime = String.valueOf(bloodLevelObservation.getEffectiveDateTimeType()).replace("DateTimeType", "");

         ArrayList<String> readings = new ArrayList<>();
         readings.add(systolicBloodPressure);
         readings.add(bloodPressureDateTime);

         bloodPressureReadings.add(readings);


      }

      //System.out.println("Before - " + bloodPressureReadings.size());

      if(bloodPressureReadings.size() > 5){
         while (bloodPressureReadings.size() != 5){
            bloodPressureReadings.remove(0);
         }
      }

      //System.out.println("After - " + bloodPressureReadings.size());
   }

   /**
    * The update method is used to retrieve new BP values and effectiveDateTime values for the patient (if any)
    * The new values are then passed into the notifyObserver method to inform the patientInfo class
    */
   @Override
   public void update() {
      processObservation();
      notifyObserver(systolicBloodPressure, diastolicBloodPressure, bloodPressureDateTime, bloodPressureReadings);
   }
}

