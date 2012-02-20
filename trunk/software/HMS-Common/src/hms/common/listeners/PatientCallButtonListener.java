package hms.common.listeners;
import hms.common.events.PatientCallButtonEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.EventListener;


public interface PatientCallButtonListener extends Remote, EventListener {
	public void patientCallButtonPressed(PatientCallButtonEvent event) throws RemoteException;
}
