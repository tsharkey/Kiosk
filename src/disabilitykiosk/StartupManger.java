/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package disabilitykiosk;

import Backend.*;
import GUI.adddeletespec.AddDeleteSpecFrame;
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
    
    public StartupManger(){
        try {
            Data data = new Data();
        } catch (IOException ex) {
            Logger.getLogger(StartupManger.class.getName()).log(Level.SEVERE, null, ex);
        }
        SpecialistList s = new SpecialistList();
          
        Admin admin = new Admin();
        Admin.deSerialize();
        SpecialistList.deSerialize();
        
        //for the first use of the application
        //create an administrator account
        //open the managing-specialist window
        if(Admin.admins.isEmpty()){
            int submitted;
            JTextField unInput = new JTextField(10);
            JPasswordField pwInput = new JPasswordField(10);
            do{
                JPanel adminInput = new JPanel();
                adminInput.add(new JLabel("Username: " ));
                adminInput.add(unInput);
                adminInput.add(Box.createHorizontalStrut(15));
                adminInput.add(new JLabel("Password: "));
                adminInput.add(pwInput);
                submitted = JOptionPane.showConfirmDialog(null, adminInput, "Please enter new Admin Username & Password.", JOptionPane.OK_CANCEL_OPTION);
                if (submitted == JOptionPane.OK_OPTION && !unInput.getText().equals("") && pwInput.getPassword().length != 0) {
                    String pw = new String(pwInput.getPassword());
                    Admin.admins.add(new AdminAccount(unInput.getText(), pw));
                    Admin.serialize();//add the new administrator account to the file "admins"
                    //System.out.println(Admin.admins.toString());//test line
                }
                else if(submitted != JOptionPane.CANCEL_OPTION){
                      JOptionPane.showMessageDialog(null, "Please enter a Username and Password.", "Input Error", JOptionPane.ERROR_MESSAGE);

                }
            }while(submitted == JOptionPane.OK_OPTION && (unInput.getText().equals("") || pwInput.getPassword().length == 0));
        
            if(submitted == JOptionPane.CANCEL_OPTION) {
                System.exit(0);
            }
            
            new DisabilityKiosk();    
            new AddDeleteSpecFrame();
            Admin.isAdminWorking = true;
        }
        else{
            new DisabilityKiosk();
        }
    }
    
    
    public static void main(String[] args){
        new StartupManger();
    }
    
}
