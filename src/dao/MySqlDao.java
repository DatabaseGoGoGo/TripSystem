package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bean.Project;
import bean.User;

/**
 * Created by Jun Yuan on 2015/12/30.
 */
public class MySqlDao {
  private static MySqlDao dao;

  private static String driver = "com.mysql.jdbc.Driver";
  private String url = "jdbc:mysql://localhost:3306/trip?useUnicode=true&characterEncoding=UTF-8";
  //sql user information
  private String dbUsername = "root";
//  private String dbPassword = "950720SophiaYj";
  private String dbPassword = "mm941216";

  private MySqlDao() {}

  public static MySqlDao getInstance() {
    if (dao == null) {
      dao = new MySqlDao();
    }
    return dao;
  }

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

  public void addUsers(List<User> users) throws SQLException {
    Connection connection = null;
    Statement statement = null;
    try {
      connection = DriverManager.getConnection(url, dbUsername, dbPassword);
      statement = connection.createStatement();
      Set<String> userIDs = new HashSet<>();
      for (User user : users) {
        if (userIDs.contains(user.getUserID())) {
          continue;
        }
        String query1 = String.format(
            "insert user(userID, userName, role, password) values('%s', '%s', %d, '%s');", user.getUserID(),
            user.getUserName(), user.getRole(), user.getPassword());
        String query2 = "";
        switch (user.getRole()) {
        case 0:
        	query2 = String.format("insert admin(userID) values('%s');", user.getUserID());
        	break;
        case 1:
        	query2 = String.format("insert manager(userID) values('%s');", user.getUserID());
        	break;
        case 2:
        	query2 = String.format("insert salesman(userID) values('%s');", user.getUserID());
        	break;
        case 3:
        	query2 = String.format("insert developer(userID) values('%s');", user.getUserID());
        	break;
        }
        statement.addBatch(query1);
        statement.addBatch(query2);
        userIDs.add(user.getUserID());
      }
      statement.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (statement != null) {
        statement.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
  }

  public void addProjects(List<Project> projects) throws SQLException {
    Connection connection = null;
    Statement statement = null;
    try {
      connection = DriverManager.getConnection(url, dbUsername, dbPassword);
      statement = connection.createStatement();
      for (Project project : projects) {
        String query = String.format(
            "insert project(projectID, userID, projectName, projectDescription) values(%d, (%s), '%s', '%s')", 
            project.getProjectID(), 
            "select userID from manager where userID='" + project.getManagerID() +"'",
            project.getProjectName(), project.getProjectDescription());
        statement.addBatch(query);
      }
      statement.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (statement != null) {
        statement.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
  }

  public void createTable(List<String> querys) throws SQLException {
    Connection connection = null;
    Statement statement = null;
    try {
      connection = DriverManager.getConnection(url, dbUsername, dbPassword);
      statement = connection.createStatement();
      for (String query : querys) {
        statement.addBatch(query);
      }
      statement.executeBatch();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (statement != null) {
        statement.close();
      }
      if (connection != null) {
        connection.close();
      }
    }
  }
}
