import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

import dao.LoginDao;
import UserOperation.DeveloperOp;
import UserOperation.ManagerOp;
import bean.Application;
import bean.Assignment;
import bean.Project;
import bean.RejectLog;
import bean.Trip;
import bean.TripRecord;
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
	private static final String assign = "assign";
	// input request
	private static final String viewAllApplicationRequests = "show me all trip requests";
	private static final String searchApplicationByPjName = "search for trip requests by project name";
	private static final String searchApplicationByState = "search for trip requests by state";
	private static final String approveApplication = "approve application";
	private static final String assignDevelopersToPj = "assign developers to project";
	private static final String rejectApplication = "reject application";
	private static final String viewAllAssignmentState = "show me all assignmnet state";
	private static final String viewAllTripState = "show me all trip state";
	private static final String viewAllTripRecord = "show me all trip records";
	private static final String viewAllRejectRecord = "show me all reject records";
	// output hint
	private static final String searchApplicationByPjNameHint = "please enter the key word of the correspond project's name: ";
	private static final String searchApplicationByStateHint = "please enter the state: ";
	private static final String noResult = "No result found!";
	private static final String enterApplicationIDHint = "please enter the applicationID: ";
	private static final String enterRejectReasonHint = "please enter the reject reason: ";
	private static final String assignDevelopersHint = "please enter the developers' id that you want to assign to: (e.g 2015110002, 201511004)";
	private static final String assignDevelopersToPjHint = "please enter the developers' id and the project id want to assign to: (e.g developerID, projexctID)";
	private static final String viewAllTripRecordHint = "please enter the tripID: (*the trip should be finished)";
	
	/*Operation string of a developer. */
	// input request
	private static final String viewAllUncomfirmedAssignments = "show me all uncomfirmed assignments";
	private static final String viewAllComfirmedAssignments = "show me all comfirmed assignments";
	//private static final String viewOneAssignment = "show me the assignment's infomation";
	private static final String confirmAssigment = "confirm assignment";
	private static final String handinRecord = "hand in record";
	private static final String viewResponsiblePj = "show me the project I responsible for";
	// output hint
	private static final String enterTripRecordHint = "please enter the trip record in the following format: (e.g. actualDepartTime, actualTripDays, tripContent)\nHint: actualDepartTime shoud be yyyy-mm-dd format!!!";
	
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
				case assign:
					assignDevelopersToPj(managerOp);
					break;
				case EXIT:
					System.out.println("see you~");
					System.exit(0);
				default:
					break;
			}
		}
			
	}
	
	private static void ViewOfDeveloper(User user) {
		DeveloperOp developerOp = new DeveloperOp(user.getUserID());
		String command = "";		
		while (scanner.hasNextLine()){			
			command = scanner.nextLine();
			switch (command){
				case viewAllComfirmedAssignments:
					viewAssignment(developerOp.getConfirmedAssignments());
					break;
				case viewAllUncomfirmedAssignments:
					viewAssignment(developerOp.getUnconfirmedAssignments());
					break;
				case confirmAssigment:
					confirmAssignment(developerOp);
					break;
				case handinRecord:
					handinRecord(developerOp);
					break;
				case viewResponsiblePj:
					viewProject(developerOp.getAllprojects());
					break;
				case EXIT:
					System.out.println("see you~");
					System.exit(0);
				default:
					break;
			}
		}
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
				viewApplications(managerOp.getAllApplication());
				break;		
			case viewAllAssignmentState:
				viewAllAssignmentState(managerOp);
				break;
			case viewAllTripState:
				viewTrip(managerOp.getAllTripState());
				break;
			case viewAllTripRecord:
				viewTripRecord(managerOp);
			case viewAllRejectRecord:
				viewAllRejectLog(managerOp);
			default:
				break;
		}
	}
	
	private static void approveApplication(ManagerOp managerOp){
		printHint(enterApplicationIDHint);
		int applicationID = scanner.nextInt();
		managerOp.setApplicationState(applicationID, APPROVED);
		printHint(assignDevelopersHint);
		scanner.nextLine();
		String[] developers = splitToken(scanner.nextLine(), ", ");
		while(!managerOp.assignDevelopersToTrip(applicationID, developers)){
			System.out.println("Assigned failed! check if the number of assigned developers exceeds");
			printHint(assignDevelopersHint);
			scanner.nextLine();
			developers = splitToken(scanner.nextLine(), ", ");
		}
		System.out.println("Assigned successfully");// 1660026822
	}
	
	private static void rejectApplication(ManagerOp managerOp){
		printHint(enterApplicationIDHint);
		int applicationID = scanner.nextInt();
		managerOp.setApplicationState(applicationID, REFUSED);
		printHint(enterRejectReasonHint);
		String reason = scanner.nextLine();
		managerOp.giveRefusedReason(applicationID, reason);
		System.out.println("rejected successfully!");
	}
	
	private static void viewAllAssignmentState(ManagerOp managerOp){
		printHint(enterApplicationIDHint);
		int applicationID = scanner.nextInt();
		Assignment assignment = managerOp.getAssignmentStateByID(applicationID);
		System.out.println(assignment.toString());
	}
	
	private static void viewTripRecord(ManagerOp managerOp){
		printHint(viewAllTripRecordHint);
		int tripID = scanner.nextInt();
		viewTripRecord(managerOp.getFinishedTripRecord(tripID));
	}

	private static void viewAllRejectLog(ManagerOp managerOp){
		printHint(enterApplicationIDHint);
		int applicationID = scanner.nextInt();
		viewRejectLog(managerOp.getRejectLog(applicationID));
	}
	
	private static void assignDevelopersToPj(ManagerOp managerOp){
		printHint(assignDevelopersToPjHint);
		String[] info = splitToken(scanner.nextLine(), ", ");
		managerOp.assignDeveloperToProject(info[1], info[2]);
		System.out.println("assigned successfully");
	}
	
	private static void viewApplications(List<Application> applications){
		if (applications.size() == 0 ){
			printHint(noResult);
		}
		for (int i = 0, len = applications.size(); i < len; i++){
			System.out.println(applications.get(i).toString());
		}
	}
	
	private static void viewTrip(List<Trip> trip){
		if (trip.size() == 0 ){
			printHint(noResult);
		}
		for (int i = 0, len = trip.size(); i < len; i++){
			System.out.println(trip.get(i).toString());
		}
	}
	
	private static void viewTripRecord(List<TripRecord> triprecord){
		if (triprecord == null){
			System.out.println("This trip is not finished yet!");
			return;
		}
		if (triprecord.size() == 0 ){
			printHint(noResult);
		}
		for (int i = 0, len = triprecord.size(); i < len; i++){
			System.out.println(triprecord.get(i).toString());
		}
	}
	
	private static void viewRejectLog(List<RejectLog> rejectlog){
		if (rejectlog == null){
			System.out.println("This application is not rejected!");
			return;
		}
		if (rejectlog.size() == 0 ){
			printHint(noResult);
		}
		for (int i = 0, len = rejectlog.size(); i < len; i++){
			System.out.println(rejectlog.get(i).toString());
		}
	}
	
	/* developer operation */
	private static void viewAssignment(List<Assignment> assignments){
		if (assignments.size() == 0 ){
			printHint(noResult);
		}
		for (int i = 0, len = assignments.size(); i < len; i++){
			System.out.println(assignments.get(i).toString());
		}
	}
	
	private static void viewProject(List<Project> projects){
		if (projects.size() == 0 ){
			printHint(noResult);
		}
		for (int i = 0, len = projects.size(); i < len; i++){
			System.out.println(projects.get(i).toString());
		}
	}
	
	private static void confirmAssignment(DeveloperOp developerOp){
		printHint(enterApplicationIDHint);
		int applicationID = scanner.nextInt();
		developerOp.confirmAssigment(applicationID);
		System.out.println("confirmed successfuly!");
	}
	
	private static void handinRecord(DeveloperOp developerOp){
		printHint(enterApplicationIDHint);
		int applicationID = scanner.nextInt();
		printHint(enterTripRecordHint);
		scanner.nextLine();
		String[] info = splitToken(scanner.nextLine(), ", ");
		developerOp.handinRecord(applicationID+"", info[0], info[1], info[2]);
		System.out.println("handed successfully!");
	}
	
	private static void printHint(String hint){
		System.out.println(hint);
	}
	
	private static String[] splitToken(String line, String token){
		String[] arry = line.split(token+"|\n");
		return arry;
	}
}
