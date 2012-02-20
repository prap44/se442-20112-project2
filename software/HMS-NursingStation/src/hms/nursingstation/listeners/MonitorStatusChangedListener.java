package hms.nursingstation.listeners;

import hms.nursingstation.events.MonitorStatusChangedEvent;

import java.util.EventListener;

public interface MonitorStatusChangedListener extends EventListener {
	public void monitorStatusChanged(MonitorStatusChangedEvent event);
}
