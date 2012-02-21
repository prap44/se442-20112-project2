package hms.nursingstation;

import hms.common.Monitor;
import hms.common.Patient;
import hms.common.events.PatientAlarmEvent;
import hms.common.events.PatientCallButtonEvent;
import hms.common.events.PatientDataEvent;
import hms.common.events.PatientInformationChangedEvent;
import hms.common.listeners.PatientAlarmListener;
import hms.common.listeners.PatientCallButtonListener;
import hms.common.listeners.PatientDataListener;
import hms.common.listeners.PatientInformationChangedListener;
import hms.nursingstation.events.AlarmReceivedEvent;
import hms.nursingstation.events.AlarmResetEvent;
import hms.nursingstation.events.CallButtonReceivedEvent;
import hms.nursingstation.events.CallButtonResetEvent;
import hms.nursingstation.events.DataReceivedEvent;
import hms.nursingstation.events.InformationChangeReceivedEvent;
import hms.nursingstation.listeners.AlarmReceivedListener;
import hms.nursingstation.listeners.AlarmResetListener;
import hms.nursingstation.listeners.CallButtonReceivedListener;
import hms.nursingstation.listeners.CallButtonResetListener;
import hms.nursingstation.listeners.DataReceivedListener;
import hms.nursingstation.listeners.InformationChangeReceivedListener;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

