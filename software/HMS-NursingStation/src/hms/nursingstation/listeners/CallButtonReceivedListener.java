package hms.nursingstation.listeners;

import hms.nursingstation.events.CallButtonReceivedEvent;

import java.util.EventListener;

public interface CallButtonReceivedListener extends EventListener {
	public void callButtonRequestReceived(CallButtonReceivedEvent event);
}
