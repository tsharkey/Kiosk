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

public class AdminFrame extends JFrame {
    //size of the window
    private final int WINDOW_WIDTH = 500;
    private final int WINDOW_HEIGHT = 100;
    //Jbuttons fields for each of the buttons
    private final JButton reportButton = new JButton("VIEW REPORT");
    private final JButton closeKioskButton = new JButton("CLOSE KIOSK");
    private final JButton createNewAdmin = new JButton("ADMIN OPTIONS");//allow the admin to create another admin
    private final JButton specialist = new JButton("ADD SPECIALIST"); //creates a adddeletespecframe -Brendan S
    private final JButton logout = new JButton("LOG OUT");

    public AdminFrame() {

        setLayout(new GridLayout());
        //creates panel for buttons
        JPanel button = new JPanel();
        //adding the buttons to the panel
        button.add(createNewAdmin);
        button.add(closeKioskButton);
        button.add(reportButton);
        button.add(specialist);
        button.add(logout);

        add(button);
        setTitle("SELECT CHOICE");
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
                //Admin.serialize();
                //SpecialistList.serialize();
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
            /*
            int submitted;
            JTextField userName = new JTextField(10);
            JPasswordField password = new JPasswordField(10);
            boolean new_admin_created = false;
            do {
                JPanel adminInput = new JPanel();
                adminInput.add(new JLabel("Username: "));

                adminInput.add(userName);
                adminInput.add(Box.createHorizontalStrut(15));

                adminInput.add(new JLabel("Password: "));

                adminInput.add(password);
                submitted = JOptionPane.showConfirmDialog(null, adminInput, "Please enter new Admin Username & Password.", JOptionPane.OK_CANCEL_OPTION);
                if (submitted == JOptionPane.OK_OPTION && !userName.getText().equals("") && password.getPassword().length != 0) {
                    String pw = new String(password.getPassword());
                    if (!AdminTable.admin_exist(userName.getText())) {
                        AdminTable.addAdmin(userName.getText(), pw);
                        new_admin_created = true;
                        JOptionPane.showMessageDialog(null, "New admin created", "Notice", JOptionPane.INFORMATION_MESSAGE);
                        //get back to admin window
                        AdminFrame test = new AdminFrame();
                        test.setVisible(true);
                    }
                    else{
                        
                    }
                } else if (submitted != JOptionPane.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(null, "Please enter a Username and Password.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } while (submitted == JOptionPane.OK_OPTION && (userName.getText().equals("") || password.getPassword().length == 0) && new_admin_created == true);

            if (submitted == JOptionPane.CANCEL_OPTION) {
                dispose();
                //get back to admin window
                AdminFrame test = new AdminFrame();
                test.setVisible(true);
            }*/
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

