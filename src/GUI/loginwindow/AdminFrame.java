/**
 * Created by Calvin Wong on 04/26/2014
 * updated by Henrique Aguiar
 * AfterLoginFrame
 *
 */
package GUI.loginwindow;

import disabilitykiosk.*;
import GUI.adddelete.AddDeleteAdminFrame;
import GUI.adddeletespec.AddDeleteSpecFrame;
import GUI.reportwindow.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * AdminFrame class creates a graphic interface to allows an administrator to do some tasks.
 * this tasks or privileges  are: create a new administrator, add an specialist, view a report
 * the window has options to log out, launch the main window for the KIOSK application front it and close the application.
 * This class will create each of the buttons for the each of the functions and their listeners
 */

@SuppressWarnings("serial")
public class AdminFrame extends JFrame {
    //size of the window
    private final int WINDOW_WIDTH = 250;
    private final int WINDOW_HEIGHT = 200;
    //Jbuttons fields for each of the buttons
    private final JButton reportButton = new JButton("VIEW REPORT");
    private final JButton closeKioskButton = new JButton("SHUTDOWN KIOSK");
    private final JButton createNewAdmin = new JButton("ADMIN OPTIONS");//allow the admin to create another admin
    private final JButton specialist = new JButton("SPECIALIST OPTIONS"); //creates a adddeletespecframe -Brendan S
    private final JButton logout = new JButton("LOG OUT");

    public AdminFrame() {
    	
    	Container c = this.getContentPane();
    	c.setLayout(new GridLayout(0,1));
    	c.add(reportButton);
    	c.add(specialist);
    	c.add(createNewAdmin);
    	c.add(logout);
    	c.add(closeKioskButton);
    	
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // view report button link
        ReportButtonListener onClick = new ReportButtonListener();
        getReportButton().addActionListener(onClick);

        // close kiosk button link
        CloseKioskButtonListener close = new CloseKioskButtonListener();
        getCloseKioskButton().addActionListener(close);

        //button helps creating another admin account
        getCreateAdminButton().addActionListener(new CreateAdminButtonListener());

        //Brendan S 
        getSpecialistButton().addActionListener(new SpecialistButtonListener());

        //log out
        getLogoutButton().addActionListener(new LogoutButtonListener());

        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.WHEN_IN_FOCUSED_WINDOW);
        //setAlwaysOnTop(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    // getters for buttons
    public final JButton getReportButton() {
        return reportButton;
    }

    public final JButton getCloseKioskButton() {
        return closeKioskButton;
    }

    public final JButton getCreateAdminButton() {
        return createNewAdmin;
    }

    public final JButton getLogoutButton() {
        return logout;
    }

    public final JButton getSpecialistButton() {
        return specialist;
    }

    // action listeners for each button

    //action listener for the button 'close kiosk'
    public class CloseKioskButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ee) {
            if (ee.getSource() == getCloseKioskButton()) {
                dispose();
                // close app
                System.exit(0);
            }
        }
    }

    //action listener for the button 'report'
    private class ReportButtonListener implements ActionListener {

        @Override
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

    //action listener for the button 'create admin'
    //create another admin account, and return to admin window
    private class CreateAdminButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
        	dispose(); //Brendan S
            new AddDeleteAdminFrame();
        }
    }

    //action listener for the button 'log out'
    //current admin logs out, return to main window of the application
    private class LogoutButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DisabilityKiosk.isAdminWorking = false;
            DisabilityKiosk.workingAdmin = "";
            dispose(); //Brendan S
            new DisabilityKiosk();
        }

    }

    //action listener for the button 'add specialist'
    private class SpecialistButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new AddDeleteSpecFrame();

        }
    }

    //we should add more buttons to give the admin more rights.
} // end of class

