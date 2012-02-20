package hms.common;

import java.io.Serializable;

public class PatientCallButtonEvent implements Serializable {
	private boolean isActive;
	
	public PatientCallButtonEvent(boolean isActive) {
		this.isActive = isActive;
	}
	
	public boolean isActive() {
		return isActive;
	}
}
