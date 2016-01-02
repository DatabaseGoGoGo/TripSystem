package bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class Trip {
	private int tripID;
	private int applicationID;
	private Timestamp departTime;
	private int days;
	private String description = "please write some description";
	private int state;
	private String tripName;

	public static final int UNSTARTED = -1;
	public static final int FINISHED = 0;
	public static final int ONGOING = 1;
	
	private List<User> developers = new LinkedList<User>();
	
	public Trip(int tripID, int applicationID, int state){
		this.tripID = tripID;
		this.tripName = applicationID + "_trip";
		this.applicationID = applicationID;
		this.state = state;
	}
	
	public void setTripName(String name){
		this.tripName = name;
	}
	
	public void setDepartTime(Timestamp departTime) {
		this.departTime = departTime;
	}
	
	public void setDescription(String description) {
		this.description = new String(description);
	}
	
	public Trip(int tripID, int applicationID, Timestamp departTime, int days, String description, int state) {
		this.tripID = tripID;
		this.applicationID = applicationID;
		this.departTime = departTime;
		this.days = days;
		this.description = description;
		this.state = state;
	}
	
	public int getTripID() {
		return tripID;
	}

	public int getApplicationID() {
		return applicationID;
	}

	public Timestamp getDepartTime() {
		return departTime;
	}

	public int getState() {
		return state;
	}
	
	public int getDays() {
		return days;
	}

	public String getDescription() {
		return description;
	}	
}
