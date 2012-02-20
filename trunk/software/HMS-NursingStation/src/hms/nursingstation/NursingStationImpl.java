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
import java.util.List;
import java.util.Map;

public class NursingStationImpl {
	
	private ArrayList<Patient> patients = new ArrayList<Patient>();
	private ArrayList<MonitorProxy> bedsideStations = new ArrayList<MonitorProxy>();
	
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
	
	public List<Patient> getPatientList() {
		return this.patients;
	}
	
	public boolean addMonitor(MonitorProxy monitor) {
		return this.bedsideStations.add(monitor);
	}
	
	public MonitorProxy getMonitor(int index) {
		return this.bedsideStations.get(index);
	}
	
	public MonitorProxy removeMonitor(int index) {
		return this.bedsideStations.remove(index);
	}
	
	public boolean removeMonitor(MonitorProxy monitor) {
		return this.bedsideStations.remove(monitor);
	}
	
	public int getMonitorCount() {
		return this.bedsideStations.size();
	}
	
}