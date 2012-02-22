package hms.common.listeners;

import hms.common.events.MonitorShutdownEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.EventListener;

public interface MonitorShutdownListener extends Remote, EventListener {
	public void monitorShuttingDown(MonitorShutdownEvent event) throws RemoteException;
}
