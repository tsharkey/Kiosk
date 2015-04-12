package Backend;

import java.util.Date;

/**
 * This subclass of UserData stores information from the VISITS table about a
 * particular entry along with the USER information for that visit.
 * 
 * @author Tom Sharkey, Brendan Casey
 */

public class VisitData extends UserData {
	private Date visitDate;
	private Date visitTime;
	private String reason;
	private boolean followUp;
	private String specialist;
	private String location;

	/**
	 * Constructor to store all various information about a visit.
	 */
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
	
	/**
	 * Getters for details specific to VISITS.
	 * 
	 * @return
	 */

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
