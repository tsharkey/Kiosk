package GUI.reportwindow;

import Backend.*;
import GUI.loginwindow.AdminFrame;

import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.PrinterException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import javax.swing.*;

/**
 * ReportWindow Class display a window with a Report for all the visits to the
 * Kiosk Application It will display date, time, first and last name of the
 * visitor, the email, phone, Reason for the visit, follow up field if needs to
 * be follow up, the specialist and the location where the visitor is getting
 * the services. In this window the user (administrator or specialist in
 * accordance with the privileges) will have the capability to search and print
 * data from it. /
 * 
 * /** Created by Pat
 * 
 * Front end search and displaying of data capabilities added by Hannah. Maria
 * del Mar Moncaleano
 */

@SuppressWarnings("serial")
public class ReportWindow extends JFrame implements FocusListener {
	// gui panels
	private JPanel northPanel;
	private JPanel comboPanel;

	private JPanel centerPanel;
	private JPanel southPanel;
	// combo boxes to display the reasons and dates in the gui
	private JComboBox<String> reasonsComboBox;

	private JTextArea textArea;
	private JScrollPane scrollPane;

	private JButton printFileBtn;
	private JButton goBackBtn;
	private JButton csvBtn;

	private JTextField searchBox, startInput, endInput;

	// date and time formatting
	DateFormat dateFormat;
	DateFormat timeFormat;

	private final String[] reasons = {
			"All Students",
			"New, Prospective Student/Group",
			"Disclose and Document Disability (In-take)",
			"Placement Testing with Accommodation",
			"Schedule An Appointment with Disability Specialist",
			"Meet with a Disability Specialist",
			"Take Test with Accommodations",
			"Drop Off/Pick Up Notes",
			"Academic Advising (Course Selection, Add/Drop, Withdrawal)",
			"Fill Out Accommodation Forms",
			"Address Problems with Specific Accommodations",
			"Address Specific Course Assignment or Issue",
			"Alternative Format for Texts and Handouts",
			"Professional Consultation (Faculty, Staff, Administration, Department)",
			"Other" };
	private String[] datesPastYr;
	
	private ArrayList<VisitData> csvStore;

	/**
	 * Constructor of the ReportWindow.
	 */
	public ReportWindow() throws IOException {
		super("Report Window");

		// date and time formatting
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		timeFormat = new SimpleDateFormat("h:mm a");

		setExtendedState(Frame.MAXIMIZED_BOTH);
		// setUndecorated(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setLayout(new BorderLayout());
		buildPanels();
		setVisible(true);
	}

	/**
	 * Builds the panels in the report window.
	 */
	public void buildPanels() throws IOException {
		// Setting up northPanel
		northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());

		comboPanel = new JPanel();
		// creates the comboBox for the reasons
		reasonsComboBox = new JComboBox<>(reasons);
		reasonsComboBox
				.addItemListener(new ReportWindow.ReasonChangeListener());
		datesPastYr = new String[365];

		Calendar cal = Calendar.getInstance();

		for (int i = 0; i < datesPastYr.length; i++) {
			if (i == 0) {
				Date day = cal.getTime();
				datesPastYr[i] = dateFormat.format(day);
			} else {
				cal.add(Calendar.DAY_OF_YEAR, -1);
				Date day = cal.getTime();
				datesPastYr[i] = dateFormat.format(day);
			}
		}

		JLabel startLabel = new JLabel("Start Date:");
		JLabel endLabel = new JLabel("End Date:");
		startInput = new JTextField("YYYY-MM-DD", 10);
		startInput.addFocusListener(this);
		endInput = new JTextField("YYYY-MM-DD", 10);
		endInput.addFocusListener(this);

		JLabel reasonLabel = new JLabel("Search by Reason:");

		// create search box
		searchBox = new JTextField("Search by Name", 10);
		searchBox.addFocusListener(this);

		// add action listener to search box
		searchBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// clear old data and add header
				textArea.setText("");
				addHeader();
				
