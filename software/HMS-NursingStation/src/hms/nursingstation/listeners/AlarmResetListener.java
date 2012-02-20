package hms.nursingstation.listeners;

import hms.nursingstation.events.AlarmResetEvent;

import java.util.EventListener;

public interface AlarmResetListener extends EventListener {
	public void alarmReset(AlarmResetEvent event);
}
