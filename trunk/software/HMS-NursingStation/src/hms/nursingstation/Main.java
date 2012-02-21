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
			System.out.println("Client created MonitorProxy");
			try {
				mp.connectToMonitor();
			} catch (NotBoundException e) {
				e.printStackTrace();
				return;
			}
			System.out.println("MonitorProxy registered with real Monitor");
			client.addMonitor(mp);
			System.out.println("MonitorProxy added to NursingStationImpl");
		} catch (RemoteException re) {
			re.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}