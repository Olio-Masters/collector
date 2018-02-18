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
import lejos.robotics.SampleProvider;

public class Moves {
	
	private EV3LargeRegulatedMotor largeMotora;
	private EV3LargeRegulatedMotor largeMotorb;
	private EV3LargeRegulatedMotor largeMotorc;
	private final SampleProvider sp;
	private int beaconInfoH;
	private int beaconInfoD;
	private float[] sample;
	private static EV3IRSensor ir1;
	
	public Moves() {
		Moves.ir1 = new EV3IRSensor(SensorPort.S4);
		this.largeMotora = new EV3LargeRegulatedMotor(MotorPort.A);
		this.largeMotorb = new EV3LargeRegulatedMotor(MotorPort.B);
		this.largeMotorc = new EV3LargeRegulatedMotor(MotorPort.C);
		this.sp = ir1.getSeekMode();
		this.beaconInfoD = 0;
		this.beaconInfoH = 0;
		this.sample = new float[sp.sampleSize()]; // Sample from IR sensor
	}
	
	public void run() {
		
		largeMotora.setSpeed(500);
		largeMotorb.setSpeed(500);
		largeMotorc.setSpeed(100);
		
		largeMotorc.backward();
		Delay.msDelay(230);
		largeMotorc.setSpeed(0);
		

		while (Button.ESCAPE.isUp()) {

			
			sp.fetchSample(sample, 0);

			beaconInfoH = (int) sample[2]; // sample for direction
			beaconInfoD = (int) sample[3]; // sample for distance

			LCD.drawInt(beaconInfoH, 0, 1); // print direction on screen
			LCD.drawInt(beaconInfoD, 0, 2); // print distance on screen

			Delay.msDelay(1);

			if (beaconInfoH > 2) { // when beacon is seen to the right

				largeMotora.setSpeed(200);
				largeMotorb.setSpeed(200);

				largeMotora.backward();
				largeMotorb.forward();
				Delay.msDelay(100);

				LCD.clear();
				LCD.drawString("oikealle", 0, 4);
			}

			if (beaconInfoH < -2) { // when beacon is seen to the left

				largeMotora.setSpeed(200);
				largeMotorb.setSpeed(200);

				largeMotorb.backward();
				largeMotora.forward();
				Delay.msDelay(100);

				LCD.clear();
				LCD.drawString("vasemmalle", 0, 4);
			}

			if (beaconInfoH > -2 && beaconInfoH < 2) { // when beacon is in front of robot
				largeMotora.setSpeed(100);
				largeMotorb.setSpeed(100);

				largeMotora.backward();
				largeMotorb.backward();

				LCD.clear();
				LCD.drawString("eteen", 0, 4);

				if (beaconInfoD < 10 && beaconInfoD > 4) {

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
	}
}