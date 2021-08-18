package task2.application;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for subjects to be observed
 */

public abstract class ObservationSubject {
   private List<Observer> observers = new ArrayList<>();

   /**
    * Attach an observer from the list
    * @param observer -
    */
   public void attach(Observer observer){
      observers.add(observer);
   }

   /**
    * Remove an observer from the list
    * @param observer-
    */
   public void detach(Observer observer){
      observers.remove(observer);
   }

   /**
    * Notify the observer to update cholesterol values (overloaded method)
    * @param cholesterol cholesterol value in String
    * @param dateTime effectiveDateTime in String
    */
   public void notifyObserver(String cholesterol, String dateTime){
      for(Observer observer : observers){
         observer.updateCholesterol(cholesterol);
         observer.updateDateTime(dateTime);
      }
   }

   /**
    * Notify the observer to update BP values (overloaded method)
    * @param systolicBP systolic BP value in String
    * @param diastolicBP diastolic BP value in String
    * @param bloodPressureDateTime effectiveDateTime in String
    * @param bloodPressureReadings a nested ArrayList of the readings with the associated dateTime
    */
   public void notifyObserver(String systolicBP, String diastolicBP, String bloodPressureDateTime, ArrayList<ArrayList<String>> bloodPressureReadings){
      for(Observer observer : observers){
         observer.updateSystolicBP(systolicBP);
         observer.updateDiastolicBP(diastolicBP);
         observer.updateBPDateTime(bloodPressureDateTime);
         observer.updateBPReadings(bloodPressureReadings);


      }
   }

   public abstract void processObservation();

   public abstract void update();

}

