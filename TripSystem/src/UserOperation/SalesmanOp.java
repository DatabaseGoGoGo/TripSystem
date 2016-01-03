package UserOperation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import dao.DBHelper;
import bean.Application;
import bean.Assignment;
import bean.RejectLog;
import bean.Project;
import bean.Trip;
import Config.Config;

public class SalesmanOp {
	private final int WAITING = 2;
	private String salesmanID;
	private String url;
	GeneralOp generalOp;
	
	public SalesmanOp(String userID){
		this.salesmanID = userID;
		Config config = new Config();
		url = config.getUrl();
		generalOp = new GeneralOp(url);
	}
	
	public List<Application> getAllApplications() {
		List<Application> myApplications = new LinkedList<Application>();
		String sql = "select * from application where"
				+ " userID='" + salesmanID +"';";
		
		ResultSet result;
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			result = dbHelper.getPst().executeQuery();
			while (result.next()){
				int applicationID = result.getInt("applicationID");
				String applicationName = result.getString("applicationName");
				int projectID = result.getInt("projectID");
				int state = result.getInt("state");
				int groupSize = result.getInt("groupSize");
				myApplications.add(new Application(applicationID, applicationName, salesmanID, projectID, state, groupSize));				
			}
			result.close();
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return myApplications;
	}
	
	public List<Application> getApplicationByState(int state){
		List<Application> applicationRequests = new LinkedList<Application>();
		String sql = "select * from application "
				+ "where state = " + state + " and "
				+ "userID = '" + salesmanID +"' "
				+ "order by applyTime DESC";
		ResultSet result;
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			result = dbHelper.getPst().executeQuery();
			while (result.next()){
				int applicationID = result.getInt("applicationID");
				String applicationName = result.getString("applicationName");
				int projectID = result.getInt("projectID");
				String applyerID = result.getString("userID");	
				int groupSize = result.getInt("groupSize");
				applicationRequests.add(new Application(applicationID, applicationName, applyerID, projectID, state, groupSize));				
			}
			result.close();
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
    	List<Application> allApplication = sortRequest(applicationRequests);
		return allApplication;
	}
	
	public List<RejectLog> getRejectLogs(int applicationID) {
		List<RejectLog> log = new LinkedList<RejectLog>();
		String sql = "select * from rejectionLog "
				+ "where applicationID = " + applicationID;
		ResultSet result;
    	DBHelper dbHelper = new DBHelper(url, sql);    	
    	try {
    		result = dbHelper.getPst().executeQuery();
    		while (result.next()){
    			int rejectID = result.getInt("rejectionID");
    			String rejectReason = result.getString("rejectReason");

    			log.add(new RejectLog(rejectID, applicationID, rejectReason));
    		}
    		result.close();
    		dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return log;
	}
	
	public void updateTrip(Trip trip) {
		String sql = "update trip "
				+ "set departTime = '" + trip.getDepartTime() +"', \n"
				+ "days = " + trip.getDays() + ", \n"
				+ "description = '" + trip.getDescription() + "', \n"
				+ "state = " + trip.getState() + ";";
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			dbHelper.getPst().executeUpdate();			
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateApplication(Application application) {
		String sql = "update application "
				+ "set applyTime = " + application.getApplyTime() + ", "
				+ "state = " + application.getState() + ", "
				+ "applicationName = '" + application.getApplicationName() + "', " 
				+ "groupSize = " + application.getGroupSize() + " "
				+ "where applicationID = " + application.getApplicationID() + ";";
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			dbHelper.getPst().executeUpdate();	
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createApplication(Application application, Trip trip) {
		String sql = "insert into application(applicationID, projectID, userID, \n"
				+ "applicationName, state, rejectTimes, groupSize) \n"
				+ "values(" + application.getApplicationID() + ", \n"
				+ application.getProjectID() + ", \n"
				+ "'" + salesmanID + "', \n"
				+ "'" + application.getApplicationName() + "', \n"
				+ application.getState() + ", \n"
				+ application.getRejectTimes() + ", "
				+ application.getGroupSize() + ");";
		DBHelper dbHelper = new DBHelper(url, sql);  
//		System.out.println(sql);
		try {
			dbHelper.getPst().executeUpdate();			
			dbHelper.close();
			
			createTrip(trip);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createTrip(Trip trip) {
		String sql = "insert into trip(tripID, applicationID, departTime, days, \n"
				+ "description, state) values(\n"
				+ trip.getTripID() + ", " + trip.getApplicationID() + ", \n"
				+ "'" + trip.getDepartTime() + "', " + trip.getDays() + ", \n"
				+ "'" + trip.getDescription() + "', " + trip.getState() + ");\n";
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			dbHelper.getPst().executeUpdate();			
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void assignTrip(String developerID, int applicationID) {
		String sql = "insert into assign(applicationID, userID, state) values("
				+ applicationID + ", '" + developerID +"', "
				+ Assignment.WAITING + ");";
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			dbHelper.getPst().executeUpdate();			
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Set<Integer> getProjectIDsByUserID(String userID) {
		String sql = "select * from sell where userID = '" + userID +"';";
		ResultSet result;
    	DBHelper dbHelper = new DBHelper(url, sql);   
    	Set<Integer> projects = new HashSet<Integer>();
    	try {
    		result = dbHelper.getPst().executeQuery();
    		while (result.next()){
    			int projectID = result.getInt("projectID");
    			projects.add((Integer)projectID);
    		}
    		result.close();
    		dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return projects;
	}
	
	public Trip getTripByApplicationID(int applicationID) {
		String sql = "select * from trip where applicationID = " + applicationID +";";
		ResultSet result;
    	DBHelper dbHelper = new DBHelper(url, sql);   
    	try {
    		result = dbHelper.getPst().executeQuery();
    		Trip trip = null;
    		if (result.next()){
    			int tripID = result.getInt("tripID");
    			Timestamp departTime = result.getTimestamp("departTime");
    			int days = result.getInt("days");
    			String description = result.getString("description");
    			int state = result.getInt("state");
    			trip = new Trip(tripID, applicationID, departTime, days, description, state);
    		}
    		result.close();
    		dbHelper.close();
			return trip;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return null;
	}
	
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
	
}
