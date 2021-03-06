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

public class EV3IRBeacon {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		LCD.clear();
		LCD.drawString("EV3 IR Beacon", 0, 5);

		// Get an instance of the IR EV3 sensor
		final EV3IRSensor infraredSensor = new EV3IRSensor(SensorPort.S1);

		InfraredSignalCheckerThread checkerThread = new InfraredSignalCheckerThread(
				infraredSensor);
		checkerThread.start();
	}

}