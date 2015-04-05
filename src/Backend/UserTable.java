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
public class UserTable {
    private DatabaseConnector dc;

    public UserTable(){
        dc = new DatabaseConnector();
    }

    //adding a user to database
    public void addUser(String _fName, String _lName, String _email, String _phone, String _role){
        //make the connection to the database then create the statement and execute it
        try
        {
            Statement stmt = dc.getConnection().createStatement();
            String insertion = "INSERT INTO USER(fName, lName, email, phone, role)"
                    + "VALUES('"+_fName+"', '"+_lName+"', '"+_email+
                    "', '"+_phone+"', '"+_role+"')";
            stmt.executeUpdate(insertion);
        }

        catch(Exception e){
          e.printStackTrace();
        }
    }

    //deletes a user or a user and all visits based on the boolean value passed
    //if the boolean is true then the users visits will also be deleted
    public void deleteUser(String email, boolean deleteVisits){
        try{
            Statement stmt = dc.getConnection().createStatement();
            String deleteUserTable = "DELETE FROM USER " +
                            "WHERE email = '" + email + "'";
            stmt.execute(deleteUserTable);
            if(deleteVisits){
                String deleteVisitsTable = "DELETE FROM VISITS " +
                                           "WHERE email = '" + email +"'";
                stmt.executeUpdate(deleteVisitsTable);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //returns all of the users
    public ResultSet getAllUsers(){
        ResultSet rs = null;
        try{
            Statement stmt = dc.getConnection().createStatement();
            String getAllUsers = "SELECT * FROM USER";
            rs = stmt.executeQuery(getAllUsers);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    //returns a user based on a specific email
    public ResultSet getUser(String email){
        ResultSet rs = null;
        try{
            Statement stmt = dc.getConnection().createStatement();
            String getUser = "SELECT * FROM USER WHERE email = '" + email +"'";
            rs = stmt.executeQuery(getUser);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }


}
