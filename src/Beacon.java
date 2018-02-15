package beacons;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;

public class Beacon extends Thread {
	
	private final EV3IRSensor irsensor1;
	private SensorMode position;
	private String piirto = "";
	private float[] seekSample = new float[position.sampleSize()];
	
	public Beacon(){
		this.irsensor1 = new EV3IRSensor(SensorPort.S1);
		this.position = irsensor1.getSeekMode();
	}
	
	@Override
	public void run() {
		try {
			this.position = irsensor1.getSeekMode();
			irsensor1.fetchSample(seekSample, 0);
			this.piirto +=  seekSample[0];
			
			Thread.sleep(10);
		} catch(Exception e) {
		}
	}
	
	public SensorMode getSensorMode() {
		return position;
	}
	@Override
	public String toString() {
		return piirto;
	}

}
