package moving;

import beacons.Beacons;
import colour.Colour;
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

	private Colour color;
	private Head tunes;
	private Beacons beacon;
//	int i;

	/**
	 * @param beacons
	 * Gets information from the Beacons class and uses it in the run() method.
	 */
	public Moves(Beacons beacons, Head tune, Colour colors) {
		this.largeMotora = new EV3LargeRegulatedMotor(MotorPort.A);
		this.largeMotorb = new EV3LargeRegulatedMotor(MotorPort.B);
		this.largeMotorc = new EV3LargeRegulatedMotor(MotorPort.C);
		this.beacon = beacons;
		this.tunes = tune;	
		this.color = colors;
//		this.i = 0;
	}

	/**
	 * Don't call this method. Instead call method "start()"
	 * 
	 * This method runs the main functions of the robot
	 */
	public void run() {

		try {
			
			int i =0;
			
			largeMotora.setSpeed(500);
			largeMotorb.setSpeed(500);
			largeMotorc.setSpeed(100);

			largeMotorc.backward(); // Initialize the robot grab and set it to the right altitude.
			Delay.msDelay(280);
			largeMotorc.setSpeed(0);
			
			color.colorturn();

			while (i == 0) {

				LCD.drawInt(beacon.angle(), 0, 1); // print direction on screen.
				LCD.drawInt(beacon.distance(), 0, 2); // print distance on screen.
				LCD.drawInt(color.colorturn(), 0, 3);

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
					tunes.search();

					LCD.clear();
					LCD.drawString("vasemmalle", 0, 4);
				}

				if (beacon.angle() > -2 && beacon.angle() < 2) { // when beacon is in front of robot
					largeMotora.setSpeed(100);
					largeMotorb.setSpeed(100);

					largeMotora.backward();
					largeMotorb.backward();
					tunes.target();

					LCD.drawString("eteen", 0, 4);

					if (beacon.distance() < 18 && beacon.distance() > 4) { // when beacon is near the sensor

						largeMotora.setSpeed(10);
						largeMotorb.setSpeed(10);
						largeMotorc.setSpeed(100);
						largeMotorc.backward();		// grab the beacon
						tunes.pickup();				// play the pickup tune
						Delay.msDelay(400);
						
						largeMotorc.setSpeed(0);
						tunes.success();			// play the success tune
						i++;
					}
				}
			}
			
			while (i == 1) {
				
				LCD.clear();
				
				largeMotora.setSpeed(400);
				largeMotorb.setSpeed(400);
				
				largeMotorb.backward();
				largeMotora.backward();
				
				if (color.colorturn() == 3) {

					LCD.drawString("Lasketaan kuorma", 0, 5);
					largeMotora.setSpeed(0);
					largeMotorb.setSpeed(0);
					largeMotorc.setSpeed(200);
					largeMotorc.forward();
					
					Delay.msDelay(300);
					
					largeMotora.setSpeed(300);
					largeMotorb.setSpeed(300);
					largeMotora.forward();
					largeMotorb.forward();
					
					Delay.msDelay(2000);
					
					largeMotora.close();
					largeMotorb.close();
					largeMotorc.close();
					
					i = 2;
				}
				
				while (i == 2) {
					LCD.clear();
					LCD.drawString("Tavara palautettu", 0, 1);
					LCD.drawString("Kiitos etta valitsit KR2000", 0, 2);
					LCD.drawString("Tervetuloa uudelleen!", 0, 3);
					LCD.drawString("", 0, 4);
					LCD.drawString("Terveisin:", 0, 5);
					LCD.drawString("-- Olio Masters --", 0, 6);
					Delay.msDelay(5000);
					break;
				}
			}
			
		} catch (Exception e) {
		}
	}
}