package hms.nursingstation.listeners;

import hms.nursingstation.events.InformationChangeReceivedEvent;

import java.util.EventListener;

public interface InformationChangeReceivedListener extends EventListener {
	public void informationChangeReceived(
			InformationChangeReceivedEvent event);
}
