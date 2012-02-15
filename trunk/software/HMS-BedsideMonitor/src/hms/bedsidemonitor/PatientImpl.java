package hms.bedsidemonitor;

import hms.common.Patient;
import hms.common.PatientAlarmEvent;
import hms.common.PatientAlarmListener;
import hms.common.PatientCallButtonEvent;
import hms.common.PatientCallButtonListener;
import hms.common.PatientDataEvent;
import hms.common.PatientDataListener;

import java.rmi.RemoteException;

import javax.swing.event.EventListenerList;

public class PatientImpl implements Patient {
	private EventListenerList listenerList = new EventListenerList();

	private String firstName = "";
	private String middleName = "";
	private String lastName = "";

	@Override
	public String getPatientFirstName() throws RemoteException {
		return this.firstName;
	}

	@Override
	public String getPatientMiddleName() throws RemoteException {
		return this.middleName;
	}

	@Override
	public String getPatientLastName() throws RemoteException {
		return this.lastName;
	}

	@Override
	public void setPatientFirstName(String name) throws RemoteException {
		this.firstName = name;
	}

	@Override
	public void setPatientMiddleName(String name) throws RemoteException {
		this.middleName = name;
	}

	@Override
	public void setPatientLastName(String name) throws RemoteException {
		this.lastName = name;
	}

	@Override
	public void addPatientAlarmListener(PatientAlarmListener listener)
			throws RemoteException {
		this.listenerList.add(PatientAlarmListener.class, listener);
	}

	@Override
	public void removePatientAlarmListener(PatientAlarmListener listener)
			throws RemoteException {
		this.listenerList.remove(PatientAlarmListener.class, listener);
	}

	private void raisePatientAlarmEvent(PatientAlarmEvent event)
			throws RemoteException {
		for (PatientAlarmListener listener : this.listenerList
				.getListeners(PatientAlarmListener.class)) {
			listener.patientDataReceived(event);
		}
	}

	@Override
	public void addPatientCallButtonListener(PatientCallButtonListener listener)
			throws RemoteException {
		this.listenerList.add(PatientCallButtonListener.class, listener);
	}

	@Override
	public void removePatientCallButtonListener(
			PatientCallButtonListener listener) throws RemoteException {
		this.listenerList.remove(PatientCallButtonListener.class, listener);
	}

	private void raisePatientCallButtonEvent(PatientCallButtonEvent event)
			throws RemoteException {
		for (PatientCallButtonListener listener : this.listenerList
				.getListeners(PatientCallButtonListener.class)) {
			listener.patientCallButtonPressed(event);
		}
	}

	@Override
	public void addPatientDataListener(PatientDataListener listener)
			throws RemoteException {
		this.listenerList.add(PatientDataListener.class, listener);
	}

	@Override
	public void removePatientDataListener(PatientDataListener listener)
			throws RemoteException {
		this.listenerList.remove(PatientDataListener.class, listener);
	}

	private void raisePatientDataEvent(PatientDataEvent event)
			throws RemoteException {
		for (PatientDataListener listener : this.listenerList
				.getListeners(PatientDataListener.class)) {
			listener.patientDataReceived(event);
		}
	}
}
