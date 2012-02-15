package hms.bedsidemonitor;

import hms.common.Patient;

import java.rmi.RemoteException;

public class PatientImpl implements Patient {
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
	public void setPatientLastName(String name) throws RemoteException {
		this.lastName = name;
	}

	@Override
	public void setPatientMiddleName(String name) throws RemoteException {
		this.middleName = name;
	}
}
