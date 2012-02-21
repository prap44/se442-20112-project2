package hms.bedsidemonitor.threads;

import hms.common.Monitor;
import hms.common.events.PatientInformationChangedEvent;
import hms.common.listeners.PatientInformationChangedListener;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PatientInformationChangedWorker implements Runnable {

	private BlockingQueue<PatientInformationChangedEvent> eventQueue = 
		new LinkedBlockingQueue<PatientInformationChangedEvent>();
	
	private Monitor monitor;
	
	public PatientInformationChangedWorker(Monitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public void run() {
		try {
			while (true) {
				PatientInformationChangedEvent head = this.eventQueue.take();
				if (this.monitor != null && 
						this.monitor.getEventListenerList() != null) {
					for (Iterator<PatientInformationChangedListener> it = 
						Arrays.asList(this.monitor.getEventListenerList().
								getListeners(PatientInformationChangedListener.class)).
								iterator(); it.hasNext();) {
						PatientInformationChangedListener l = it.next();
						l.patientInformationChanged(head);
						
						System.out.println("[PatientInformationChangedWorker] " +
							"patientInformationChanged");
					}
				}
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (RemoteException re) {
			re.printStackTrace();
		}
	}
	
	public void addPatientInformationChangedEvent(PatientInformationChangedEvent event) {
		this.eventQueue.add(event);
	}

}
