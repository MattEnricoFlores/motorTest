package motorTest;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class colorSense extends Thread {

	static float value;
	static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S4);

	@Override
	public void run() {
		// TODO Auto-generated method stub

		EV3 ev3 = (EV3) BrickFinder.getLocal();
		TextLCD lcd = ev3.getTextLCD();
		float redSample[];
		SensorMode redMode = null;

		while (!Thread.currentThread().isInterrupted()) {

			// Initialize sampleFetcher

			redMode = colorSensor.getRedMode();
			redSample = new float[redMode.sampleSize()];

			redMode.fetchSample(redSample, 0);
			colorSense.value = redSample[0];

			// Output sample data
			lcd.clear();
			lcd.drawString(String.valueOf(redSample[0]), 1, 3);

		}
	}

	public float getValue() {
		return value;
	}
}
