package Backend;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

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
}
