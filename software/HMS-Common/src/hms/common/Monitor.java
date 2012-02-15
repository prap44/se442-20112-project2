package hms.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Monitor extends Remote {
	public void setPatient(Patient patient) throws RemoteException;
	public Patient getPatient() throws RemoteException;
	public void addPatientAlarmListener(PatientAlarmListener listener) throws RemoteException;
	public void addPatientCallButtonListener(PatientCallButtonListener listener) throws RemoteException;
	public void addPatientDataListener(PatientDataListener listener) throws RemoteException;
	public void removePatientAlarmListener(PatientAlarmListener listener) throws RemoteException;
	public void removePatientCallButtonListener(PatientCallButtonListener listener) throws RemoteException;
	public void removePatientDataListener(PatientDataListener listener) throws RemoteException;
}
