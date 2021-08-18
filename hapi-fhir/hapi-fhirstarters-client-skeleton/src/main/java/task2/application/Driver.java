package task2.application;

import org.hl7.fhir.r4.model.Practitioner;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Driver {
   public static void main(String[] args){
      // Login + Set frequency (Frequency should be allowed to be changed anytime) + Get practitioner
      // We can probably remove the PractitionerAccount class

      Scanner scanner = new Scanner(System.in);  // Create a Scanner object
      System.out.println("Enter practitioner ID: ");

      String practitionerID = scanner.nextLine();  // Read user input
      System.out.println("Your ID is: " + practitionerID);  // Output user input

      System.out.println("Enter frequency (n seconds): ");
      int n = Integer.parseInt(scanner.nextLine());

      Practitioner practitioner = ServerBase.getClient().read()
         .resource(Practitioner.class)
         .withId(practitionerID)
         .execute();

      String pracFamilyName = practitioner.getName().get(0).getFamily();
      String pracGivenName = String.valueOf(practitioner.getName().get(0).getGiven().get(0));
      String practitionerName = "Dr. " + pracGivenName + " " + pracFamilyName;
      String practitionerIdentifier = practitioner.getIdentifier().get(0).getValue();

      // Get patients
      EncounterProcessor encounters = new EncounterProcessor(practitionerIdentifier);
      LinkedHashSet<String> patientIDs = encounters.retrievePatientIDs(practitionerName);

      PatientProcessor patientProcessor = new PatientProcessor(patientIDs);
      LinkedHashSet<PatientInfo> patients = patientProcessor.processPatients();
      List<PatientInfo> patientInfoList = new ArrayList<>(patients);

      // Code example to show the observer pattern
      // The cholesterol values and the dateTime are overwritten
      PatientInfo patient1 = patientInfoList.get(0);
      System.out.println(patient1);
      patient1.setCholesterol("130.00 mg/DL");
      patient1.setCholesterolDateTime("[2001-02-01T10:34:45+11:00]");

      System.out.println("Example cholesterol = " + patient1.getCholesterol());
      System.out.println("Example time = " + patient1.getCholesterolDateTime());

      CholesterolProcessor cholesterolProcessor = new CholesterolProcessor(patient1);

      cholesterolProcessor.update();

      //System.out.println("Example updated cholesterol = " + patient1.getCholesterol());
      //System.out.println("Example updated time = " + patient1.getCholesterolDateTime());

      // Lambda expression to update
      Runnable helloRunnable = () -> {
         cholesterolProcessor.update();
         System.out.println("Example updated cholesterol = " + patient1.getCholesterol());
         System.out.println("Example updated time = " + patient1.getCholesterolDateTime());
      };

      ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
      executor.scheduleAtFixedRate(helloRunnable, 0, n, TimeUnit.SECONDS); // update every n seconds


   }
}
