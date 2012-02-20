package hms.nursingstation.events;

import hms.nursingstation.MonitorProxy;

public class CallButtonReceivedEvent {
	private MonitorProxy monitor;

	public CallButtonReceivedEvent(MonitorProxy monitor) {
		this.monitor = monitor;
	}

	public MonitorProxy getMonitor() {
		return this.monitor;
	}

	public String generateMessage() {
		return "Patient pressed call button";
	}
}
