package Backend;

import java.util.ArrayList;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * This class handles interfacing with the MySQL database with regards to the
 * SPECIALIST table that has three columns: 'photo', 'hash', and 'email'.
 * 
 * @author Sean Johnston, Brendan Casey
 * 
 *         TODO: proper error handling
 */

public class SpecialistTable {
	/**
	 * Create a new USER entry as well as SPECIALIST in the database using
	 * parameter values.
	 * 
	 * @param fName
	 * @param lName
	 * @param phone
	 * @param email
	 * @param password
	 * @param photo
	 * @return boolean of success
	 */
	public static boolean addSpecialist(String fName, String lName,
			String phone, String email, String password, String photo) {
		if (UserTable.addUser(fName, lName, phone, email, "Specialist") != true) {
			return false; // cannot add user or they already exist, attempt to
							// update instead?
		}
		return insertSpecialist(email, password, photo);
	}

	/**
	 * Insert Specialist information into database.
	 * 
	 * @param email
	 * @param password
	 * @param photo
	 *            can be null, uses search.jpg by default
	 * @return boolean of success
	 */
	public static boolean insertSpecialist(String email, String password,
			String photo) {
		int insertCount = 0;
		try {
			insertCount = DatabaseConnector
					.executeUpdate("INSERT INTO SPECIALIST VALUES("
							+ ((photo != null) ? ("'" + photo + "', ")
									: "NULL, ") + "'"
							+ PasswordHash.createHash(password) + "', '"
							+ email + "')");
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return (insertCount != 0) ? true : false;
	}

	/**
	 * Deletes specified specialist using primary key.
	 * 
	 * @param email
	 * @return boolean of success
	 */
	public static boolean deleteSpecialist(String email) {
		int insertCount = DatabaseConnector
				.executeUpdate("DELETE FROM USER WHERE email = '" + email + "'");
		return (insertCount != 0) ? true : false;
	}

	/**
	 * Update an existing SPECIALIST entry's photo value with update parameter.
	 * 
	 * @param email
	 * @param update
	 * @return boolean of success
	 */
	public static boolean updatePhoto(String email, String update) {
		return updateSpecialist(email, "photo", update);
	}

	/**
	 * Update an existing SPECIALIST entry's password hash value with update
	 * parameter.
	 * 
	 * @param email
	 * @param update
	 * @return boolean of success
	 */
	public static boolean updatePassword(String email, String update) {
		return updateSpecialist(email, "hash", update);
	}

	/**
	 * Update an existing SPECIALIST entry's email value with update parameter.
	 * 
	 * @param email
	 * @param update
	 * @return boolean of success
	 */
	public static boolean updateEmail(String email, String update) {
		return updateSpecialist(email, "email", update);
	}

	/**
	 * Update an existing SPECIALIST entry's phone value with update parameter.
	 * 
	 * @param email
	 * @param update
	 * @return boolean of success
	 */
	public static boolean updatePhone(String email, String update) {
		return updateSpecialist(email, "phone", update);
	}

	/**
	 * Generic method to handle updating various columns from SPECIALIST table
	 * using the email primary key to locate entry and the store update value.
	 * 
	 * @param email
	 * @param column
	 * @param update
	 * @return boolean of success
	 */
	private static boolean updateSpecialist(String email, String column,
			String update) {
		if (!specialist_exist(email)) {
			return false; // specialist doesn't exist
		}
		int updateCount = 0;
		switch (column) {
		case "photo":
			updateCount = DatabaseConnector
					.executeUpdate("UPDATE SPECIALIST SET photo='" + update
							+ "' WHERE email = '" + email + "'");
			break;
		case "hash":
			try {
				updateCount = DatabaseConnector
						.executeUpdate("UPDATE SPECIALIST SET hash='"
								+ PasswordHash.createHash(update)
								+ "' WHERE email = '" + email + "'");
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
			}
			break;
		case "email":
			updateCount = DatabaseConnector
					.executeUpdate("UPDATE USER SET email='" + update
							+ "' WHERE email = '" + email + "'");
			break;
		case "phone":
			updateCount = DatabaseConnector
					.executeUpdate("UPDATE USER SET phone='" + update
							+ "' WHERE email = '" + email + "'");
			break;
		default:
			break;
		}
		return (updateCount != 0) ? true : false;
	}

	/**
	 * Verify password for specified specialist's email.
	 * 
	 * @param email
	 * @param password
	 * @return boolean of success
	 */
	public static boolean verifyPassword(String email, String password) {
		boolean isValid = false;
		String hash = DatabaseConnector
				.executeQueryString(
						"hash",
						"SELECT hash FROM SPECIALIST WHERE hash = (SELECT hash FROM SPECIALIST WHERE email='"
								+ email + "')");
		if (hash != null) { // making sure password hash exists
			try {
				isValid = PasswordHash.validatePassword(password, hash);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
			}
		}
		return isValid;
	}

	/**
	 * Check if SPECIALIST table is empty.
	 * 
	 * @return boolean whether it is empty or not
	 */
	public static boolean isEmpty() {
		int count = DatabaseConnector.executeQueryInt("count",
				"SELECT COUNT(*) AS count FROM SPECIALIST");
		return (count == 0) ? true : false;
	}

	/**
	 * Check if the specialist exist in the database using primary key.
	 * 
	 * @param email
	 * @return boolean of successfully found or not
	 */
	public static boolean specialist_exist(String email) {
		ArrayList<String> emails = getEmails();
		for (String emailLoop : emails) {
			if (emailLoop.equals(email)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets an ordered ArrayList of all the email strings from SPECIALIST table.
	 * 
	 * @return ArrayList of email strings
	 */
	public static ArrayList<String> getEmails() {
		return DatabaseConnector.executeQueryStrings("email",
				"SELECT email FROM SPECIALIST ORDER BY email ASC");
	}

	/**
	 * Gets an ordered ArrayList of all the photo file name strings from
	 * SPECIALIST table.
	 * 
	 * @return ArrayList of photo strings
	 */
	public static ArrayList<String> getPhotos() {
		return DatabaseConnector.executeQueryStrings("photo",
				"SELECT photo FROM SPECIALIST ORDER BY email ASC");
	}

	/**
	 * Gets an ordered ArrayList of all the phone number strings from SPECIALIST
	 * table.
	 * 
	 * @return ArrayList of phone strings
	 */
	public static ArrayList<String> getPhones() {
		return DatabaseConnector
				.executeQueryStrings(
						"phone",
						"SELECT phone AS phone FROM USER INNER JOIN SPECIALIST ON USER.email=SPECIALIST.email ORDER BY SPECIALIST.email ASC");
	}

	/**
	 * Gets an ordered ArrayList of all the first+last name strings from
	 * SPECIALIST table.
	 * 
	 * @return ArrayList of name strings
	 */
	public static ArrayList<String> getNames() {
		return DatabaseConnector
				.executeQueryStrings(
						"name",
						"SELECT CONCAT(fName, ' ', lName) AS name FROM USER INNER JOIN SPECIALIST ON USER.email=SPECIALIST.email ORDER BY SPECIALIST.email ASC");
	}

	/**
	 * Find an email of specialist based on first and last name.
	 * 
	 * @param fName
	 * @param lName
	 * @return String of email or null if not found
	 */
	public static String getEmailFromName(String fName, String lName) {
		return DatabaseConnector
				.executeQueryString(
						"email",
						"SELECT email FROM SPECIALIST WHERE email IN (SELECT email FROM USER WHERE fName='"
								+ fName + "' AND lName='" + lName + "')");
	}
}
