package hms.bedsidemonitor;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

import javax.swing.event.EventListenerList;

import hms.bedsidemonitor.threads.PatientAlarmWorker;
import hms.bedsidemonitor.threads.PatientCallButtonWorker;
import hms.bedsidemonitor.threads.PatientDataWorker;
import hms.bedsidemonitor.threads.PatientInformationChangedWorker;
import hms.common.Monitor;
import hms.common.Patient;
import hms.common.Sensor;
import hms.common.events.MonitorShutdownEvent;
import hms.common.events.PatientAlarmEvent;
import hms.common.events.PatientCallButtonEvent;
import hms.common.events.PatientDataEvent;
import hms.common.events.PatientInformationChangedEvent;
import hms.common.listeners.MonitorShutdownListener;
import hms.common.listeners.PatientAlarmListener;
import hms.common.listeners.PatientCallButtonListener;
import hms.common.listeners.PatientDataListener;
import hms.common.listeners.PatientInformationChangedListener;

public class MonitorImpl implements Monitor {
	
	private final int SENSOR_POLL_INTERVAL_MS = 250;

	private String id = null;
	private EventListenerList listenerList = new EventListenerList();
	private Patient patient = null;
	private List<Sensor> sensors = new ArrayList<Sensor>();

	private Timer sensorPollingTimer = new Timer(true);
	private TimerTask sensorPollingTimerTask = new TimerTask() {
		@Override
		public void run() {
			if (MonitorImpl.this.patient != null
					&& MonitorImpl.this.sensors != null
					&& !MonitorImpl.this.sensors.isEmpty()) {
				Map<String, Integer> data = new HashMap<String, Integer>();
				for (Sensor s : MonitorImpl.this.sensors) {
					s.vitalChange();
					data.put(s.getName(), s.convert(s.getCurrentValue()));

					if (s.exceedHighLimit() || s.exceedsLowLimit()) {
						if (!s.getAlarmState()) {
							s.setAlarmState(true);
							try {
								raisePatientAlarmEvent(new PatientAlarmEvent(
										s.getName(), true));
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}

				try {
					MonitorImpl.this
							.raisePatientDataEvent(new PatientDataEvent(data));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};

	private PatientAlarmWorker paWorker;
	private PatientCallButtonWorker pcbWorker;
	private PatientDataWorker pdWorker;
	private PatientInformationChangedWorker picWorker;

	public MonitorImpl() {
		this.sensorPollingTimer.scheduleAtFixedRate(
				this.sensorPollingTimerTask, 0, SENSOR_POLL_INTERVAL_MS);
		this.initializeWorkerThreads();
		this.executeWorkerThreads();
	}
	
	public void bind(String id) throws MalformedURLException, RemoteException, AlreadyBoundException {
		Naming.bind(id, this);
		this.id = id;
	}
	
	public void rebind(String id) throws RemoteException, MalformedURLException {
		Naming.rebind(id, this);
		this.id = id;
	}

	public void unbind() throws RemoteException, MalformedURLException,
			NotBoundException {
		if (this.id != null) {
			/*
			 * Notifies the clients that the server is shutting down and waits
			 * for them to perform their cleanup operations before disconnecting
			 */
			this.raiseMonitorShutdownEvent(new MonitorShutdownEvent());
			Naming.unbind(this.id);
			this.id = null;
		}
	}
	
	public String getID() {
		return this.id;
	}

	public List<Sensor> getSensorList() {
		return this.sensors;
	}

	@Override
	public Patient getPatient() throws RemoteException {
		return this.patient;
	}

	public void setPatient(Patient patient) throws RemoteException {
		this.patient = patient;
	}

	@Override
	public void assignPatient(String firstName, String middleName,
			String lastName) throws RemoteException {
		if (this.patient == null) {
			this.patient = new PatientImpl();
		}
		this.patient.setPatientFirstName(firstName);
		this.patient.setPatientMiddleName(middleName);
		this.patient.setPatientLastName(lastName);
	}

	@Override
	public void unsassignPatient() throws RemoteException {
		this.patient = null;
	}

	@Override
	public void addPatientAlarmListener(PatientAlarmListener listener)
			throws RemoteException {
		this.listenerList.add(PatientAlarmListener.class, listener);
	}

	@Override
	public void addPatientCallButtonListener(PatientCallButtonListener listener)
			throws RemoteException {
		this.listenerList.add(PatientCallButtonListener.class, listener);
	}

	@Override
	public void addPatientDataListener(PatientDataListener listener)
			throws RemoteException {
		this.listenerList.add(PatientDataListener.class, listener);
	}

	@Override
	public void addPatientInformationChangedListener(
			PatientInformationChangedListener listener) throws RemoteException {
		this.listenerList
				.add(PatientInformationChangedListener.class, listener);
	}
	
	@Override
	public void addMonitorShutdownListener(MonitorShutdownListener listener)
			throws RemoteException {
		this.listenerList.add(MonitorShutdownListener.class, listener);
	}

	@Override
	public void removePatientAlarmListener(PatientAlarmListener listener)
			throws RemoteException {
		this.listenerList.remove(PatientAlarmListener.class, listener);
	}

	@Override
	public void removePatientCallButtonListener(
			PatientCallButtonListener listener) throws RemoteException {
		this.listenerList.remove(PatientCallButtonListener.class, listener);
	}

	@Override
	public void removePatientDataListener(PatientDataListener listener)
			throws RemoteException {
		this.listenerList.remove(PatientDataListener.class, listener);
	}

	@Override
	public void removePatientInformationChangedListener(
			PatientInformationChangedListener listener) throws RemoteException {
		this.listenerList.remove(PatientInformationChangedListener.class,
				listener);
	}
	
	@Override
	public void removeMonitorShutdownListener(MonitorShutdownListener listener)
			throws RemoteException {
		this.listenerList.remove(MonitorShutdownListener.class, listener);
	}

	@Override
	public EventListenerList getEventListenerList() throws RemoteException {
		return this.listenerList;
	}

	public void raisePatientAlarmEvent(PatientAlarmEvent event)
			throws RemoteException {
		this.paWorker.addPatientAlarmEvent(event);
	}

	public void raisePatientCallButtonEvent(final PatientCallButtonEvent event)
			throws RemoteException {
		this.pcbWorker.addPatientCallButtonEvent(event);
	}

	@Override
	public void raisePatientDataEvent(PatientDataEvent event)
			throws RemoteException {
		this.pdWorker.addPatientDataEvent(event);
	}

	@Override
	public void raisePatientInformationChangedEvent(
			PatientInformationChangedEvent event) throws RemoteException {
		this.picWorker.addPatientInformationChangedEvent(event);
	}
	
	@Override
	public void raiseMonitorShutdownEvent(MonitorShutdownEvent event)
			throws RemoteException {
		/* Don't use a thread here for now, we want this to be a synchronous operation */
		for(MonitorShutdownListener l : this.listenerList.getListeners(MonitorShutdownListener.class)) {
			l.monitorShuttingDown(event);
		}
	}
	
	private void initializeWorkerThreads() {
		this.paWorker = new PatientAlarmWorker(this);
		this.pcbWorker = new PatientCallButtonWorker(this);
		this.pdWorker = new PatientDataWorker(this);
		this.picWorker = new PatientInformationChangedWorker(this);
	}
	
	private void executeWorkerThreads() {
		Executor monitorExecutor = new Executor() {
			
			@Override
			public void execute(Runnable command) {
				Thread t = new Thread(command);
				t.setDaemon(true);
				t.start();
			}
			
		};
		
		monitorExecutor.execute(paWorker);
		monitorExecutor.execute(pcbWorker);
		monitorExecutor.execute(pdWorker);
		monitorExecutor.execute(picWorker);
	}
}
