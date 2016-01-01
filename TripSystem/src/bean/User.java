package bean;

public class User {
	/**
	 * user's information
	 */
	private String userID;
	private String userName;
	private String password;
	
	/**
	 * user's role
	 * If role == 0, the user is an administrator.
	 * If role == 1, the user is a manager.
	 * If role == 2, the user is a salesman.
	 * If role == 3, the user is a developer.
	 */
	private int role;
	
	public User(String userID, String userName, String password, String roleString) {
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		switch (roleString) {
		case "����Ա":
			role = 0;
			break;
		case "�ܾ���":
			role = 1;
			break;
		case "����":
			role = 2;
			break;
		case "����":
			role = 3;
			break;
		}
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
	
	
	
}
