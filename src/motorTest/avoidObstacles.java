package motorTest;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;

public class avoidObstacles implements Runnable {
	
	private boolean isFollowingLine = false; // flag to indicate if the robot is currently following the line
	
	@Override
	public void run() {
		
		RegulatedMotor leftMotor = Motor.D;
		RegulatedMotor rightMotor = Motor.A;
		EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S1);
		EV3 ev3 = (EV3) BrickFinder.getLocal();
		TextLCD lcd = ev3.getTextLCD();
		
		// Initialize sampleFetcher
		float distanceSample[];
		SensorMode distanceMode = (SensorMode) ultrasonicSensor.getDistanceMode();
		distanceSample = new float[distanceMode.sampleSize()];
		
		// Hard-coded values
		leftMotor.setSpeed(400);
		rightMotor.setSpeed(400);
		float safeDistance = 0.15f;
		
		while (true) {
			
			// Check if the robot is currently following the line
			if (isFollowingLine) {
				// Pause execution and wait for the followLine thread to finish
				Thread.yield();
				continue;
			}
			
			distanceMode.fetchSample(distanceSample, 0);
			
			// Output sample data
			lcd.clear();
			lcd.drawString(String.valueOf(distanceSample[0]), 1, 3);
			
			// Avoid obstacles
			if (distanceSample[0] < safeDistance) {
				leftMotor.backward();
				rightMotor.backward();
				leftMotor.rotate(180);
				rightMotor.rotate(-180);
			} else {
				leftMotor.forward();
				rightMotor.forward();
			}
			
			// Allow for some time before checking again
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
		}
	}
	
	// Setter method to update the value of isFollowingLine flag
	public void setFollowingLine(boolean isFollowingLine) {
		this.isFollowingLine = isFollowingLine;
	}
}