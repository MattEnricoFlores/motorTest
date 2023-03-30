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
	
	public UltraSense (DataExchange DE) {
		DEObj = DE;
	}

	@Override
	public void run() {
		
		while(Button.getButtons() != Keys.ID_ESCAPE) {
			
			distanceMode = obstacleSensor.getDistanceMode();
			objDistance = new float[distanceMode.sampleSize()];
			
			distanceMode.fetchSample(objDistance, 0);
			UltraSense.value = objDistance[0];
			
			if (value > 0.7) {
				DEObj.setCMD(1);
			}
			else {
				DEObj.setCMD(0);
				//Sound.twoBeeps();
			}
		
		}
	}
	
	

}
