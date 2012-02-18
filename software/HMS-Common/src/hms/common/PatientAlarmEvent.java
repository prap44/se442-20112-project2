package hms.common;

import java.io.Serializable;

public class PatientAlarmEvent implements Serializable {
	private Patient patient;
	private String vital;
	
	public PatientAlarmEvent(Patient patient, String vital) {
		this.patient = patient;
		this.vital = vital;
	}
	
	public Patient getPatient() {
		return this.patient;
	}
	
	public String getVital() {
		return this.vital;
	}
}
