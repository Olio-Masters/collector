package testivari;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.utility.Delay;

public class Main {

	EV3ColorSensor cs;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		cs = new EV3ColorSensor(SensorPort.S1);
		LCD.drawString("Tahan asti toimii", 0, 2);
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