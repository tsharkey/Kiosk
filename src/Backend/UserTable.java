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

    public void addUser(String _fName, String _lName, String _email, String _phone, String _role){

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

    //if the boolean is true then the users visits will also be deleted
    public void deleteUser(String email, boolean deleteVisits){
        try{
            Statement stmt = dc.getConnection().createStatement();
            String deleteUserTable = "DELETE FROM USER " +
                            "WHERE email = '" + email + "'";
            stmt.execute(deleteUserTable);
            if(deleteVisits){
                String deleteVisitsTable = "DELETE FROM USER " +
                                           "WHERE email = " + email;
                stmt.executeUpdate(deleteVisitsTable);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

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


}
