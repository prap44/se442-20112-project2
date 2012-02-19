package hms.bedsidemonitor;

import hms.bedsidemonitor.gui.MainWindow;
import hms.common.Monitor;
import hms.common.PatientAlarmEvent;
import hms.common.PatientCallButtonEvent;
import hms.common.PatientDataEvent;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;

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
		
		try {
			MainWindow window = new MainWindow(server);
			window.setState(JFrame.MAXIMIZED_BOTH);
			window.setVisible(true);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			PatientImpl patient = new PatientImpl();
			patient.setPatientFirstName("Philip");
			patient.setPatientMiddleName("Thomas");
			patient.setPatientLastName("Rodriguez");
			Map<String, Integer> patientVitals = new HashMap<String, Integer>();
			patientVitals.put("heartbeat", 100);
			
			Scanner sc = new Scanner(System.in);
			sc.nextLine();
			
			System.out.println("Monitor raising patient data event");
			server.raisePatientDataEvent(new PatientDataEvent(patient, patientVitals));
			
			Thread.sleep(5000);
			
			System.out.println("Monitor raising patient alarm event");
			server.raisePatientAlarmEvent(new PatientAlarmEvent(patient, "heartbeat"));
			
			Thread.sleep(5000);
			
			System.out.println("Monitor raising patient call button event");
			server.raisePatientCallButtonEvent(new PatientCallButtonEvent(patient));
		} catch (RemoteException re) {
			re.printStackTrace();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		
	}

}
