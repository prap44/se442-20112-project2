package hms.common;
import java.io.Serializable;
import java.util.Map;

public class PatientDataEvent implements Serializable {
	private Patient patient;
	private Map<String, Integer> vitals;
	
	public PatientDataEvent(Patient patient, Map<String, Integer> vitals) {
		this.patient = patient;
		this.vitals = vitals;
	}
	
	public Patient getPatient() {
		return this.patient;
	}
	
	public Map<String, Integer> getVitals() {
		return this.vitals;
	}
}
