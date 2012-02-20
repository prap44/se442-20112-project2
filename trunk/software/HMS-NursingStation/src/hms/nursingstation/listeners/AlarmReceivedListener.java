package hms.nursingstation.listeners;

import hms.nursingstation.events.AlarmReceivedEvent;

import java.util.EventListener;

public interface AlarmReceivedListener extends EventListener {
	public void alarmReceived(AlarmReceivedEvent event);
}
