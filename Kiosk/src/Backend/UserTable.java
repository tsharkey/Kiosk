package Backend;

import java.util.ArrayList;

/**
 *
 * @author Sean Johnston, Brendan Casey
 */
public class UserTable {

	// adding a user to database
	public static boolean addUser(String _fName, String _lName, String _phone,
			String _email, String _role) {
		if (user_exist(_email)) {
			return false; // already exists -> update instead?
		}
		int insertCount = DatabaseConnector.executeUpdate("INSERT INTO USER "
				+ "VALUES('" + _fName + "', '" + _lName + "', '" + _email
				+ "', '" + _phone + "', '" + _role + "')");
		return (insertCount != 0) ? true : false;
	}

	// deletes a user or a user and all visits based on the boolean value passed
	// if the boolean is true then the users visits will also be deleted
	public static boolean deleteUser(String email, boolean deleteVisits) {
		if (deleteVisits) { // delete first due to foreign key
			DatabaseConnector.executeUpdate("DELETE FROM VISITS "
					+ "WHERE email = '" + email + "'");
		}
		int deleteCount = DatabaseConnector.executeUpdate("DELETE FROM USER "
				+ "WHERE email = '" + email + "'");

		return (deleteCount != 0) ? true : false;
	}

	public static boolean updateFName(String email, String update) {
		return updateUser(email, "fName", update);
	}

	public static boolean updateLName(String email, String update) {
		return updateUser(email, "lName", update);
	}

	public static boolean updatePhone(String email, String update) {
		return updateUser(email, "phone", update);
	}

	public static boolean updateEmail(String email, String update) {
		return updateUser(email, "email", update);
	}

	public static boolean updateRole(String email, String update) {
		return updateUser(email, "role", update);
	}

	// update user info
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
	 * check if the user exist in the database using primary key
	 * 
	 * @param email
	 * @return
	 */
	public static boolean user_exist(String email) {
		ArrayList<String> emails = getEmails();
		for (String emailLoop : emails) {
			if (emailLoop.equals(email)) {
				return true;
			}
		}
		return false;
	}

	// checks if user table is empty
	public static boolean isEmpty() {
		int count = DatabaseConnector.executeQueryInt("count",
				"SELECT COUNT(*) AS count FROM USER");
		return (count == 0) ? true : false;
	}

	// returns string ArrayList with ordered first names
	public static ArrayList<String> getFirstNames() {
		return DatabaseConnector.executeQueryStrings("fName",
				"SELECT fName FROM USER ORDER BY email ASC");
	}

	// returns string ArrayList with ordered last names
	public static ArrayList<String> getLastNames() {
		return DatabaseConnector.executeQueryStrings("lName",
				"SELECT lName FROM USER ORDER BY email ASC");
	}

	// returns string ArrayList with ordered emails
	public static ArrayList<String> getEmails() {
		return DatabaseConnector.executeQueryStrings("email",
				"SELECT email FROM USER ORDER BY email ASC");
	}

	// returns string ArrayList with ordered phones
	public static ArrayList<String> getPhotos() {
		return DatabaseConnector.executeQueryStrings("phone",
				"SELECT phone FROM USER ORDER BY email ASC");
	}

	// returns string ArrayList with ordered roles
	public static ArrayList<String> getRoles() {
		return DatabaseConnector.executeQueryStrings("role",
				"SELECT role FROM USER ORDER BY email ASC");
	}
	
	// returns string ArrayList with ordered first+last names
	public static ArrayList<String> getNames() {
		return DatabaseConnector
				.executeQueryStrings(
						"name",
						"SELECT CONCAT(fName, ' ', lName) AS name FROM USER ORDER BY email ASC");
	}
}
