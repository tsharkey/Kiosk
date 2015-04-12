package Backend;

import java.util.ArrayList;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * This class handles interfacing with the MySQL database with regards to the
 * ADMIN table that has two columns: 'user' and 'hash'.
 * 
 * @author Parth Patel, John Cyzeski, Maria del Mar Moncaleano, Brendan Casey
 * 
 *         TODO: proper error handling
 */

public class AdminTable {
	/**
	 * Creates a new entry in ADMIN table with specified user name and password.
	 * If user already exists, update password instead.
	 * 
	 * @param user
	 * @param password
	 * @return boolean of success
	 */
	public static boolean addAdmin(String user, String password) {
		if (admin_exist(user)) {
			// attempt to UPDATE rather than INSERT if user already exists
			// logic path could always return false to disable
			return updatePassword(user, password);
		}
		int insertCount = 0;
		try {
			insertCount = DatabaseConnector
					.executeUpdate("INSERT INTO ADMIN VALUES('" + user + "', '"
							+ PasswordHash.createHash(password) + "')");
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return (insertCount != 0) ? true : false;
	}

	/**
	 * Update the password for an existing user with string found in
	 * newPassword.
	 * 
	 * @param user
	 * @param newPassword
	 * @return boolean of success
	 */
	public static boolean updatePassword(String user, String newPassword) {
		int updateCount = 0;
		try {
			updateCount = DatabaseConnector
					.executeUpdate("UPDATE ADMIN SET hash='"
							+ PasswordHash.createHash(newPassword)
							+ "' WHERE user = '" + user + "'");
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return (updateCount != 0) ? true : false;
	}

	/**
	 * Deletes specified user from ADMIN table.
	 * 
	 * @param user
	 * @return boolean of success
	 */
	public static boolean deleteAdmin(String user) {
		int insertCount = DatabaseConnector
				.executeUpdate("DELETE FROM ADMIN WHERE user = '" + user + "'");
		return (insertCount != 0) ? true : false;
	}

	/**
	 * Verify password for specified user.
	 * 
	 * @param user
	 * @param password
	 * @return boolean of success
	 */
	public static boolean verifyPassword(String user, String password) {
		boolean isValid = false;
		String hash = DatabaseConnector.executeQueryString("hash",
				"SELECT hash FROM ADMIN WHERE hash = (SELECT hash FROM ADMIN WHERE user='"
						+ user + "')");
		if (hash != null) {
			try {
				isValid = PasswordHash.validatePassword(password, hash);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
			}
		}
		return isValid;
	}

	/**
	 * Get a list of all user names found inside ADMIN table.
	 *
	 * @return ArrayList of user strings found in ADMIN
	 */
	public static ArrayList<String> getAdmins() {
		return DatabaseConnector.executeQueryStrings("user",
				"SELECT user FROM ADMIN ORDER BY user ASC");
	}

	/**
	 * Check if the specified user exists inside the ADMIN table
	 * 
	 * @param user
	 * @return boolean whether exists or not
	 */
	public static boolean admin_exist(String user) {
		ArrayList<String> admins = getAdmins();
		for (String admin : admins) {
			if (admin.equals(user)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if ADMIN table is empty.
	 *
	 * @return boolean whether it is empty or not
	 */
	public static boolean isEmpty() {
		int count = DatabaseConnector.executeQueryInt("count",
				"SELECT COUNT(*) AS count FROM ADMIN");
		return (count == 0) ? true : false;
	}
}