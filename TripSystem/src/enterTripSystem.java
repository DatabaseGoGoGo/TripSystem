import java.sql.SQLException;
import java.util.Scanner;

import dao.LoginDao;
import bean.User;


public class enterTripSystem {
	private static Scanner scanner = new Scanner(System.in);
	/*exit string*/
	private static final String EXIT = "exit";
	
	/*login question and information string*/
	private static final String askForUserId = "please enter your userID:";
	private static final String askForPassword = "please enter your password:";
	private static final String idNotMatchPassword = "Wrong ID or password! PLEASE INPUT THEM AGAIN!";	
	
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
}
