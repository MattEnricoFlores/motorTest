package motorTest;

import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public class UltraSense extends Thread{
	
	
	
	static EV3UltrasonicSensor obstacleSensor = new EV3UltrasonicSensor(SensorPort.S1);

	private static float value;
	
	private DataExchange DEObj;
	
	float objDistance[];
	SampleProvider distanceMode =null;
	

	@Override
	public void run() {
		
		while(!Thread.currentThread().isInterrupted()) {
			
			distanceMode = obstacleSensor.getDistanceMode();
			objDistance = new float[distanceMode.sampleSize()];
			
			distanceMode.fetchSample(objDistance, 0);
			UltraSense.value = objDistance[0];
			
			if (value > 25) {
				DEObj.setCMD(1);
			}
			else {
				DEObj.setCMD(0);
				Sound.twoBeeps();
				Sound.twoBeeps();
			}
		
		}
	}
	
	

}
