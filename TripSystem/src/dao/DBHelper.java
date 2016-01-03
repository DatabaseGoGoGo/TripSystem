package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Config.Config;


public class DBHelper {
	private static String driver = "com.mysql.jdbc.Driver";
	
	//your username and password
	private String dbUsername = "root"; 
	private String dbPassword = Config.getPassword();
	
	private Connection connection = null;
	private PreparedStatement pstatement= null;
	
	public DBHelper (String url, String sql){
		try{
			// 加载MySql的驱动类   
			Class.forName(driver); //determine the connect type
			this.connection = DriverManager.getConnection(url, dbUsername, dbPassword);
			this.pstatement = connection.prepareStatement(sql); // prepared to execute
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	

	
	public PreparedStatement getPst(){
		return pstatement;
	}
	
	
	public void close(){
		try {
			this.connection.close();
			this.pstatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
}
