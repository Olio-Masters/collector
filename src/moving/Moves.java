package moving;

import beacons.Beacons;
import colour.Colour;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import soundInterface.Head;

/**
 * Class Moves is an extension of Thread. It moves the robot according to
 * Beacons, Head and Colour classes utilizing the run() method.
 */
public class Moves extends Thread {

	private EV3LargeRegulatedMotor motorRightA;
	private EV3LargeRegulatedMotor motorLeftB;
	private EV3LargeRegulatedMotor motorGrabC;
	private Colour color;
	private Head tunes;
	private Beacons beacon;
	private int mode;

	/**
	 * Gets information from the Beacons class and uses it in the run() method.
	 * @param beacons
	 */
	public Moves(Beacons beacons, Head tune, Colour colors) {
		this.motorRightA = new EV3LargeRegulatedMotor(MotorPort.A);
		this.motorLeftB = new EV3LargeRegulatedMotor(MotorPort.B);
		this.motorGrabC = new EV3LargeRegulatedMotor(MotorPort.C);

		this.beacon = beacons;
		this.tunes = tune;
		this.color = colors;
		this.mode = 1;
	}

	/**
	 * Don't call this method. Instead call method "start()"
	 * 
	 * This method runs the main functions of the robot
	 */
	public void run() {

		try {
			motorRightA.setSpeed(500);
			motorLeftB.setSpeed(500);
			motorGrabC.setSpeed(100);

			motorGrabC.backward(); // Initialize the robot grab and set it to the right altitude.
			Delay.msDelay(280);
			motorGrabC.setSpeed(0);

			color.colorturn();

			while (mode == 1) {
				searchBeacon();
			}

			while (mode == 2) {
				dropCargo();
			}
			if (mode == 3) {
				endGreeting();
			}

		} catch (Exception e) {
		}
	}

	/**
	 * Mode 1 of the robot. It searches the beacons and acts upon finding any. When
	 * it finds and closes in on the target, it grabs the target up in its grabbing
	 * device.
	 */
	public void searchBeacon() {

		while (Button.ESCAPE.isDown()) {
			break;
		}

		LCD.drawInt(beacon.angle(), 0, 1); // print direction on screen.
		LCD.drawInt(beacon.distance(), 0, 2); // print distance on screen.
		LCD.drawInt(color.colorturn(), 0, 3);

		Delay.msDelay(1);

		while (beacon.angle() == 0 && beacon.distance() > 90) { // AGGRESSIVE SEARCH MODE

			LCD.drawInt(beacon.angle(), 0, 1); // print direction on screen.
			LCD.drawInt(beacon.distance(), 0, 2); // print distance on screen.

			if (Button.ESCAPE.isDown()) {
				break;
			}

			motorRightA.setSpeed(300);
			motorLeftB.setSpeed(300);

			motorRightA.backward();
			motorLeftB.forward();

			Delay.msDelay(3500);

			motorRightA.backward();
			motorLeftB.backward();

			Delay.msDelay(300);
		}

		if (beacon.angle() > 2) { // when beacon is seen to the right

			motorRightA.setSpeed(100);
			motorLeftB.setSpeed(100);

			motorRightA.backward();
			motorLeftB.forward();
			tunes.search();
		}

		if (beacon.angle() < -2) { // when beacon is seen to the left

			motorRightA.setSpeed(100);
			motorLeftB.setSpeed(100);

			motorLeftB.backward();
			motorRightA.forward();
			tunes.search(); // play search tune
		}

		if (beacon.angle() > -2 && beacon.angle() < 2) { // when beacon is in front of robot
			motorRightA.setSpeed(100);
			motorLeftB.setSpeed(100);

			motorRightA.backward();
			motorLeftB.backward();
			tunes.target();

			if (beacon.distance() < 25 && beacon.distance() > 4) { // when beacon is near the sensor

				motorRightA.setSpeed(0);
				motorLeftB.setSpeed(0);
				motorGrabC.setSpeed(100);

				motorGrabC.backward(); // grab the beacon
				tunes.pickup(); // play pickup tune
				Delay.msDelay(100);

				motorGrabC.setSpeed(0);
				tunes.success(); // play the success tune
				LCD.clear();
				mode++; // changes to mode 2 (dropCargo)
			}
		}
	}

	/**
	 * Mode 2 of the robot. Main purpose is to get out of the zone and drop cargo
	 * out.
	 */
	public void dropCargo() { // mode 2

		motorRightA.setSpeed(150);
		motorLeftB.setSpeed(150);

		motorLeftB.backward();
		motorRightA.backward();

		while (Button.ESCAPE.isDown()) {
			break;
		}

		if (color.colorturn() == 3) {

			LCD.drawString("Lasketaan kuorma", 0, 5);
			motorRightA.setSpeed(0);
			motorLeftB.setSpeed(0);
			motorGrabC.setSpeed(200);
			motorGrabC.forward();

			Delay.msDelay(300);

			motorRightA.setSpeed(300);
			motorLeftB.setSpeed(300);
			motorRightA.forward();
			motorLeftB.forward();

			Delay.msDelay(2000);

			motorRightA.close();
			motorLeftB.close();
			motorGrabC.close();
			LCD.clear();
			mode++; // Changes to mode 3 (endGreeting)
		}
	}

	/**
	 * Mode 3 of the robot. Does end greetings and ends the program
	 */
	public void endGreeting() { // mode 3

		while (Button.ESCAPE.isDown()) {
			break;
		}
		LCD.clear();
		Delay.msDelay(10);
		LCD.drawString("Tavara palautettu", 0, 1);
		LCD.drawString("Kiitos etta valitsit KR2000", 0, 2);
		LCD.drawString("Tervetuloa uudelleen!", 0, 3);
		LCD.drawString("", 0, 4);
		LCD.drawString("Terveisin:", 0, 5);
		LCD.drawString("-- Olio Masters --", 0, 6);
		Delay.msDelay(5000);
	}
}
