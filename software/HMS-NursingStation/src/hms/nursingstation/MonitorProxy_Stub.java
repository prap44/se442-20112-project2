// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

package hms.nursingstation;

public final class MonitorProxy_Stub
    extends java.rmi.server.RemoteStub
    implements hms.common.listeners.PatientDataListener, hms.common.listeners.PatientAlarmListener, hms.common.listeners.PatientCallButtonListener, hms.common.listeners.PatientInformationChangedListener, hms.common.listeners.MonitorShutdownListener, java.rmi.Remote
{
    private static final long serialVersionUID = 2;
    
    private static java.lang.reflect.Method $method_monitorShuttingDown_0;
    private static java.lang.reflect.Method $method_patientAlarmReceived_1;
    private static java.lang.reflect.Method $method_patientCallButtonPressed_2;
    private static java.lang.reflect.Method $method_patientDataReceived_3;
    private static java.lang.reflect.Method $method_patientInformationChanged_4;
    
    static {
	try {
	    $method_monitorShuttingDown_0 = hms.common.listeners.MonitorShutdownListener.class.getMethod("monitorShuttingDown", new java.lang.Class[] {hms.common.events.MonitorShutdownEvent.class});
	    $method_patientAlarmReceived_1 = hms.common.listeners.PatientAlarmListener.class.getMethod("patientAlarmReceived", new java.lang.Class[] {hms.common.events.PatientAlarmEvent.class});
	    $method_patientCallButtonPressed_2 = hms.common.listeners.PatientCallButtonListener.class.getMethod("patientCallButtonPressed", new java.lang.Class[] {hms.common.events.PatientCallButtonEvent.class});
	    $method_patientDataReceived_3 = hms.common.listeners.PatientDataListener.class.getMethod("patientDataReceived", new java.lang.Class[] {hms.common.events.PatientDataEvent.class});
	    $method_patientInformationChanged_4 = hms.common.listeners.PatientInformationChangedListener.class.getMethod("patientInformationChanged", new java.lang.Class[] {hms.common.events.PatientInformationChangedEvent.class});
	} catch (java.lang.NoSuchMethodException e) {
	    throw new java.lang.NoSuchMethodError(
		"stub class initialization failed");
	}
    }
    
    // constructors
    public MonitorProxy_Stub(java.rmi.server.RemoteRef ref) {
	super(ref);
    }
    
    // methods from remote interfaces
    
    // implementation of monitorShuttingDown(MonitorShutdownEvent)
    public void monitorShuttingDown(hms.common.events.MonitorShutdownEvent $param_MonitorShutdownEvent_1)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_monitorShuttingDown_0, new java.lang.Object[] {$param_MonitorShutdownEvent_1}, -8821289541870119932L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of patientAlarmReceived(PatientAlarmEvent)
    public void patientAlarmReceived(hms.common.events.PatientAlarmEvent $param_PatientAlarmEvent_1)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_patientAlarmReceived_1, new java.lang.Object[] {$param_PatientAlarmEvent_1}, -9182850798736339500L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of patientCallButtonPressed(PatientCallButtonEvent)
    public void patientCallButtonPressed(hms.common.events.PatientCallButtonEvent $param_PatientCallButtonEvent_1)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_patientCallButtonPressed_2, new java.lang.Object[] {$param_PatientCallButtonEvent_1}, -3047350365391077957L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of patientDataReceived(PatientDataEvent)
    public void patientDataReceived(hms.common.events.PatientDataEvent $param_PatientDataEvent_1)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_patientDataReceived_3, new java.lang.Object[] {$param_PatientDataEvent_1}, 2313825473522175155L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of patientInformationChanged(PatientInformationChangedEvent)
    public void patientInformationChanged(hms.common.events.PatientInformationChangedEvent $param_PatientInformationChangedEvent_1)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_patientInformationChanged_4, new java.lang.Object[] {$param_PatientInformationChangedEvent_1}, 3858580490811947910L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
}
