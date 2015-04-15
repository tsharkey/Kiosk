package Backend;

import java.util.ArrayList;

/**
 * This class handles interfacing with the MySQL database with regards to the
 * VISITS table that has eight columns: 'visitDate', 'visitTime', 'reason',
 * 'followUp', 'email', 'ID', 'specialist' and 'location'.
 *
 * @author Sean Johnston, Brendan Casey
 */

public class VisitsTable {
	/**
	 * Create a new visit entry in the database using parameter values.
	 * 
	 * @param reason
	 * @param followUp
	 * @param email
	 *            must match an already existing email in the USER table.
	 * @param specialist
	 * @param location
	 * @return boolean on success or failure
	 */
	public static boolean addVisit(String reason, Boolean followUp,
			String email, String specialist, String location) {
		int insertCount = DatabaseConnector
				.executeUpdate(
						"INSERT INTO VISITS(reason, followUp, email, specialist, location) VALUES(?, ?, ?, ?, ?)",
						reason, followUp, email, specialist, location);
		return (insertCount != 0) ? true : false;
	}

	/**
	 * Deletes a specific visit based on a date, time and email.
	 * 
	 * @param email
	 * @param date
	 * @param time
	 * @return boolean on success or failure
	 */
	public static boolean deleteVisit(String email, String date, String time) {
		int deleteCount = DatabaseConnector
				.executeUpdate(
						"DELETE FROM VISITS WHERE visitDate = ? AND visitTime = ? AND email = ?",
						date, time, email);
		return (deleteCount != 0) ? true : false;
	}

	/**
	 * Get all entries inside VISITS table.
	 * 
	 * @return ArrayList containing all rows in VISITS parsed as VisitData
	 */
	public static ArrayList<VisitData> getAllVisits() {
		return DatabaseConnector
				.executeQueryVisitData("SELECT USER.*, VISITS.* FROM VISITS INNER JOIN USER ON USER.email=VISITS.email ORDER BY VISITS.ID ASC");
	}

	/**
	 * Searching reason column inside VISITS table for a specific reason.
	 * 
	 * @param reason
	 * @return ArrayList of VisitData that matches condition
	 */
	public static ArrayList<VisitData> searchReason(String reason) {
		return search("reason", reason);
	}

	/**
	 * Searching email column inside VISITS table for a specific email.
	 * 
	 * @param email
	 * @return ArrayList of VisitData that matches condition
	 */
	public static ArrayList<VisitData> searchEmail(String email) {
		return search("email", email);
	}

	/**
	 * Searching specialist column inside VISITS table for a specific
	 * specialist.
	 * 
	 * @param specialist
	 * @return ArrayList of VisitData that matches condition
	 */
	public static ArrayList<VisitData> searchSpecialist(String specialist) {
		return search("specialist", specialist);
	}

	/**
	 * Searching location column inside VISITS table for specific location.
	 * 
	 * @param location
	 * @return ArrayList of VisitData that matches condition
	 */
	public static ArrayList<VisitData> searchLocation(String location) {
		return search("location", location);
	}

	/**
	 * Searching followUp column inside VISITS table for VISITS that are follow
	 * ups or not.
	 * 
	 * @param followUp
	 * @return ArrayList of VisitData that matches condition
	 */
	public static ArrayList<VisitData> searchFollowUp(boolean hasFollow) {
		return search("followUp", hasFollow);
	}

	/**
	 * Searching visitDate column inside VISITS table for specific visit date.
	 * 
	 * @param date
	 *            uses format of 'YYYY-MM-DD'
	 * @return ArrayList of VisitData that matches condition
	 */
	public static ArrayList<VisitData> searchDate(String date) {
		return search("visitDate", date);
	}

