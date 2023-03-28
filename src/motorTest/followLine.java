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
	


	@Override
	public void run() {
		
		
		RegulatedMotor leftMotor = Motor.D;
		RegulatedMotor rightMotor = Motor.A;
//		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4);
//		EV3 ev3 = (EV3) BrickFinder.getLocal();
//		TextLCD lcd = ev3.getTextLCD();
		
//		// Initialize sampleFetcher
//		float redSample[];
//		SensorMode redMode = colorSensor.getRedMode();
//		redSample = new float[redMode.sampleSize()];
		
		// Hard-coded values
		float lower = 0.1f;
		float upper = 0.10f;
		
		// Start moving the robot
		
//		leftMotor.backward(); // backward because of gears
//		rightMotor.backward();
		
		colorSense colorThread = new colorSense();
		colorThread.start();
				
		EV3 ev3brick = (EV3) BrickFinder.getLocal();

		Keys buttons = ev3brick.getKeys();

		while(buttons.getButtons() != Keys.ID_ESCAPE) {
			

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