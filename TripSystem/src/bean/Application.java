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
	private int groupSize;
	
	public Application(int applicationID, String applicationName, String applyerID, String applerName, int projectID, int state, int groupSize){
		this.applicationID = applicationID;
		this.applicationName = applicationName;
		this.projectID = projectID;
		this.applyerID = applyerID;
		this.applyerName = applerName;
		this.groupSize = groupSize;
		this.state = state;
	}
	
	public Application(int applicationID, String applicationName, String applyerID, int projectID, int state, int groupSize){
		this.applicationID = applicationID;
		this.applicationName = applicationName;
		this.projectID = projectID;
		this.applyerID = applyerID;
		this.groupSize = groupSize;
		this.state = state;
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
	
	public int getGroupSize() {
		return groupSize;
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
	
	@Override
	public String toString() {
		String result = "";
		result += "application id: " + applicationID + "\t";
		result += "application name: " + applicationName + "\n";
		result += "project id: " + projectID + "\n";
		result += "applyer: " + applyerID + " " + applyerName + "\t";
		result += "apply time: " + applyTime + "\n";
		result += "group size: " + groupSize + "\n";
		result += "state: ";
		switch (state) {
		case APPROVED:
			result += "APPROVED\n";
			break;
		case CANCELED:
			result += "CANCELED\n";
			break;
		case REFUSED:
			result += "REFUSED\n";
			break;
		case WAITING:
			result += "WAITING\n";
			break;
		}
		result += "rejected times: " + rejectTimes;
		return result;		
	}
}
