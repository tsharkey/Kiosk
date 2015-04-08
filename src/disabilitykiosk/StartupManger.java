/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package disabilitykiosk;

import Backend.*;
import GUI.adddeletespec.AddDeleteSpecFrame;
import GUI.loginwindow.DatabaseInitFrame;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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
