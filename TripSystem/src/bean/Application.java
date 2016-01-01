package bean;

public class Application {
	private final int APPROVED = 0;
	private final int REFUSED = 1;
	private final int WAITING = 2;
	private final int CANCELED = 3;
	private int applicationID;
	private int projectID;
	private int applyerID;
	private int state;
	private String projectName;
	private String applyerName;
	
	public Application(int applicationID, int projectID, int state){
		this.applicationID = applicationID;
		this.projectID = projectID;
		this.applyerID = applicationID;
		this.state = state;
	}
	
	public void setApplyerName(String name){
		this.applyerName = name;
	}
	
	public void setProjectName(String name){
		this.projectName = name;
	}

}
