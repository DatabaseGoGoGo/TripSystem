import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import dao.LoginDao;
import UserOperation.AdminOperation;
import UserOperation.DeveloperOp;
import UserOperation.ManagerOp;
import UserOperation.SalesmanOp;
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
	/*Help string. */
	private static final String HELP = "help";
	
	/*Login question and information string. */
	private static final String askForUserId = "please enter your userID:";
	private static final String askForPassword = "please enter your password:";
	private static final String idNotMatchPassword = "Wrong ID or password! PLEASE INPUT THEM AGAIN!";
	
	/*Operation string of an administrator. */
	private static final String addOneUser = "add one user";
	private static final String importUsers = "add 1+ users";
	private static final String deleteOneUser = "delete one user";
	private static final String exludeUsers = "delete 1+ users";
	private static final String modifyUser = "modify user";
	private static final String modifyProject = "modify project";
	
	private static final String askForTheUserId = "please input user id";
	private static final String askForTheUserName = "please input user's new name";
	private static final String askForThePassword = "please input user's new password";
	private static final String askForTheRole = "please input user's role";
	private static final String askForPath = "please input the file path(relative)";
	private static final String askForProjectId = "please input the project id";
	private static final String askForProjectName = "please input new name";
	private static final String askForProjectDescription = "please input new description";
	private static final String askForProjectManagerID = "please input new manager id";
	private static final String successfulUpdate = "update successfully!";
	private static final String failUpdate = "update failed, please input agian!";
	
	/*Operation string of a salesman. */
	private static final String showAllApplicationsOfSalesmane = "show applications";
	private static final String showApplicationInSpecificStateOfSalesman = "show applications in state";
	private static final String createNewApplication = "create new application";
	private static final String modifyApplication = "modify application";
	private static final String cancelApplication = "cancel application";
	private static final String checkRefuseLog = "check refuse log";
	
	private static final String askForState = "please input the state of the appplications: ";
	private static final String stateOptions = "(0 for APPROVED, 1 for REFUSED, 2 for WAITING, 3 for CANCELED)";
	private static final String NoMoreNewApplication = "you had 3 pending applications, CANNOT apply now!";
	private static final String DuplicateApplication = "you had pending application for this project, CANNOT apply now!";
	private static final String askForApplicationName = "please enter the application name";
	private static final String askForApplicationID = "please enter the application ID";
	private static final String askForGroupSize = "please enter the number of people will go for a trip";
	private static final String askForDepartYear = "pleas enter the year for trip";
	private static final String askForDepartMonth = "please enter the month for trip";
	private static final String askForDepartDay = "please enter the date for trip";
	private static final String askForTripDays = "please enter the days need for trip";
	private static final String askForDetail = "please have a brief description of the trip";
	private static final String createSuccessfually = "create successfully!";
	private static final String createFail = "crate failed!";
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
		AdminOperation admin = new AdminOperation(user.getUserID());
		String command = "";
		while (scanner.hasNextLine()) {
			command = scanner.next();
			switch (command) {
			case addOneUser:
				addOneNewUser(admin);
				break;
			case importUsers:
				addUsers(admin);
				break;
			case deleteOneUser:
				deleteOneUser(admin);
				break;
			case exludeUsers:
				deleteUsers(admin);
				break;
			case modifyUser:
				updateUser(admin);
				break;
			case modifyProject:
				updateProject(admin);
				break;
			case EXIT:
				return;
			default:
				System.out.println("Invalid Command! Please Input Again!");
				break;
			}
		}
	}
	
	private static void ViewOfSalesman(User user) {
		SalesmanOp salesman = new SalesmanOp(user.getUserID());
		String command = "";
		while (scanner.hasNextLine()) {
			command = scanner.next();
			switch (command) {
			case showAllApplicationsOfSalesmane:
				List<Application> apps = salesman.getAllApplications();
				System.out.println("****************All Applications of salesman***************");
				for (Application app : apps) {
					System.out.println(app.toString());
				}
				break;
			case showApplicationInSpecificStateOfSalesman:
				getApplicationForSalesmanInSpecificState(salesman);
				break;
			case createNewApplication:
				createNewApplication(salesman, user.getUserID());
				break;
			case modifyApplication:
				modifyApplication(salesman);
				break;
			case cancelApplication:
				cancelApplication(salesman);
				break;
			case checkRefuseLog:
				checkRefuseLog(salesman);
				break;
			case EXIT:
				return;
			default:
				System.out.println("Invalid Command! Please Input Again!");
				break;
			}
		}
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
			printHint(noResult);		}
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
	
	
	private static void getApplicationForSalesmanInSpecificState(SalesmanOp salesman){
		System.out.println(askForState);
		System.out.println(stateOptions);
		String command = "";
		List<Application> apps = null;
		int state = 0;
		boolean flag = true;
		while (flag) {
			command = scanner.next();
			switch (command) {
			case HELP:
				System.out.println(stateOptions);
				break;
			case "0":
			case "1":
			case "2":
			case "3":
				state = Integer.parseInt(command);
				apps = salesman.getApplicationByState(state);
				flag = false;
				break;
			default:
				System.out.println("Invalid number");
			}
			if (!flag) {
				System.out.print("************* Applications of salesman in the state of ");
				switch (state) {
				case Application.APPROVED:
					System.out.print("APPROVED ");
					break;
				case Application.CANCELED:
					System.out.print("CANCELED ");
					break;
				case Application.REFUSED:
					System.out.print("REFUSED ");
					break;
				case Application.WAITING:
					System.out.print("WAITING ");
					break;
				}
				System.out.println("************");
				for (Application app: apps) {
					System.out.println(app.toString());
				}
			}
		}
	}
	
	private static void addOneNewUser(AdminOperation admin) {
		boolean flag = true;
		while (flag) {
			System.out.println(askForTheUserId);
			String userID = scanner.next();
			System.out.println(askForTheUserName);
			String username = scanner.next();
			System.out.println(askForThePassword);
			String password = scanner.next();
			System.out.println(askForTheRole);
			String role = scanner.next();
			boolean success = admin.insertOneUser(new User(userID, username, password, role));
			if (success) {
				System.out.println(successfulUpdate);
				flag = false;
			} else {
				System.out.println(failUpdate);
			}
		}
	}
	
	private static void deleteOneUser(AdminOperation admin) {
		System.out.println(askForTheUserId);
		String userID = scanner.next();
		boolean success = admin.deleteOneUser(new User(userID));
		if (success) {
			System.out.println(successfulUpdate);
		} else {
			System.out.println(failUpdate);
		}		
	}
	
	private static void deleteUsers(AdminOperation admin) {
		System.out.println(askForPath);
		String filename = scanner.next();
		boolean success = admin.deleteUsersByFile(filename);
		if (success) {
			System.out.println(successfulUpdate);
		} else {
			System.out.println(failUpdate);
		}	
	}
	
	private static void addUsers(AdminOperation admin) {
		System.out.println(askForPath);
		String filename = scanner.next();
		boolean success = admin.insertUsersByFile(filename);
		if (success) {
			System.out.println(successfulUpdate);
		} else {
			System.out.println(failUpdate);
		}
	}
	
	private static void updateUser(AdminOperation admin) {
		System.out.println(askForTheUserId);
		String userID = scanner.next();
		System.out.println(askForTheUserName);
		String username = scanner.next();
		System.out.println(askForThePassword);
		String password = scanner.next();
		System.out.println(askForTheRole);
		String role = scanner.next();
		boolean success = admin.updateUser(new User(userID, username, password, role));
		if (success) {
			System.out.println(successfulUpdate);
		} else {
			System.out.println(failUpdate);
		}
	}
	
	private static void updateProject(AdminOperation admin) {
		System.out.println(askForProjectId);
		String projectIDString = scanner.next();
		int projectID = 0;
		try {
			projectID = Integer.parseInt(projectIDString);
		} catch (Exception e){
			System.out.println(failUpdate + " INVALID project ID!");
			return;
		}	
		System.out.println(askForProjectName);
		String projectName = scanner.next();
		System.out.println(askForProjectManagerID);
		String managerID = scanner.next();
		System.out.println(askForProjectDescription);
		String description = scanner.next();
		boolean success = admin.updateProject(new Project(projectID, managerID, projectName, description));
		if (success) {
			System.out.println(successfulUpdate);
		} else {
			System.out.println(failUpdate);
		}
	}
	
	private static void createNewApplication(SalesmanOp salesman, String userID) {
		System.out.println(askForProjectId);
		Set<Integer> projectIdSet = salesman.getProjectIDsByUserID(userID);		
		Integer projectID = 0;
		try {
			projectID = Integer.parseInt(scanner.next());
		} catch(Exception e) {
			System.out.println("Invalid project ID!");
			return;
		}
		if (!projectIdSet.contains(projectID)) {
			System.out.println("you are not working for this project!");
			return;
		}
		List<Application> apps = salesman.getApplicationByState(Application.WAITING);
		if (apps.size() >= 3) {
			System.out.println(NoMoreNewApplication);
			return;
		}
		for (Application app : apps){
			if (app.getProjectID() == projectID) {
				System.out.println(DuplicateApplication);
				return;
			}
		}
		System.out.println(askForApplicationName);
		String applicationName = scanner.next();
		System.out.println(askForGroupSize);
		int groupSize = Integer.parseInt(scanner.next());
		System.out.println(askForDepartYear);
		int year = Integer.parseInt(scanner.next());
		System.out.println(askForDepartMonth);
		int month = Integer.parseInt(scanner.next());
		System.out.println(askForDepartDay);
		int day = Integer.parseInt(scanner.next());
		Timestamp departTime = new Timestamp(year, month, day, 0, 0, 0, 0);
		System.out.println(askForTripDays);
		int days = Integer.parseInt(scanner.next());
		System.out.println(askForDetail);
		String description = scanner.next();
		Random random = new Random();
		try {
			int applicationID = (int) (System.currentTimeMillis() * 1000 + random.nextInt(1000));  
			int tripID = (int) (System.currentTimeMillis() * 1000 + random.nextInt(1000));
			Trip trip = new Trip(tripID, applicationID, departTime, days, description, Trip.UNSTARTED);
			trip.setTripName(applicationName + "Trip");
			salesman.createApplication(new Application(applicationID, applicationName, userID, projectID, 
					Application.WAITING, groupSize), trip);
			System.out.println(createSuccessfually);
		} catch(Exception e) {
			System.out.println(createFail);
		}
	}
	
	private static void modifyApplication(SalesmanOp salesman) {
		System.out.println(askForApplicationID);
		int applicationID = Integer.parseInt(scanner.next());
		List<Application> apps = salesman.getAllApplications();
		Application application = null; 
		for (Application app : apps) {
			if (app.getApplicationID() == applicationID) {
				application = app;
				break;
			}
		}
		if (application == null) {
			System.out.println("Invalid Application ID!");
			return;
		}
		Trip trip = salesman.getTripByApplicationID(applicationID);
		System.out.println(askForApplicationName);
		String applicationName = scanner.next();
		System.out.println(askForGroupSize);
		int groupSize = Integer.parseInt(scanner.next());
		System.out.println(askForDepartYear);
		int year = Integer.parseInt(scanner.next());
		System.out.println(askForDepartMonth);
		int month = Integer.parseInt(scanner.next());
		System.out.println(askForDepartDay);
		int day = Integer.parseInt(scanner.next());
		Timestamp departTime = new Timestamp(year, month, day, 0, 0, 0, 0);
		System.out.println(askForTripDays);
		int days = Integer.parseInt(scanner.next());
		System.out.println(askForDetail);
		String description = scanner.next();
		trip.setDepartTime(departTime);
		trip.setDescription(description);
		trip.setTripName(applicationName + "Trip");
		try { 
			salesman.updateApplication(new Application(applicationID, applicationName, 
					application.getApplyerID(), application.getProjectID(),	Application.WAITING, groupSize));
			salesman.updateTrip(trip);
			System.out.println(successfulUpdate);
		} catch(Exception e) {
			System.out.println(failUpdate);
		}
	}
	
	private static void cancelApplication(SalesmanOp salesman) {
		System.out.println(askForApplicationID);
		int applicationID = Integer.parseInt(scanner.next());
		List<Application> apps = salesman.getAllApplications();
		Application application = null; 
		for (Application app : apps) {
			if (app.getApplicationID() == applicationID) {
				application = app;
				break;
			}
		}
		if (application == null) {
			System.out.println("Invalid Application ID!");
			return;
		}
		if ((application.getState() != Application.REFUSED) && (application.getState() != Application.WAITING)) {
			System.out.println("This application cannot be cancelled!");
			return;
		}
		application.setState(Application.CANCELED);
		salesman.updateApplication(application);
	}
	
	private static void checkRefuseLog(SalesmanOp salesman) {
		System.out.println(askForApplicationID);
		int applicationID = Integer.parseInt(scanner.next());
		List<Application> apps = salesman.getAllApplications();
		Application application = null; 
		for (Application app : apps) {
			if (app.getApplicationID() == applicationID) {
				application = app;
				break;
			}
		}
		if (application == null) {
			System.out.println("Invalid Application ID!");
			return;
		}
		List<RejectLog> log = salesman.getRejectLogs(applicationID);
		
	}
}