				String input = searchBox.getText();
				displayVisitData(VisitsTable.searchName(input));
			}
		});

		// add date change action listeners
		startInput.addActionListener(new ReportWindow.DateChangeListener());
		endInput.addActionListener(new ReportWindow.DateChangeListener());

		// add to panel
		comboPanel.add(startLabel);
		comboPanel.add(startInput);
		comboPanel.add(endLabel);
		comboPanel.add(endInput);
		comboPanel.add(reasonLabel);
		comboPanel.add(reasonsComboBox);
		comboPanel.add(searchBox);

		northPanel.add(new JLabel("Current students waiting to be seen:"),
				BorderLayout.NORTH);
		northPanel.add(comboPanel, BorderLayout.SOUTH);

		// Setting up centerPanel
		centerPanel = new JPanel();
		// centerPanel.setLayout();
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		addHeader();

		// initial opening of the report window, display all visits from
		// database
		displayVisitData(VisitsTable.getAllVisits());

		scrollPane = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		centerPanel.add(scrollPane);

		// setting up southPanel
		southPanel = new JPanel();

		printFileBtn = new JButton("Print File");
		printFileBtn.addActionListener(new ReportWindow.ButtonListener());
		southPanel.add(printFileBtn);

		csvBtn = new JButton("Export to CSV");
		csvBtn.addActionListener(new ReportWindow.ButtonListener());
		southPanel.add(csvBtn);

		goBackBtn = new JButton("Go Back");
		goBackBtn.addActionListener(new ReportWindow.ButtonListener());
		southPanel.add(goBackBtn);

		// adding all panels to frame
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}

	/**
	 * Adds the label information to the top of the report window text area.
	 */
	private void addHeader() {
		textArea.append(addBuffer("Date", 15));
		textArea.append(addBuffer("Time", 15));
		textArea.append(addBuffer("FirstName", 15));
		textArea.append(addBuffer("LastName", 15));
		textArea.append(addBuffer("Email", 30));
		textArea.append(addBuffer("Phone", 15));
		textArea.append(addBuffer("Reason", 75));
		textArea.append(addBuffer("Follow Up?", 15));
		textArea.append(addBuffer("Role", 15));
		textArea.append(addBuffer("Specialist", 15));
		textArea.append(addBuffer("Location", 15));

		Scanner scan = new Scanner(textArea.getText());
		String s = scan.nextLine();
		textArea.append("\n");
		for (int i = 0; i < s.length(); i++) {
			textArea.append("-");
		}
		textArea.append("\n");
		scan.close();
	}

	/**
	 * Adds a buffer to the end of the string.
	 */
	private static String addBuffer(String s, int buffSize) {
		String temp = s;
		for (int i = s.length(); i <= buffSize; i++) {
			temp += " ";
		}

		return temp;
	}

	/**
	 * Inner class that allows for print listening.
	 */
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == printFileBtn) {
				try {
					boolean complete = textArea.print();
					if (complete) {
						JOptionPane.showMessageDialog(null, "Done Printing!",
								"Information", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (PrinterException exception) {
					JOptionPane.showMessageDialog(null, exception);
				}
			} else if (e.getSource() == goBackBtn) {
				new AdminFrame();
				dispose();
			} else if (e.getSource() == csvBtn) {
				String csvFileName = "REPORT_" + new SimpleDateFormat("yyyyMMddhhmm'.csv'").format(new Date());
				try(FileWriter csv = new FileWriter(csvFileName)){
					csv.append("Date,Time,FirstName,LastName,Email,Phone,Reason,Follow Up,Role,Specialist,Location\n");
					for (VisitData visit : getCSVData()) {
						csv.append(dateFormat.format(visit.getVisitDate()));
						csv.append(",");
						csv.append(timeFormat.format(visit.getVisitTime()));
						csv.append(",");
						csv.append(visit.getFirstName());
						csv.append(",");
						csv.append(visit.getLastName());
						csv.append(",");
						csv.append(visit.getEmail());
						csv.append(",");
						csv.append(visit.getPhone());
						csv.append(",");
						csv.append("\"" + visit.getReason() + "\""); // commas
						csv.append(",");
						csv.append(String.valueOf(visit.isFollowUp()));
						csv.append(",");
						csv.append(visit.getRole());
						csv.append(",");
						csv.append(visit.getSpecialist());
						csv.append(",");
						csv.append(visit.getLocation());
						csv.append("\n");
					}
					JOptionPane.showMessageDialog(null, "CSV successfully exported: " + csvFileName, "Information", JOptionPane.INFORMATION_MESSAGE);
				}catch (IOException e1){
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * Inner class for updating the report window based on reason for visit.
	 */
	class ReasonChangeListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {

				textArea.setText("");
				addHeader();

				// get reason
				String reason = (String) event.getItem();

				// show all students if all students collected
				if (reason.equals("All Students")) {
					displayVisitData(VisitsTable.getAllVisits());
				} else {
					displayVisitData(VisitsTable.searchReason(reason));
				}

			}
		}
	}

	/**
	 * Inner class for updating the report window based on date of visit
	 */
	class DateChangeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {

			// clear old data and add header
			textArea.setText("");
			addHeader();

			// store input
			String startText = startInput.getText();
			String endText = endInput.getText();

			displayVisitData(VisitsTable.searchDates(startText, endText));

		}
	}

	/**
	 * Unified method to parse and display VisitData
	 * 
	 * @param visitData
	 */
	public void displayVisitData(ArrayList<VisitData> visitData) {
		this.csvStore = visitData; // store value for csv export on update display
		String visitsInfo = "";
		for (VisitData visit : visitData) {
			visitsInfo += addBuffer(dateFormat.format(visit.getVisitDate()), 15)
					+ addBuffer(timeFormat.format(visit.getVisitTime()), 15)
					+ addBuffer(visit.getFirstName(), 15)
					+ addBuffer(visit.getLastName(), 15)
					+ addBuffer(visit.getEmail(), 30)
					+ addBuffer(visit.getPhone(), 15)
					+ addBuffer(visit.getReason(), 75)
					+ addBuffer(String.valueOf(visit.isFollowUp()), 15)
					+ addBuffer(visit.getRole(), 15)
					+ addBuffer(visit.getSpecialist(), 15)
					+ addBuffer(visit.getLocation(), 15) + "\n";
		}
		visitsInfo += "\nENTRY COUNT: " + visitData.size() + "\n";
		textArea.append(visitsInfo);
	}

	/**
	 * Clearing search fields from hint text on focus
	 */
	@Override
	public void focusGained(FocusEvent e) {
		Object source = e.getSource();
		if(source == startInput || source == endInput){
			if(((JTextField) source).getText().equals("YYYY-MM-DD")){
				((JTextField) source).setText("");
			}
		}else if(source == searchBox){
			if(((JTextField) source).getText().equals("Search by Name")){
				((JTextField) source).setText("");
			}
		}
	}

	/**
	 * Set hint text to search fields on loss of focus if empty
	 */
	@Override
	public void focusLost(FocusEvent e) {
		Object source = e.getSource();
		if(source == startInput || source == endInput){
			if(((JTextField) source).getText().isEmpty()){
				((JTextField) source).setText("YYYY-MM-DD");
			}
		}else if(source == searchBox){
			if(((JTextField) source).getText().isEmpty()){
				((JTextField) source).setText("Search by Name");
			}
		}
	}
	
	ArrayList<VisitData> getCSVData() { return this.csvStore; }
}