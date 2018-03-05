package beacons;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class Colors extends Thread {

	private EV3ColorSensor cs;
	private int color;
	
	/**
	 * Colors sets things up: Color sensor to port S1 in leJos EV3
	 */
	
	public Colors() {
		this.cs = new EV3ColorSensor(SensorPort.S1);
		/*
		 * LCD.drawString("Tahan asti toimii", 0, 2); Delay.msDelay(5000);
		 * 
		 * 
		 */
		this.color = 0;
	}
	
	/**
	 * Run includes all the cases for different colors. The color senson had trouble with black and white, 
	 * so more saturated colors were chosen for the final code.
	 */

	@Override
	public void run() {
		try {
			while (Button.ENTER.isUp()) {
				switch (cs.getColorID()) {
				case Color.BLACK:
					LCD.drawString("Black", 0, 2);
					Delay.msDelay(200);
					color = 0;
					break;
				case Color.WHITE:
					LCD.drawString("White", 0, 2);
					Delay.msDelay(200);
					color = 0;
					break;
				case Color.GREEN:
					LCD.drawString("Green", 0, 2);
					Delay.msDelay(200);
					color = 1;
					break;
				case Color.BLUE:
					LCD.drawString("Blue", 0, 2);
					Delay.msDelay(200);
					color = 2;
					break;
				case Color.RED:
					LCD.drawString("Red", 0, 2);
					Delay.msDelay(200);
					color = 3;
					break;
				default:
					LCD.drawString("Undefined", 0, 2);
					Delay.msDelay(200);
					color = 0;
					break;
				}
				LCD.refresh();
				LCD.clear();
				Delay.msDelay(200);
				Thread.sleep(20);
			}
			LCD.clear();
			cs.close();
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
