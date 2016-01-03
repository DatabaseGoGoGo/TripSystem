package UserOperation;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import dao.DBHelper;
import bean.Application;
import bean.Assignment;
import bean.Project;
import bean.Trip;
import Config.Config;

public class DeveloperOp {
	// assignment
	private final int CONFIRMED = 0;
	private final int WAITING = 2;
	// trip
	private final int FINISHED = 0;
	private final int ONGOING = 1;
	
	private String developerID;
	private String url;
	GeneralOp generalOp;
	
	public DeveloperOp(String developerID){
		this.developerID = developerID;
		Config config = new Config();
		url = config.getUrl();
		generalOp = new GeneralOp(url);
		
	}
	
	/**
	 * @description get all confimed assignments
	 * @return
	 */
	public List<Assignment> getConfirmedAssignments(){
		List<Assignment> assignments = new LinkedList<Assignment>();
		String sql = "select applicationID, applicationName from application "
					+ "where applicationID in ( "
					+ "select applicationID from assign "
					+ "where state = " + CONFIRMED + " and "
					+ "userID = " + developerID + ")";
    	ResultSet result;
    	DBHelper dbHelper = new DBHelper(url, sql);
    	try {
    		result = dbHelper.getPst().executeQuery();
    		while (result.next()){
    			int applicationID = result.getInt("applicationID");
    			String applicationName = result.getString("applicationName");
    			
    			assignments.add(new Assignment(applicationID, applicationName));
    		}
    		result.close();
    		dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return assignments;
	}
	
	/**
	 * @description get all unconfimed assignments
	 * @return
	 */
	public List<Assignment> getUnconfirmedAssignments(){
		List<Assignment> assignments = new LinkedList<Assignment>();
		String sql = "select applicationID, applicationName from application "
					+ "where applicationID in ( "
					+ "select applicationID from assign "
					+ "where state = " + WAITING + " and "
					+ "userID = " + developerID + ")";
    	ResultSet result;
    	DBHelper dbHelper = new DBHelper(url, sql);
    	try {
    		result = dbHelper.getPst().executeQuery();
    		while (result.next()){
    			int applicationID = result.getInt("applicationID");
    			String applicationName = result.getString("applicationName");
    			
    			assignments.add(new Assignment(applicationID, applicationName));
    		}
    		result.close();
    		dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return assignments;
	}
	
	
	public void confirmAssigment(int applicationID){
		String sql = "update assign "
				+ "set state = " + CONFIRMED + " "
				+ "where userID = '" + developerID + "' and "
				+ "applicationID = " + applicationID;
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			dbHelper.getPst().executeUpdate();			
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void handinRecord(String applicationID, String actualDepartTime, String actualTripDays, String tripContent){
		int tripID = generalOp.getTripIDByApplicationID(applicationID);
		Date date = generalOp.String2Date(actualDepartTime);
		List<Assignment> assignments = new LinkedList<Assignment>();
		String sql = "insert ignore into triprecord(tripID, userID, actualDepartTime, actualTripDays, tripContent) "
				+ "values("+ tripID + ", '" + developerID + "', '" + actualDepartTime + "', " + actualTripDays + ", '" + tripContent + "')";
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			dbHelper.getPst().executeUpdate();			
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		checkTripState(tripID, applicationID);
	}
	
	public void checkTripState(int tripID, String applicationID){
		int developerNum = getDeveloperNum(applicationID);
		int recordNum = getRecordNum(tripID);
		System.out.println(developerNum);
		System.out.println(recordNum);
		if (recordNum == developerNum){
			String sql = "update trip "
					+ "set state = " + FINISHED + " "
					+ "where applicationID = " + applicationID;
			DBHelper dbHelper = new DBHelper(url, sql);    	
			try {
				dbHelper.getPst().executeUpdate();			
				dbHelper.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private int getDeveloperNum (String applicationID){
		int developerNum = 0;
		String sql = "select count(userID) as developerNum from assign "
					+ "where applicationID = " + applicationID + " and "
					+ "state = " +  CONFIRMED;
    	ResultSet result;
    	DBHelper dbHelper = new DBHelper(url, sql);
    	try {
    		result = dbHelper.getPst().executeQuery();
    		while (result.next()){
    			developerNum = result.getInt("developerNum");
    		}
    		result.close();
    		dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return developerNum;
	}

	private int getRecordNum(int tripID){
		int recordNum = 0;
		String sql = "select count(userID) as recordNum from triprecord "
					+ "where tripID = " + tripID;
    	ResultSet result;
    	DBHelper dbHelper = new DBHelper(url, sql);
    	try {
    		result = dbHelper.getPst().executeQuery();
    		while (result.next()){
    			recordNum = result.getInt("recordNum");
    		}
    		result.close();
    		dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return recordNum;
	}

	// ==========================================
	// check the pj responsible for
	// ==========================================
	public List<Project> getAllprojects(){
		List<Project> projects = new LinkedList<Project>();
		String sql = "select * from project, user "
					+ "where project.userID = user.userID and "
					+ "projectID in ("
					+ "select projectID from develop "
					+ "where userID = '" + developerID + "' )";
    	ResultSet result;
    	DBHelper dbHelper = new DBHelper(url, sql);    	
    	try {
    		result = dbHelper.getPst().executeQuery();
    		while (result.next()){
    			int projectID = result.getInt("projectID");
    			String managerID = result.getString("userID");
    			String managerName = result.getString("userName");
    			String projectName = result.getString("projectName");
    			String projectDescription = result.getString("projectDescription");  

    			projects.add(new Project(projectID, managerID, managerName, projectName, projectDescription));
    		}
    		result.close();
    		dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return projects;
	}
	
	public static void main(String[] a){
		GeneralOp generalOp = new GeneralOp("jdbc:mysql://127.0.0.1:3306/trip?useUnicode=true&amp;characterEncoding=UTF-8&amp;");
		DeveloperOp d = new DeveloperOp("2015110003");
		// getUnconfirmedAssignments
//		List<Assignment> l = d.getUnconfirmedAssignments();
		// getConfirmedAssignments
//		List<Assignment> l = d.getConfirmedAssignments();
//		for (int i = 0, len = l.size(); i < len; i++){
//			System.out.println(l.get(i).getApplicationID());
//		}
		
		// confirmAssigment
//		d.confirmAssigment(51438896);
		
//		d.handinRecord(51438896+"", "2020-01-20", "10", "1ffff!~~~");
		

		// getConfirmedAssignments
		List<Project> l = d.getAllprojects();
		for (int i = 0, len = l.size(); i < len; i++){
			System.out.println(l.get(i).getProjectID());
		}
	}

}
