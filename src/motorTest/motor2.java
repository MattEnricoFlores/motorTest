package motorTest;

import lejos.hardware.Button;
import lejos.hardware.motor.Motor;

public class motor2 implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Motor.D.setSpeed(90);
		Motor.D.forward();
		Button.waitForAnyPress();
		Motor.D.stop();
	}

}
