package hms.nursingstation;

import hms.common.Monitor;

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
		Monitor server;
		
		try {
			Registry registry = LocateRegistry.getRegistry();
			server = (Monitor)registry.lookup(BEDSIDE_SERVER_NAME);
			System.out.println("Nursing Station found a Bedside Monitor Server.");
			
			// subscribe to B.S.M. server object.
			// server will handle request that client wants updates from it.
			// server will maintain a list of clients to send updates to.
			// once the server gets its patient data, it sends that data in events
			// to its list of clients that are subscribed to it.
		} catch (RemoteException re) {
			re.printStackTrace();
		} catch (NotBoundException nbe) {
			nbe.printStackTrace();
		}
	}

}
