package Backend;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.util.regex.Pattern; 
import java.util.regex.Matcher; 

/**
 * Created by Tom on 4/1/15.
 *
 * These are sample methods of what the code might look like for making the updates to the db
 */
public class SqlStatements{
    private MysqlDataSource dataSource;
    private Connection conn;


    public SqlStatements(){

    }

    //insert method
    public void makeInsert(String insertString){
        String insert = insertString;
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(insert);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    //delete method
    public void makeDelete(String deleteString){
        String delete = deleteString;
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(delete);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //query method
    public ResultSet makeQuery(String sqlStatement){
        //has a connection to the database
        String query = sqlStatement;
        ResultSet rs = null;
        try{
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            return rs;

        }  catch (Exception e ) {
            e.printStackTrace();
        }
        return rs;
    }
    
    // sanitize email input to prevent SQL injection
    public static boolean sanitizeEmail(String email)
    {
        Pattern pattern = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
