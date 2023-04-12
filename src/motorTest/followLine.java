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
import lejos.utility.Delay;

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
		File soundFile = new File("VictoryBit.wav");
		
		int tmp =0;
		
        
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
				tmp++;
				leftMotor.setSpeed(320);
				rightMotor.setSpeed(180); 

				rightMotor.forward();
				leftMotor.forward();
 
				Delay.msDelay(1000);
				Sound.buzz();
				
				
				
				leftMotor.setSpeed(120);
				rightMotor.setSpeed(250);

				leftMotor.forward();
				rightMotor.forward();
				
				Delay.msDelay(3000); 
				Sound.buzz();
				
				
				leftMotor.setSpeed(300);
				rightMotor.setSpeed(90);

				rightMotor.forward();
				leftMotor.forward();

				Delay.msDelay(750);
				Sound.buzz();
				
				if(tmp >= 2) {
					Sound.playSample(soundFile);
					leftMotor.setSpeed(360);
					rightMotor.setSpeed(0);
					
					leftMotor.forward();
					rightMotor.forward();
					Delay.msDelay(3000); 
					
					leftMotor.setSpeed(360);
					rightMotor.setSpeed(0);
					
					leftMotor.forward();
					rightMotor.forward();
					Delay.msDelay(3000);
					
					leftMotor.setSpeed(360);
					rightMotor.setSpeed(0);
					
					leftMotor.forward();
					rightMotor.forward();
					Delay.msDelay(3000); 
					
					leftMotor.setSpeed(360);
					rightMotor.setSpeed(0);
					
					leftMotor.forward();
					rightMotor.forward();  
					
					
					}
				
				 } 
			
			
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