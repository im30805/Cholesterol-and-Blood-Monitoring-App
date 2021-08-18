# FIT3077 Assignment 2/3

The project makes use of the HAPI FHIR API and is configured as a Maven project <br />
Ensure that the pom.xml file is present.

The application is run from the AppLogin class. The path to find the class is as
follows <br />

hapi-fhir > hapi-fhir-client-skeleton > src > main > java > GUI > AppLogin

<br />

When the app is run, a dialog box prompting to enter the Practitioner's ID
should appear. After entering a valid ID, another dialog box indicating that 
the login is successful will appear. However, it will take a few moments to show.
Once it appears, click on OK. <br />

The login box will stay on the screen for approximately 3-5 minutes because the
application takes some time to fetch and process the data from the server. 
When the process is done, a screen should appear with the Practitioner's name
and their list of patients under them. An empty table and a graph is also 
rendered. <br />

To add patients into the table, there is a section for the Practitioner to input
a number as assigned to the patient in the list. Below this section is an input 
field for the Practitioner to change the update frequency of cholesterol
and blood pressure values. There are also input fields for the Practitioner to
enter the systolic/diastolic blood pressure values they want to monitor. <br />

When a patient is added, their name, total cholesterol and effectiveDateTime in
addition the systolic/diastolic blood pressure and the respective 
effectiveDateTime will be displayed in the table. The total cholesterol will be
highlighted if the patient has a reading of more than the average total cholesterol
among all the monitored patients. If any of the blood pressures are monitored
the patients that have a value higher than the given value will have their reading 
highlighted in blue. In the last column, each patient has a button that allows the
Practitioner to view more of their patients' details. Another window will open 
upon clicking the button. <br />

The table view can be toggled as well to only show either the cholesterol or
blood pressure readings. It can be toggled back to show both. <br />

In addition, if systolic blood pressure is chosen to be monitored, after the
patients' readings are highlighted (if any) a button 'Monitor High Systolic BP
Patient' will appear. When clicked, a textual monitor of each monitored patient
that has a high systolic BP reading showing a history of the 5 latest readings 
will be printed next to the table. Once displayed, another button 'View Graphs 
for High Systolic BP Patient' will appear. When clicked, the graphs of each
monitored patient will be opened in individual windows. <br />

If a Practitioner wants to stop monitoring a patient, they can click on the
patient's row in the table and click on the 'delete' button that is placed next
to the table. <br /> 

Note: When the application window is closed, the data is not saved. The 
Practitioner would need to login and wait again to see their patients' details.
<br />






