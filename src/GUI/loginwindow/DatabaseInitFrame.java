package GUI.loginwindow;

import Backend.DatabaseConnector;
import disabilitykiosk.DisabilityKiosk;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * DatabaseInitFrame creates a frame to connect to the database
 * Created by catherinehuang on 4/6/15.
 */
public class DatabaseInitFrame extends JFrame{
    //size of the frame
    private final int WINDOW_WIDTH = 500;
    private final int WINDOW_HEIGHT = 200;

    private DatabaseInitPanel initPanel;

    /**
     * Constructor
     */
    public DatabaseInitFrame() {
        setTitle("Connect to Database"); // set title
        buildConnectWindow(); // build window
    }

    //creates the window that connects with the Database
    private void buildConnectWindow() {

        //creating the Panel to initialized the database connection
        initPanel = new DatabaseInitPanel();
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        //creating the button for the connection and adding the listener to it
        ConnectButtonListener onClick = new ConnectButtonListener();
        initPanel.getConnectButton().addActionListener(onClick);

        //creating the button for cancel and adding the listener to it
        CancelButtonListener close = new CancelButtonListener();
        initPanel.getCancelButton().addActionListener(close);

        add(initPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.WHEN_IN_FOCUSED_WINDOW);
        getRootPane().setDefaultButton(initPanel.getConnectButton());
        // setAlwaysOnTop(true);
        setVisible(true);
        setResizable(false);
    }//end of Constructor

    /**
     * Cancel button ActionListener.
     * Exit the program
     */
    private class CancelButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            if (ae.getSource() == initPanel.getCancelButton()) {
                dispose();
                System.exit(0); // cancels and exit the program.
            }
        }
    }

    /**
     * Connect button ActionListener.
     */
    private class ConnectButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == initPanel.getConnectButton()) {
                //information for the connection
                String sUserName = initPanel.getUsernameText();
                String sPassWord = initPanel.getPasswordText();
                String sDb = initPanel.getHostText();
                String sHost = "107.170.166.28";

                if (DatabaseConnector.setDatabaseConnection(sUserName, sPassWord, sHost, sDb)) {
                    //Admin.isAdminWorking = true;
//                    AdminFrame test = new AdminFrame();
                    DisabilityKiosk test = new DisabilityKiosk();
                    test.setVisible(true);
                    dispose();

                } else
                    JOptionPane.showMessageDialog(null, "Incorrect database information.");
            }

        }
    }
}//End of class
