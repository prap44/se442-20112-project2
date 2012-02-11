
public class Sensor {
	private String name;
	private double scalar;
	private double offset;
	
	public Sensor(String name, double scalar, double offset) {
		this.name = name;
		this.scalar = scalar;
		this.offset = offset;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setScalar(double scalar) {
		this.scalar = scalar;
	}
	
	public double getScalar() {
		return this.scalar;
	}
	
	public void setOffset(double offset) {
		this.offset = offset;
	}
	
	public double getOffset() {
		return this.offset;
	}
	
	public int convert(double value) {
		return (int)Math.round(value * this.scalar + this.offset);
	}
}
