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

public void addUser(String[] str) {
    ut.addUser(str);
}

public void addSpecialist(){
    
    st.addSpecialist();
}

public void addVisit(String[] args){
    
    
}

public void addAdmin(){
    
}
//Since the only information stored in the db about admins
//is username and password, what does this method return?
public ResultSet getAdmin(){
    
}

public ResultSet getUser(){
    
}

public ResultSet getSpecialist(){
    
    
}

public ResultSet getVisit(){
    
}

public ResultSet getUserTable(){
    
    return ut.getAllUsers();
}

public ResultSet getSpecialistTable(){
    
}

public ResultSet getVisitsTable(){
    
}

//Get all visits between date range
public ResultSet getVisitsInDateRange(){
    
}

//Get all the visits specific to one student
public ResultSet getVisits(){
    
}

public void deleteUser(){
    
}

public void deleteSpecialist(){
    
}

public void deleteVisit(){
    
}

public void deleteAdmin(){
    
}
}
