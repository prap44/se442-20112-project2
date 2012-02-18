package hms.common;

import java.io.Serializable;

public class PatientCallButtonEvent implements Serializable {
	private Patient patient;
	
	public PatientCallButtonEvent(Patient patient) {
		this.patient = patient;
	}
	
	public Patient getPatient() {
		return this.patient;
	}
}
