import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.EventListener;


public interface PatientAlarmListener extends Remote, EventListener {
	public void patientDataReceived(PatientAlarmEvent event) throws RemoteException;
}
