package hms.nursingstation.listeners;

import hms.nursingstation.events.DataReceivedEvent;

import java.util.EventListener;

public interface DataReceivedListener extends EventListener {
	public void dataReceived(DataReceivedEvent event);
}
