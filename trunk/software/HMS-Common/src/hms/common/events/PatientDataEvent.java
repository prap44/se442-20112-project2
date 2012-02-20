package hms.common.events;
import java.io.Serializable;
import java.util.Map;

public class PatientDataEvent implements Serializable {
	private Map<String, Integer> vitals;
	
	public PatientDataEvent(Map<String, Integer> vitals) {
		this.vitals = vitals;
	}
	
	public Map<String, Integer> getVitals() {
		return this.vitals;
	}
}
