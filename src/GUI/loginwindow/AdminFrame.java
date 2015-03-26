/**
 * Created by Calvin Wong on 04/26/2014
 *
 * AfterLoginFrame
 *
 */
package GUI.loginwindow;

import disabilitykiosk.*;
import Backend.Admin;
import Backend.AdminAccount;
import Backend.SpecialistList;
import GUI.reportwindow.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AdminFrame extends JFrame {

    private final int WINDOW_WIDTH = 500;
    private final int WINDOW_HEIGHT = 100;

    private JButton launchKioskButton = new JButton("LAUNCH KIOSK");
    private JButton reportButton = new JButton("VIEW REPORT");
    private JButton closeKioskButton = new JButton("CLOSE KIOSK");
    private JButton createNewAdmin = new JButton("NEW ADMIN");//allow the admin to create another admin
    private JButton logout = new JButton("LOG OUT");
    
    public AdminFrame() {

        setLayout(new GridLayout());
        JPanel button = new JPanel();
        button.add(launchKioskButton);
        button.add(createNewAdmin);
        button.add(closeKioskButton);
        button.add(reportButton);
        button.add(logout);

        add(button);
        setTitle("SELECT CHOICE");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // view report button link
        ReportButtonListener onClick = new ReportButtonListener();
        getReportButton().addActionListener(onClick);

        // launch kiosk button link
        LaunchKioskButtonListener click = new LaunchKioskButtonListener();
        getLaunchKioskButton().addActionListener(click);

        // close kiosk button link
        CloseKioskButtonListener close = new CloseKioskButtonListener();
        getCloseKioskButton().addActionListener(close);
        
        //button helps creating another admin account
        getCreateAdminButton().addActionListener(new CreateAdminButtonListener());
        
        //log out
        getLogoutButton().addActionListener(new LogoutButtonListener());

        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.WHEN_IN_FOCUSED_WINDOW);
        //setAlwaysOnTop(true);
        setResizable(false);
        getRootPane().setDefaultButton(launchKioskButton);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // getters for buttons
    public final JButton getReportButton() {
        return reportButton;
    }

    public final JButton getLaunchKioskButton() {
        return launchKioskButton;
    }

    public final JButton getCloseKioskButton() {
        return closeKioskButton;
    }
    
    public final JButton getCreateAdminButton() {
        return createNewAdmin;
    }
    
    public final JButton getLogoutButton(){
        return logout;
    }

    // action listeners for each button
    public class LaunchKioskButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == getLaunchKioskButton()) {
                new DisabilityKiosk();
                // launches Disability window
                dispose();
            }
        }
    }

    public class CloseKioskButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ee) {
            if (ee.getSource() == getCloseKioskButton()) {
            	Admin.serialize();
                SpecialistList.serialize();
                dispose();
                // close app
                System.exit(0);
            }
        }
    }

    private class ReportButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == getReportButton()) {
                // this button goes to the report window
                try {
					new ReportWindow();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                dispose();
            }
        }
    }
    
    //create another admin account, and return to admin window
    private class CreateAdminButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int submitted;
            JTextField userName = new JTextField(10);
            JPasswordField password = new JPasswordField(10);
            do{
                JPanel adminInput = new JPanel();
                adminInput.add(new JLabel("Username: " ));
                
                adminInput.add(userName);
                adminInput.add(Box.createHorizontalStrut(15));
                 
                adminInput.add(new JLabel("Password: "));
                
                adminInput.add(password);
                submitted = JOptionPane.showConfirmDialog(null, adminInput, "Please enter new Admin Username & Password.", JOptionPane.OK_CANCEL_OPTION);
                if (submitted == JOptionPane.OK_OPTION && !userName.getText().equals("") && password.getPassword().length != 0) {
                    String pw = new String(password.getPassword());
                    Admin.admins.add(new AdminAccount(userName.getText(), pw));
                    Admin.serialize();//add the new administrator account to the file "admins"
                    System.out.println(Admin.admins.toString());//test line
                    //get back to admin window
                    AdminFrame test = new AdminFrame();
                    test.setVisible(true);
                }
                else if(submitted != JOptionPane.CANCEL_OPTION){
                      JOptionPane.showMessageDialog(null, "Please enter a Username and Password.", "Input Error", JOptionPane.ERROR_MESSAGE);

                }
            }while(submitted == JOptionPane.OK_OPTION && (userName.getText().equals("") || password.getPassword().length == 0));
            
            if(submitted == JOptionPane.CANCEL_OPTION){
                dispose();
                //get back to admin window
                AdminFrame test = new AdminFrame();
		test.setVisible(true);
            }
        }
    }
    
    //current admin logs out, return to main window of the application
    private class LogoutButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Admin.isAdminWorking = false;
            new DisabilityKiosk();
        }
        
    }
    
    //we should add more buttons to give the admin more rights.
    
    
} // end of class
