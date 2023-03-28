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
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4);
		EV3 ev3 = (EV3) BrickFinder.getLocal();
		TextLCD lcd = ev3.getTextLCD();
		
		// Initialize sampleFetcher
		float redSample[];
		SensorMode redMode = colorSensor.getRedMode();
		redSample = new float[redMode.sampleSize()];
		
		// Hard-coded values
		leftMotor.setSpeed(600);
		rightMotor.setSpeed(600);
		float lower = 0.1f;
		float upper = 0.10f;
		
		// Start moving the robot
		
		leftMotor.backward(); // backward because of gears
		rightMotor.backward();
		
		
		
		EV3 ev3brick = (EV3) BrickFinder.getLocal();

		Keys buttons = ev3brick.getKeys();

		while(buttons.getButtons() != Keys.ID_ESCAPE) {
			redMode.fetchSample(redSample, 0);
			
			// Output sample data
			lcd.clear();
			lcd.drawString(String.valueOf(redSample[0]), 1, 3);
			// Correct direction
			if (lower <= redSample[0] && redSample[0] <= upper) {
				leftMotor.setSpeed(300);
				rightMotor.setSpeed(300);
			}
			else if (redSample[0] < lower) { 
				leftMotor.setSpeed(300);
				rightMotor.setSpeed(100);
			}
			else if (redSample[0] > upper) { 
				leftMotor.setSpeed(100);
				rightMotor.setSpeed(300);
			}
			
			// Allow for some time before self-correcting
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
			
		}
		
		leftMotor.stop();
		rightMotor.stop();
		colorSensor.close();
	
	}
}