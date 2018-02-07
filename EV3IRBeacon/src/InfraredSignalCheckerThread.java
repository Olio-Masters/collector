import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorModes;


public class InfraredSignalCheckerThread extends Thread {

	
    private EV3IRSensor infraredSensor;
    EV3LargeRegulatedMotor largeMotor;
    
    public InfraredSignalCheckerThread(final EV3IRSensor sensor){
        this.infraredSensor = sensor;
    }	

    @Override
    public void run() {
    	EV3LargeRegulatedMotor largeMotora = new EV3LargeRegulatedMotor(MotorPort.A);
    	EV3LargeRegulatedMotor largeMotorb = new EV3LargeRegulatedMotor(MotorPort.B);
    	largeMotora.setSpeed(200);
    	largeMotora.setSpeed(200);

    	while(Button.ESCAPE.isUp()){
            final int remoteCommand = infraredSensor.getRemoteCommand(1);
            switch (remoteCommand){
                case 1:
                	largeMotora.forward();                	
                    break;
                case 2:
                	largeMotora.backward();                	
                	break;
                case 3:
                	largeMotorb.forward();                	
                	break;
                case 4:
                	largeMotorb.backward();                	
                	break;
                default:
            }
        }
    }    
    
}