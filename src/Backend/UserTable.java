package Backend;

import java.util.ArrayList;

/**
 * This class handles interfacing with the MySQL database with regards to the
 * USER table that has five columns: 'fName', 'lName', 'email', 'phone', and
 * 'role'.
 * 
 * @author Sean Johnston, Brendan Casey
 */

public class UserTable {

	/**
	 * Create a new USER entry in the database using parameter values.
	 * 
	 * @param _fName
	 * @param _lName
	 * @param _phone
	 * @param _email
	 * @param _role
	 * @return boolean of success
	 */
	public static boolean addUser(String _fName, String _lName, String _phone,
			String _email, String _role) {
		if (user_exist(_email)) {
			return false; // already exists
		}
		int insertCount = DatabaseConnector.executeUpdate("INSERT INTO USER "
				+ "VALUES('" + _fName + "', '" + _lName + "', '" + _email
				+ "', '" + _phone + "', '" + _role + "')");
		return (insertCount != 0) ? true : false;
	}

	/**
	 * Delete a user or a user and their all visit data if deleteVisits is true.
	 * 
	 * @param email
	 * @param deleteVisits
	 * @return boolean of success
	 */
	public static boolean deleteUser(String email, boolean deleteVisits) {
		if (deleteVisits) { // delete first due to foreign key
			DatabaseConnector.executeUpdate("DELETE FROM VISITS "
					+ "WHERE email = '" + email + "'");
		}
		int deleteCount = DatabaseConnector.executeUpdate("DELETE FROM USER "
				+ "WHERE email = '" + email + "'");

		return (deleteCount != 0) ? true : false;
	}

	/**
	 * Update an existing USER entry's last name value with update parameter
	 * using email primary key.
	 * 
	 * @param email
	 * @param update
	 * @return boolean of success
	 */
	public static boolean updateFName(String email, String update) {
		return updateUser(email, "fName", update);
	}

	/**
	 * Update an existing USER entry's last name value with update parameter
	 * using email primary key.
	 * 
	 * @param email
	 * @param update
	 * @return boolean of success
	 */
	public static boolean updateLName(String email, String update) {
		return updateUser(email, "lName", update);
	}

	/**
	 * Update an existing USER entry's phone value with update parameter using
	 * email primary key.
	 * 
	 * @param email
	 * @param update
	 * @return boolean of success
	 */
	public static boolean updatePhone(String email, String update) {
		return updateUser(email, "phone", update);
	}

	/**
	 * Update an existing USER entry's email value with update parameter using
	 * email primary key.
	 * 
	 * @param email
	 * @param update
	 * @return boolean of success
	 */
	public static boolean updateEmail(String email, String update) {
		return updateUser(email, "email", update);
	}

	/**
	 * Update an existing USER entry's role value with update parameter using
	 * email primary key.
	 * 
	 * @param email
	 * @param update
	 * @return boolean of success
	 */
	public static boolean updateRole(String email, String update) {
		return updateUser(email, "role", update);
	}

	/**
	 * Generic method to handle updating various columns from USER table using
	 * the email primary key to locate entry and the store update value.
	 * 
	 * @param email
	 * @param column
	 * @param update
	 * @return boolean of success
	 */
	private static boolean updateUser(String email, String column, String update) {
		int updateCount = 0;
		switch (column) {
		case "fName":
		case "lName":
		case "phone":
		case "email":
		case "role":
			updateCount = DatabaseConnector.executeUpdate("UPDATE USER SET "
					+ column + "='" + update + "' WHERE email = '" + email
					+ "'");
			break;
		default:
			break;
		}
		return (updateCount != 0) ? true : false;
	}

	/**
	 * Check if the user exist in the database using primary key.
	 * 
	 * @param email
	 * @return boolean of successfully found or not
	 */
	public static boolean user_exist(String email) {
		ArrayList<String> emails = DatabaseConnector.executeQueryStrings(
				"email", "SELECT email FROM USER ORDER BY email ASC");
		for (String emailLoop : emails) {
			if (emailLoop.equals(email)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if USER table is empty.
	 * 
	 * @return boolean whether it is empty or not
	 */
	public static boolean isEmpty() {
		int count = DatabaseConnector.executeQueryInt("count",
				"SELECT COUNT(*) AS count FROM USER");
		return (count == 0) ? true : false;
	}

	/**
	 * Gets an ordered ArrayList of UserData containing all entries in USER
	 * table. The isSpecialist toggles between returning all specialists or all
	 * non=specialists.
	 * 
	 * @param isSpecialist
	 * @return ArrayList of UserData
	 */
	public static ArrayList<UserData> getAllUsers(Boolean isSpecialist) {
		return DatabaseConnector
				.executeQueryUserData("SELECT * FROM USER WHERE role"
						+ (!isSpecialist ? "!" : "")
						+ "='Specialist' ORDER BY email ASC");
	}

	/**
	 * Get all entries in USER table.
	 * 
	 * @return ArrayList of UserData
	 */
	public static ArrayList<UserData> getAllUsers() {
		return DatabaseConnector
				.executeQueryUserData("SELECT * FROM USER ORDER BY email ASC");
	}

	/**
	 * Search for USER entries that are tied to a partial name that starts with
	 * String found in input parameter. Remove all leading or trailing spaces
	 * from input. Toggle between searching specialists or non-specialists.
	 * 
	 * @param query
	 * @param isSpecialist
	 * @return ArrayList of UserData
	 */
	public static ArrayList<UserData> searchName(String input,
			Boolean isSpecialist) {
		input = input.trim();
		return DatabaseConnector
				.executeQueryUserData("SELECT * FROM USER WHERE ((fName LIKE '"
						+ input + "%') OR (lName LIKE '" + input
						+ "%') OR (CONCAT(fName, ' ', lName) LIKE '" + input
						+ "%')) AND (role" + (!isSpecialist ? "!" : "")
						+ "='Specialist') ORDER BY email ASC");
	}
}
