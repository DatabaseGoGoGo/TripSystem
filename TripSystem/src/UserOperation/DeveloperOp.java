package UserOperation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import dao.DBHelper;
import bean.Assignment;
import Config.Config;

public class DeveloperOp {
	private final int CONFIRMED = 0;
	private final int WAITING = 2;
	
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
	 * @description get all unconfimed assignments
	 * @return
	 */
	public List<Assignment> getAssignments(){
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
}
