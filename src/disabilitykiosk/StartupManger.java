/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package disabilitykiosk;

import GUI.loginwindow.DatabaseInitFrame;


/**
 *
 * @author Eric Sullivan
 */
public class StartupManger {
    
    public StartupManger() {
        
        DatabaseInitFrame dbcf = new DatabaseInitFrame();
        
        
    }
    
    public static void main(String[] args){
        new StartupManger();
    }
    
}
