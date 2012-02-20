package hms.nursingstation;

import hms.common.Monitor;
import hms.common.Patient;
import hms.common.PatientAlarmEvent;
import hms.common.PatientAlarmListener;
import hms.common.PatientCallButtonEvent;
import hms.common.PatientCallButtonListener;
import hms.common.PatientDataEvent;
import hms.common.PatientDataListener;
import hms.common.PatientInformationChangedEvent;
import hms.common.PatientInformationChangedListener;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonitorProxy extends UnicastRemoteObject implements
		PatientDataListener, PatientAlarmListener, PatientCallButtonListener,
		PatientInformationChangedListener {
	
	public class MonitorDisconnectedException extends Exception {
	}

	static final String BEDSIDE_SERVER_NAME = "hms.bedsidemonitor";

	private Monitor realMonitor;

	public MonitorProxy() throws IOException {
	}

	@Override
	public void patientCallButtonPressed(PatientCallButtonEvent event)
			throws RemoteException {
		System.out.println("[MonitorProxy] Patient Call Button Pressed");
		Patient p;
		try {
			p = this.getPatient();
			if (p != null) {
				if (event.isActive()) {
					System.out.println("[MonitorProxy] Patient "
							+ p.getPatientFirstName() + " " + p.getPatientLastName()
							+ " pressed the call button");
				} else {
					System.out.println("[MonitorProxy] Patient "
							+ p.getPatientFirstName() + " " + p.getPatientLastName()
							+ "'s call button was reset");					
				}
			}
		} catch (MonitorDisconnectedException e) {
			Logger.getLogger(MonitorProxy.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	@Override
	public void patientAlarmReceived(PatientAlarmEvent event)
			throws RemoteException {
		System.out.println("[MonitorProxy] Patient Alarm Received");
		Patient p;
		try {
			p = this.getPatient();
			String vital = event.getVital();
			if (p != null) {
				if (event.isAlarmOn()) {
					System.out.println("[MonitorProxy] Patient "
							+ p.getPatientFirstName() + " " + p.getPatientLastName()
							+ "'s vital sign " + vital + " is critical");
				} else {
					System.out.println("[MonitorProxy] Patient "
							+ p.getPatientFirstName() + " " + p.getPatientLastName()
							+ "'s alarm for vital sign " + vital + " was reset");
				}
			}
		} catch (MonitorDisconnectedException e) {
			Logger.getLogger(MonitorProxy.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	@Override
	public void patientDataReceived(PatientDataEvent event)
			throws RemoteException {
		System.out.println("[MonitorProxy] Patient Data Received");
		Patient p;
		try {
			p = this.getPatient();
			Map<String, Integer> patientVitals = event.getVitals();
			if (p != null) {
				System.out.println("[MonitorProxy] Patient: "
						+ p.getPatientFirstName() + " " + p.getPatientLastName());
				String vital = patientVitals.keySet().iterator().next();
				System.out.println("[MonitorProxy] Patient Vital Signs: " + vital
						+ ", " + patientVitals.get(vital));
			}
		} catch (MonitorDisconnectedException e) {
			Logger.getLogger(MonitorProxy.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	@Override
	public void patientInformationChanged(PatientInformationChangedEvent event)
			throws RemoteException {
		System.out.println("[MonitorProxy] Patient Information Changed");
	}

	public void connectToMonitor() {
		try {
			Registry registry = LocateRegistry.getRegistry();
			this.realMonitor = (Monitor) registry.lookup(BEDSIDE_SERVER_NAME);
		} catch (NotBoundException nbe) {
			nbe.printStackTrace();
		} catch (RemoteException re) {
			re.printStackTrace();
		}
	}

	public void registerProxy() {
		try {
			realMonitor.addPatientAlarmListener(this);
			realMonitor.addPatientCallButtonListener(this);
			realMonitor.addPatientDataListener(this);
			realMonitor.addPatientInformationChangedListener(this);
		} catch (RemoteException re) {
			re.printStackTrace();
		}
	}
	
	public boolean isConnected() {
		return this.realMonitor != null;
	}

	public void setPatient(Patient patient) throws RemoteException, MonitorDisconnectedException {
		if(!this.isConnected()) {
			throw new MonitorDisconnectedException();
		}
		this.realMonitor.setPatient(patient);
	}

	public Patient getPatient() throws RemoteException, MonitorDisconnectedException {
		if(!this.isConnected()) {
			throw new MonitorDisconnectedException();
		}
		return this.realMonitor.getPatient();
	}

}
