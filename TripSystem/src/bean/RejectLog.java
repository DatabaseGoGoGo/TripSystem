package bean;

public class RejectLog {
	private int rejectionID;
	private int applicationID;
	private String rejectReason;
	private int rejectTimes;
	
	public RejectLog(int rejectID, int applicationID, String rejectReason, int rejectTimes) {
		this.rejectionID = rejectID;
		this.applicationID = applicationID;
		this.rejectReason = rejectReason;
		this.rejectTimes = rejectTimes;
	}
	
	
}
