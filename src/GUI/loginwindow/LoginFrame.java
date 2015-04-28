package GUI.loginwindow;

/**
 * Created by Calvin Wong on 04/24/2014
 *
 * Login Frame
 *
 * Login:
 * admin:cs225
 */

import javax.swing.*;

import Backend.*;
import disabilitykiosk.DisabilityKiosk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame {

 private final int WINDOW_WIDTH = 500;
 private final int WINDOW_HEIGHT = 200;
 private LoginPanel loginPanel;

    /**
     * Constructor
     */
	public LoginFrame() {
		setTitle("ADMINISTRATOR LOG-IN"); // set title
		buildLoginWindow(); // build window
	}

 private void buildLoginWindow() {

     loginPanel = new LoginPanel();
     setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

     LoginButtonListener onClick = new LoginButtonListener();

     loginPanel.getLoginButton().addActionListener(onClick);

     CancelButtonListener close = new CancelButtonListener();

     loginPanel.getCancelButton().addActionListener(close);

     add(loginPanel);
     setLocationRelativeTo(null);
     setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
     setUndecorated(true);
     getRootPane().setWindowDecorationStyle(JRootPane.WHEN_IN_FOCUSED_WINDOW);
     getRootPane().setDefaultButton(loginPanel.getLoginButton());
     // setAlwaysOnTop(true);
     setVisible(true);
     setResizable(false);
 }//end of Constructor


    /**
     * Cancel login into AdminFrame, returns to DisabilityKiosk
     */
    private class CancelButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent ae) {

            if (ae.getSource() == loginPanel.getCancelButton()) {
                dispose();
                new DisabilityKiosk(); // cancels open new Kiosk window
            }
        }
    }

    /**
     * Checks the input username and password with database.
     * if any pairs match, allow access into AdminFrame.
     */
 private class LoginButtonListener implements ActionListener {

  public void actionPerformed(ActionEvent e) {

   if (e.getSource() == loginPanel.getLoginButton()) {

       String sUserName = loginPanel.getUsernameText();
       String sPassWord = loginPanel.getPasswordText();

       //allow login for everyone.
       //TODO doesn't open up an AdminFrame directly when the password is correct.
       if(AdminTable.verifyPassword(sUserName, sPassWord)){
           DisabilityKiosk.isAdminWorking = true;
           DisabilityKiosk.workingAdmin = sUserName;
           dispose(); //Brendan S
           new AdminFrame();
       } else
           JOptionPane.showMessageDialog(null, "Incorrect username or password");

   }
                            
  }
 }
} // end of class

