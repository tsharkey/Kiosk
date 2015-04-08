package Backend;

import java.util.ArrayList;

/**
 *
 * @author Sean Johnston, Brendan Casey
 */
public class UserTable {

    //adding a user to database
    public static boolean addUser(String _fName, String _lName, String _phone, String _email, String _role)
    {
    	int insertCount = DatabaseConnector.executeUpdate("INSERT INTO USER "
                + "VALUES('"+_fName+"', '"+_lName+"', '"+_email+ "', '"+_phone+"', '"+_role+"')");
    	return (insertCount != 0) ? true : false;
    }

    //deletes a user or a user and all visits based on the boolean value passed
    //if the boolean is true then the users visits will also be deleted
    public static boolean deleteUser(String email, boolean deleteVisits){
    	if(deleteVisits){ // delete first due to foreign key
    		DatabaseConnector.executeUpdate("DELETE FROM VISITS " +
                    "WHERE email = '" + email +"'");
        }
    	int deleteCount = DatabaseConnector.executeUpdate("DELETE FROM USER " +
                "WHERE email = '" + email + "'");

    	return (deleteCount != 0) ? true : false;
    }
    
    // update user info
    public static boolean updateUser(String email, String column, String update){
    	int updateCount = 0;
    	switch(column){
    		case "fName":
    		case "lName":
    		case "phone":
    		case "email":
    		case "role":
    			updateCount = DatabaseConnector.executeUpdate("UPDATE USER SET " + column + "='" + update + "' WHERE email = '" + email +"'");
    			break;
    		default:
    			break;
    	}
    	return (updateCount != 0) ? true : false;
    }
    
    // checks if user table is empty
    public static boolean isEmpty()
    {
    	int count = DatabaseConnector.executeQueryInt("count", "SELECT COUNT(*) AS count FROM USER");
    	return (count == 0) ? true : false;
    }
    
    // returns string ArrayList with ordered first names
    public static ArrayList<String> getFirstNames()
    {
    	return DatabaseConnector.executeQueryStrings("fName", "SELECT fName FROM USER");
    }
    
    // returns string ArrayList with ordered last names
    public static ArrayList<String> getLastNames()
    {
    	return DatabaseConnector.executeQueryStrings("lName", "SELECT lName FROM USER");
    }
    
    // returns string ArrayList with ordered emails
    public static ArrayList<String> getEmails()
    {
    	return DatabaseConnector.executeQueryStrings("email", "SELECT email FROM USER");
    }
    
    // returns string ArrayList with ordered phones
    public static ArrayList<String> getPhotos()
    {
    	return DatabaseConnector.executeQueryStrings("phone", "SELECT phone FROM USER");
    }
    
    // returns string ArrayList with ordered roles
    public static ArrayList<String> getRoles()
    {
    	return DatabaseConnector.executeQueryStrings("role", "SELECT role FROM USER");
    }
}
