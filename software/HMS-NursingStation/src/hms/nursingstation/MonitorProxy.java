package hms.nursingstation;

import hms.common.Monitor;
import hms.common.Patient;
import hms.common.PatientAlarmEvent;
import hms.common.PatientAlarmListener;
import hms.common.PatientCallButtonEvent;
import hms.common.PatientCallButtonListener;
import hms.common.PatientDataEvent;
import hms.common.PatientDataListener;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

public class MonitorProxy extends UnicastRemoteObject implements
		PatientDataListener, PatientAlarmListener, PatientCallButtonListener {
	
	static final String BEDSIDE_SERVER_NAME = "hms.bedsidemonitor";

	private Monitor realMonitor;
	
	public MonitorProxy() throws IOException {
		//super();
		//this.connectToMonitor();
		//this.registerProxy();
	}

	@Override
	public void patientCallButtonPressed(PatientCallButtonEvent event)
			throws RemoteException {
		System.out.println("[MonitorProxy] Patient Call Button Pressed");
		Patient p = event.getPatient();
		System.out.println("[MonitorProxy] Patient " + 
				p.getPatientFirstName() + " " + p.getPatientLastName() + 
				" pressed the call button");
	}

	@Override
	public void patientAlarmReceived(PatientAlarmEvent event)
			throws RemoteException {
		System.out.println("[MonitorProxy] Patient Alarm Received");
		Patient p = event.getPatient();
		String vital = event.getVital();
		System.out.println("[MonitorProxy] Patient " +
				p.getPatientFirstName() + " " + p.getPatientLastName() + 
				"'s vital sign " + vital + " is critical");
	}

	@Override
	public void patientDataReceived(PatientDataEvent event)
			throws RemoteException {
		System.out.println("[MonitorProxy] Patient Data Received");
		Patient p = event.getPatient();
		Map<String, Integer> patientVitals = event.getVitals();
		System.out.println("[MonitorProxy] Patient: " + 
				p.getPatientFirstName() + " " + p.getPatientLastName());
		System.out.println("[MonitorProxy] Patient Hearbeat Vital Signs: " + 
				patientVitals.get("heartbeat"));
	}
	
	public void connectToMonitor() {
		try {
			Registry registry = LocateRegistry.getRegistry();
			this.realMonitor = (Monitor)registry.lookup(BEDSIDE_SERVER_NAME);
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
		} catch (RemoteException re) {
			re.printStackTrace();
		}
	}

}
