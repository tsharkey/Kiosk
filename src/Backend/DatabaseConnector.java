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
            Statement stmt = conn.createStatement();

            //sql statement
            ResultSet rs = stmt.executeQuery("SELECT * FROM USER");
            //iterate over the result set and use getString() by column name
            while(rs.next()){
                String s = rs.getString("fName");
                System.out.println(s);

             return conn;
            }
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("error");
        }
}