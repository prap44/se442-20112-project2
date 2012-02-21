package hms.nursingstation;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setSecurityManager(new RMISecurityManager());
		NursingStationImpl client = new NursingStationImpl();

		try {
			MonitorProxy mp = new MonitorProxy();
			try {
				mp.connectToMonitor();
			} catch (NotBoundException e) {
				e.printStackTrace();
				return;
			}
			client.addMonitor(mp);
		} catch (RemoteException re) {
			re.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}