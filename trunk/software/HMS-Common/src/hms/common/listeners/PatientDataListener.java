package hms.common.listeners;
import hms.common.events.PatientDataEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.EventListener;


public interface PatientDataListener extends Remote, EventListener {
	public void patientDataReceived(PatientDataEvent event) throws RemoteException;
}
