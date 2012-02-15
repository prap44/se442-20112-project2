package hms.bedsidemonitor;

import hms.common.Monitor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Main {
	
	static final String BEDSIDE_SERVER_NAME = "hms.bedsidemonitor";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Monitor server = null;
		
		try {
			// Create the server hosting the bedside monitor
			server = new MonitorImpl();
			
			// Export the object to RMI
			UnicastRemoteObject.exportObject(server);
		} catch (RemoteException re) {
			re.printStackTrace();
		}
		
		// Register an external name for the service
		try {
			Naming.rebind(BEDSIDE_SERVER_NAME, server);
		} catch (RemoteException re) {
			re.printStackTrace();
		} catch (MalformedURLException murle) {
			murle.printStackTrace();
		}
		
		System.out.println("Server started.");
	}

}