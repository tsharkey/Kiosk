package Backend;
import java.sql.*;

/**
 * Created by Tom on 4/1/15.
 *
 * These are sample methods of what the code might look like for making the updates to the db
 */
public class SqlStatements{
    private Connection connection;

    //should take in the db connection from the admin or should it be made here
    public SqlStatements(){
        //init the connection

    }

    //insert method
    public void makeInsert(String insertString){
        String insert = insertString;
        try{
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(insert);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    //delete method
    public void makeDelete(String deleteString){
        String delete = deleteString;
        try{
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(delete);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    //query method
    public ResultSet makeQuery(String sqlStatement){
        //has a connection to the database
        String query = sqlStatement;
        ResultSet rs = null;
        try{
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            return rs;

        }  catch (SQLException e ) {
            e.printStackTrace();
        }
        return rs;
    }
}
