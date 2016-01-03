import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import bean.Application;
import bean.Trip;
import UserOperation.SalesmanOp;

public class test {
	private static SalesmanOp salesman = new SalesmanOp("2015110012");
	
	public static void main(String[] argv) {
		List<Application> applications = generateAppliations(12);
		List<Trip> trips = generateTrip(applications);
		for (int i = 0; i < 12; i++) {
			salesman.createApplication(applications.get(i), trips.get(i));
		}
		checkAllApplication();
		checkApplicationsOfState(Application.WAITING);
	}
	
	static Random random = new Random();
	
	private static List<Application> generateAppliations(int size) {
		List<Application> appList = new LinkedList<Application>();
		for (int i = 0; i < size; i++) {
			int applicationID = (int) System.currentTimeMillis() * 10000 + random.nextInt(10000);
			String applicationName = "applyYooo" + random.nextInt(1000);
			String applyerID = "2015110012";
			int projectID = 2015120008;
			int state = Application.WAITING;
			int groupSize = random.nextInt(6);
			Application application = new Application(applicationID, applicationName, applyerID, "ÉòÓ¨", projectID, state, groupSize);
			appList.add(application);
		}
		return appList;
	}
	
	private static List<Trip> generateTrip(List<Application> appList) {
		List<Trip> trips = new LinkedList<Trip>();
		for (Application app : appList) {
			int tripID = app.getApplicationID() * 10000 + random.nextInt(10000);
			Trip trip = new Trip(tripID, app.getApplicationID(), Trip.UNSTARTED);
			trip.setDepartTime(new Timestamp(System.currentTimeMillis()+700000000));
			trips.add(trip);
		}
		return trips;
	}
	
	private static void checkAllApplication() {
		List<Application> allApplication = salesman.getAllApplications();
		System.out.println("****************All Applications of salesman***************");
		for (Application app : allApplication) {
			System.out.println("-----------------------------------------------------------");
			System.out.println(app.toString());
			System.out.println("-----------------------------------------------------------");
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
			System.out.println("-----------------------------------------------------------");
			System.out.println(app.toString());
			System.out.println("-----------------------------------------------------------");
		}
	}
}
