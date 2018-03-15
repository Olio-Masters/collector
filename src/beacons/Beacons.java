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

	private EV3IRSensor infraredSensor; 
	private String draw;
	private SampleProvider position;
	private float[] seekSample;
	private int beaconAngle;
	private int beaconDistance;

	public Beacons() {
		this.infraredSensor = new EV3IRSensor(SensorPort.S4);
		this.draw = "";
		this.position = infraredSensor.getSeekMode();
		this.seekSample = new float[position.sampleSize()];
		this.beaconDistance = 0;
		this.beaconAngle = 0;
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
			this.position = infraredSensor.getSeekMode();
			
			position.fetchSample(seekSample, 0);

			beaconAngle = (int) seekSample[2]; // sample for beacon angle
			beaconDistance = (int) seekSample[3]; // sample for beacon distance
			
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
		return beaconDistance;
	}
	
	/**
	 * This method returns the angle of the beacon in relation to the infraredsensor
	 * in the robot.
	 * 
	 * @return returns int variable of the angle of the beacon.
	 */
	public int angle() {
		return beaconAngle;
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