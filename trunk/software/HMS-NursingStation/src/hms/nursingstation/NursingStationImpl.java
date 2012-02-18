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
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;

public class NursingStationImpl extends UnicastRemoteObject
		implements PatientAlarmListener, 
		PatientCallButtonListener, PatientDataListener {
	
	private ArrayList<Patient> patients = new ArrayList<Patient>();
	
	public NursingStationImpl() 
			throws IOException
	{
	}
	
	public boolean addPatient(Patient patient) {
		return this.patients.add(patient);
	}
	
	public Patient getPatient(int index) {
		return this.patients.get(index);
	}
	
	public Patient removePatient(int index) {
		return this.patients.remove(index);
	}
	
	public boolean removePatient(Patient patient) {
		return this.patients.remove(patient);
	}
	
	public int getPatientCount() {
		return this.patients.size();
	}
	
	public void connectToMonitor(Monitor bedsideStation) {
		try {
			bedsideStation.addPatientAlarmListener(this);
			bedsideStation.addPatientCallButtonListener(this);
			bedsideStation.addPatientDataListener(this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void patientDataReceived(PatientDataEvent event)
			throws RemoteException {
		System.out.println("[NursingStationImpl] Patient Data Received");
		Patient p = event.getPatient();
		Map<String, Integer> patientVitals = event.getVitals();
		System.out.println("[NursingStationImpl] Patient: " + 
				p.getPatientFirstName() + " " + p.getPatientLastName());
		System.out.println("[NursingStationImpl] Patient Hearbeat Vital Signs: " + 
				patientVitals.get("heartbeat"));
	}

	@Override
	public void patientCallButtonPressed(PatientCallButtonEvent event)
			throws RemoteException {
		System.out.println("[NursingStationImpl] Patient Call Button Pressed");
		Patient p = event.getPatient();
		System.out.println("[NursingStationImpl] Patient " + 
				p.getPatientFirstName() + " " + p.getPatientLastName() + 
				" pressed the call button");
	}

	@Override
	public void patientAlarmReceived(PatientAlarmEvent event)
			throws RemoteException {
		System.out.println("[NursingStationImpl] Patient Alarm Received");
		Patient p = event.getPatient();
		String vital = event.getVital();
		System.out.println("[NursingStationImpl] Patient " +
				p.getPatientFirstName() + " " + p.getPatientLastName() + 
				"'s vital sign " + vital + " is critical");
	}
	
}
