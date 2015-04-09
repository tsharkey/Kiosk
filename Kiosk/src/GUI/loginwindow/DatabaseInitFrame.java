package GUI.loginwindow;

import Backend.DatabaseConnector;
import disabilitykiosk.DisabilityKiosk;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by catherinehuang on 4/6/15.
 */
public class DatabaseInitFrame extends JFrame{
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

    private void buildConnectWindow() {

        initPanel = new DatabaseInitPanel();
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        ConnectButtonListener onClick = new ConnectButtonListener();

        initPanel.getConnectButton().addActionListener(onClick);

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

    
    private class ConnectButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == initPanel.getConnectButton()) {

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
