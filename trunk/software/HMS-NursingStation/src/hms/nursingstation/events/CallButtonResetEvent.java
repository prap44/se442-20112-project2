package hms.nursingstation.events;

import hms.nursingstation.MonitorProxy;

public class CallButtonResetEvent {
	private MonitorProxy monitor;

	public CallButtonResetEvent(MonitorProxy monitor) {
		this.monitor = monitor;
	}

	public MonitorProxy getMonitor() {
		return this.monitor;
	}

	public String generateMessage() {
		return "Call butten reset at bedside station";
	}
}
