package motorTest;

import lejos.hardware.Button;
import lejos.hardware.motor.Motor;

public class motor1 implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		Motor.A.setSpeed(90);
		Motor.A.forward();
		Button.waitForAnyPress();
		Motor.A.stop();
		
	}

}
