package hms.common;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.EventListener;


public interface PatientAlarmListener extends Remote, EventListener {
	public void patientAlarmReceived(PatientAlarmEvent event) throws RemoteException;
}
