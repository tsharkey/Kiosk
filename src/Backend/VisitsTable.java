package Backend;

import java.util.ArrayList;

/**
 *
 * @author Sean Johnston, Brendan Casey
 */
public class VisitsTable {

	// creates a new visit, the email must match an already existing email in
	// the user database
	public static boolean addVisit(String reason, Boolean followUp,
			String email, String specialist, String location) {
		int insertCount = DatabaseConnector
				.executeUpdate("INSERT INTO VISITS(reason, followUp, email, specialist, location) "
						+ "VALUES('"
						+ reason
						+ "', "
						+ followUp
						+ ", '"
						+ email
						+ "', '"
						+ specialist
						+ "', '"
						+ location
						+ "')");
		return (insertCount != 0) ? true : false;
	}

	// deletes a visit based on date and email
	public static boolean deleteVisit(String email, String date, String time) {
		int deleteCount = DatabaseConnector.executeUpdate("DELETE FROM VISITS"
				+ "WHERE visitDate = '" + date + "'and visitTime = '" + time
				+ "' and email = '" + email + "'");
		return (deleteCount != 0) ? true : false;
	}

	// get all visit data
	public static ArrayList<VisitData> getAllVisits() {
		return DatabaseConnector
				.executeQueryVisitData("SELECT USER.fName, lName, phone, role, VISITS.* FROM VISITS INNER JOIN USER ON USER.email=VISITS.email ORDER BY VISITS.ID ASC");
	}

	// search column name for specific query
	private static ArrayList<VisitData> search(String column, String query) {
		return DatabaseConnector
				.executeQueryVisitData("SELECT USER.fName, lName, phone, role, VISITS.* FROM VISITS INNER JOIN USER ON USER.email=VISITS.email WHERE VISITS."
						+ column + " = '" + query + "' ORDER BY VISITS.ID ASC");
	}

	private static ArrayList<VisitData> search(String column, Boolean query) {
		return DatabaseConnector
				.executeQueryVisitData("SELECT USER.fName, lName, phone, role, VISITS.* FROM VISITS INNER JOIN USER ON USER.email=VISITS.email WHERE VISITS."
						+ column + " = " + query + " ORDER BY VISITS.ID ASC");
	}

	public static ArrayList<VisitData> searchReason(String reason) {
		return search("reason", reason);
	}

	public static ArrayList<VisitData> searchEmail(String email) {
		return search("email", email);
	}

	public static ArrayList<VisitData> searchSpecialist(String specialist) {
		return search("specialist", specialist);
	}

	public static ArrayList<VisitData> searchLocation(String location) {
		return search("location", location);
	}

	public static ArrayList<VisitData> searchFollowUp(boolean hasFollow) {
		return search("followUp", hasFollow);
	}

	// YYYY-MM-DD
	public static ArrayList<VisitData> searchDate(String date) {
		return search("visitDate", date);
	}

	// YYYY-MM-DD to YYYY-MM-DD -> inclusive
	public static ArrayList<VisitData> searchDates(String date_start,
			String date_end) {
		return DatabaseConnector
				.executeQueryVisitData("SELECT USER.fName, lName, phone, role, VISITS.* FROM VISITS INNER JOIN USER ON USER.email=VISITS.email WHERE VISITS.visitDate BETWEEN '"
						+ date_start
						+ "' AND '"
						+ date_end
						+ "' ORDER BY VISITS.ID ASC");
	}

	// YYYY-MM-DD ; HH:MM:SS to HH:MM:SS -> inclusive
	public static ArrayList<VisitData> searchDateAndTimes(String date,
			String time_start, String time_end) {
		return DatabaseConnector
				.executeQueryVisitData("SELECT USER.fName, lName, phone, role, VISITS.* FROM VISITS INNER JOIN USER ON USER.email=VISITS.email WHERE (VISITS.visitTime BETWEEN '"
						+ time_start
						+ "' AND '"
						+ time_end
						+ "') AND VISITS.visitDate = '"
						+ date
						+ "' ORDER BY VISITS.ID ASC");
	}
}
