package hms.nursingstation.events;

public class AlarmReceivedEvent {
	private String vital;
	
	public AlarmReceivedEvent(String vital) {
		this.vital = vital;
	}
	
	public String getVital() {
		return this.vital;
	}
}
