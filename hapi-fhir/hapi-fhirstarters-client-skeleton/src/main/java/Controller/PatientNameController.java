package Controller;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import task2.application.EncounterProcessor;
import task2.application.PatientInfo;
import task2.application.PatientProcessor;

/**
 * PatientNameController is the controller class for ViewData, this controller class is responsible for
 * printing all the practitioner's patient name. Allowing practitioner to see their patients, allow them to
 * know which patient they want to add/monitor
 * */
public class PatientNameController{

	private String practitionerName;
	private String practitionerIdentifier;
	private List<PatientInfo> patientInfoList;

	/**
	 * Constructor class
	 *
    * */
	public PatientNameController(String practitionerName, String practitionerIdentifier) {
		this.practitionerName = practitionerName;
		this.practitionerIdentifier = practitionerIdentifier;
	    getList();

	}
	
	/**
	 * This method gets a list of PatientInfo
	 * @return patientInfoList - a list of PatientInfo
	 * */
    public List<PatientInfo> getList(){
		EncounterProcessor encounters = new EncounterProcessor(practitionerIdentifier);
	    LinkedHashSet<String> patientIDs = encounters.retrievePatientIDs(practitionerName);

	    PatientProcessor patientProcessor = new PatientProcessor(patientIDs);
	    LinkedHashSet<PatientInfo> patients = patientProcessor.processPatients();
	    List<PatientInfo> patientInfoList = new ArrayList<>(patients);
	    this.patientInfoList = patientInfoList;
	    return patientInfoList;
    }
    
    /**
     * This methods print all of the patient name and surname into the GUI
     * @return s - string containing all patientName
     * */
	public String getPatientName() {
		String s = "<html>";
		for (int i=0; i< patientInfoList.size(); i++) {
			PatientInfo patient = patientInfoList.get(i);
	    	String patientName = patient.getName() + " " + patient.getSurname();
			s += (i+1) + ". " + patientName;
			s += "<br>";
		}
		 s+= "</html>";
		 return s;
	}
}
