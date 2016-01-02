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
	
	
	public void setTripName(String name){
		this.tripName = name;
	}
}
