/*
 * 
 */
package Backend;

import java.sql.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author Sean Johnston
 */
public class DatabaseConnector {


public DatabaseConnector(){

}


public Connection getConnection() {

//establishing the connection to the database
        MysqlDataSource dataSource = new MysqlDataSource();

        //user information
        dataSource.setUser("kiosk");
        dataSource.setPassword("massbaysp2015");
        dataSource.setURL("jdbc:mysql://107.170.166.28:3306/kioskdb2");

        //make the connection and create the statement
        try{
            Connection conn = dataSource.getConnection();
            return conn;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("error");
            return null;
        }

    }
}