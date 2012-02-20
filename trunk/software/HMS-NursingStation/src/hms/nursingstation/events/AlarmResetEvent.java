package hms.nursingstation.events;

public class AlarmResetEvent {
	private String vital;
	
	public AlarmResetEvent(String vital) {
		this.vital = vital;
	}
	
	public String getVital() {
		return this.vital;
	}
}
