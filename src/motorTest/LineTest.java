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

public class LineTest implements LineTracer{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// Initialize motors, sensors and screen
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
				leftMotor.setSpeed(100);
				rightMotor.setSpeed(100);
				float lower = 0.15f;
				float upper = 0.55f;
				
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
						leftMotor.backward();
						rightMotor.backward();
					}
					else if (redSample[0] < lower) { 
						leftMotor.backward();
						rightMotor.stop();
					}
					else if (redSample[0] > upper) { 
						leftMotor.stop();
						rightMotor.backward();
					}
					
					// Allow for some time before self-correcting
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {}
					
				}
				
//				leftMotor.stop();
//				rightMotor.stop();
//				colorSensor.close();
			
			}
		}