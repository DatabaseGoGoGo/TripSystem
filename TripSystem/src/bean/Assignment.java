package bean;

import java.util.LinkedList;
import java.util.List;

public class Assignment {
	private final int CONFIRMED = 0;
	private final int WAITING = 2;
	
	private List<User> developersAssignedTo = new LinkedList<User>();
	private List<Integer> developerState = new LinkedList<Integer>();
	private List<String> developerStateName = new LinkedList<String>();
	private int applicationID;
	
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
	
}
