package bean;

import java.util.LinkedList;
import java.util.List;

public class Assignment {
	public static final int CONFIRMED = 0;
	public static final int WAITING = 2;
	
	private List<User> developersAssignedTo = new LinkedList<User>();
	private List<Integer> developerState = new LinkedList<Integer>();
	private List<String> developerStateName = new LinkedList<String>();
	private int applicationID;
	private String applicationName;
	
	public Assignment(int applicationID, String applicationName){
		this.applicationID = applicationID;
		this.applicationName = applicationName;
	}
	
	public Assignment(int applicationID){
		this.applicationID = applicationID;
	}
	
	public void setDevelopersAssignedTo(List<User> developers){
		this.developersAssignedTo = developers;
	}
	
	public void setDeveloperState(List<Integer> states){
		this.developerState = states;
		developerStateName.clear();
		for (int i = 0; i < states.size(); i++){
			String stateName = getStateName(i);
			developerStateName.add(stateName);
		}		
	}
	
	public String getApplicationName(){
		return applicationName;
	}
	
	public int getApplicationID(){
		return applicationID;
	}
	
	public List<String> getDeveloperStateName(){
		return developerStateName;
	}
	
	public String getStateName(int index){
		String stateName = "";
		switch (developerState.get(index)){
			case 0:
				stateName = "已确认";
				break;
			case 2:
				stateName = "未确认";
				break;
			default:
				break;
		}
		return stateName;
	}
	
	@Override
	public String toString() {
		String result = "-----------------------------------------------------------\n";
		result += "application id: " + applicationID + "\t";
		result += "application name: " + applicationName + "\n";
		for (int i = 0; i < developersAssignedTo.size(); i++){
			User developer = developersAssignedTo.get(i);
			result += "developer id: " + developer.getUserID() + "\t";
			result += "developer name: " + developer.getUserName() + "\t";
			result += "developer state: " + developerStateName.get(i) + "\n";
		}
		result += "\n-----------------------------------------------------------";
		return result;		
	}
}
