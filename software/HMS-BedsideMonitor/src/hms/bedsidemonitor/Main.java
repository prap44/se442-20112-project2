package hms.bedsidemonitor;

import hms.bedsidemonitor.gui.MainWindow;
import hms.common.Monitor;
import hms.common.PatientAlarmEvent;
import hms.common.PatientCallButtonEvent;
import hms.common.PatientDataEvent;
import hms.common.PatientInformationChangedEvent;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
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
		System.setSecurityManager(new RMISecurityManager());
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
		
		System.out.println("[hms.bedsidemonitor.Main] Server started.");
		
		try {
			MainWindow window = new MainWindow(server);
			window.setState(JFrame.MAXIMIZED_BOTH);
			window.setVisible(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		Scanner sc = new Scanner(System.in);
		System.out.println("[hms.bedsidemonitor.Main] press enter after client registers");
		sc.nextLine();
		
		try {
			Main.testOnePatientDataEvent(server);
			System.out.println("[hms.bedsidemonitor.Main] press enter to continue tests");
			sc.nextLine();
			Main.testOnePatientAlarmEvent(server);
			System.out.println("[hms.bedsidemonitor.Main] press enter to continue tests");
			sc.nextLine();
			Main.testOnePatientCallButtonEvent(server);
			System.out.println("[hms.bedsidemonitor.Main] press enter to continue tests");
			sc.nextLine();
			Main.testTwoPatientDataEvents(server);
			System.out.println("[hms.bedsidemonitor.Main] press enter to continue tests");
			sc.nextLine();
			Main.testOnePatientInformationChangedEvent(server);
		} catch (RemoteException re) {
			re.printStackTrace();
		}
		
	}
	
	private static void testOnePatientDataEvent(Monitor m) 
			throws RemoteException {
		System.out.println("[hms.bedsidemonitor.Main] entering testOnePatientDataEvent");
		PatientImpl patient = new PatientImpl();
		patient.setPatientFirstName("Philip");
		patient.setPatientMiddleName("Thomas");
		patient.setPatientLastName("Rodriguez");
		Map<String, Integer> patientVitals = new HashMap<String, Integer>();
		patientVitals.put("heartbeat", 100);
		m.raisePatientDataEvent(new PatientDataEvent(patient, patientVitals));
		System.out.println("[hms.bedsidemonitor.Main] exiting testOnePatientDataEvent");
	}
	
	private static void testTwoPatientDataEvents(Monitor m) 
			throws RemoteException {
		System.out.println("[hms.bedsidemonitor.Main] entering testTwoPatientDataEvents");
		PatientImpl patient1 = new PatientImpl();
		patient1.setPatientFirstName("Philip");
		patient1.setPatientMiddleName("Thomas");
		patient1.setPatientLastName("Rodriguez");
		PatientImpl patient2 = new PatientImpl();
		patient2.setPatientFirstName("John");
		patient2.setPatientMiddleName("A");
		patient2.setPatientLastName("Smith");
		Map<String, Integer> patient1Vitals = new HashMap<String, Integer>();
		patient1Vitals.put("heartbeat", 100);
		Map<String, Integer> patient2Vitals = new HashMap<String, Integer>();
		patient1Vitals.put("blood-pressure", 120);
		m.raisePatientDataEvent(new PatientDataEvent(patient1, patient1Vitals));
		m.raisePatientDataEvent(new PatientDataEvent(patient2, patient2Vitals));
		System.out.println("[hms.bedsidemonitor.Main] exiting testTwoPatientDataEvents");
	}
	
	private static void testOnePatientAlarmEvent(Monitor m) 
			throws RemoteException {
		System.out.println("[hms.bedsidemonitor.Main] entering testOnePatientAlarmEvent");
		PatientImpl patient = new PatientImpl();
		patient.setPatientFirstName("Philip");
		patient.setPatientMiddleName("Thomas");
		patient.setPatientLastName("Rodriguez");
		m.raisePatientAlarmEvent(new PatientAlarmEvent(patient, "heartbeat"));
		System.out.println("[hms.bedsidemonitor.Main] exiting testOnePatientAlarmEvent");
	}
	
	private static void testOnePatientCallButtonEvent(Monitor m) 
			throws RemoteException {
		System.out.println("[hms.bedsidemonitor.Main] entering testOnePatientCallButtonEvent");
		PatientImpl patient = new PatientImpl();
		patient.setPatientFirstName("Philip");
		patient.setPatientMiddleName("Thomas");
		patient.setPatientLastName("Rodriguez");
		m.raisePatientCallButtonEvent(new PatientCallButtonEvent(patient));
		System.out.println("[hms.bedsidemonitor.Main] exiting testOnePatientCallButtonEvent");
	}
	
	private static void testOnePatientInformationChangedEvent(Monitor m) 
			throws RemoteException {
		System.out.println("[hms.bedsidemonitor.Main] entering testOnePatientInformationChangedEvent");
		m.raisePatientInformationChangedEvent(new PatientInformationChangedEvent());
		System.out.println("[hms.bedsidemonitor.Main] exiting testOnePatientInformationChangedEvent");
	}

}
