package hms.bedsidemonitor;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.EventListenerList;

import hms.common.Monitor;
import hms.common.Patient;
import hms.common.PatientAlarmEvent;
import hms.common.PatientAlarmListener;
import hms.common.PatientCallButtonEvent;
import hms.common.PatientCallButtonListener;
import hms.common.PatientDataEvent;
import hms.common.PatientDataListener;
import hms.common.Sensor;

public class MonitorImpl implements Monitor {
	private EventListenerList listenerList = new EventListenerList();
	private Patient patient = null;
	private List<Sensor> sensors = new ArrayList<Sensor>();
	private Thread sensorPollingThread = new Thread() {
		public void run() {
			while(true) {
				Map<String, Integer> data = new HashMap<String, Integer>();
				for (Sensor s : MonitorImpl.this.sensors) {
					s.vitalChange();
					data.put(s.getName(), s.convert(s.getCurrentValue()));
				}
	
				try {
					MonitorImpl.this.raisePatientDataEvent(new PatientDataEvent(
							patient, data));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};

	public MonitorImpl() {
		this.sensorPollingThread.setDaemon(true);
		this.sensorPollingThread.start();
	}
	
	public List<Sensor> getSensorList() {
		return this.sensors;
	}

	@Override
	public Patient getPatient() throws RemoteException {
		return this.patient;
	}

	@Override
	public void setPatient(Patient patient) throws RemoteException {
		this.patient = patient;
	}

	@Override
	public void addPatientAlarmListener(PatientAlarmListener listener)
			throws RemoteException {
		System.out.println("[MonitorImpl] entering addPatientAlarmListener");
		this.listenerList.add(PatientAlarmListener.class, listener);
		System.out.println("[MonitorImpl] exiting addPatientAlarmListener");
	}

	@Override
	public void addPatientCallButtonListener(PatientCallButtonListener listener)
			throws RemoteException {
		this.listenerList.add(PatientCallButtonListener.class, listener);
	}

	@Override
	public void addPatientDataListener(PatientDataListener listener)
			throws RemoteException {
		this.listenerList.add(PatientDataListener.class, listener);
	}

	@Override
	public void removePatientAlarmListener(PatientAlarmListener listener)
			throws RemoteException {
		this.listenerList.remove(PatientAlarmListener.class, listener);
	}

	@Override
	public void removePatientCallButtonListener(
			PatientCallButtonListener listener) throws RemoteException {
		this.listenerList.remove(PatientCallButtonListener.class, listener);
	}

	@Override
	public void removePatientDataListener(PatientDataListener listener)
			throws RemoteException {
		this.listenerList.remove(PatientDataListener.class, listener);
	}

	public void raisePatientAlarmEvent(PatientAlarmEvent event)
			throws RemoteException {
		System.out.println("[MonitorImpl] entering raisePatientAlarmEvent");
		System.out.println("[MonitorImpl] listenerList.length == "
				+ listenerList.getListeners(PatientDataListener.class).length);
		for (PatientAlarmListener listener : this.listenerList
				.getListeners(PatientAlarmListener.class)) {
			listener.patientAlarmReceived(event);
		}
		System.out.println("[MonitorImpl] exiting raisePatientAlarmEvent");
	}

	public void raisePatientCallButtonEvent(PatientCallButtonEvent event)
			throws RemoteException {
		System.out
				.println("[MonitorImpl] entering raisePatientCallButtonEvent");
		System.out.println("[MonitorImpl] listenerList.length == "
				+ listenerList.getListeners(PatientDataListener.class).length);
		for (PatientCallButtonListener listener : this.listenerList
				.getListeners(PatientCallButtonListener.class)) {
			listener.patientCallButtonPressed(event);
		}
		System.out.println("[MonitorImpl] exiting raisePatientCallButtonEvent");
	}

	public void raisePatientDataEvent(PatientDataEvent event)
			throws RemoteException {
		System.out.println("[MonitorImpl] entering raisePatientDataEvent");
		System.out.println("[MonitorImpl] listenerList.length == "
				+ listenerList.getListeners(PatientDataListener.class).length);
		for (PatientDataListener listener : this.listenerList
				.getListeners(PatientDataListener.class)) {
			listener.patientDataReceived(event);
		}
		System.out.println("[MonitorImpl] exiting raisePatientDataEvent");
	}
}
