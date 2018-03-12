import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorModes;

public class InfraredSignalCheckerThread extends Thread {

	private EV3IRSensor infraredSensor;
	EV3LargeRegulatedMotor largeMotor;
	private EV3LargeRegulatedMotor largeMotora;
	private EV3LargeRegulatedMotor largeMotorb;
	private EV3LargeRegulatedMotor largeMotorc;

	public InfraredSignalCheckerThread(final EV3IRSensor sensor) {
		this.infraredSensor = sensor;
	}

	@Override
	public void run() {
		largeMotora = new EV3LargeRegulatedMotor(MotorPort.A);
		largeMotorb = new EV3LargeRegulatedMotor(MotorPort.B);
		largeMotorc = new EV3LargeRegulatedMotor(MotorPort.C);
		largeMotora.setSpeed(100);
		largeMotorb.setSpeed(100);
		largeMotorc.setSpeed(30);

		while (Button.ESCAPE.isUp()) {
			final int remoteCommand = infraredSensor.getRemoteCommand(0);
			switch (remoteCommand) {
			case 1:
				largeMotora.backward();
				largeMotorc.setSpeed(30);
				LCD.clear();
				LCD.drawString("vasen eteen", 0, 5);
				break;
			case 2:
				largeMotora.forward();
				largeMotorc.setSpeed(30);
				LCD.clear();
				LCD.drawString("vasen taakse", 0, 5);
				break;
			case 3:
				largeMotorb.backward();
				largeMotorc.setSpeed(0);
				LCD.clear();
				LCD.drawString("oikea eteen", 0, 5);
				break;
			case 4:
				largeMotorb.forward();
				largeMotorc.setSpeed(0);
				LCD.clear();
				LCD.drawString("oikea taakse", 0, 5);
				break;
			default:
			}
		}
	}

}