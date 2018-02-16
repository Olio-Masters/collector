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

public class Ohjaus {

	private static EV3IRSensor ir1 = new EV3IRSensor(SensorPort.S4);

	public static void main(String[] args) {

		EV3LargeRegulatedMotor largeMotora = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor largeMotorb = new EV3LargeRegulatedMotor(MotorPort.B);
		EV3LargeRegulatedMotor largeMotorc = new EV3LargeRegulatedMotor(MotorPort.C);
		largeMotora.setSpeed(500);
		largeMotorb.setSpeed(500);
		largeMotorc.setSpeed(100);

		final SampleProvider sp = ir1.getSeekMode();

		int beaconInfoH = 0;
		int beaconInfoD = 0;
		
		largeMotorc.backward();
		Delay.msDelay(230);
		largeMotorc.setSpeed(0);
		

		while (Button.ESCAPE.isUp()) {

			float[] sample = new float[sp.sampleSize()]; // Sample from IR sensor
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

// import lejos.hardware.lcd.LCD;
// import lejos.hardware.port.SensorPort;
// import lejos.hardware.sensor.EV3IRSensor;
// import lejos.robotics.SampleProvider;
// import lejos.utility.Delay;
//
// public class Ohjaus {
//
// //Robot Configuration
// private static EV3IRSensor ir1 = new EV3IRSensor(SensorPort.S4);
//
//
// public static void main(String[] args) {
//
// final SampleProvider sp = ir1.getSeekMode();
//
//
// int beaconInfoH = 0;
// int beaconInfoD = 0;
//
//
// //Control loop
// final int iteration_threshold = 10000;
// for(int i = 0; i <= iteration_threshold; i++) {
//
// float [] sample = new float[sp.sampleSize()];
// sp.fetchSample(sample, 0);
//
// beaconInfoH = (int)sample[2];
// beaconInfoD = (int)sample[3];
//
// LCD.drawInt(beaconInfoH, 1, 1);
// LCD.drawInt(beaconInfoD, 1, 2);
//
//
// Delay.msDelay(50);
// }
// }
// }

// import lejos.hardware.Button;
// import lejos.hardware.ev3.LocalEV3;
// import lejos.hardware.lcd.Font;
// import lejos.hardware.lcd.GraphicsLCD;
// import lejos.hardware.motor.EV3LargeRegulatedMotor;
// import lejos.hardware.port.MotorPort;
// import lejos.hardware.port.SensorPort;
// import lejos.hardware.sensor.EV3IRSensor;
// import lejos.hardware.sensor.SensorMode;
// import lejos.robotics.RegulatedMotor;
// import lejos.utility.Delay;
//
// public class Ohjaus {
//
// public static void introMessage() {
//
// GraphicsLCD g = LocalEV3.get().getGraphicsLCD();
// g.drawString("Follow Beacon Demo", 5, 0, 0);
// g.setFont(Font.getSmallFont());
// g.drawString("Demonstration of IR Beacon", 2, 20, 0);
// g.drawString("seek mode. Requires", 2, 30, 0);
// g.drawString("a wheeled vehicle with two", 2, 40, 0);
// g.drawString("independently controlled", 2, 50, 0);
// g.drawString("motors connected to motor", 2, 60, 0);
// g.drawString("ports B and C, and an", 2, 70, 0);
// g.drawString("infrared sensor connected", 2, 80, 0);
// g.drawString("to port 4.", 2, 90, 0);
//
// // Quit GUI button:
// g.setFont(Font.getSmallFont()); // can also get specific size using
// Font.getFont()
// int y_quit = 100;
// int width_quit = 45;
// int height_quit = width_quit/2;
// int arc_diam = 6;
// g.drawString("QUIT", 9, y_quit+7, 0);
// g.drawLine(0, y_quit, 45, y_quit); // top line
// g.drawLine(0, y_quit, 0, y_quit+height_quit-arc_diam/2); // left line
// g.drawLine(width_quit, y_quit, width_quit, y_quit+height_quit/2); // right
// line
// g.drawLine(0+arc_diam/2, y_quit+height_quit, width_quit-10,
// y_quit+height_quit); // bottom line
// g.drawLine(width_quit-10, y_quit+height_quit, width_quit,
// y_quit+height_quit/2); // diagonal
// g.drawArc(0, y_quit+height_quit-arc_diam, arc_diam, arc_diam, 180, 90);
//
// // Enter GUI button:
// g.fillRect(width_quit+10, y_quit, height_quit, height_quit);
// g.drawString("GO", width_quit+15, y_quit+7, 0,true);
//
// Button.waitForAnyPress();
// if(Button.ESCAPE.isDown()) System.exit(0);
// g.clear();
// }
//
// public static void main(String[] args) {
//
// introMessage();
//
// EV3IRSensor ir = new EV3IRSensor(SensorPort.S4);
// RegulatedMotor left = new EV3LargeRegulatedMotor(MotorPort.B);
// RegulatedMotor right = new EV3LargeRegulatedMotor(MotorPort.C);
// SensorMode seek = ir.getSeekMode();
// float[] sample = new float[seek.sampleSize()];
//
// while(Button.ESCAPE.isUp()) {
// seek.fetchSample(sample, 0);
// int direction = (int) sample[0];
// System.out.println("Direction: " + direction);
// int distance = (int) sample[1];
//
// if (direction > 0) {
// left.forward();
// right.stop(true);
// } else if (direction < 0) {
// right.forward();
// left.stop(true);
// } else {
// if (distance < Integer.MAX_VALUE) {
// left.forward();
// right.forward();
// } else {
// left.stop(true);
// right.stop(true);
// }
// }
// }
//
// left.close();
// right.close();
// ir.close();
// }
// }