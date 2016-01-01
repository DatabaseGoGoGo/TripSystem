import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Project;
import bean.User;
import dao.MySqlDao;

/**
 * Created by Jun Yuan on 2015/12/31.
 */
public class DatabaseOperation {
  public static void main(String[] argv) {  
      operateXlsFile();
  }

  public static void operateXlsFile() {
    MySqlDao mySqlDao = MySqlDao.getInstance();

    Workbook workbook = null;
    try {
      workbook = Workbook.getWorkbook(new File("ExcelData/01初始用户数据.xls"));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (BiffException e) {
      e.printStackTrace();
    }
    Sheet sheet = workbook.getSheet(0);
    List<User> users = new ArrayList<>();
    for (int index = 1; index < sheet.getRows(); index++) {
      Cell cell = sheet.getCell(0, index);
      String userID = cell.getContents();
      
      cell = sheet.getCell(1, index);
      String userName = cell.getContents();
      
      cell = sheet.getCell(2, index);
      String password = cell.getContents();
      
      cell = sheet.getCell(3, index);
      String role = cell.getContents();
      
      User user = new User(userID, userName, password, role);
      users.add(user);
    }

    List<Project> projects = new ArrayList<>();
    try {
      workbook = Workbook.getWorkbook(new File("ExcelData/02初始项目数据.xls"));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (BiffException e) {
      e.printStackTrace();
    }
    sheet = workbook.getSheet(0);
    for (int row = 1; row < sheet.getRows(); row++) {
      Cell cell = sheet.getCell(0, row);
      if (cell.getContents().equals("")) {
    	break;
      }
      int projectID = Integer.parseInt(cell.getContents());

      cell = sheet.getCell(1, row);
      String projectName = cell.getContents();

      cell = sheet.getCell(2, row);
      String projectDescription = cell.getContents();

      cell = sheet.getCell(3, row);
      String managerID = cell.getContents();

      Project project = new Project(projectID, managerID, projectName, projectDescription);
      projects.add(project);
    }
    try {
      mySqlDao.addUsers(users);
      mySqlDao.addProjects(projects);
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }
  
  public static List<String> readSqlFile(String filename) {
    StringBuffer temp = new StringBuffer();
    List<String> sqlList = new ArrayList<>();
    try {
      BufferedReader in = new BufferedReader(new FileReader(filename));
      String str;
      while ((str = in.readLine()) != null) {
        temp.append(str);
      }
      in.close();
    } catch (IOException e) {
      e.getStackTrace();
    }
    String sqls[] = temp.toString().split(";");
    for (String sql : sqls) {
      sqlList.add(sql);
    }
    return sqlList;
  }
}
