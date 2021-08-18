package task2.application;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;

/**
 * The CholesterolProcessor class is a concrete implementation of the abstract class ObservationSubject for the
 * purpose of implementing the Observer design pattern
 *
 * It receives a Bundle of Observations from the FHIR server by using the patient's ID and sorts the data by latest
 * updated. To get the total cholesterol and effectiveDateTime, code=2093 is used in the query string
 */

public class CholesterolProcessor extends ObservationSubject{
   private PatientInfo patientInfo; // a patient's information
   private String cholesterol; // patient's cholesterol value
   private String effectiveDateTime; // patient's time when cholesterol was last updated

   /**
    * Constructor
    * @param patientInfo the patient's information
    */
   public CholesterolProcessor(PatientInfo patientInfo){
      this.patientInfo = patientInfo;
   }

   /**
    * The processObservation method here goes through the retrieved Bundle of Observations and retrieves the total cholesterol and
    * effectiveDateTime for the particular patient. This method is called in the update method below
    */
   @Override
   public void processObservation() {
      attach(patientInfo);

      String searchString = "Observation?patient=" + patientInfo.getId()+ "&code=2093-3&_sort=date&_count=13";
      Bundle observationBundle = ServerBase.getClient().search()
         .byUrl(searchString)
         .returnBundle(Bundle.class)
         .execute();


      for(int i = 0; i < observationBundle.getEntry().size(); i++){
         String urlObservation = observationBundle.getEntry().get(i).getFullUrl();

         Observation observation = ServerBase.getClient().read()
            .resource(Observation.class)
            .withUrl(urlObservation)
            .execute();

         cholesterol = observation.getValueQuantity().getValue().toString();
         effectiveDateTime = String.valueOf(observation.getEffectiveDateTimeType()).replace("DateTimeType", "");

      }

   }

   /**
    * The update method is used to retrieve new cholesterol and effectiveDateTime values for the patient (if any)
    * The new values are then passed into the notifyObserver method to inform the patientInfo class
    */
   @Override
   public void update() {
      processObservation();
      notifyObserver(cholesterol, effectiveDateTime);
   }
}



