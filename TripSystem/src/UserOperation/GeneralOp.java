package UserOperation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import bean.Application;
import bean.User;
import dao.DBHelper;

public class GeneralOp {
	private String url;
	
	public GeneralOp(String url){
		this.url = url;
	}
	
	public String getNameByID(String id, String name, String table){
		String nameByID = "";
		
		String sql = "select " + name + " from " + table + " "
				+ "where "+ table + "ID = " + id;		
		ResultSet result;
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			result = dbHelper.getPst().executeQuery();
			while (result.next()){
				nameByID = result.getString(name);			
			}
			result.close();
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nameByID;
	}
	
	public int getProjectIDByApplicationID(String applicationID){
		int projectID = -1;
		String sql = "select projectID from application "
					+ "where applicationID = " + applicationID;		
		ResultSet result;
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			result = dbHelper.getPst().executeQuery();
			while (result.next()){
				projectID = result.getInt("projectID");			
			}
			result.close();
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projectID;
	}
	
	public int getTripIDByApplicationID(String applicationID){
		int tripID = -1;
		String sql = "select tripID from trip "
					+ "where applicationID = " + applicationID;		
		ResultSet result;
		DBHelper dbHelper = new DBHelper(url, sql);    	
		try {
			result = dbHelper.getPst().executeQuery();
			while (result.next()){
				tripID = result.getInt("tripID");			
			}
			result.close();
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tripID;
	}
	
	public static void main(String[] a){
		GeneralOp g = new GeneralOp("jdbc:mysql://localhost:3306/trip?useUnicode=true&characterEncoding=UTF-8");
		String s = g.getNameByID("2015110001", "userName", "user");
		System.out.println(s);
//		for (int i = 0, len = l.size(); i < len; i++){
//			System.out.println(l.get(i).getProjectName());
//		}
	}

}
