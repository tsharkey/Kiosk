/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author Sean Johnston
 */
public class UserTable {
    
    private Connection conn = null;
    private Statement stmt = null;
    
    
public UserTable(Connection conn){
    
    this.conn = conn;
    
    try{
    stmt = conn.createStatement();
    }
    catch(Exception e){
            e.printStackTrace();
            System.out.println("error");
            
    }
}

public void addUser(String[] args){
    
    try
    {
        
    String insertion = "insert into USER(fName, lName, email, phone, role) "
            + "VALUES("+args[1]+", "+args[2]+", "+args[3]+
            ", "+args[4]+", "+args[5]+")";
    
    stmt.executeUpdate(insertion);
    
    }
    
    catch(Exception e){
      
      e.printStackTrace();
    }
}

public ResultSet getAllUsers(){
    
    
    ResultSet rs;
    return rs;
}


}
