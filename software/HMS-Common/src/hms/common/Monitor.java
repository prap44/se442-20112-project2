package hms.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Monitor extends Remote {
	public void setPatient(Patient patient) throws RemoteException;
	public Patient getPatient() throws RemoteException;
}
