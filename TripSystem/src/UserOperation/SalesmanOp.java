package UserOperation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import dao.DBHelper;
import bean.Application;
import bean.RejectLog;
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
				myApplications.add(new Application(applicationID, applicationName, salesmanID, projectID, state));				
			}
			result.close();
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Iterator<Application> it= myApplications.iterator();
    	while(it.hasNext()){
    		Application a = (Application)it.next();
    		String applyerName = generalOp.getNameByID(a.getApplyerID(), "userName", "user");
    		a.setApplyerName(applyerName);
    		String projectName = generalOp.getNameByID(a.getProjectID()+"", "projectName", "project");
    		a.setProjectName(projectName);
    	}
		
		return myApplications;
	}
	
	public List<Application> getApplicationByState(int state){
		List<Application> applicationRequests = new LinkedList<Application>();
		String sql = "select * from application "
				+ "where state == " + state + " and "
				+ "userID == '" + salesmanID +"' "
				+ "order by applyTime DESC";
		ResultSet result;
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			result = dbHelper.getPst().executeQuery();
			while (result.next()){
				int applicationID = result.getInt("applicationID");
				String applicationName = result.getString("applicationName");
				int projectID = result.getInt("projectID");
				String applyerID = result.getString("applyerID");	
				applicationRequests.add(new Application(applicationID, applicationName, applyerID, projectID, state));				
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
    			int rejectTimes = result.getInt("rejectTimes");

    			log.add(new RejectLog(rejectID, applicationID, rejectReason, rejectTimes));
    		}
    		result.close();
    		dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return log;
	}
	
	public void updateTrip(Trip trip) {
		String sql = "update trip"
				+ "set departTime = " + trip.getDepartTime() +", "
				+ "days = " + trip.getDays() + ", "
				+ "description = " + trip.getDescription() + ", "
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
				+ "set applyTime = " + application.getApplyTime() + " "
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
		String sql = "insert into application(applicationID, projectID, userID, "
				+ "applicationName, state, applyTime, rejectTimes) "
				+ "values(" + application.getApplicationID() + ", "
				+ application.getProjectID() + ", "
				+ salesmanID + ", "
				+ application.getApplicationName() + ", "
				+ application.getState() + ", "
				+ application.getApplyTime() + ", "
				+ application.getRejectTimes() + ");";
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			dbHelper.getPst().executeUpdate();			
			dbHelper.close();
			
			createTrip(trip);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createTrip(Trip trip) {
		String sql = "insert into trip(tripID, applicationID, departTime, days, "
				+ "description, state) values("
				+ trip.getTripID() + ", " + trip.getApplicationID() + ", "
				+ trip.getDepartTime() + ", " + trip.getDays() + ", "
				+ trip.getDescription() + ", " + trip.getState() + ");";
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			dbHelper.getPst().executeUpdate();			
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
