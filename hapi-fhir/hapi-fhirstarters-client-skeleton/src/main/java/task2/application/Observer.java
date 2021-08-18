package task2.application;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Interface for Observer
 * As of now, the PatientInfo class is the only class implementing the interface
 */

public interface Observer {

   void updateCholesterol(String cholesterol);

   void updateDateTime(String dateTime);

   void updateSystolicBP(String systolicBP);

   void updateDiastolicBP(String diastolicBP);

   void updateBPDateTime(String dateTime);

   void updateBPReadings(ArrayList<ArrayList<String>> readings);

}
