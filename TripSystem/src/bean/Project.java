package bean;

public class Project {
	private int projectID;
	private String managerID;
	private String managerName;
	private String projectName;
	private String projectDescription;
	
	public Project(int projectID, String managerID, String projectName, String projectDescription) {
		this.projectID = projectID;
		this.managerID = managerID;
		this.projectName = projectName;
		this.projectDescription = projectDescription;
	}
	
	public Project(int projectID, String managerID, String managerName, String projectName, String projectDescription) {
		this.projectID = projectID;
		this.managerID = managerID;
		this.managerName = managerName;
		this.projectName = projectName;
		this.projectDescription = projectDescription;
	}
	
	public Project(int projectID, String managerID, String projectName, String projectDescription) {
		this.projectID = projectID;
		this.managerID = managerID;
		this.projectName = projectName;
		this.projectDescription = projectDescription;
	}
	
	public Project(int projectID) {
		this.projectID = projectID;
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
	
	@Override
	public String toString() {
		String result = "-----------------------------------------------------------\n";
		result += "project id: " + projectID + "\t";
		result += "project name: " + projectName + "\n";
		result += "project description: " + projectDescription + "\n";
		result += "manager id: " + managerID + "\t";
		result += "manager name: " + managerName + "\n";
		result += "-----------------------------------------------------------";
		return result;		
	}
	
		
}
