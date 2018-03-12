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

/**
 * Class Head is and extension of Thread.
 * This class plays different tunes according to different situations.
 */
public class Head {
	
	private LED led;
	private Audio audio;
	private int pattern1;
	private int pattern2;
	private int pattern3;
	private int pattern4;
	private EV3 ev3;

	/**
	 * Defines different tunes using different subclasses.    
	 * Success: Plays scene1 - 
	 * Pickup: Plays scene2 - 
	 * Search: Plays scene3 - 
	 * Target: Plays scene4
	 */
	public Head() {
		this.led = BrickFinder.getLocal().getLED();
		this.ev3 = (EV3) BrickFinder.getLocal();
		this.pattern1 = 7;
		this.pattern2 = 8;
		this.pattern3 = 1;
		this.pattern4 = 9;
		this.audio = ev3.getAudio();
	}
	
	public void pickup() {
			led.setPattern(pattern1);
			audio.setVolume(100);
		    audio.playSample(new File("pickup.wav"),100);
	}
	
	public void search() {
			led.setPattern(pattern2);
			audio.setVolume(3);
			audio.playTone(700, 700);
	}
	
	public void success() {
			led.setPattern(pattern3);
			audio.setVolume(100);
		    audio.playSample(new File("victorya.wav"),100);
	}
	
	public void target() {
				led.setPattern(pattern4);
				audio.setVolume(3);
				audio.playTone(300, 500);
	}
}