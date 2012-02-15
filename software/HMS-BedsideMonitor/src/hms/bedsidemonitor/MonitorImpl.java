package hms.bedsidemonitor;

import java.rmi.RemoteException;

import hms.common.Monitor;
import hms.common.Patient;

public class MonitorImpl implements Monitor {
	private Patient patient = null;

	@Override
	public Patient getPatient() throws RemoteException {
		return this.patient;
	}

	@Override
	public void setPatient(Patient patient) throws RemoteException {
		this.patient = patient;
	}
}
