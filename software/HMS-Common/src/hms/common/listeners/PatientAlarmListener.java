package hms.common.listeners;
import hms.common.events.PatientAlarmEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.EventListener;


public interface PatientAlarmListener extends Remote, EventListener {
	public void patientAlarmReceived(PatientAlarmEvent event) throws RemoteException;
}
