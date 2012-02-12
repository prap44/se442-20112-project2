package hms.common;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Patient extends Remote {
	public String getPatientFirstName() throws RemoteException;
	public String getPatientMiddleName() throws RemoteException;
	public String getPatientLastName() throws RemoteException;
	
	public void setPatientFirstName(String name) throws RemoteException;
	public void setPatientMiddleName(String name) throws RemoteException;
	public void setPatientLastName(String name) throws RemoteException;
	
	public void addPatientAlarmListener(PatientAlarmListener listener) throws RemoteException;
	public void removePatientAlarmListener(PatientAlarmListener listener) throws RemoteException;
	
	public void addPatientCallButtonListener(PatientCallButtonListener listener) throws RemoteException;
	public void removePatientCallButtonListener(PatientCallButtonListener listener) throws RemoteException;
	
	public void addPatientDataListener(PatientDataListener listener) throws RemoteException;
	public void removePatientDataListener(PatientDataListener listener) throws RemoteException;
}