public class MonitorProxy extends UnicastRemoteObject implements
		PatientDataListener, PatientAlarmListener, PatientCallButtonListener,
		PatientInformationChangedListener {

	private static final long serialVersionUID = -3195762579705707707L;

	public class MonitorDisconnectedException extends Exception {
		private static final long serialVersionUID = -8207360613569516548L;
	}

	static final String BEDSIDE_SERVER_NAME = "hms.bedsidemonitor";

	private Monitor realMonitor;
	private EventListenerList listenerList = new EventListenerList();

	public MonitorProxy() throws IOException {
	}

	@Override
	public void patientCallButtonPressed(PatientCallButtonEvent event)
			throws RemoteException {
		Patient p;
		try {
			p = this.getPatient();
			if (p != null) {
				if (event.isActive()) {
					CallButtonReceivedEvent localEvent = new CallButtonReceivedEvent(
							this);
					this.raiseCallButtonReceivedEvent(localEvent);
				} else {
					final CallButtonResetEvent localEvent = new CallButtonResetEvent(
							this);
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							try {
								MonitorProxy.this.raiseCallButtonResetEvent(localEvent);
							} catch (RemoteException e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		} catch (MonitorDisconnectedException e) {
			Logger.getLogger(MonitorProxy.class.getName()).log(Level.SEVERE,
					null, e);
		}
	}

	@Override
	public void patientAlarmReceived(PatientAlarmEvent event)
			throws RemoteException {
		Patient p;
		try {
			p = this.getPatient();
			String vital = event.getVital();
			if (p != null) {
				if (event.isAlarmOn()) {
					AlarmReceivedEvent localEvent = new AlarmReceivedEvent(
							this, vital);
					this.raiseAlarmReceivedEvent(localEvent);
				} else {
					AlarmResetEvent localEvent = new AlarmResetEvent(this,
							vital);
					this.raiseAlarmResetEvent(localEvent);
				}
			}
		} catch (MonitorDisconnectedException e) {
			Logger.getLogger(MonitorProxy.class.getName()).log(Level.SEVERE,
					null, e);
		}
	}

	@Override
	public void patientDataReceived(PatientDataEvent event)
			throws RemoteException {
		Patient p;
		try {
			p = this.getPatient();
			Map<String, Integer> patientVitals = event.getVitals();
			if (p != null) {
				DataReceivedEvent localEvent = new DataReceivedEvent(
						patientVitals);
				this.raiseDataReceivedEvent(localEvent);
			}
		} catch (MonitorDisconnectedException e) {
			Logger.getLogger(MonitorProxy.class.getName()).log(Level.SEVERE,
					null, e);
		}
	}

	@Override
	public void patientInformationChanged(PatientInformationChangedEvent event)
			throws RemoteException {
		InformationChangeReceivedEvent localEvent = new InformationChangeReceivedEvent();
		this.raiseInformationChangedReceivedEvent(localEvent);
	}

	public void connectToMonitor() throws RemoteException, NotBoundException {
		this.realMonitor = lookupRemoteMonitor();
		this.realMonitor.addPatientAlarmListener(this);
		this.realMonitor.addPatientCallButtonListener(this);
		this.realMonitor.addPatientDataListener(this);
		this.realMonitor.addPatientInformationChangedListener(this);
	}
	
	public void disconnectFromMonitor() throws RemoteException {
		if(this.isConnected()) {
			Monitor monitor = this.realMonitor;
			this.realMonitor = null;
			monitor.removePatientAlarmListener(this);
			monitor.removePatientCallButtonListener(this);
			monitor.removePatientDataListener(this);
			monitor.removePatientInformationChangedListener(this);
		}
	}

	public boolean isConnected() {
		return this.realMonitor != null;
	}

	public String getMonitorID() {
		return BEDSIDE_SERVER_NAME;
	}

	public String getMonitorAddress() {
		return "127.0.0.1:1099";
	}

	public void assignPatient(String firstName, String middleName,
			String lastName) throws RemoteException,
			MonitorDisconnectedException {
		if (!this.isConnected()) {
			throw new MonitorDisconnectedException();
		}
		this.realMonitor.assignPatient(firstName, middleName, lastName);
	}

	public void unassignPatient() throws RemoteException,
			MonitorDisconnectedException {
		if (!this.isConnected()) {
			throw new MonitorDisconnectedException();
		}
		this.realMonitor.unsassignPatient();
	}

	public Patient getPatient() throws RemoteException,
			MonitorDisconnectedException {
		if (!this.isConnected()) {
			throw new MonitorDisconnectedException();
		}
		return this.realMonitor.getPatient();
	}

	public void addAlarmReceivedListener(AlarmReceivedListener listener) {
		this.listenerList.add(AlarmReceivedListener.class, listener);
	}

	public void addAlarmResetListener(AlarmResetListener listener) {
		this.listenerList.add(AlarmResetListener.class, listener);
	}

	public void addCallButtonReceivedListener(
			CallButtonReceivedListener listener) {
		this.listenerList.add(CallButtonReceivedListener.class, listener);
	}

	public void addCallButtonResetListener(CallButtonResetListener listener) {
		this.listenerList.add(CallButtonResetListener.class, listener);
	}

	public void addDataReceivedListener(DataReceivedListener listener) {
		this.listenerList.add(DataReceivedListener.class, listener);
	}

	public void addInformationChangeReceivedListener(
			InformationChangeReceivedListener listener) {
		this.listenerList
				.add(InformationChangeReceivedListener.class, listener);
	}

	private void raiseAlarmReceivedEvent(AlarmReceivedEvent event)
			throws RemoteException {
		for (AlarmReceivedListener l : this.listenerList
				.getListeners(AlarmReceivedListener.class)) {
			l.alarmReceived(event);
		}
	}

	private void raiseAlarmResetEvent(AlarmResetEvent event)
			throws RemoteException {
		for (AlarmResetListener l : this.listenerList
				.getListeners(AlarmResetListener.class)) {
			l.alarmReset(event);
		}
	}

	private void raiseCallButtonReceivedEvent(CallButtonReceivedEvent event)
			throws RemoteException {
		for (CallButtonReceivedListener l : this.listenerList
				.getListeners(CallButtonReceivedListener.class)) {
			l.callButtonRequestReceived(event);
		}
	}

	private void raiseCallButtonResetEvent(CallButtonResetEvent event)
			throws RemoteException {
		for (CallButtonResetListener l : this.listenerList
				.getListeners(CallButtonResetListener.class)) {
			l.callButtonRequestReset(event);
		}
	}

	private void raiseDataReceivedEvent(DataReceivedEvent event)
			throws RemoteException {
		for (DataReceivedListener l : this.listenerList
				.getListeners(DataReceivedListener.class)) {
			l.dataReceived(event);
		}
	}

	private void raiseInformationChangedReceivedEvent(
			InformationChangeReceivedEvent event) throws RemoteException {
		for (InformationChangeReceivedListener l : this.listenerList
				.getListeners(InformationChangeReceivedListener.class)) {
			l.informationChangeReceived(event);
		}
	}
	
	private Monitor lookupRemoteMonitor() throws RemoteException,
			NotBoundException, AccessException {
		Registry registry = LocateRegistry.getRegistry();
		return (Monitor) registry.lookup(BEDSIDE_SERVER_NAME);
	}

}
