/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;
import java.sql.Connection;
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
}
