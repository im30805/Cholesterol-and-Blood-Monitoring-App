package task2.application;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Patient;

public class TestApplication {

   /**
    * This is the Java main method, which gets executed
    */
   public static void main(String[] args) {

      // Create a context
      FhirContext ctx = FhirContext.forR4();

      // Create a client
      //IGenericClient client = ctx.newRestfulGenericClient("http://fhirtest.uhn.ca/baseR4");
      IGenericClient client = ctx.newRestfulGenericClient("https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/");

      //[1, 34590, 35882, 61198, 64315, 124987, 182021, 185674, 216794]
      // Read a patient with the given ID
      Patient patient = client.read().resource(Patient.class).withId("64315").execute();

      // Print the output
      String string = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient);
      System.out.println(string);

      //Example data extraction
      System.out.println(patient.getName().get(0).getFamily()); // get surname
      System.out.println(patient.getIdElement().getIdPart()); //get patient's ID


		/*
       * Some more things to try:
		 * 
		 * Search for Patient resources with the name â€œTestâ€? and print the results
		 *   Bonus: Load the second page
		 * 
		 * Create a new Patient resource and upload it to the server
		 *   Bonus: Log the ID that the server assigns to your resource
		 *
 		 * Read a resource from the server
		 *   Bonus: Display an error if it has been deleted
		 *   Hint for Bonus- See this page: http://hapifhir.io/apidocs/ca/uhn/fhir/rest/server/exceptions/package-summary.html
		 */

   }

}
