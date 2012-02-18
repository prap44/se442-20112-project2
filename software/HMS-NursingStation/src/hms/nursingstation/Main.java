package hms.nursingstation;

import hms.common.Monitor;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
	
	static final String BEDSIDE_SERVER_NAME = "hms.bedsidemonitor";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setSecurityManager(new RMISecurityManager());
		Monitor remoteMonitor;
		NursingStationImpl client;
		
		try {
			Registry registry = LocateRegistry.getRegistry();
			remoteMonitor = (Monitor)registry.lookup(BEDSIDE_SERVER_NAME);
			System.out.println("Nursing Station found a Bedside Monitor remote object.");
			
			client = new NursingStationImpl();
			client.connectToMonitor(remoteMonitor);
		} catch (RemoteException re) {
			re.printStackTrace();
		} catch (NotBoundException nbe) {
			nbe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
