package Backend;

import java.sql.ResultSet;
import java.sql.Statement;

//checking the commit
/**
 * Created by Parth Patel and John Cyzeski, Maria del Mar Moncaleano
 */
public class AdminTable
{

    private DatabaseConnector dc;

        public AdminTable()
        {
            dc = new DatabaseConnector();
        }

        //Adds an Admin
        public void addAdmin(String user, String password)
        {
            try
            {
                Statement stmt = dc.getConnection().createStatement();
                String insertion = "INSERT INTO ADMIN\" + " +
                        "VALUES('" + user + "', '" + PasswordHash.createHash(password) + "')";
                stmt.executeUpdate(insertion);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        //Deletes an Admin
        public void deleteAdmin(String user, String password){
            try
            {
                Statement stmt = dc.getConnection().createStatement();
                String deleteUserTable = "DELETE FROM ADMIN " +
                        "WHERE user = '" + user + "'";
                stmt.execute(deleteUserTable);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        //returns all of the Admins
        public ResultSet getAllAdmins()
        {
            ResultSet rs = null;
            try
            {
                Statement stmt = dc.getConnection().createStatement();
                String getAllAdmins = "SELECT * FROM ADMIN";
                rs = stmt.executeQuery(getAllAdmins);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return rs;
        }

        //verify password   TODO implement the sanitizerUser in the SqlStatements to check the input for the user
        public boolean verifyPassword(String user, String password)
        {
            boolean isValid = false;
           // if(SqlStatement.sanitizeUser(user) == false)
           // {
           //     return isValid;
           // }
            try
            {
                Statement stmt = dc.getConnection().createStatement();
                String getHash = "SELECT hash FROM ADMIN WHERE hash = SELECT hash FROM ADMIN WHERE user='" + user +"')";
                ResultSet rs = stmt.executeQuery(getHash);
                if(rs.next())
                {
                    isValid = PasswordHash.validatePassword(password, rs.getString("hash"));
                }
                rs.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return isValid;
        }
    }


