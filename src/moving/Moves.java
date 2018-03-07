package moving;

import beacons.Beacons;
import colour.Colour;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;
import soundInterface.Head;


public class Moves extends Thread {
/**
 * Class Moves is an extension of Thread. 
 * It moves the robot according to Beacons class utilizing the run() method.
 */
	private EV3LargeRegulatedMotor oikeaA;
	private EV3LargeRegulatedMotor largeMotorb;
	private EV3LargeRegulatedMotor kauhaC;

	private Colour color;
	private Head tunes;
	private Beacons beacon;
	private int mode;

	/**
	 * @param beacons
	 * Gets information from the Beacons class and uses it in the run() method.
	 */
	public Moves(Beacons beacons, Head tune, Colour colors) {
		this.oikeaA = new EV3LargeRegulatedMotor(MotorPort.A);
		this.largeMotorb = new EV3LargeRegulatedMotor(MotorPort.B);
		this.kauhaC = new EV3LargeRegulatedMotor(MotorPort.C);
		this.beacon = beacons;
		this.tunes = tune;	
		this.color = colors;
		this.mode = 0;
	}

	/**
	 * Don't call this method. Instead call method "start()"
	 * 
	 * This method runs the main functions of the robot
	 */
	public void run() {

		try {
			
			oikeaA.setSpeed(500);
			largeMotorb.setSpeed(500);
			kauhaC.setSpeed(100);

			kauhaC.backward(); // Initialize the robot grab and set it to the right altitude.
			Delay.msDelay(280);
			kauhaC.setSpeed(0);
			
			color.colorturn();

			while (mode == 0) {
				searchBeacon();
			}
			
			while (mode == 1) {
				dropCargo();
			}
			if (mode == 2) {
				endGreeting();
			}
			
		} catch (Exception e) {
		}
	}
	
	public void endGreeting() {
		
			LCD.clear();
			LCD.drawString("Tavara palautettu", 0, 1);
			LCD.drawString("Kiitos etta valitsit KR2000", 0, 2);
			LCD.drawString("Tervetuloa uudelleen!", 0, 3);
			LCD.drawString("", 0, 4);
			LCD.drawString("Terveisin:", 0, 5);
			LCD.drawString("-- Olio Masters --", 0, 6);
			Delay.msDelay(5000);
	}
	public void searchBeacon(){
		LCD.drawInt(beacon.angle(), 0, 1); // print direction on screen.
		LCD.drawInt(beacon.distance(), 0, 2); // print distance on screen.
		LCD.drawInt(color.colorturn(), 0, 3);

		Delay.msDelay(1);

		if (beacon.angle() > 2) { // when beacon is seen to the right

			oikeaA.setSpeed(100);
			largeMotorb.setSpeed(100);

			oikeaA.backward();
			largeMotorb.forward();
			tunes.search();
			
			LCD.clear();
			LCD.drawString("oikealle", 0, 4);

		}

		if (beacon.angle() < -2) { // when beacon is seen to the left

			oikeaA.setSpeed(100);
			largeMotorb.setSpeed(100);

			largeMotorb.backward();
			oikeaA.forward();
			tunes.search();

			LCD.clear();
			LCD.drawString("vasemmalle", 0, 4);
		}

		if (beacon.angle() > -2 && beacon.angle() < 2) { // when beacon is in front of robot
			oikeaA.setSpeed(100);
			largeMotorb.setSpeed(100);

			oikeaA.backward();
			largeMotorb.backward();
			tunes.target();

			LCD.drawString("eteen", 0, 4);

			if (beacon.distance() < 18 && beacon.distance() > 4) { // when beacon is near the sensor

				oikeaA.setSpeed(10);
				largeMotorb.setSpeed(10);
				kauhaC.setSpeed(100);
				kauhaC.backward();		// grab the beacon
				tunes.pickup();				// play the pickup tune
				Delay.msDelay(400);
				
				kauhaC.setSpeed(0);
				tunes.success();			// play the success tune
				mode++;
			}
		}
	}
	public void dropCargo() {
		LCD.clear();
		
		oikeaA.setSpeed(400);
		largeMotorb.setSpeed(400);
		
		largeMotorb.backward();
		oikeaA.backward();
		
		if (color.colorturn() == 3) {

			LCD.drawString("Lasketaan kuorma", 0, 5);
			oikeaA.setSpeed(0);
			largeMotorb.setSpeed(0);
			kauhaC.setSpeed(200);
			kauhaC.forward();
			
			Delay.msDelay(300);
			
			oikeaA.setSpeed(300);
			largeMotorb.setSpeed(300);
			oikeaA.forward();
			largeMotorb.forward();
			
			Delay.msDelay(2000);
			
			oikeaA.close();
			largeMotorb.close();
			kauhaC.close();
			
			mode = 2;
		}
	}
}