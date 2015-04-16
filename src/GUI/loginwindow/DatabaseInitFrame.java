package GUI.loginwindow;

import Backend.DatabaseConnector;
import disabilitykiosk.DisabilityKiosk;
import disabilitykiosk.StartupManger;
import javax.swing.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * DatabaseInitFrame creates a frame to connect to the database Created by
 * catherinehuang on 4/6/15.
 */

@SuppressWarnings("serial")
public class DatabaseInitFrame extends JFrame {
	// size of the frame
	private final int WINDOW_WIDTH = 500;
	private final int WINDOW_HEIGHT = 200;
	// default configuration file name
	private String CONFIG_FILE = "db_config.txt";

	private DatabaseInitPanel initPanel;

	/**
	 * Constructor
	 */
	public DatabaseInitFrame() {
		setTitle("Connect to Database"); // set title
		buildConnectWindow(); // build window
	}

	// creates the window that connects with the Database
	private void buildConnectWindow() {
		// creating the Panel to initialized the database connection
		initPanel = new DatabaseInitPanel();
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		// creating the button for the connection and adding the listener to it
		ConnectButtonListener onClick = new ConnectButtonListener();
		initPanel.getConnectButton().addActionListener(onClick);

		// creating the button for cancel and adding the listener to it
		CancelButtonListener close = new CancelButtonListener();
		initPanel.getCancelButton().addActionListener(close);

		add(initPanel);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
		getRootPane()
				.setWindowDecorationStyle(JRootPane.WHEN_IN_FOCUSED_WINDOW);
		getRootPane().setDefaultButton(initPanel.getConnectButton());

		// load previous successfully connected to database and populate text field
		if (new File("./" + CONFIG_FILE).exists()) {
			try {
				FileReader fileReader = new FileReader("./" + CONFIG_FILE);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				initPanel.setHostText(bufferedReader.readLine());
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// setAlwaysOnTop(true);
		setVisible(true);
		setResizable(false);
	}

	/**
	 * Cancel button ActionListener. Exit the program
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
				// information for the connection
				String sUserName = initPanel.getUsernameText();
				String sPassWord = initPanel.getPasswordText();
				String[] sHostDB = initPanel.getHostText().split("/"); // "HOST/DB_NAME"

				if (sHostDB.length == 2
						&& DatabaseConnector.setDatabaseConnection(sUserName,
								sPassWord, sHostDB[0], sHostDB[1])) {
					
					// Initialize database if command line parameter is set
					// Prompt user to confirm reset of database
					if (StartupManger.initalizeDB == true) {
						int dialogResult = JOptionPane
								.showConfirmDialog(
										null,
										"This will erase the existing database and initalize "
										+ "a new one.\n Are you sure you wish to continue?",
										"Database Initalization Warning",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.WARNING_MESSAGE);
						if (dialogResult == JOptionPane.YES_OPTION) {
							DatabaseConnector.initDatabase();
						}
					}

					// save database host and database
					try {
						FileWriter fileWriter = new FileWriter("./"
								+ CONFIG_FILE);
						BufferedWriter bufferedWriter = new BufferedWriter(
								fileWriter);
						bufferedWriter.write(initPanel.getHostText());
						bufferedWriter.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					DisabilityKiosk test = new DisabilityKiosk();
					test.setVisible(true);
					dispose();

				} else
					JOptionPane.showMessageDialog(null,
							"Incorrect database information.");
			}

		}
	}
}