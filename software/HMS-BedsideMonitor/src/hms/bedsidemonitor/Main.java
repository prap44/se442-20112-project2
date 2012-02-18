package hms.bedsidemonitor;

import hms.common.Monitor;
import hms.common.PatientDataEvent;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	
	static final String BEDSIDE_SERVER_NAME = "hms.bedsidemonitor";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MonitorImpl server = null;
		
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
		Map<String, Integer> patientVitals = new HashMap<String, Integer>();
		patientVitals.put("heartbeat", 100);
		
		try {
			PatientImpl patient = new PatientImpl();
			patient.setPatientFirstName("Philip");
			patient.setPatientMiddleName("Thomas");
			patient.setPatientLastName("Rodriguez");
			
			Scanner sc = new Scanner(System.in);
			sc.nextLine();
			
			server.raisePatientDataEvent(new PatientDataEvent(patient, patientVitals));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
