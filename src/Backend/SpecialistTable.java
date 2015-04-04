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
 * @author Sean
 */
public class SpecialistTable {
    
    private Connection conn = null;
    private Statement stmt = null;
    
    public SpecialistTable(Connection conn){
        
        this.conn = conn;
        
        try{
        stmt = conn.createStatement();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("error");
            
        }
    }
    
    public void addSpecialist(String[] args){
        
    }
    
    
}
