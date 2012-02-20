package hms.common;

import java.io.Serializable;

public class PatientAlarmEvent implements Serializable {
	private String vital;
	
	public PatientAlarmEvent(String vital) {
		this.vital = vital;
	}
	
	public String getVital() {
		return this.vital;
	}
}
