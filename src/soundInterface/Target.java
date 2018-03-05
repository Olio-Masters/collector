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

public class Target extends Thread {
	private LED led;
	private Audio audio;
	private int pattern;
	private EV3 ev3;

	public Target() {
		this.led = BrickFinder.getLocal().getLED();
		this.ev3 = (EV3) BrickFinder.getLocal();
		this.pattern = 9;
		this.audio = ev3.getAudio();
	}

	public void run() {
		try {
			int i;
			for (i = 0; i < 10; i++) {
				led.setPattern(pattern);
				audio.setVolume(100);
				audio.playTone(300, 500);
				sleep(500);
			}
		} catch (Exception e) {
			LCD.drawString("ERROR!", 5, 5);
		}
	}
}