package hms.bedsidemonitor.threads;

import hms.common.Monitor;
import hms.common.events.PatientDataEvent;
import hms.common.listeners.PatientDataListener;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PatientDataWorker implements Runnable {
	
	private BlockingQueue<PatientDataEvent> eventQueue = 
		new LinkedBlockingQueue<PatientDataEvent>();
	
	private Monitor monitor;
	
	public PatientDataWorker(Monitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public void run() {
		try {
			while (true) {
				PatientDataEvent head = this.eventQueue.take();
				if (this.monitor != null && 
						this.monitor.getEventListenerList() != null) {
					for (Iterator<PatientDataListener> it = 
						Arrays.asList(this.monitor.getEventListenerList().
								getListeners(PatientDataListener.class)).
								iterator(); it.hasNext();) {
						PatientDataListener l = it.next();
						l.patientDataReceived(head);
						
						System.out.println("[PatientDataWorker] " +
							"patientDataReceived");
					}
				}
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (RemoteException re) {
			re.printStackTrace();
		}
	}
	
	public void addPatientDataEvent(PatientDataEvent event) {
		this.eventQueue.add(event);
	}

}
