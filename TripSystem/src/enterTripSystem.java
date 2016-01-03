import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

import dao.LoginDao;
import UserOperation.ManagerOp;
import bean.Application;
import bean.User;


public class enterTripSystem {
	/* default values */
	// application
	public static final int APPROVED = 0;
	public static final int REFUSED = 1;
	public static final int WAITING = 2;
	public static final int CANCELED = 3;
	
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
	// keyword
	private static final String show = "show";
	private static final String search = "search";
	private static final String approve = "approve";
	private static final String reject = "reject";
	// input request
	private static final String viewAllApplicationRequests = "show me all trip requests";
	private static final String searchApplicationByPjName = "search for trip requests by project name";
	private static final String searchApplicationByState = "search for trip requests by state";
	private static final String approveApplication = "approve application";
	private static final String rejectApplication = "reject application";
	private static final String viewAllTripState = "show me all trip state";
	// output hint
	private static final String searchApplicationByPjNameHint = "please enter the key word of the correspond project's name: ";
	private static final String searchApplicationByStateHint = "please enter the state: ";
	private static final String noResult = "No result found!";
	private static final String enterApplicationIDHint = "please enter the applicationID: ";
	private static final String enterRejectReasonHint = "please enter the reject reason: ";
	private static final String assignDevelopersHint = "please enter the developers' id that you want to assign to: (e.g 2015110002, 201511004)";
	
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
		String keyword = "";
		String command = "";		
		while (scanner.hasNextLine()){
			keyword = scanner.next();
			command = keyword + scanner.nextLine();
//			System.out.println(keyword);
//			System.out.println(command);
			switch (keyword){
				case show:
					show(managerOp, command);
					break;
				case search:
					search(managerOp, command);
					break;
				case approve:
					approveApplication(managerOp);
					break;
				case reject:
					rejectApplication(managerOp);
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
		if (applications.size() == 0 ){
			printHint(noResult);
		}
		for (int i = 0, len = applications.size(); i < len; i++){
			System.out.println(applications.get(i).toString());
		}
	}
	
	private static void search(ManagerOp managerOp, String command){
		switch(command){
			case searchApplicationByPjName:
				printHint(searchApplicationByPjNameHint);
				String pjName = scanner.next();
				viewApplications(managerOp.getApplicationByProjectName(pjName));
				break;
			case searchApplicationByState:
				printHint(searchApplicationByStateHint);
				String state = scanner.next();
				viewApplications(managerOp.getApplicationByState(Integer.parseInt(state)));
				break;
			default:
				break;
		}
		
	}
	
	private static void show(ManagerOp managerOp, String command){
		switch (command){			
			case viewAllApplicationRequests:
				System.out.println("1");
				viewApplications(managerOp.getAllApplication());
				break;		
				
			default:
				break;
		}
	}
	
	private static void approveApplication(ManagerOp managerOp){
		printHint(enterApplicationIDHint);
		int applicationID = scanner.nextInt();
		managerOp.setApplicationState(applicationID, APPROVED);
		printHint(assignDevelopersHint);
		String[] developers = splitToken(scanner.nextLine(), ", ");
		while(!managerOp.assignDevelopersToTrip(applicationID, developers)){
			System.out.println("Assigned failed! check if the number of assigned developers exceeds");
			printHint(assignDevelopersHint);
			developers = splitToken(scanner.nextLine(), ", ");
		}
		System.out.println("Assigned successfully");
	}
	
	private static void rejectApplication(ManagerOp managerOp){
		printHint(enterApplicationIDHint);
		int applicationID = scanner.nextInt();
		managerOp.setApplicationState(applicationID, REFUSED);
		printHint(enterRejectReasonHint);
		String reason = scanner.nextLine();
		managerOp.giveRefusedReason(applicationID, reason);
	}
	
	private static void printHint(String hint){
		System.out.println(hint);
	}
	
	private static String[] splitToken(String line, String token){
		String[] arry = line.split(token);
		return arry;
	}
}
