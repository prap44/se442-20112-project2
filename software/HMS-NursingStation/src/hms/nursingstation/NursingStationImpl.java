package hms.nursingstation;

import hms.common.Patient;

import java.util.ArrayList;

public class NursingStationImpl {
	private ArrayList<Patient> patients = new ArrayList<Patient>();
	
	public NursingStationImpl() {
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
}
