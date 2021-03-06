import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import bean.Application;
import bean.Trip;
import UserOperation.GeneralOp;
import UserOperation.ManagerOp;
import UserOperation.SalesmanOp;

public class test {
	private static SalesmanOp salesman = new SalesmanOp("2015110005");
	
	public static void main(String[] argv) {
		List<Application> applications = generateAppliations(3);
		List<Trip> trips = generateTrip(applications);
		for (int i = 0; i < 3 ; i++) {
			salesman.createApplication(applications.get(i), trips.get(i));
		}
		checkAllApplication();
		checkApplicationsOfState(Application.WAITING);
		

	}
	
	static Random random = new Random();
	
	private static List<Application> generateAppliations(int size) {
		List<Application> appList = new LinkedList<Application>();
		for (int i = 0; i < size; i++) {
			int applicationID = (int) System.currentTimeMillis() * 10 + random.nextInt(10);
			String applicationName = "applyYooo" + random.nextInt(10);
			String applyerID = "2015110005";
			int projectID = 2015120007;
			int state = Application.WAITING;
			int groupSize = random.nextInt(6);
			Timestamp time = new Timestamp(System.currentTimeMillis());
			Application application = new Application(applicationID, applicationName, applyerID, "��Ө",
					projectID, state, time, groupSize);
			appList.add(application);
		}
		return appList;
	}
	
	private static List<Trip> generateTrip(List<Application> appList) {
		List<Trip> trips = new LinkedList<Trip>();
		for (Application app : appList) {
			int tripID = app.getApplicationID() * 10 + random.nextInt(10);
			Trip trip = new Trip(tripID, app.getApplicationID(), "trip", Trip.UNSTARTED);
			trip.setDepartTime(new Timestamp(System.currentTimeMillis()+700000000));
			trips.add(trip);
		}
		return trips;
	}
	
	private static void checkAllApplication() {
		List<Application> allApplication = salesman.getAllApplications();
		System.out.println("****************All Applications of salesman***************");
		for (Application app : allApplication) {
			System.out.println(app.toString());
		}
	}
	
	private static void checkApplicationsOfState(int state) {
		List<Application> applications = salesman.getApplicationByState(state);
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
		for (Application app : applications) {
			System.out.println(app.toString());
		}
	}
}
