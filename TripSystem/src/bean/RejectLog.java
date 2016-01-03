package bean;

public class RejectLog {
	private int rejectionID;
	private int applicationID;
	private String rejectReason;
	
	public RejectLog(int rejectID, int applicationID, String rejectReason) {
		this.rejectionID = rejectID;
		this.applicationID = applicationID;
		this.rejectReason = rejectReason;
	}
	
	@Override
	public String toString() {
		String result = "-----------------------------------------------------------\n";
		result += "application id: " + applicationID + "\n";
		result += "reject id: " + rejectionID + "\t";
		result += "reject reason: " + rejectReason + "\n";
		result += "-----------------------------------------------------------";
		return result;		
	}
	
}
