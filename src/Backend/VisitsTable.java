/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author Sean Johnston
 */
public class VisitsTable {
    private DatabaseConnector dc;
    
    public VisitsTable(Connection conn){
        dc = new DatabaseConnector();
    }

    public void addVisit(String[] args){
        
    }
}
