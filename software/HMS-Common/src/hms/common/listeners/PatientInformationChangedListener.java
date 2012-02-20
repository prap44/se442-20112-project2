package hms.common.listeners;

import hms.common.events.PatientInformationChangedEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.EventListener;

public interface PatientInformationChangedListener extends Remote, EventListener {
	public void patientInformationChanged(PatientInformationChangedEvent event) throws RemoteException;
}
