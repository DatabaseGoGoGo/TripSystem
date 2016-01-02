package bean;

import java.util.LinkedList;
import java.util.List;

public class Trip {
	private final int FINISHED = 0;
	private final int ONGOING = 1;
	
	private List<User> developers = new LinkedList<User>();
	private int tripID;
	private int applicationID;
	private int state;
	private String tripName;
	
	public Trip(int tripID, int applicationID, int state){
		this.tripID = tripID;
		this.applicationID = applicationID;
		this.state = state;
	}
	
	public int getApplicationID(){
		return applicationID;
	}
	
	public void setTripName(String name){
		this.tripName = name;
	}
	
	
	
}
