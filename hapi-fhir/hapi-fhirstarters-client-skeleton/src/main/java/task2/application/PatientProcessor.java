package task2.application;

import org.hl7.fhir.r4.model.Patient;

import java.util.LinkedHashSet;

/**
 *
 */


public class PatientProcessor {
   private LinkedHashSet<PatientInfo> patients = new LinkedHashSet<>();;
   private LinkedHashSet<String> patientIDs;

   public PatientProcessor(LinkedHashSet<String> patientIDs){
      this.patientIDs = patientIDs;
   }

   public LinkedHashSet<PatientInfo> processPatients(){
      for(String id : patientIDs) {
         Patient patient = ServerBase.getClient().read()
            .resource(Patient.class)
            .withId(id)
            .execute();

         String patientGivenName = String.valueOf(patient.getName().get(0).getGiven().get(0)).replaceAll("[0-9]","");
         String patientSurname = patient.getName().get(0).getFamily().replaceAll("[0-9]","");
         String patientBirthDate = String.valueOf(patient.getBirthDate());
         String patientGender = String.valueOf(patient.getGender());
         String patientCity = patient.getAddress().get(0).getCity();
         String patientState = patient.getAddress().get(0).getState();
         String patientCountry = patient.getAddress().get(0).getCountry();


         PatientInfo patientInfo = new PatientInfo(patientGivenName, patientSurname, id, patientBirthDate,
            patientGender, patientCity, patientState, patientCountry);

         patients.add(patientInfo);


      }

      return patients;

   }
}
