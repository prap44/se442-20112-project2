package hms.common;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Patient extends Remote {
	public String getPatientFirstName() throws RemoteException;
	public String getPatientMiddleName() throws RemoteException;
	public String getPatientLastName() throws RemoteException;
	
	public void setPatientFirstName(String name) throws RemoteException;
	public void setPatientLastName(String name) throws RemoteException;
	public void setPatientMiddleName(String name) throws RemoteException;
}
