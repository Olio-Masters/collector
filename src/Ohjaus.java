import beacons.Beacons;
import colour.Colour;
import lejos.hardware.Button;
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
import moving.Moves;
import soundInterface.Head;

public class Ohjaus {
	
/**
 * 
 */
	public static void main(String[] args) {

		LCD.clear();
		LCD.drawString("valmiina keraamaan", 0, 5);
		
		Colour color = new Colour();
		color.start();
		
		Beacons beacon = new Beacons();
		Head tunes = new Head();
		Moves run = new Moves(beacon,tunes,color);
		
		beacon.start();
		run.start();
	}

}