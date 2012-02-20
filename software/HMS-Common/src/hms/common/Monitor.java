package hms.common;

import hms.common.events.PatientAlarmEvent;
import hms.common.events.PatientCallButtonEvent;
import hms.common.events.PatientDataEvent;
import hms.common.events.PatientInformationChangedEvent;
import hms.common.listeners.PatientAlarmListener;
import hms.common.listeners.PatientCallButtonListener;
import hms.common.listeners.PatientDataListener;
import hms.common.listeners.PatientInformationChangedListener;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Monitor extends Remote {
	public void assignPatient(String firstName, String middleName, String lastName) throws RemoteException;
	public void unsassignPatient() throws RemoteException;
	public Patient getPatient() throws RemoteException;
	public void addPatientAlarmListener(PatientAlarmListener listener) throws RemoteException;
	public void addPatientCallButtonListener(PatientCallButtonListener listener) throws RemoteException;
	public void addPatientDataListener(PatientDataListener listener) throws RemoteException;
	public void addPatientInformationChangedListener(PatientInformationChangedListener listener) throws RemoteException;
	public void removePatientAlarmListener(PatientAlarmListener listener) throws RemoteException;
	public void removePatientCallButtonListener(PatientCallButtonListener listener) throws RemoteException;
	public void removePatientDataListener(PatientDataListener listener) throws RemoteException;
	public void removePatientInformationChangedListener(PatientInformationChangedListener listener) throws RemoteException;
	public void raisePatientAlarmEvent(PatientAlarmEvent event) throws RemoteException;
	public void raisePatientCallButtonEvent(PatientCallButtonEvent event) throws RemoteException;
	public void raisePatientDataEvent(PatientDataEvent event) throws RemoteException;
	public void raisePatientInformationChangedEvent(PatientInformationChangedEvent event) throws RemoteException;
}
