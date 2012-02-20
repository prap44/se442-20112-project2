package hms.common;

import java.io.Serializable;

public class PatientAlarmEvent implements Serializable {
	private String vital;
	private boolean isAlarmOn;
	
	public PatientAlarmEvent(String vital, boolean isAlarmOn) {
		this.vital = vital;
		this.isAlarmOn = isAlarmOn;
	}
	
	public String getVital() {
		return this.vital;
	}
	
	public boolean isAlarmOn() {
		return isAlarmOn;
	}
}
