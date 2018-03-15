package colour;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class Colour extends Thread {

	private EV3ColorSensor colorSensor;
	private int color;
	
	/**
	 * Colors sets things up: Color sensor to port S1 in leJos EV3
	 */
	public Colour() {
		this.colorSensor = new EV3ColorSensor(SensorPort.S1);
		this.color = 10;
	}
	
	/**
	 * Run includes all the cases for different colors. The color sensor has trouble with black and white, 
	 * so more saturated colors were chosen for the final code.
	 */
	@Override
	public void run() {
		try {
			while (Button.ESCAPE.isUp()) {
				switch (colorSensor.getColorID()) {
				case Color.BLACK:
					color = 0;
					break;
				case Color.WHITE:
					color = 0;
					break;
				case Color.GREEN:
					color = 1;
					break;
				case Color.BLUE:
					color = 2;
					break;
				case Color.RED:
					color = 3;
					break;
				default:
					color = 0;
					break;
				}
				LCD.refresh();
				LCD.clear();
				Delay.msDelay(200);
				Thread.sleep(20);
			}
			LCD.clear();
			colorSensor.close();
		}catch(Exception e) {
		}
	}
	
	/**
	 * Colorturn returns the value for color that was determined in run.
	 */
	public int colorturn() {
		return color;
	}

}