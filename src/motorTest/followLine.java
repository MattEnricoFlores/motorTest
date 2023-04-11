package motorTest;

import java.io.File;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
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
		
		
		// Hard-coded values
		float lower = 0.1f;
		float upper = 0.10f;
		
		colorSense colorThread = new colorSense();
		colorThread.start();
		UltraSense ultraSense = new UltraSense();
		Thread sensorThread = new Thread(ultraSense);
		sensorThread.start();
				
		
		EV3 ev3brick = (EV3) BrickFinder.getLocal();

		Keys buttons = ev3brick.getKeys();
		int i =10 ;
		int s=1;

		while(buttons.getButtons() != Keys.ID_ESCAPE) {
			

			
			while(ultraSense.getCmd()==1 ) {
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
			while(ultraSense.getCmd() == 0){
				
				leftMotor.stop();
				rightMotor.stop();
				try {
					Thread.sleep(2000);
				}catch(Exception e) {}
				
				while(i>0) {
				leftMotor.setSpeed(50);
				leftMotor.forward();
				rightMotor.forward();
				rightMotor.setSpeed(200);
				i--;
				}
				
				while(ultraSense.getCmd() !=1) {
					leftMotor.setSpeed(200);
					leftMotor.forward();
					rightMotor.forward();
					rightMotor.setSpeed(50);
					}
				s++ ;
				if(s>=2) {
					System.out.println("Completed");
					Sound.playSample(new File("VictoryBit.wav"), Sound.VOL_MAX);
					
				}
				
				} //set CMD to 1 again here
			
			// Allow for some time before self-correcting
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
			
		}
		colorThread.interrupt();
		sensorThread.interrupt();
		
		
		
		leftMotor.stop();
		rightMotor.stop();
	
	}
}