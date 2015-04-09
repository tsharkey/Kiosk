package Backend;

import java.sql.ResultSet;
/**
 *
 * @author Sean Johnston, Brendan Casey
 */
public class VisitsTable {
	
    //creates a new visit, the email must match an already existing email in the user database
    public static void addVisit(String reason, int followUp, String email, String specialist, String location){
    	DatabaseConnector.executeUpdate("INSERT INTO VISITS(visitDate, visitTime, reason, followUp, email, ID, specialist, location)"
                +"VALUES(null, null, '"+reason+"', "+followUp+",'"+email+"', null, '"+specialist+"', '"+location+"')");
    }

    //deletes a visit based on date and email
    public static void deleteVisit(String email, String date, String time){
    	DatabaseConnector.executeUpdate("DELETE FROM VISITS" +
                "WHERE visitDate = '" + date + "'and visitTime = '" + time + "' and email = '" + email + "'");
    }

    //takes in a start and end date to return a set of visits between the ranges
    public static ResultSet visitsByDate(String startDate, String endDate){
        return null;//DatabaseConnector.executeQuery("SELECT * FROM VISITS"+
                //"WHERE visitDate between '"+startDate+"' and '"+endDate+"'");
    }
}
