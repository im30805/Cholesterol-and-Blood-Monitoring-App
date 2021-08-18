package task2.application;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;

/*
 * The ServerBase class is used in the processor classes to fetch their respective requests with the root URL
 */

public class ServerBase {
   /*private static ServerBase singleInstance = null;

   public FhirContext context;
   public IGenericClient client;

   private ServerBase(){
      context = FhirContext.forR4();
      client = context.newRestfulGenericClient("https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/");
   }

   public static ServerBase getSingleInstance(){
      if(singleInstance == null){
         singleInstance = new ServerBase();
      }

      return singleInstance;
   }
   */
   private static final FhirContext context = FhirContext.forR4();

   public static IGenericClient getClient(){
      return context.newRestfulGenericClient("https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/");
   }

}

