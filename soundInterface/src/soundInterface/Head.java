package soundInterface;

import java.awt.Button;
import java.io.*;

import lejos.hardware.Audio;
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.LED;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.utility.Delay;

public class Head {

	public static void main(String[] args) {
		Succes scene1 = new Succes();
		//scene1.start();
		Pickup scene2 = new Pickup();
		//scene2.start();
		Search scene3 = new Search();
		//scene3.start();
		Target scene4 = new Target();
		scene4.start();




	}

}