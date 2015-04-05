/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.sql.*;

/**
 *
 * @author Sean
 */
public class SpecialistTable {
    private DatabaseConnector dc;

    public SpecialistTable(){
        dc = new DatabaseConnector();
    }

    //adds a specialist, TODO: the password needs to be hashed out, I don't know how to do this- TOM
    public void addSpecialist(String photo, String password, String email){
        try{
            Statement stmt = dc.getConnection().createStatement();
            String insert = "INSERT INTO SPECIALIST" +
                            "VALUES('" + photo + "', '" + password + "', '" + email +"')";
            stmt.executeUpdate(insert);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //deletes a specialist
    public void deleteSpecialist(String email){
        try{
            Statement stmt = dc.getConnection().createStatement();
            String delete = "DELETE FROM SPECIALIST" +
                            "WHERE email = '" + email + "'";
            stmt.executeUpdate(delete);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //returns a single specialist based on email
    public ResultSet getSpecialist(String email){
        ResultSet rs = null;
        try{
            Statement stmt = dc.getConnection().createStatement();
            String getOneSpecialist = "SELECT * FROM SPECIALIST WHERE email = '" + email +"'";
            rs = stmt.executeQuery(getOneSpecialist);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    //returns all specialists
    public ResultSet getAllSpecialists(){
        ResultSet rs = null;
        try{
            Statement stmt = dc.getConnection().createStatement();
            String getSpecialists = "SELECT * FROM SPECIALIST";
            rs = stmt.executeQuery(getSpecialists);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }
    
}
