package UserOperation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import bean.Application;
import bean.Assignment;
import bean.Trip;
import bean.TripRecord;
import bean.User;
import Config.Config;
import dao.DBHelper;

public class ManagerOp {
	// application
	private final int APPROVED = 0;
	private final int REFUSED = 1;
	private final int WAITING = 2;
	private final int CANCELED = 3;
	// trip
	private final int FINISHED = 0;
	private final int ONGOING = 1;
	
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
		String sql = "select * from application, user "
					+ "where application.userID = user.userID and "
					+ "application.projectID in ("
					+ "select projectID from project "
					+ "where project.userID = " + managerID + ") "
					+ "order by applyTime";
    	ResultSet result;
    	DBHelper dbHelper = new DBHelper(url, sql);    	
    	try {
    		result = dbHelper.getPst().executeQuery();
    		while (result.next()){
    			int applicationID = result.getInt("applicationID");
    			String applicationName = result.getString("applicationName");
    			int projectID = result.getInt("projectID");
    			String applyerID = result.getString("userID");
    			String applyerName = result.getString("userName");
    			int state = result.getInt("state");
    			int groupSize = result.getInt("groupSize");
    			Timestamp applyTime = result.getTimestamp("applyTime");
    			applicationRequests.add(new Application(applicationID, applicationName, applyerID, 
    					applyerName, projectID, state, applyTime, groupSize));
    		}
    		result.close();
    		dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	 
		return sortRequest(applicationRequests);
//    	return applicationRequests;
	}

