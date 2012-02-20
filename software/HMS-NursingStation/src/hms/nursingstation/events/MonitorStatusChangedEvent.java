package hms.nursingstation.events;

import hms.nursingstation.MonitorProxy;

public class MonitorStatusChangedEvent {
	public enum MonitorChangedOperation {
		ADDED, REMOVED
	};
	
	private MonitorProxy monitor;
	private MonitorChangedOperation op;
	
	public MonitorStatusChangedEvent(MonitorProxy monitor, MonitorChangedOperation op) {
		this.monitor = monitor;
		this.op = op;
	}
	
	public MonitorProxy getMonitor() {
		return this.monitor;
	}
	
	public MonitorChangedOperation getOperation() {
		return this.op;
	}
}
