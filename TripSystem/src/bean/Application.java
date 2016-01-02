package bean;

import java.sql.Time;
import java.sql.Timestamp;

public class Application {
	public static final int APPROVED = 0;
	public static final int REFUSED = 1;
	public static final int WAITING = 2;
	public static final int CANCELED = 3;
	private int applicationID;
	private int projectID;
	private String applyerID;
	private int state;
	private String applicationName;
	private String applyerName;
	private Timestamp applyTime;
	private int rejectTimes;
	
	public Application(int applicationID, String applicationName, String applyerID, int projectID, int state){
		this.applicationID = applicationID;
		this.applicationName = applicationName;
		this.projectID = projectID;
		this.applyerID = applyerID;
		this.state = state;
		this.applicationName = applicationName;
	}
	
	public Timestamp getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}

	public int getRejectTimes() {
		return rejectTimes;
	}
	
	public int getApplicationID() {
		return applicationID;
	}
	
	public void setApplyerName(String name){
		this.applyerName = name;
	}
	
	public String getApplicationName(){
		return applicationName;
	}
	
	public String getApplyerID(){
		return applyerID;
	}
	
	public int getProjectID(){
		return projectID;
	}
	
	public int getState(){
		return state;
	}
	
	public String getStateName(){
		String stateName = "";
		switch (state){
			case 0:
				stateName = "批准";
				break;
			case 1:
				stateName = "拒绝";
				break;
			case 2:
				stateName = "待审";
				break;
			case 3:
				stateName = "取消";
				break;
			default:
				break;
		}
		return stateName;
	}
}
