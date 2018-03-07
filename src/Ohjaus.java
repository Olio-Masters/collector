import beacons.Beacons;
import colour.Colour;
import lejos.hardware.lcd.LCD;
import moving.Moves;
import soundInterface.Head;

/**
 * The main class of the robot. This class only starts the other runnable classes.
 * @author C0mmunist1
 *
 */

public class Ohjaus {

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