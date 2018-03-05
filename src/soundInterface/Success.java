package soundInterface;

import java.awt.Button;
import java.io.*;

import lejos.hardware.Audio;
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.LED;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.lcd.TextLCD;
import lejos.utility.Delay;

public class Success extends Thread {
	private LED led;
	private Audio audio;
	private int pattern;
	private EV3 ev3;

	public Success() {
		this.led = BrickFinder.getLocal().getLED();
		this.ev3 = (EV3) BrickFinder.getLocal();
		this.pattern = 1;
		this.audio = ev3.getAudio();
	}

	public void run() {
		try {

			led.setPattern(pattern);
		    audio.playSample(new File("victorya.wav"),100);
			sleep(1000);
		} catch (Exception e) {
			LCD.drawString("ERROR!", 5, 5);
		}
	}
}