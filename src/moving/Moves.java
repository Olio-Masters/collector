package moving;

import beacons.Beacons;
import lejos.hardware.Button;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.LCD;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;
import soundInterface.Head;
import lejos.robotics.SampleProvider;


public class Moves extends Thread {
/**
 * Class Moves is an extension of Thread. 
 * It moves the robot according to Beacons class utilizing the run() method.
 */
	private EV3LargeRegulatedMotor largeMotora;
	private EV3LargeRegulatedMotor largeMotorb;
	private EV3LargeRegulatedMotor largeMotorc;

	
	private Head tunes;
	private Beacons beacon;


	/**
	 * @param beacons
	 * Gets information from the Beacons class and uses it in the run() method..
	 */
	public Moves(Beacons beacons, Head tune) {
		this.largeMotora = new EV3LargeRegulatedMotor(MotorPort.A);
		this.largeMotorb = new EV3LargeRegulatedMotor(MotorPort.B);
		this.largeMotorc = new EV3LargeRegulatedMotor(MotorPort.C);
		this.beacon = beacons;
		this.tunes = tune;	
	}

	/**
	 * Don't call this method. Instead call method "start()"
	 * 
	 * This method runs the main functions of the robot
	 */
	public void run() {

		try {
			largeMotora.setSpeed(500);
			largeMotorb.setSpeed(500);
			largeMotorc.setSpeed(100);

			largeMotorc.backward();
			Delay.msDelay(280);
			largeMotorc.setSpeed(0);

			while (Button.ESCAPE.isUp()) {

				LCD.drawInt(beacon.angle(), 0, 1); // print direction on screen
				LCD.drawInt(beacon.distance(), 0, 2); // print distance on screen

				Delay.msDelay(1);

				if (beacon.angle() > 2) { // when beacon is seen to the right

					largeMotora.setSpeed(100);
					largeMotorb.setSpeed(100);

					largeMotora.backward();
					largeMotorb.forward();
					
					tunes.search();
					LCD.clear();
					LCD.drawString("oikealle", 0, 4);

				}

				if (beacon.angle() < -2) { // when beacon is seen to the left

					largeMotora.setSpeed(100);
					largeMotorb.setSpeed(100);

					largeMotorb.backward();
					largeMotora.forward();
//					tunes.search();

					LCD.clear();
					LCD.drawString("vasemmalle", 0, 4);
				}

				if (beacon.angle() > -2 && beacon.angle() < 2) { // when beacon is in front of robot
					largeMotora.setSpeed(100);
					largeMotorb.setSpeed(100);

					largeMotora.backward();
					largeMotorb.backward();

					LCD.clear();
					LCD.drawString("eteen", 0, 4);

					if (beacon.distance() < 18 && beacon.distance() > 4) {

						largeMotora.setSpeed(50);
						largeMotorb.setSpeed(50);
						largeMotorc.setSpeed(100);
						largeMotorc.backward();
						Delay.msDelay(800);

						largeMotora.close();
						largeMotorb.close();
						largeMotorc.close();
						break;
					}
				}
			}
		} catch (Exception e) {
		}
	}
}