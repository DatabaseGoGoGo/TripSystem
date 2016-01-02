package UserOperation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import bean.Application;
import bean.User;
import Config.Config;
import dao.DBHelper;

public class ManagerOp {
	private final int APPROVED = 0;
	private final int REFUSED = 1;
	private final int WAITING = 2;
	private final int CANCELED = 3;
	
	private String managerID;
	private String url;
	GeneralOp generalOp;
	
	public ManagerOp(String managerID){
		this.managerID = managerID;
		Config config = new Config();
		url = config.getUrl();
		generalOp = new GeneralOp(url);
	}
	
	// ==============================================
	// get application
	// ==============================================
	public List<Application> getAllApplication(){
		List<Application> applicationRequests = new LinkedList<Application>();
		String sql = "select * from application "
					+ "where application.projectID in ("
					+ "select projectID from project "
					+ "where project.userID = " + managerID + ") "
					+ "order by applyTime DESC";
    	ResultSet result;
    	DBHelper dbHelper = new DBHelper(url, sql);    	
    	try {
    		result = dbHelper.getPst().executeQuery();
    		while (result.next()){
    			int applicationID = result.getInt("applicationID");
    			int projectID = result.getInt("projectID");
    			String applyerID = result.getString("applyerID");
    			int state = result.getInt("state");
    			System.out.println("projectID:" + projectID);
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
    		String projectName = generalOp.getNameByID(a.getProjectID()+"", "projectName", "project");
    		a.setProjectName(projectName);
    	}
    	
    	
    	return applicationRequests;
	}

	public List<Application> getApplicationByProjectName(String name){
		List<Application> applicationRequests = new LinkedList<Application>();
		String sql = "select * from application "
				+ "where projectID in ("
				+ "select projectID from project "
				+ "where projectName = " + name + ") "
				+ "order by applyTime DESC";
		ResultSet result;
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			result = dbHelper.getPst().executeQuery();
			while (result.next()){
				int applicationID = result.getInt("applicationID");
				int projectID = result.getInt("projectID");
				String applyerID = result.getString("applyerID");
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
				+ "where state = " + state + " and "
				+ "projectID in ("
				+ "select projectID from project "
				+ "where userID = " + managerID + ") "
				+ "order by applyTime DESC";
		ResultSet result;
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			result = dbHelper.getPst().executeQuery();
			while (result.next()){
				int applicationID = result.getInt("applicationID");
				int projectID = result.getInt("projectID");
				String applyerID = result.getString("applyerID");
	
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
    		String projectName = generalOp.getNameByID(a.getProjectID()+"", "projectName", "project");
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
	// ==============================================
	// get application
	// ==============================================

	
	// ==============================================
	// check application
	// ==============================================
	public void setApplicationState(int applicationID, int state){
		String sql = "update application "
					+ "set state = " + state + " "
					+ "where applicationID = " + applicationID;
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			dbHelper.getPst().executeUpdate();			
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void giveRefusedReason(int applicationID, String reason){
		String sql = "insert into rejectionlog(applicationID, rejectReason) "
					+ "values("+ applicationID + ", " + reason + ")";
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			dbHelper.getPst().executeUpdate();			
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public List<User> getAllDeveloperByProjectID(String applicationID){
		int projectID = generalOp.getProjectIDByApplicationID(applicationID);
		if (projectID == -1){
			System.out.println("getProjectIDByApplicationID failed");
			return null;
		}
		
		List<User> developers = new LinkedList<User>();
		String sql = "select userID, userName from user "
					+ "where userID in ("
					+ "select userID from develop "
					+ "where projectID = " + projectID + ")";
    	ResultSet result;
    	DBHelper dbHelper = new DBHelper(url, sql);    	
    	try {
    		result = dbHelper.getPst().executeQuery();
    		while (result.next()){
    			String userID = result.getString("userID");
    			String userName = result.getString("userName");

    			developers.add(new User(userID, userName));
    		}
    		result.close();
    		dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return developers;
	}
	
	public void assignDevelopers(int[] developers){
		
	}
	// ==============================================
	// check application
	// ==============================================
	
	
	
	
	
	public static void main(String[] a){
		ManagerOp m = new ManagerOp("2015110009");
		List<Application> l = m.getAllApplication();
		for (int i = 0, len = l.size(); i < len; i++){
			System.out.println(l.get(i).getProjectName());
		}
	}
}
