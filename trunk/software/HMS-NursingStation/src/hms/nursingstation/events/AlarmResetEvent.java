package hms.nursingstation.events;

import hms.nursingstation.MonitorProxy;

public class AlarmResetEvent {
	private MonitorProxy monitor;
	private String vital;

	public AlarmResetEvent(MonitorProxy monitor, String vital) {
		this.monitor = monitor;
		this.vital = vital;
	}

	public String getVital() {
		return this.vital;
	}

	public MonitorProxy getMonitor() {
		return this.monitor;
	}

	public String generateMessage() {
		return "Alarm reset at bedside station";
	}
}
