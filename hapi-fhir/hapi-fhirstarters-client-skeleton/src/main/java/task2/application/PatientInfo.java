package task2.application;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * The PatientInfo class stores the required details of the patient
 * The class implements the Observer interface to include the updateCholesterol and updateDateTime methods so that new
 * values can be overwrite the old values with ease
 */

public class PatientInfo implements Observer{
   private String name;
   private String surname;
   private String id;
   private String birthDate;
   private String gender;
   private String city;
   private String state;
   private String country;
   private String cholesterol;
   private String cholesterolDateTime;

   private String systolicBloodPressure;
   private String diastolicBloodPressure;
   private String bloodPressureDateTime;

   private ArrayList<ArrayList<String>> bloodPressureReadings = new ArrayList<>();

   /**
    * Constructor; used within the package
    * @param name patient's name
    * @param surname patient's surname
    * @param id patient's ID
    * @param birthDate patient's date of birth
    * @param gender patient's gender
    * @param city city the patient resides in
    * @param state state the patient lives in
    * @param country patient's country of origin
    */
   PatientInfo(String name, String surname, String id, String birthDate, String gender, String city, String state,
               String country){
      this.name = name;
      this.surname = surname;
      this.id = id;
      this.birthDate = birthDate;
      this.gender = gender;
      this.city = city;
      this.state = state;
      this.country = country;

   }

   public String getName(){
      return name;
   }

   public String getSurname(){
      return surname;
   }

   public String getId(){
      return id;
   }

   public String getBirthDate(){
      return birthDate;
   }

   public String getGender(){
      return gender;
   }

   public String getCity(){
      return city;
   }

   public String getState(){
      return state;
   }

   public String getCountry(){
      return country;
   }

   public String getCholesterol(){
      return cholesterol;
   }

   public String getCholesterolDateTime(){
      return cholesterolDateTime;
   }

   public String getSystolicBloodPressure(){
      return systolicBloodPressure;
   }

   public String getDiastolicBloodPressure(){
      return diastolicBloodPressure;
   }

   public String getBloodPressureDateTime(){
      return bloodPressureDateTime;
   }

   public ArrayList<ArrayList<String>> getBloodPressureReadings (){
      return bloodPressureReadings;
   }

   /**
    * Used for testing purpose
    * @param cholesterol the patient's cholesterol
    */
   public void setCholesterol(String cholesterol){
      this.cholesterol = cholesterol;

   }

   /**
    * Used for testing purpose
    * @param cholesterolDateTime the patient's effectiveDateTime
    */
   public void setCholesterolDateTime(String cholesterolDateTime){
      this.cholesterolDateTime = cholesterolDateTime;
   }

   /**
    * Method to update the patient's cholesterol
    * @param cholesterol cholesterol value in String
    */
   @Override
   public void updateCholesterol(String cholesterol) {
      this.cholesterol = cholesterol;
   }

   /**
    * Method to update the cholesterol's effectiveDateTime
    * @param dateTime effectiveDateTime in String
    */
   @Override
   public void updateDateTime(String dateTime) {
      this.cholesterolDateTime = dateTime;
   }

   /**
    * Method to update the systolic BP
    * @param systolicBP systolic BP value in String
    */
   @Override
   public void updateSystolicBP(String systolicBP) {
      this.systolicBloodPressure = systolicBP;
   }

   /**
    * Method to update the diastolic BP
    * @param diastolicBP diastolic BP value in String
    */
   @Override
   public void updateDiastolicBP(String diastolicBP) {
      this.diastolicBloodPressure = diastolicBP;
   }

   /**
    * Method to update the blood pressure effectiveDateTime
    * @param dateTime effectiveDateTime in String
    */
   @Override
   public void updateBPDateTime(String dateTime) {
      this.bloodPressureDateTime = dateTime;
   }

   /**
    * Method to update the latest 5 systolic BP readings
    * @param readings a nested ArrayList of the readings with the associated dateTime
    */
   @Override
   public void updateBPReadings(ArrayList<ArrayList<String>> readings) {
      this.bloodPressureReadings = readings;
   }
}
