package task2.application;

import ca.uhn.fhir.rest.client.exceptions.FhirClientConnectionException;
import ca.uhn.fhir.rest.server.exceptions.PayloadTooLargeException;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Encounter;

import java.util.LinkedHashSet;


/**
 * The EncounterProcessor class receives a Bundle of encounters from the FHIR server by using the
 * practitioner's identifier. The Bundle is then processed to extract the IDs of the practitioner's patients.
 * In addition to using the identifier, since different practitioners can share the same identifier
 * value, the practitioner's full name is used as well to ensure that the correct list of patient IDs
 * is retrieved
 *
 * Note that EncounterProcessor will need to take about approximately 2-5 minutes to go through the Bundle as the server
 * returns the result in a series of pages
 */

public class EncounterProcessor {
   private Bundle encounterResponse; // response is returned in a Bundle
   private LinkedHashSet<String> patientIDs = new LinkedHashSet<>(); // a LinkedHashSet is used to prevent duplicate
                                                                     // entries
   private String searchString; // the query string used in addition with the root URL

   /**
    * Constructor
    * @param identifier The practitioner's identifier
    */
   public EncounterProcessor(String identifier){
      searchString = "Encounter?participant.identifier=http://hl7.org/fhir/sid/us-npi%7C" + identifier
         +"&_include=Encounter.participant.individual&_include=Encounter.patient&_date=ge2020-06-01&date=le2020-06-05";


   }

   /**
    * The getResponse method retrieves a Bundle of resources of type Encounter
    * @throws FhirClientConnectionException throws an exception if the connection to the server takes too long
    */
   private void getResponse() throws FhirClientConnectionException{
      try {
         encounterResponse = ServerBase.getClient().search()
            .byUrl(searchString)
            .returnBundle(Bundle.class)
            .execute();
      }
      catch (Exception e){
         e.printStackTrace();
      }
   }

   /**
    * The retrievePatientIDs method goes through the retrieved Bundle of Encounters and checks if there are patients
    * associated with the practitioner's full name. If yes, it will append their IDs into a LinkedHashSet. As a
    * practitioner can have many encounters with the same patient, this list type is used to prevent repeating entries
    *
    * Because the server returns the response in a series of pages, the process will stop after going through the first
    * 4 pages of the Bundle as it will take quite a long time to go through all of the pages
    *
    * @param practitionerName the practitioner's full name
    * @return a LinkedHashSet of patient IDs
    */
   public LinkedHashSet<String> retrievePatientIDs(String practitionerName) {
      getResponse();

      int i = 0;
      int pageCount = 0;
      //boolean currentlyRunning = true;

      while(i < encounterResponse.getEntry().size() ){
         String id = encounterResponse.getEntry().get(i).getResource().getIdElement().getIdPart();
         Encounter en = ServerBase.getClient().read()
            .resource(Encounter.class)
            .withId(id)
            .execute();

         String foundPracName = en.getParticipant().get(0).getIndividual().getDisplay();

         if (foundPracName.equals(practitionerName)) {
            String patientRef = en.getSubject().getReference();
            String patientID = patientRef.substring(8, patientRef.length());
            patientIDs.add(patientID);
         }

         i++;
         if(i == encounterResponse.getEntry().size()){
            try {
               returnNextPage(); // throws a PayloadTooLargeException if request is overloading the server
               i = 0;
               pageCount++;

               // Stop the process and return IDs after searching through 4 pages (takes about roughly 3-5 minutes)
               if(pageCount == 4){
                  //System.out.println("Max page count");
                  //List<String> patientIDList = new ArrayList<>(patientIDs);
                  //System.out.println(patientIDList);
                  //currentlyRunning = false;
                  return patientIDs;

               }
            }
            catch(Exception e){
               System.out.println("PayloadTooLargeException "); // handle the PayloadTooLargeException and return IDs
               //currentlyRunning = false;
               return patientIDs;

            }

         }
      }
      return null;
   }

   /**
    * The returnNextPage method allows the response to return the next 10 results
    *
    * @throws PayloadTooLargeException this is thrown if the data in the response exceeds a certain limit
    */
   private void returnNextPage() throws PayloadTooLargeException {
      if (encounterResponse.getLink(Bundle.LINK_NEXT) != null ) {
         encounterResponse = ServerBase.getClient()
            .loadPage()
            .next(encounterResponse)
            .execute();
      }

   }

}
