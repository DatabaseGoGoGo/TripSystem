package UserOperation;

import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Application;
import dao.DBHelper;

public class GeneralOp {
	private String url;
	
	public GeneralOp(String url){
		this.url = url;
	}
	
	public String getNameByID(int id, String name, String table){
		String nameByID = "";
		
		String sql = "select " + name + " from " + table + " "
				+ "where "+ table + "ID == " + id;		
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
}
