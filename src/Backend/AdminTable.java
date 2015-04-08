package Backend;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

/**
 * This class handles interfacing with the MySQL database with regards to the
 * ADMIN table
 * 
 * @author Parth Patel, John Cyzeski, Maria del Mar Moncaleano, Brendan Casey
 * 
 * TODO: vulnerable to SQL injections -> sanitize inputs
 */

public class AdminTable {

	// Adds an Admin
	public static boolean addAdmin(String user, String password) {
		int insertCount = 0;
		try {
			insertCount = DatabaseConnector.executeUpdate("INSERT INTO ADMIN\" + " + "VALUES('" + user
					+ "', '" + PasswordHash.createHash(password) + "')");
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return (insertCount != 0) ? true : false;
	}
	
	// Deletes an Admin
    public static boolean deleteAdmin(String user)
    {
    	int insertCount = DatabaseConnector.executeUpdate("DELETE FROM ADMIN " +
                "WHERE user = '" + user + "'");
    	return (insertCount != 0) ? true : false;
    }

	// check the input for the user
	public static boolean verifyPassword(String user, String password) {
		boolean isValid = false;
		String hash = DatabaseConnector.executeQueryString(1, "SELECT hash FROM ADMIN WHERE hash = (SELECT hash FROM ADMIN WHERE user='" + user +"')");
		if(hash != null){
			try {
				isValid = PasswordHash.validatePassword(password, hash);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
			}
		}
		return isValid;
	}
	
	//update password for existing user
	public static boolean updatePassword(String user, String newPassword)
	{
		int updateCount = 0;
		try {
			updateCount = DatabaseConnector.executeUpdate("UPDATE ADMIN SET hash='" + PasswordHash.createHash(newPassword) + "' WHERE user = '" + user +"'");
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return (updateCount != 0) ? true : false;
	}
	
	// returns a list of admins
    public static ArrayList<String> getAdmins()
    {
    	return DatabaseConnector.executeQueryStrings("user", "SELECT user FROM ADMIN");
    }
    
    // checks if admin table is empty
    public static boolean isEmpty()
    {
    	int count = DatabaseConnector.executeQueryInt("count", "SELECT COUNT(*) AS count FROM ADMIN");
    	return (count == 0) ? true : false;
    }
}
