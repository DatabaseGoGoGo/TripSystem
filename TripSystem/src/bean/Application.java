package bean;

public class Application {
	private final int APPROVED = 0;
	private final int REFUSED = 1;
	private final int WAITING = 2;
	private final int CANCELED = 3;
	private int applicationID;
	private int projectID;
	private String applyerID;
	private int state;
	private String applicationName;
	private String applyerName;
	
	public Application(int applicationID, String applicationName, String applyerID, int projectID, int state){
		this.applicationID = applicationID;
		this.projectID = projectID;
		this.applyerID = applyerID;
		this.state = state;
		this.applicationName = applicationName;
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
