package bean;

public class Project {
	private int projectID;
	private String managerID;
	private String projectName;
	private String projectDescription;
	
	public Project(int projectID, String managerID, String projectName, String projectDescription) {
		this.projectID = projectID;
		this.managerID = managerID;
		this.projectName = projectName;
		this.projectDescription = projectDescription;
	}
	
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public String getManagerID() {
		return managerID;
	}
	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectDescription() {
		return projectDescription;
	}
	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
	
		
}
