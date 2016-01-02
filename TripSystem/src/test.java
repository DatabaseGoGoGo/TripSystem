import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import bean.Application;
import bean.Trip;
import UserOperation.SalesmanOp;


public class test {
	public static void main(String[] argv) {
		SalesmanOp salesman = new SalesmanOp("2015110012");
		List<Application> applications = generateAppliations(5);
		List<Trip> trips = generateTrip(applications);
	}
	
	static Random random = new Random();
	
	private static List<Application> generateAppliations(int size) {
		List<Application> appList = new LinkedList<Application>();
		for (int i = 0; i < size; i++) {
			int applicationID = (int) System.currentTimeMillis();
			String applicationName = "applyYooo" + random.nextInt(100);
			String applyerID = "2015110012";
			int projectID = 2015130001;
			int state = Application.WAITING;
			Application application = new Application(applicationID, applicationName, applyerID, projectID, state);
			appList.add(application);
		}
		return appList;
	}
	
	private static List<Trip> generateTrip(List<Application> appList) {
		List<Trip> trips = new LinkedList<Trip>();
		for (Application app : appList) {
			int tripID = app.getApplicationID() * 10 + random.nextInt(9);
			Trip trip = new Trip(tripID, app.getApplicationID(), Trip.UNSTARTED);
			trips.add(trip);
		}
		return trips;
	}
}
