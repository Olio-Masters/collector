package beacons;

import lejos.hardware.Button;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public class Beacons extends Thread {
	/**
	 * Class Beacon is an extension of Thread. The point is to search beacons
	 * constantly utilizing run() method.
	 * 
	 */

	private EV3IRSensor irsensor1; 
	private String draw;
	private SampleProvider position;
	private float[] seekSample;
	private int beaconInfoH;
	private int beaconInfoD;

	public Beacons() {
		this.irsensor1 = new EV3IRSensor(SensorPort.S4);
		this.draw = "";	// Empty String for the adding of samples
		this.position = irsensor1.getSeekMode();
		this.seekSample = new float[position.sampleSize()];	// Array to store samples in temporarely.
		this.beaconInfoD = 0;	// sample for distance
		this.beaconInfoH = 0;	// sample for direction
	}

	
	/**
	 * Don't call this method. Instead call method "start()"
	 * 
	 * This method periodically primes the various variables in
	 * this class. To use the variables, call the other methods in this class.
	 */
	@Override
	public void run() {
		try {
			while(Button.ESCAPE.isUp()) {
			this.position = irsensor1.getSeekMode();
			
			position.fetchSample(seekSample, 0);

			beaconInfoH = (int) seekSample[2]; // sample for direction
			beaconInfoD = (int) seekSample[3]; // sample for distance
			
			this.draw = "" + seekSample[0];		//Add samples to string
			Thread.sleep(20);
			}
		} catch(Exception e) {
		}
	}

	/**
	 * SensorMode method getSensorMode()
	 * 
	 * @return returns the position of a beacon as a SensorMode variable.
	 */
	public SensorMode getSensorMode() {
		return (SensorMode) position;
	}
	
	/**
	 * This method returns the distance of the beacon in relation to the infraredsensor
	 * in the robot.
	 * 
	 * @return returns int variable of the distance of the beacon.
	 */
	public int distance() {
		return beaconInfoD;
	}
	
	/**
	 * This method returns the angle of the beacon in relation to the infraredsensor
	 * in the robot.
	 * 
	 * @return returns int variable of the angle of the beacon.
	 */
	public int angle() {
		return beaconInfoH;
	}

	/**
	 * toString()
	 * 
	 * @return returns a String of the SensorMode variables to be printed.
	 */
	@Override
	public String toString() {
		return draw;
	}

}