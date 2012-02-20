package hms.nursingstation;

import hms.common.Patient;
import hms.nursingstation.events.MonitorStatusChangedEvent;
import hms.nursingstation.events.MonitorStatusChangedEvent.MonitorChangedOperation;
import hms.nursingstation.listeners.MonitorStatusChangedListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

public class NursingStationImpl {

	private ArrayList<Patient> patients = new ArrayList<Patient>();
	private ArrayList<MonitorProxy> bedsideStations = new ArrayList<MonitorProxy>();
	private EventListenerList listenerList = new EventListenerList();

	public boolean addPatient(Patient patient) {
		return this.patients.add(patient);
	}

	public Patient getPatient(int index) {
		return this.patients.get(index);
	}

	public Patient removePatient(int index) {
		return this.patients.remove(index);
	}

	public boolean removePatient(Patient patient) {
		return this.patients.remove(patient);
	}

	public int getPatientCount() {
		return this.patients.size();
	}

	public List<Patient> getPatientList() {
		return this.patients;
	}

	public boolean addMonitor(MonitorProxy monitor) {
		this.raiseMonitorStatusChangedEvent(new MonitorStatusChangedEvent(
				monitor, MonitorChangedOperation.ADDED));
		return this.bedsideStations.add(monitor);
	}

	public MonitorProxy getMonitor(int index) {
		return this.bedsideStations.get(index);
	}

	public MonitorProxy removeMonitor(int index) {
		MonitorProxy monitor = this.bedsideStations.remove(index);
		this.raiseMonitorStatusChangedEvent(new MonitorStatusChangedEvent(
				monitor, MonitorChangedOperation.REMOVED));
		return monitor;
	}

	public boolean removeMonitor(MonitorProxy monitor) {
		boolean removed = this.bedsideStations.remove(monitor);
		if (removed) {
			this.raiseMonitorStatusChangedEvent(new MonitorStatusChangedEvent(
					monitor, MonitorChangedOperation.REMOVED));
		}
		return removed;
	}

	public int getMonitorCount() {
		return this.bedsideStations.size();
	}

	public boolean containsMonitor(MonitorProxy monitor) {
		return this.bedsideStations.contains(monitor);
	}

	public void addMonitorStatusChangedListener(
			MonitorStatusChangedListener listener) {
		this.listenerList.add(MonitorStatusChangedListener.class, listener);
	}

	public void removeMonitorStatusChangedListener(
			MonitorStatusChangedListener listener) {
		this.listenerList.remove(MonitorStatusChangedListener.class, listener);
	}

	private void raiseMonitorStatusChangedEvent(MonitorStatusChangedEvent event) {
		for (MonitorStatusChangedListener l : this.listenerList
				.getListeners(MonitorStatusChangedListener.class)) {
			l.monitorStatusChanged(event);
		}
	}

}