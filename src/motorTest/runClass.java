package motorTest;



public class runClass {
	
	private static DataExchange DE;
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		// Motor Test 
//		motor1 Motor1 = new motor1();
//		motor2 Motor2 = new motor2();
//		
//		Thread thread1 = new Thread(Motor1);
//		Thread thread2 = new Thread(Motor2);
//		
//		thread1.start();
//		thread2.start();
		
		
		
		
		//Line Following Thread
		
		//Color Sensor Thread
//		colorSense colorThread = new colorSense();
//		
//		colorThread.start();
		
		
		// Motor Thread
		followLine FollowLine1 = new followLine();
		
		Thread thread1 = new Thread(FollowLine1);
		
		thread1.start();


		
		
	}

}
