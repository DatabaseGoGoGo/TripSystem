package UserOperation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import bean.Application;
import Config.Config;
import dao.DBHelper;

public class ManagerOp {
	private int managerID;
	private String url;
	private List<Application> applicationRequests = new LinkedList<Application>();
	
	public ManagerOp(int managerID){
		this.managerID = managerID;
		Config config = new Config();
		url = config.getUrl();
	}
	
	public void getApplicationRequest(){
		String sql = "select * from application "
					+ "where projectID in ("
					+ "select projectID from project "
					+ "where userID == " + managerID + ")";
    	ResultSet result;
    	DBHelper dbHelper = new DBHelper(url, sql);    	
    	try {
    		result = dbHelper.getPst().executeQuery();
    		while (result.next()){
    			int applicationID = result.getInt("applicationID");
    			int projectID = result.getInt("projectID");
    			int applyerID = result.getInt("applyerID");
    			int state = result.getInt("state");

    			applicationRequests.add(new Application(applicationID, projectID, state));
    			
    		}
    		result.close();
    		dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
	}
}
