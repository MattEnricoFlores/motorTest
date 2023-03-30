package motorTest;

public class DataExchange{
	
	//Obstacle Detection
	private boolean obstacleDetected = false;

	
	private int CMD = 1;
	
	public DataExchange() {
		
	}
	
	
	public boolean getObstacleDetected() {
		return obstacleDetected;
	}

	public void setObstacleDetected(boolean obstacleDetected) {
		this.obstacleDetected = obstacleDetected;
	}
	
	
	public void setCMD(int cMD) {
		CMD = cMD;
	}
	
	public int getCMD(){
		 return CMD;
		 }
	

}

	


