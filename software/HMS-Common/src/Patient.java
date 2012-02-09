import java.rmi.Remote;

public interface Patient extends Remote {
	public String getPatientFirstName();
	public String getPatientMiddleName();
	public String getPatientLastName();
	public void setPatientFirstName(String name);
	public void setPatientMiddleName(String name);
	public void setPatientLastName(String name);
}
