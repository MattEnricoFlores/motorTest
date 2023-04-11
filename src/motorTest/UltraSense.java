package motorTest;

import lejos.hardware.Button;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

public class UltraSense implements Runnable{
	
	
	
	static EV3UltrasonicSensor obstacleSensor = new EV3UltrasonicSensor(SensorPort.S1);

	private static float value;
	
	private DataExchange DEObj;
	
	float objDistance[];
	
	SampleProvider distanceMode = null;
	
	private static int cmd = 1 ;
	
	

	@Override
	public void run() {
		
		while(!Thread.currentThread().isInterrupted()) {
			
			
			//Initialize Sample Fetcher
			
			distanceMode = obstacleSensor.getDistanceMode();
			objDistance = new float[distanceMode.sampleSize()];
			
			distanceMode.fetchSample(objDistance, 0);
			UltraSense.value = objDistance[0];
			
			
			
			if (value >= 0.15) {
				cmd=1;
			}
			else {
				cmd=0;
				
			}
			
		
		}
	}
	
	public int getCmd() {
		return cmd ;
	}
	
	

}
