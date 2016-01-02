package bean;

import java.sql.Time;

public class Application {
	public final int APPROVED = 0;
	public final int REFUSED = 1;
	public final int WAITING = 2;
	public final int CANCELED = 3;
	private int applicationID;
	private int projectID;
	private String applyerID;
	private int state;
	private String projectName;
	private String applyerName;
	private String applicationName;
	private Time applyTime;
	private int rejectTimes;
	
	public Time getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Time applyTime) {
		this.applyTime = applyTime;
	}
	
	public String getApplicationName() {
		return applicationName;
	}

	public int getRejectTimes() {
		return rejectTimes;
	}
	public Application(int applicationID, String applicationName, String applyerID, int projectID, int state){
		this.applicationID = applicationID;
		this.applicationName = applicationName;
		this.projectID = projectID;
		this.applyerID = applyerID;
		this.state = state;
	}
	
	public int getApplicationID() {
		return applicationID;
	}
	
	public void setApplyerName(String name){
		this.applyerName = name;
	}
	
	public void setProjectName(String name){
		this.projectName = name;
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
