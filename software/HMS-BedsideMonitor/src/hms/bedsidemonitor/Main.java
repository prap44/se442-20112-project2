package hms.bedsidemonitor;

import hms.bedsidemonitor.gui.MainWindow;
import hms.common.Monitor;
import hms.common.events.PatientAlarmEvent;
import hms.common.events.PatientCallButtonEvent;
import hms.common.events.PatientDataEvent;
import hms.common.events.PatientInformationChangedEvent;

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
			try {
				for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
						.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						javax.swing.UIManager.setLookAndFeel(info
								.getClassName());
						break;
					}
				}
			} catch (ClassNotFoundException ex) {
				java.util.logging.Logger.getLogger(MainWindow.class.getName())
						.log(java.util.logging.Level.SEVERE, null, ex);
			} catch (InstantiationException ex) {
				java.util.logging.Logger.getLogger(MainWindow.class.getName())
						.log(java.util.logging.Level.SEVERE, null, ex);
			} catch (IllegalAccessException ex) {
				java.util.logging.Logger.getLogger(MainWindow.class.getName())
						.log(java.util.logging.Level.SEVERE, null, ex);
			} catch (javax.swing.UnsupportedLookAndFeelException ex) {
				java.util.logging.Logger.getLogger(MainWindow.class.getName())
						.log(java.util.logging.Level.SEVERE, null, ex);
			}
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
			Main.testOnePatientAlarmTriggeredEvent(server);
			System.out.println("[hms.bedsidemonitor.Main] press enter to continue tests");
			sc.nextLine();
			Main.testOnePatientAlarmResetEvent(server);
			System.out.println("[hms.bedsidemonitor.Main] press enter to continue tests");
			sc.nextLine();
			Main.testOnePatientCallButtonPressedEvent(server);
			System.out.println("[hms.bedsidemonitor.Main] press enter to continue tests");
			sc.nextLine();
			Main.testOnePatientCallButtonResetEvent(server);
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
		m.assignPatient("Philip", "Thomas", "Rodriguez");
		Map<String, Integer> patientVitals = new HashMap<String, Integer>();
		patientVitals.put("heartbeat", 100);
		m.raisePatientDataEvent(new PatientDataEvent(patientVitals));
		System.out.println("[hms.bedsidemonitor.Main] exiting testOnePatientDataEvent");
	}
	
	private static void testTwoPatientDataEvents(Monitor m) 
			throws RemoteException {
		System.out.println("[hms.bedsidemonitor.Main] entering testTwoPatientDataEvents");
		m.assignPatient("Philip", "Thomas", "Rodriguez");
		Map<String, Integer> patient1Vitals = new HashMap<String, Integer>();
		patient1Vitals.put("heartbeat", 100);
		m.raisePatientDataEvent(new PatientDataEvent(patient1Vitals));

		m.assignPatient("John", "A", "Smith");
		Map<String, Integer> patient2Vitals = new HashMap<String, Integer>();
		patient2Vitals.put("blood-pressure", 120);
		m.raisePatientDataEvent(new PatientDataEvent(patient2Vitals));
		System.out.println("[hms.bedsidemonitor.Main] exiting testTwoPatientDataEvents");
	}
	
	private static void testOnePatientAlarmTriggeredEvent(Monitor m) 
			throws RemoteException {
		System.out.println("[hms.bedsidemonitor.Main] entering testOnePatientAlarmTriggeredEvent");
		m.assignPatient("Philip", "Thomas", "Rodriguez");
		m.raisePatientAlarmEvent(new PatientAlarmEvent("heartbeat", true));
		System.out.println("[hms.bedsidemonitor.Main] exiting testOnePatientAlarmTriggeredEvent");
	}
	
	private static void testOnePatientAlarmResetEvent(Monitor m) 
			throws RemoteException {
		System.out.println("[hms.bedsidemonitor.Main] entering testOnePatientAlarmResetEvent");
		m.assignPatient("Philip", "Thomas", "Rodriguez");
		m.raisePatientAlarmEvent(new PatientAlarmEvent("heartbeat", false));
		System.out.println("[hms.bedsidemonitor.Main] exiting testOnePatientAlarmResetEvent");
	}
	
	private static void testOnePatientCallButtonPressedEvent(Monitor m) 
			throws RemoteException {
		System.out.println("[hms.bedsidemonitor.Main] entering testOnePatientCallButtonPressedEvent");
		m.assignPatient("Philip", "Thomas", "Rodriguez");
		m.raisePatientCallButtonEvent(new PatientCallButtonEvent(true));
		System.out.println("[hms.bedsidemonitor.Main] exiting testOnePatientCallButtonPressedEvent");
	}
	
	private static void testOnePatientCallButtonResetEvent(Monitor m) 
			throws RemoteException {
		System.out.println("[hms.bedsidemonitor.Main] entering testOnePatientCallButtonResetEvent");
		m.assignPatient("Philip", "Thomas", "Rodriguez");
		m.raisePatientCallButtonEvent(new PatientCallButtonEvent(false));
		System.out.println("[hms.bedsidemonitor.Main] exiting testOnePatientCallButtonResetEvent");
	}
	
	private static void testOnePatientInformationChangedEvent(Monitor m) 
			throws RemoteException {
		System.out.println("[hms.bedsidemonitor.Main] entering testOnePatientInformationChangedEvent");
		m.raisePatientInformationChangedEvent(new PatientInformationChangedEvent());
		System.out.println("[hms.bedsidemonitor.Main] exiting testOnePatientInformationChangedEvent");
	}

}
