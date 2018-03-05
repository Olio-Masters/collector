package colour;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.utility.Delay;

/**
 * Class Colour is and extension of Thread.
 * It looks for specific colours and acts upon finding any.
 * If no specific colour is found, it sets colour as "undefined".
 */
public class Colour extends Thread {

	EV3ColorSensor cs;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Colour();
	}
	
	/**
	 * Uses switch case method to act upon finding different colours.
	 * Prints the colour found on the screen.
	 * If no colour is found, prints "undefined".
	 */
	public Colour() {
		cs = new EV3ColorSensor(SensorPort.S1);
		LCD.drawString("Tahan asti toimii", 0, 6);
		Delay.msDelay(5000);
		while (Button.ENTER.isUp()) {	// Niin kauan kun enteriä ei paineta
			switch (cs.getColorID()) {
			case Color.BLACK:  //Jos BLACK tai WHITE niin jatkaa matkaa lastin kanssa
				LCD.drawString("Black", 0, 2);
				Delay.msDelay(200);
				break;
			case Color.WHITE:
				LCD.drawString("White", 0, 2);
				Delay.msDelay(200);
				break;
			case Color.GREEN:
				LCD.drawString("Green", 0, 2); //Lastin tiputuskoodi GREENille, BLUElle ja/tai REDille?
				Delay.msDelay(200);
				break;
			case Color.BLUE:
				LCD.drawString("Blue", 0, 2);
				Delay.msDelay(200);
				break;
			case Color.RED:
				LCD.drawString("Red", 0, 2);
				Delay.msDelay(200);
				break;
			default:
				LCD.drawString("Undefined", 0, 2);
				Delay.msDelay(200);
				break;
			}
			LCD.refresh();
			LCD.clear();
			Delay.msDelay(200);
		}
		LCD.drawString("LOPPU", 0, 2);
		Delay.msDelay(2000);
		LCD.clear();
		cs.close();
	}
}