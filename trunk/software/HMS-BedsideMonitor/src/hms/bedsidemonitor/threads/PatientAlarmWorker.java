package hms.bedsidemonitor.threads;

import hms.common.Monitor;
import hms.common.events.PatientAlarmEvent;
import hms.common.listeners.PatientAlarmListener;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PatientAlarmWorker implements Runnable {

	private BlockingQueue<PatientAlarmEvent> eventQueue = 
		new LinkedBlockingQueue<PatientAlarmEvent>();
	
	private Monitor monitor;
	
	public PatientAlarmWorker(Monitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public void run() {
		try {
			while (true) {
				PatientAlarmEvent head = this.eventQueue.take();
				if (this.monitor != null && 
						this.monitor.getEventListenerList() != null) {
					for (Iterator<PatientAlarmListener> it = 
						Arrays.asList(this.monitor.getEventListenerList().
								getListeners(PatientAlarmListener.class)).
								iterator(); it.hasNext();) {
						PatientAlarmListener l = it.next();
						l.patientAlarmReceived(head);
						
						System.out.println("[PatientAlarmWorker] " +
							"patientAlarmReceived");
					}
				}
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (RemoteException re) {
			re.printStackTrace();
		}
	}
	
	public void addPatientAlarmEvent(PatientAlarmEvent event) {
		this.eventQueue.add(event);
	}

}
