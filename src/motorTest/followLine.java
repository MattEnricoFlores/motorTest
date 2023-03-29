package motorTest;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;

public class followLine implements Runnable {
	
	DataExchange DEObj;
	


	@Override
	public void run() {
		
		
		RegulatedMotor leftMotor = Motor.D;
		RegulatedMotor rightMotor = Motor.A;
	
		// Hard-coded values
		float lower = 0.1f;
		float upper = 0.10f;
		
		colorSense colorThread = new colorSense();
		colorThread.start();
				
		EV3 ev3brick = (EV3) BrickFinder.getLocal();

		Keys buttons = ev3brick.getKeys();

		while(buttons.getButtons() != Keys.ID_ESCAPE) {
			

			
			if(DEObj.getCMD()==1 ) {
			// Correct direction
			if (lower <= colorThread.getValue() && colorThread.getValue() <= upper) {
				leftMotor.setSpeed(300);
				leftMotor.forward();
				rightMotor.setSpeed(300);
				rightMotor.forward();
			}
			else if (colorThread.getValue() < lower) { 
				leftMotor.setSpeed(300);
				leftMotor.forward();
				rightMotor.setSpeed(100);
				rightMotor.forward();
			}
			else if (colorThread.getValue() > upper) { 
				leftMotor.setSpeed(100);
				leftMotor.forward();
				rightMotor.setSpeed(300);
				rightMotor.forward();
			}
			}
			else {
				leftMotor.stop();
				rightMotor.stop();
			}
			// Allow for some time before self-correcting
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
			
		}
		colorThread.interrupt();
		
		
		leftMotor.stop();
		rightMotor.stop();
	
	}
}