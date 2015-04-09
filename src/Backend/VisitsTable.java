package Backend;

import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author Sean Johnston, Brendan Casey
 */
public class VisitsTable {
	
    //creates a new visit, the email must match an already existing email in the user database
    public static boolean addVisit(String reason, Boolean followUp, String email, String specialist, String location){
    	int insertCount = DatabaseConnector.executeUpdate("INSERT INTO VISITS(reason, followUp, email, specialist, location) "
                + "VALUES('" + reason + "', " + followUp + ", '" + email + "', '" + specialist + "', '" + location + "')");
    	return (insertCount != 0) ? true : false;
    }

    //deletes a visit based on date and email
    public static boolean deleteVisit(String email, String date, String time){
    	int deleteCount = DatabaseConnector.executeUpdate("DELETE FROM VISITS" +
                "WHERE visitDate = '" + date + "'and visitTime = '" + time + "' and email = '" + email + "'");
    	return (deleteCount != 0) ? true : false;
    }
    
    public static ArrayList<Date> getVisitDates()
    {
    	return DatabaseConnector.executeQueryDates("visitDate", "SELECT visitDate FROM VISITS ORDER BY ID ASC", false);
    }
    
    public static ArrayList<Date> getVisitTimes()
    {
    	return DatabaseConnector.executeQueryDates("visitTime", "SELECT visitTime FROM VISITS ORDER BY ID ASC", true);
    }
    
    public static ArrayList<Boolean> getFollowUps()
    {
    	return DatabaseConnector.executeQueryBools("followUp", "SELECT followUp FROM VISITS ORDER BY ID ASC");
    }
    
    public static ArrayList<String> getReasons()
    {
    	return DatabaseConnector.executeQueryStrings("reason", "SELECT reason FROM VISITS ORDER BY ID ASC");
    }
    
    public static ArrayList<String> getEmails()
    {
    	return DatabaseConnector.executeQueryStrings("email", "SELECT email FROM VISITS ORDER BY ID ASC");
    }
    
    public static ArrayList<String> getSpecialists()
    {
    	return DatabaseConnector.executeQueryStrings("specialist", "SELECT specialist FROM VISITS ORDER BY ID ASC");
    }
    
    public static ArrayList<String> getLocations()
    {
    	return DatabaseConnector.executeQueryStrings("location", "SELECT location FROM VISITS ORDER BY ID ASC");
    }
    

    /*
    //takes in a start and end date to return a set of visits between the ranges
    public static ResultSet visitsByDate(String startDate, String endDate){
        return null;//DatabaseConnector.executeQuery("SELECT * FROM VISITS"+
                //"WHERE visitDate between '"+startDate+"' and '"+endDate+"'");
    }
    */
    
    /*
    public static ArrayList<VisitData> searchVisitString(String query, String column)
    {
    	//
    }
    */
}
