package hms.common;

import java.util.Random;

public class Sensor {
	private String name;
	private double scalar;
	private double offset;
	private Random randomNumber;
	private boolean spikeFlag;
	private int currentValue;
	private int varianceCount;
	private final int sensorInitialValue;
	
	public Sensor(String name, double scalar, double offset, int sensorInitial) {
		this.name = name;
		this.scalar = scalar;
		this.offset = offset;
		this.randomNumber = new Random();
		this.currentValue = sensorInitial;
		this.sensorInitialValue = sensorInitial;
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
	
	public int getCurrentValue(){
		return this.currentValue;
	}
	public void setCurrentValue(int value){
		this.currentValue = value;
	}
	public boolean getSpikeFlag(){
		return this.spikeFlag;
	}
	public void setSpikeFlag(boolean flag){
		this.spikeFlag = flag;
	}
	
	public int getSensorInitialValue(){
		return this.sensorInitialValue;
	}
	 
	public int convert(double value) {
		return (int)Math.round(value * this.scalar + this.offset);
	}
	
	private double vitalChange(){
		double rawData;
		double convert = 100;
		spikeCheck();
		if( randomNumber.nextInt()%17 == 0){
			this.vitalSpike();
			//System.out.println("Vital: " + name + " spiked");
		}
		if (this.getSpikeFlag() == false){
			if(randomNumber.nextInt()%2 == 0){
				currentValue = currentValue + (randomNumber.nextInt(3));
				varianceCount = 0;
			}
			else{
				currentValue = currentValue - (randomNumber.nextInt(3));
				varianceCount = 0;
			}
		}
		else{
			if(randomNumber.nextInt()%2 == 0){
				currentValue = currentValue + (randomNumber.nextInt(2)+randomNumber.nextInt(6));
				varianceCount++;
			}
			else{
				currentValue = currentValue - (randomNumber.nextInt(2)+randomNumber.nextInt(6));
				varianceCount++;
			
			}
		}
			
			this.setSpikeFlag(false);
			rawData = this.getCurrentValue()/convert;
			System.out.println("VitalSign " +  this.getName() + " raw data read: " +  rawData);
			return rawData;
	}
	private void vitalSpike(){
		if(currentValue < sensorInitialValue){
			currentValue = currentValue - 9;
		}
		else{
			currentValue = currentValue + 9;
		}
	}
	
	private void spikeCheck(){
		if(varianceCount == 10){
			this.setCurrentValue(this.getSensorInitialValue());
			varianceCount = 0;
		}
		else if( currentValue <= this.getSensorInitialValue()-10 || currentValue >= this.getSensorInitialValue()+10){
			this.setSpikeFlag(true);
		}
	}
	public static void main(String[] args){
		Runnable r = new Runnable(){
			public void run(){
				Sensor dummy = new Sensor("Blood Pressure",0.5,1,50);
				while (true) {
					dummy.vitalChange();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		};
		Thread t = new Thread(r);
		t.run();
	}
}
