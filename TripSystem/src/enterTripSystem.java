import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

import dao.LoginDao;
import UserOperation.ManagerOp;
import bean.Application;
import bean.User;


public class enterTripSystem {
	private static Scanner scanner = new Scanner(System.in);
	/*Exit string. */
	private static final String EXIT = "exit";
	
	/*Login question and information string. */
	private static final String askForUserId = "please enter your userID:";
	private static final String askForPassword = "please enter your password:";
	private static final String idNotMatchPassword = "Wrong ID or password! PLEASE INPUT THEM AGAIN!";
	
	/*Operation string of an administrator. */
	
	/*Operation string of a salesman. */
	
	/*Operation string of a manager. */
	// input request
	private static final String viewAllApplicationRequests = "show me all requests";
	// output hint
	
	/*Operation string of a developer. */
	
	public static void main(String[] argv) {
		welcome();
        User user = login();
        switch (user.getRole()) {
        case User.ADMIN:
        	ViewOfAdmin(user);
        	break;
        case User.SALESMAN:
        	ViewOfSalesman(user);
        	break;
        case User.MANAGER:
        	ViewOfManager(user);
        	break;
        case User.DEVELOPER:
        	ViewOfDeveloper(user);
        	break;
        }
	}
	
	private static void ViewOfAdmin(User user) {
		
	}
	
	private static void ViewOfSalesman(User user) {
		
	}
	
	private static void ViewOfManager(User user) {
		ManagerOp managerOp = new ManagerOp(user.getUserID());
		String command = "";
		while (scanner.hasNext()){
			command = scanner.next();
			switch (command){
				case viewAllApplicationRequests:
					viewAllApplicationRequests(managerOp.getAllApplication());
					break;
				default:
					break;
			}
		}
			
	}
	
	private static void ViewOfDeveloper(User user) {
		
	}
	
	private static void welcome() {
		System.out.println("Welcome to the Trip System. Have fun!");
	}
	
	private static User login() {
		User user = null;
		LoginDao dao = LoginDao.getInstance();
		while (user == null) {
			System.out.println(askForUserId);
			String userId = scanner.next();
			System.out.println(askForPassword);
			String password = scanner.next();
			try {
				user = dao.login(userId, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (user == null) {
				System.out.println(idNotMatchPassword);
			}
		}
		System.out.println("login successfully!");
		return user;
	}
	
	/* manager operation */
	private static void viewApplications(List<Application> applications){
		for (int i = 0, len = applications.size(); i < len; i++){
			Application a = applications.get(i);
			int applicationID = a.getApplicationID();
			String applicationName = a.getApplicationName();
			String applyerName = a.getApplyerName();
			String state = a.getStateName();
			Timestamp applyTime = a.getApplyTime();
			System.out.print(applicationID + " ");
		}
	}
}
