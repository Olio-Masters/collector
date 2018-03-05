import beacons.Beacons;
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

	public static void main(String[] args) {

		LCD.clear();
		LCD.drawString("valmiina keraamaan", 0, 5);

//		final EV3IRSensor infraredSensor = new EV3IRSensor(SensorPort.S4);
//
//		InfraredSignalCheckerThread checkerThread = new InfraredSignalCheckerThread(infraredSensor);
//		checkerThread.start();
		

		Beacons beacon = new Beacons();
		beacon.start();
		Head tunes = new Head();
		Moves runn = new Moves(beacon,tunes);
		runn.start();
		
		
	}

}