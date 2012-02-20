package hms.nursingstation.events;

import java.util.Map;

public class DataReceivedEvent {
	private Map<String, Integer> vitals;
	
	public DataReceivedEvent(Map<String, Integer> vitals) {
		this.vitals = vitals;
	}
	
	public Map<String, Integer> getVitals() {
		return this.vitals;
	}

}
