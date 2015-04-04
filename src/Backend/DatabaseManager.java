/*
 * The DatabaseManager contains several other classes used for database functions.
 * 
 */
package Backend;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author Sean
 */
public class DatabaseManager {
    
    private Connection con;
    private UserTable ut;
    private SpecialistTable st;
    private DatabaseConnector dc;
    private VisitsTable vt;
    
public DatabaseManager() {
    
    dc = new DatabaseConnector();
    con = dc.getConnection();
    ut = new UserTable(con);
    st = new SpecialistTable(con);
    vt = new VisitsTable(con);
}

public void addUser(String str) {
    ut.addUser(str);
}
}
