/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Sean, Brendan
 */
public class SpecialistTable {
    static private DatabaseConnector dc;

    public SpecialistTable(){
        dc = new DatabaseConnector();
    }

    // adds a specialist
    public void addSpecialist(String photo, String password, String email)
    {
        try{
            Statement stmt = dc.getConnection().createStatement();
            String insert = "INSERT INTO SPECIALIST" +
                            "VALUES('" + photo + "', '" + PasswordHash.createHash(password) + "', '" + email +"')";
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
    
    // checks if specialist table is empty
    public static boolean isEmpty()
    {
    	int count = 0;
        try{
            Statement stmt = dc.getConnection().createStatement();
            String getSpecialistCount = "SELECT COUNT(*) AS count FROM SPECIALIST";
            ResultSet rs = stmt.executeQuery(getSpecialistCount);
            rs.next();
            count = rs.getInt("count");
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return (count == 0) ? true : false;
    }
    
    // verify password
    public static boolean verifyPassword(String email, String password)
    {
    	boolean isValid = false;
    	if(SqlStatements.sanitizeEmail(email) == false){
    		return isValid;
    	}
    	
        try{
            Statement stmt = dc.getConnection().createStatement();
            String getHash = "SELECT hash FROM SPECIALIST WHERE hash = (SELECT hash FROM SPECIALIST WHERE email='" + email +"')";
            ResultSet rs = stmt.executeQuery(getHash);
            if(rs.next()){ // making sure email exist with hash
            	isValid = PasswordHash.validatePassword(password, rs.getString("hash"));
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return isValid;
    }
    
    // returns string ArrayList with ordered photo file names
    public static ArrayList<String> getSpecialistPhotos()
    {
    	ArrayList<String> specPhotos = new ArrayList<String>();
        try{
            Statement stmt = dc.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT photo FROM SPECIALIST");
            while(rs.next()){
            	specPhotos.add(rs.getString("photo"));
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return specPhotos;
    }
    
    // returns string ArrayList with ordered first+last names
    public static ArrayList<String> getSpecialistNames()
    {
    	ArrayList<String> specNames = new ArrayList<String>();
        try{
            Statement stmt = dc.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT CONCAT(fName, ' ', lName) AS name FROM USER INNER JOIN SPECIALIST ON USER.email=SPECIALIST.email");
            while(rs.next()){
            	specNames.add(rs.getString("name"));
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return specNames;
    }
}