	/**
	 * Search specified column in VISITS table using input String value as
	 * restriction.
	 * 
	 * @param column
	 * @param input
	 * @return ArrayList of VisitData that matches condition
	 */
	private static ArrayList<VisitData> search(String column, String input) {
		ArrayList<VisitData> temp = null;
		switch (column) {
		case "reason":
			temp = DatabaseConnector
					.executeQueryVisitData(
							"SELECT USER.*, VISITS.* FROM VISITS INNER JOIN USER ON USER.email=VISITS.email WHERE VISITS.reason = ? ORDER BY VISITS.ID ASC",
							input);
			break;
		case "email":
			temp = DatabaseConnector
					.executeQueryVisitData(
							"SELECT USER.*, VISITS.* FROM VISITS INNER JOIN USER ON USER.email=VISITS.email WHERE VISITS.email = ? ORDER BY VISITS.ID ASC",
							input);
			break;
		case "specialist":
			temp = DatabaseConnector
					.executeQueryVisitData(
							"SELECT USER.*, VISITS.* FROM VISITS INNER JOIN USER ON USER.email=VISITS.email WHERE VISITS.specialist = ? ORDER BY VISITS.ID ASC",
							input);
			break;
		case "location":
			temp = DatabaseConnector
					.executeQueryVisitData(
							"SELECT USER.*, VISITS.* FROM VISITS INNER JOIN USER ON USER.email=VISITS.email WHERE VISITS.location = ? ORDER BY VISITS.ID ASC",
							input);
			break;
		case "visitDate":
			temp = DatabaseConnector
					.executeQueryVisitData(
							"SELECT USER.*, VISITS.* FROM VISITS INNER JOIN USER ON USER.email=VISITS.email WHERE VISITS.visitDate = ? ORDER BY VISITS.ID ASC",
							input);
			break;
		default:
			break;
		}
		return temp;
	}

	/**
	 * Search specified column in VISITS table using input boolean value as
	 * restriction.
	 * 
	 * @param column
	 * @param input
	 * @return ArrayList of VisitData that matches condition
	 */
	private static ArrayList<VisitData> search(String column, boolean input) {
		ArrayList<VisitData> temp = null;
		switch (column) {
		case "followUp":
			temp = DatabaseConnector
					.executeQueryVisitData(
							"SELECT USER.*, VISITS.* FROM VISITS INNER JOIN USER ON USER.email=VISITS.email WHERE VISITS.followUp = ? ORDER BY VISITS.ID ASC",
							input);
			break;
		default:
			break;
		}
		return temp;
	}

	/**
	 * Searching visitDate column inside VISITS table for a specific inclusive
	 * date range.
	 * 
	 * @param date_start
	 *            uses format of 'YYYY-MM-DD'
	 * @param date_end
	 *            uses format of 'YYYY-MM-DD'
	 * @return ArrayList of VisitData that matches condition
	 */
	public static ArrayList<VisitData> searchDates(String date_start,
			String date_end) {
		return DatabaseConnector
				.executeQueryVisitData(
						"SELECT USER.*, VISITS.* FROM VISITS INNER JOIN USER ON USER.email=VISITS.email WHERE VISITS.visitDate BETWEEN ? AND ? ORDER BY VISITS.ID ASC",
						date_start, date_end);
	}

	/**
	 * Searching visitDate column inside VISITS table for a specific date and
	 * visitTime column for an inclusive time range.
	 * 
	 * @param date
	 *            uses format of 'YYYY-MM-DD'
	 * @param time_start
	 *            uses the format of 'HH:MM:SS'
	 * @param time_end
	 *            uses the format of 'HH:MM:SS'
	 * @return ArrayList of VisitData that matches condition
	 */
	public static ArrayList<VisitData> searchDateAndTimes(String date,
			String time_start, String time_end) {
		return DatabaseConnector
				.executeQueryVisitData(
						"SELECT USER.*, VISITS.* FROM VISITS INNER JOIN USER ON USER.email=VISITS.email WHERE (VISITS.visitTime BETWEEN ? AND ?) AND VISITS.visitDate = ? ORDER BY VISITS.ID ASC",
						time_start, time_end, date);
	}

	/**
	 * Search for VISITS entries that are tied to a partial name that starts
	 * with String found in input parameter. Remove all leading or trailing
	 * spaces from input.
	 * 
	 * @param input
	 * @return ArrayList of VisitData that matches condition
	 */
	public static ArrayList<VisitData> searchName(String input) {
		input = input.trim();
		input += '%';
		return DatabaseConnector
				.executeQueryVisitData(
						"SELECT USER.*, VISITS.* FROM VISITS INNER JOIN USER ON USER.email=VISITS.email WHERE ((fName LIKE ?) OR (lName LIKE ?) OR (CONCAT(fName, ' ', lName) LIKE ?)) ORDER BY VISITS.ID ASC",
						input, input, input);
	}
}