package hms.common;

public class PatientCallButtonEvent {
	private Patient patient;
	
	public PatientCallButtonEvent(Patient patient) {
		this.patient = patient;
	}
	
	public Patient getPatient() {
		return this.patient;
	}
}
