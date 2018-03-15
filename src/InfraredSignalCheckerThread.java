import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.sensor.EV3IRSensor;

public class InfraredSignalCheckerThread extends Thread {

	private EV3IRSensor infraredSensor;
	EV3LargeRegulatedMotor largeMotor;
	private EV3LargeRegulatedMotor motorRightA;
	private EV3LargeRegulatedMotor motorLeftB;
	private EV3LargeRegulatedMotor motorGrabC;

	public InfraredSignalCheckerThread(final EV3IRSensor sensor) {
		this.infraredSensor = sensor;
	}

	@Override
	public void run() {
		motorRightA = new EV3LargeRegulatedMotor(MotorPort.A);
		motorLeftB = new EV3LargeRegulatedMotor(MotorPort.B);
		motorGrabC = new EV3LargeRegulatedMotor(MotorPort.C);
		motorRightA.setSpeed(100);
		motorLeftB.setSpeed(100);
		motorGrabC.setSpeed(30);

		while (Button.ESCAPE.isUp()) {
			final int remoteCommand = infraredSensor.getRemoteCommand(0);
			switch (remoteCommand) {
			case 1:
				motorRightA.backward();
				motorGrabC.setSpeed(30);
				LCD.clear();
				LCD.drawString("vasen eteen", 0, 5);
				break;
			case 2:
				motorRightA.forward();
				motorGrabC.setSpeed(30);
				LCD.clear();
				LCD.drawString("vasen taakse", 0, 5);
				break;
			case 3:
				motorLeftB.backward();
				motorGrabC.setSpeed(0);
				LCD.clear();
				LCD.drawString("oikea eteen", 0, 5);
				break;
			case 4:
				motorLeftB.forward();
				motorGrabC.setSpeed(0);
				LCD.clear();
				LCD.drawString("oikea taakse", 0, 5);
				break;
			default:
			}
		}
	}

}