package hms.bedsidemonitor.threads;

import hms.common.Monitor;
import hms.common.events.PatientCallButtonEvent;
import hms.common.listeners.PatientCallButtonListener;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PatientCallButtonWorker implements Runnable {
	
	private BlockingQueue<PatientCallButtonEvent> eventQueue = 
		new LinkedBlockingQueue<PatientCallButtonEvent>();
	
	private Monitor monitor;
	
	public PatientCallButtonWorker(Monitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public void run() {
		try {
			while (true) {
				PatientCallButtonEvent head = this.eventQueue.take();
				if (this.monitor != null && 
						this.monitor.getEventListenerList() != null) {
					for (Iterator<PatientCallButtonListener> it = 
						Arrays.asList(this.monitor.getEventListenerList().
								getListeners(PatientCallButtonListener.class)).
								iterator(); it.hasNext();) {
						PatientCallButtonListener l = it.next();
						l.patientCallButtonPressed(head);

						System.out.println("[PatientCallButtonWorker] " +
							"patientCallButtonPressed");
					}
				}
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (RemoteException re) {
			re.printStackTrace();
		}
	}
	
	public void addPatientCallButtonEvent(PatientCallButtonEvent event) {
		this.eventQueue.add(event);
	}
	
}
