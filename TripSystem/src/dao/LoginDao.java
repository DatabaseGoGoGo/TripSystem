package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Config.Config;
import bean.User;

public class LoginDao {
	private static LoginDao dao;
	
	private static String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/trip?useUnicode=true&characterEncoding=UTF-8";
	//sql user information
	private String dbUsername = "root";
	private String dbPassword = Config.getPassword();
	
	static {
		  try {
		    Class.forName(driver).newInstance();
		  } catch (ClassNotFoundException e) {
		    e.printStackTrace();
		  } catch (InstantiationException e) {
		    e.printStackTrace();
		  } catch (IllegalAccessException e) {
			e.printStackTrace();
		  }
	}
	
	public static LoginDao getInstance() {
	    if (dao == null) {
	      dao = new LoginDao();
	    }
	    return dao;
	}
	
	public User login(String userID, String password) throws SQLException{
		Connection con = null;
		Statement sm = null;
		ResultSet results = null;
		try {
			con = DriverManager.getConnection(url, dbUsername, dbPassword);
			sm = con.createStatement();
			results = sm.executeQuery("select * from user where userID='"+userID+"'");
			if(results.next()){
				String oldPassword = results.getString("password");
				if(oldPassword.equals(password)){
					String userName = results.getString("userName");
					int role = Integer.parseInt(results.getString("role"));
					User user = new User(userID, userName, password, role);
					return user;
				}else{
					return null;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(sm != null){
				sm.close();
			}
			if(con != null){
				con.close();	
			}
			if(results != null){
				results.close();
			}
		}
		return null;
	}
}
