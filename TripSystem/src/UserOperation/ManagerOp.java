package UserOperation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import bean.Application;
import Config.Config;
import dao.DBHelper;

public class ManagerOp {
	private final int WAITING = 2;
	private int managerID;
	private String url;
	GeneralOp generalOp;
	
	public ManagerOp(int managerID){
		this.managerID = managerID;
		Config config = new Config();
		url = config.getUrl();
		generalOp = new GeneralOp(url);
	}
	
	public List<Application> getAllApplication(){
		List<Application> applicationRequests = new LinkedList<Application>();
		String sql = "select * from application "
					+ "where projectID in ("
					+ "select projectID from project "
					+ "where userID == " + managerID + ") "
					+ "order by applyTime DESC";
    	ResultSet result;
    	DBHelper dbHelper = new DBHelper(url, sql);    	
    	try {
    		result = dbHelper.getPst().executeQuery();
    		while (result.next()){
    			int applicationID = result.getInt("applicationID");
    			int projectID = result.getInt("projectID");
    			int applyerID = result.getInt("applyerID");
    			int state = result.getInt("state");

    			applicationRequests.add(new Application(applicationID, applyerID, projectID, state));
    		}
    		result.close();
    		dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    
    	Iterator<Application> it=applicationRequests.iterator();
    	while(it.hasNext()){
    		Application a = (Application)it.next();
    		String applyerName = generalOp.getNameByID(a.getApplyerID(), "userName", "user");
    		a.setApplyerName(applyerName);
    		String projectName = generalOp.getNameByID(a.getProjectID(), "projectName", "project");
    		a.setProjectName(projectName);
    	}
    	
    	
    	return applicationRequests;
	}

	public List<Application> getApplicationByProjectName(String name){
		List<Application> applicationRequests = new LinkedList<Application>();
		String sql = "select * from application "
				+ "where projectID in ("
				+ "select projectID from project "
				+ "where projectName == " + name + ") "
				+ "order by applyTime DESC";
		ResultSet result;
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			result = dbHelper.getPst().executeQuery();
			while (result.next()){
				int applicationID = result.getInt("applicationID");
				int projectID = result.getInt("projectID");
				int applyerID = result.getInt("applyerID");
				int state = result.getInt("state");
	
				applicationRequests.add(new Application(applicationID, applyerID, projectID, state));
			}
			result.close();
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Iterator<Application> it=applicationRequests.iterator();
    	while(it.hasNext()){
    		Application a = (Application)it.next();
    		String applyerName = generalOp.getNameByID(a.getApplyerID(), "userName", "user");
    		a.setApplyerName(applyerName);
    		a.setProjectName(name);
    	}
		
		return applicationRequests;
	}
	
	public List<Application> getApplicationByState(int state){
		List<Application> applicationRequests = new LinkedList<Application>();
		String sql = "select * from application "
				+ "where state == " + state + " and "
				+ "projectID in ("
				+ "select projectID from project "
				+ "where userID == " + managerID + ") "
				+ "order by applyTime DESC";
		ResultSet result;
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			result = dbHelper.getPst().executeQuery();
			while (result.next()){
				int applicationID = result.getInt("applicationID");
				int projectID = result.getInt("projectID");
				int applyerID = result.getInt("applyerID");
	
				applicationRequests.add(new Application(applicationID, applyerID, projectID, state));				
			}
			result.close();
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Iterator<Application> it=applicationRequests.iterator();
    	while(it.hasNext()){
    		Application a = (Application)it.next();
    		String applyerName = generalOp.getNameByID(a.getApplyerID(), "userName", "user");
    		a.setApplyerName(applyerName);
    		String projectName = generalOp.getNameByID(a.getProjectID(), "projectName", "project");
    		a.setProjectName(projectName);
    	}
		
    	List<Application> allApplication = sortRequest(applicationRequests);
		return allApplication;
	}

	/**
	 * @description: move the "wait" application to the front
	 */
	private List<Application> sortRequest(List<Application> appList){
		List<Application> newAppList = new LinkedList<Application>();
		Iterator<Application> it= appList.listIterator();
		while (it.hasNext()){
			Application a = it.next();
			if (a.getState() == WAITING){
				it.remove();
				newAppList.add(a);
			}
		}
		it= appList.listIterator();
		while (it.hasNext()){
			Application a = it.next();
			newAppList.add(a);
		}
		return newAppList;
	}
	
	public static void main(String[] a){
		List<String> l = new LinkedList<String>();
		List<String> newl = new LinkedList<String>();
		for (int i = 0; i < 8; i++){
			l.add(""+i);
		}
		Iterator<String> it= l.listIterator();
		while (it.hasNext()){
			String s = it.next();
			if (s.equals("6") || s.equals("2")){
				it.remove();
				newl.add(s);
			}
		}
		it= l.listIterator();
		while (it.hasNext()){
			String s = it.next();
				newl.add(s);
		}
		for (int i = 0, len = newl.size(); i < len; i++) {  
		    System.out.print(newl.get(i) + " ");  
		}
	}
}
