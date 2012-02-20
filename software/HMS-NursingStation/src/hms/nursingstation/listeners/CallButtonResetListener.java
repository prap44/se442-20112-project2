package hms.nursingstation.listeners;

import hms.nursingstation.events.CallButtonResetEvent;

import java.util.EventListener;

public interface CallButtonResetListener extends EventListener {
	public void callButtonRequestReset(CallButtonResetEvent event);
}
