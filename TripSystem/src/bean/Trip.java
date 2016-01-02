package bean;

import java.sql.Date;

public class Trip {
	private int tripID;
	private int applicationID;
	private Date departTime;
	private int days;
	private String description;
	private int state;
	private String tripName;

	private final int UNSTARTED = -1;
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
	
	public Trip(int tripID, int applicationID, Date departTime, int days, String description, int state) {
		this.tripID = tripID;
		this.applicationID = applicationID;
		this.departTime = departTime;
		this.days = days;
		this.description = description;
		this.state = state;
	}
	
	public Trip(int tripID, int applicationID, int state){
		this.tripID = tripID;
		this.applicationID = applicationID;
		this.state = state;
	}
	
	public int getTripID() {
		return tripID;
	}

	public int getApplicationID() {
		return applicationID;
	}

	public Date getDepartTime() {
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