	public List<Application> getApplicationByProjectName(String name){
		List<Application> applicationRequests = new LinkedList<Application>();
		String sql = "select * from application, user "
				+ "where application.userID = user.userID and "
				+ "projectID in ("
				+ "select projectID from project "
				+ "where projectName like '%" + name + "%') "
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
				String applyerName = result.getString("userName");
				int state = result.getInt("state");
    			int groupSize = result.getInt("groupSize");
    			Timestamp applyTime = result.getTimestamp("applyTime");
    			applicationRequests.add(new Application(applicationID, applicationName, applyerID, 
    					applyerName, projectID, state, applyTime, groupSize));
			}
			result.close();
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return applicationRequests;
	}
	
	public List<Application> getApplicationByState(int state){
		List<Application> applicationRequests = new LinkedList<Application>();
		String sql = "select * from application, user "
				+ "where application.userID = user.userID and "
				+ "state = " + state + " and "
				+ "projectID in ("
				+ "select projectID from project "
				+ "where userID = '" + managerID + "') "
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
				String applyerName = result.getString("userName");
    			int groupSize = result.getInt("groupSize");
    			Timestamp applyTime = result.getTimestamp("applyTime");
    			applicationRequests.add(new Application(applicationID, applicationName, applyerID, 
    					applyerName, projectID, state, applyTime, groupSize));
			}
			result.close();
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return applicationRequests;
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
		if (state == REFUSED){
			updateRejectTimes(applicationID);
		}
	}
	
	private void updateRejectTimes(int applicationID){
		String sql = "update application "
				+ "set rejectTimes = rejectTimes + 1 "
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
					+ "values("+ applicationID + ", '" + reason + "')";
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			dbHelper.getPst().executeUpdate();			
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public List<User> getAllDeveloperByApplicationID(int applicationID){
		int projectID = generalOp.getProjectIDByApplicationID(applicationID+"");
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
	
	public boolean assignDevelopersToTrip(int applicationID, String[] developers){
		if (!checkForGroupSize(applicationID, developers.length)){ // assigned num > request num
			return false;
		}
		for (int i = 0; i < developers.length; i++){
			String sql = "insert ignore into assign(applicationID, userID, state) "
					+ "values("+ applicationID + ", " + developers[i] + ", " + WAITING + ")";
			DBHelper dbHelper = new DBHelper(url, sql);    	
			try {
				dbHelper.getPst().executeUpdate();			
				dbHelper.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		startTrip(applicationID);
		return true;
	}
	
	private boolean checkForGroupSize(int applicationID, int size){
		int groupSize = 0;
		String sql = "select groupSize from application "
					+ "where applicationID = " + applicationID;
    	ResultSet result;
    	DBHelper dbHelper = new DBHelper(url, sql);    	
    	try {
    		result = dbHelper.getPst().executeQuery();
    		while (result.next()){
    			groupSize = result.getInt("groupSize");    		
    		}
    		result.close();
    		dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return size < groupSize;
	}
	
	private void startTrip(int applicationID){
		String sql = "update trip "
				+ "set state = " + ONGOING + " "
				+ "where applicationID = " + applicationID;
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			dbHelper.getPst().executeUpdate();			
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// ==============================================
	// check application
	// ==============================================
	
	
	// ==============================================
	// view assignment state
	// ==============================================
	/**
	 * @description check this assignment's developers' confirmed state
	 * @param applicationID
	 * @return
	 */
	public Assignment getAssignmentStateByID(int applicationID){
		List<User> developersAssignedTo = new LinkedList<User>();
		List<Integer> developerState = new LinkedList<Integer>();
		String sql = "select * from assign, user "
					+ "where applicationID = " + applicationID;
    	ResultSet result;
    	DBHelper dbHelper = new DBHelper(url, sql);
    	try {
    		result = dbHelper.getPst().executeQuery();
    		while (result.next()){
    			String developerID = result.getString("userID");
    			String developerName = result.getString("userName");
    			int state = result.getInt("state");
    			developersAssignedTo.add(new User(developerID, developerName));
    			developerState.add(state);
    		}
    		result.close();
    		dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
//    	// set userName
//    	Iterator<User> it = developersAssignedTo.iterator();
//    	while(it.hasNext()){
//    		User user = (User)it.next();
//    		String develpoerName = generalOp.getNameByID(user.getUserID(), "userName", "user");
//    		user.setUserName(develpoerName);
//    	}
    	// init assignment
    	Assignment assignment = new Assignment(applicationID);
    	assignment.setDevelopersAssignedTo(developersAssignedTo);
    	assignment.setDeveloperState(developerState);
    	
    	return assignment;
	}
	
	public List<Trip> getAllTripState(){
		List<Trip> trips = new LinkedList<Trip>();
		String sql = "select tripID, applicationID, state from trip "
					+ "where applicationID in (" 
						+ "select application.applicationID from application "
						+ "where state = " + APPROVED + " and "
						+ "application.projectID in ("
							+ "select projectID from project "
							+ "where project.userID = " + managerID + ") "
					+ ")";
    	ResultSet result;
    	DBHelper dbHelper = new DBHelper(url, sql);    	
    	try {
    		result = dbHelper.getPst().executeQuery();
    		while (result.next()){
    			int tripID = result.getInt("tripID");
    			int applicationID = result.getInt("applicationID");
    			int state = result.getInt("state");
    			trips.add(new Trip(tripID, applicationID, state));
    		}
    		result.close();
    		dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	Iterator<Trip> it = trips.iterator();
    	while(it.hasNext()){
    		Trip trip = (Trip)it.next();
    		String tripName = generalOp.getNameByID(trip.getApplicationID()+"", "applicationName", "application");
    		trip.setTripName(tripName);
    	}
    	
    	return trips;
	}
	// ==============================================
	// view assignment state
	// ==============================================
	
	// ==============================================
	// view finished trips' log
	// ==============================================
	/**
	 * @description the trip with tripID should be finished
	 * 				get the records of this trip
	 * @param tripID
	 * @return
	 */
	public List<TripRecord> getFinishedTripRecord(int tripID){
		List<TripRecord> records = new LinkedList<TripRecord>();
		String sql = "select * from tripRecord, user "
					+ "where tripID = " + tripID;
    	ResultSet result;
    	DBHelper dbHelper = new DBHelper(url, sql);    	
    	try {
    		result = dbHelper.getPst().executeQuery();
    		while (result.next()){
    			String developerID = result.getString("userID");
    			String developerName = result.getString("userName");
    			Date actualDepartTime = result.getDate("actualDepartTime");
    			int actualTripDays = result.getInt("actualTripDays");    			
    			String tripContent = result.getString("tripContent");

    			records.add(new TripRecord(tripID, developerID, developerName, actualDepartTime, actualTripDays, tripContent));
    		}
    		result.close();
    		dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    
//    	Iterator<TripRecord> it = records.iterator();
//    	while(it.hasNext()){
//    		TripRecord record = (TripRecord)it.next();
//    		String developerName = generalOp.getNameByID(record.getDeveloperID(), "userName", "user");
//    		record.setDeveloperName(developerName);
//    	}
    	
		return records;
	}
	
	// ==============================================
	// view finished trips' log
	// ==============================================
	
	public void assignDeveloperToProject(int projectID, String userID){
		String sql = "insert ignore into develop(projectID, userID) "
				+ "values("+ projectID + ", '" + userID + "')";
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			dbHelper.getPst().executeUpdate();			
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static void main(String[] a){
		ManagerOp m = new ManagerOp("2015110009");
		// getAllApplication();
//		List<Application> l = m.getAllApplication();
		// getApplicationByProjectName
//		List<Application> l = m.getApplicationByProjectName("№ЬАн");
		// getApplicationByState(0)
//		List<Application> l = m.getApplicationByState(2);
//		for (int i = 0, len = l.size(); i < len; i++){
//			System.out.println(l.get(i).getApplicationID());
//		}
//		System.out.println(l.size());
		
		// setApplicationState
//		m.setApplicationState(1660004646, 0);
//		m.assignDevelopersToTrip(1660004646, developers);
		// giveRefusedReason
//		m.giveRefusedReason(1660004646, "~~~~~~~~~~`");
		
		
		// getAllDeveloperByApplicationID
//		List<User> l = m.getAllDeveloperByApplicationID(51438896);
//		for (int i = 0, len = l.size(); i < len; i++){
//			System.out.println(l.get(i).getUserID());
//		}
//		System.out.println(l.size());
		// assignDevelopersToTrip(int, int[])
//		String[] developers = {"2015110003", "2015110001", "2015110006"};
//		m.assignDevelopersToTrip(1660025553, developers);
		
//		m.assignDeveloperToProject(2015120008, "2015110003");
//		m.assignDeveloperToProject(2015120008, "2015110006");
//		m.assignDeveloperToProject(2015120008, "2015110001");
//		m.assignDeveloperToProject(2015120008, "2015110002");
		
		List<Trip> l = m.getAllTripState();
		for (int i = 0, len = l.size(); i < len; i++){
			System.out.println(l.get(i).getState());
		}
		

	}
}
