package beacons;

import lejos.hardware.lcd.Font;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;

public class Beacons extends Thread {
	/**
	 * Class Beacon is an extension of Thread. 
	 * The point is to search beacons constantly utilizing run() method.
	 * 
	 */
	


	private final EV3IRSensor irsensor1;
	private SensorMode position;
	private String draw = "";
	private float[] seekSample = new float[position.sampleSize()];
	
	public Beacons(){
		this.irsensor1 = new EV3IRSensor(SensorPort.S4);
		this.position = irsensor1.getSeekMode();
	}
	
	@Override
	public void run() {
		try {
			this.position = irsensor1.getSeekMode();
			irsensor1.fetchSample(seekSample, 0);
			this.draw = "" + seekSample[0];
			
			Thread.sleep(10);
		} catch(Exception e) {
		}
	}
	
	/**
	 * SensorMode method getSensorMode()
	 * @return returns the position of a beacon as a SensorMode variable.
	 */
	public SensorMode getSensorMode() {
		return position;
	}
	
	/**
	 * toString() 
	 * @return returns a String of the SensorMode variables to be printed.
	 */
	@Override
	public String toString() {
		return draw;
	}

}