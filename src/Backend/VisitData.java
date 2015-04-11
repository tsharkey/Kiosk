package Backend;

import java.util.Date;

/**
 * @author Tom, Brendan
 */
public class VisitData extends UserData {
	private Date visitDate;
	private Date visitTime;
	private String reason;
	private boolean followUp;
	private String specialist;
	private String location;

	public VisitData(Date visitDate, Date visitTime, String reason,
			boolean followUp, String specialist, String location,
			String firstName, String lastName, String email, String phone,
			String role) {

		super(firstName, lastName, email, phone, role);
		this.visitDate = visitDate;
		this.visitTime = visitTime;
		this.reason = reason;
		this.followUp = followUp;
		this.specialist = specialist;
		this.location = location;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public String getReason() {
		return reason;
	}

	public boolean isFollowUp() {
		return followUp;
	}

	public String getSpecialist() {
		return specialist;
	}

	public String getLocation() {
		return location;
	}
}
