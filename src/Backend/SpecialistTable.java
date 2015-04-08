package backend;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

/**
 * This class handles interfacing with SpecialistTable inside the database.
 *
 * @author Sean Johnston, Brendan Casey
 * TODO: - vulnerable to SQL injections
 */
public class SpecialistTable {
	
    // adds a specialist
	// return false if failed
    public static boolean addSpecialist(String fName, String lName, String phone,
    		String email, String password, String photo)
    {
    	if(UserTable.addUser(fName, lName, phone, email, "Specialist") != true){
    		return false;// cannot add user or they already exist, attempt to update instead?
    	}
    	return insertSpecialist(email, password, photo);
    }
    
    // insert specialist with photo, photo can be null
    public static boolean insertSpecialist(String email, String password, String photo)
    {
    	int insertCount = 0;
    	try {
    		insertCount = DatabaseConnector.executeUpdate("INSERT INTO SPECIALIST " +
			        "VALUES(" + ((photo != null) ? ("'" + photo + "', ") : "NULL, ") +  "'" + PasswordHash.createHash(password) + "', '" + email +"')");
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
    	return (insertCount != 0) ? true : false;
    }

    //deletes a specialist
    public static boolean deleteSpecialist(String email)
    {
    	int insertCount = DatabaseConnector.executeUpdate("DELETE FROM USER " +
                "WHERE email = '" + email + "'");
    	return (insertCount != 0) ? true : false;
    }

    //updates a single specialist based on email
    //does it need ability to change name, phone?
    public static boolean updateSpecialist(String email, String column, String update){
    	int updateCount = 0;
    	switch(column){
    		case "photo":
    			updateCount = DatabaseConnector.executeUpdate("UPDATE SPECIALIST SET photo='" + update + "' WHERE email = '" + email +"'");
    			break;
    		case "hash":
				try {
					updateCount = DatabaseConnector.executeUpdate("UPDATE SPECIALIST SET hash='" + PasswordHash.createHash(update) + "' WHERE email = '" + email +"'");
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
					e.printStackTrace();
				}
    			break;
    		case "email":
    			updateCount = DatabaseConnector.executeUpdate("UPDATE USER SET email='" + update + "' WHERE email = '" + email +"'");
    			break;
       		default:
    			break;
    	}
    	return (updateCount != 0) ? true : false;
    }
    
    // checks if specialist table is empty
    public static boolean isEmpty()
    {
    	int count = DatabaseConnector.executeQueryInt("count", "SELECT COUNT(*) AS count FROM SPECIALIST");
    	return (count == 0) ? true : false;
    }
    
    // verify password
    public static boolean verifyPassword(String email, String password)
    {
    	boolean isValid = false;
    	if(SqlStatements.sanitizeEmail(email) == false){
    		return isValid;
    	}
    	
    	String hash = DatabaseConnector.executeQueryString(1, "SELECT hash FROM SPECIALIST WHERE hash = (SELECT hash FROM SPECIALIST WHERE email='" + email +"')");
    	if(hash != null){
	    	try {
				isValid = PasswordHash.validatePassword(password, hash);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
			}
    	}
        return isValid;
    }
    
    // returns string ArrayList with ordered photo file names
    public static ArrayList<String> getPhotos()
    {
    	return DatabaseConnector.executeQueryStrings("photo", "SELECT photo FROM SPECIALIST");
    }
    
    // returns string ArrayList with ordered phone numbers
    public static ArrayList<String> getPhones()
    {
    	return DatabaseConnector.executeQueryStrings("phone", "SELECT phone AS phone FROM USER INNER JOIN SPECIALIST ON USER.email=SPECIALIST.email");
    }
    
    // returns string ArrayList with ordered first+last names
    public static ArrayList<String> getNames()
    {
    	return DatabaseConnector.executeQueryStrings("name", "SELECT CONCAT(fName, ' ', lName) AS name FROM USER INNER JOIN SPECIALIST ON USER.email=SPECIALIST.email");
    }
}
