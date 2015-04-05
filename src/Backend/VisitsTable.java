/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author Sean Johnston
 */
public class VisitsTable {
    private DatabaseConnector dc;
    
    public VisitsTable(){
        dc = new DatabaseConnector();
    }

    //creates a new visit, the email must match an already existing email in the user database
    public void addVisit(String reason, int followUp, String email, String specialist, String location){
        try{
            Statement stmt = dc.getConnection().createStatement();
            String insert = "INSERT INTO VISITS(visitDate, visitTime, reason, followUp, email, ID, specialist, location)"
                           +"VALUES(null, null, '"+reason+"', "+followUp+",'"+email+"', null, '"+specialist+"', '"+location+"')";
            stmt.executeUpdate(insert);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    //deletes a visit based on date and email
    public void deleteVisit(String email, String date, String time){
        try{
            Statement stmt = dc.getConnection().createStatement();
            String delete = "DELETE FROM VISITS" +
                            "WHERE visitDate = '" + date + "'and visitTime = '" + time + "' and email = '" + email + "'";
            stmt.executeUpdate(delete);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    //takes in a start and end date to return a set of visits between the ranges
    public ResultSet visitsByDate(String startDate, String endDate){
        ResultSet rs = null;
        try{
            Statement stmt = dc.getConnection().createStatement();
            String visits = "SELECT * FROM VISITS"+
                            "WHERE visitDate between '"+startDate+"' and '"+endDate+"'";
            rs = stmt.executeQuery(visits);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }
}
