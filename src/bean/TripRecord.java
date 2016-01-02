package bean;

import java.sql.Date;

public class TripRecord {
	private int tripID;
	private String developerID;
	private String developerName;
	private Date actualDepartTime;
	private int actualTripDays;
	private String tripContent;
	
	public TripRecord(int tripID, String developerID, Date actualDepartTime, int actualTripDays, String tripContent){
		this.tripID = tripID;
		this.developerID = developerID;
		this.actualDepartTime = actualDepartTime;
		this.actualTripDays = actualTripDays;
		this.tripContent = tripContent;
	}
	
	public void setDeveloperName(String name){
		this.developerName = name;
	}
	
	public String getDeveloperID(){
		return developerID;
	}
}
