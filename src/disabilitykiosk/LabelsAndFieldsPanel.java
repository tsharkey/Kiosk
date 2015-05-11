package disabilitykiosk;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//import Main.Games.ninestones.Interface;


import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * LabelsAndFieldsPanel is the panel contains all the fields and labels to field
 * out as a form for their visits.
 *
 * @author Spconway 4/26/2014
 * updated by Henrique Aguiar
 * 
 * Input checks with regular expressions by Hannah
 */
public class LabelsAndFieldsPanel extends JPanel{

    /*
     * Labels, text fields and combo box
     */
    JComboBox reasonI;
    JComboBox followUpI;
    JComboBox roleI;
    JComboBox locationI;
    private JPasswordField firstI,
                           lastI,
                           emailI,
                           phoneI;
    private JLabel date, time, first, last, email, phone, reason, followUp, role, dateI, timeI, location;
    private JButton firstButton, locationButton, lastButton, emailButton, phoneButton, reasonButton, followUpButton, roleButton;
    private ArrayList<JButton> buttonList;
    private ImageIcon microphone = new ImageIcon(getClass().getResource("/microphone.jpg"));
    public JCheckBox cancelSpeech;
    private boolean firstNameBool,
                    lastNameBool;

    /*
     * Numbers for enlarging text
     */
    static final int TEXT_MIN = 18;
    static final int TEXT_MAX = 32;
    static final int TEXT_INIT = 24;

    /*
     * Character length for text fields
     */
    private final int CHAR_LENGTH = 30;

    /*
     * Font for gridbag
     */
    public Font font = new Font("SERIF", Font.BOLD, 28);
    public Font textFieldFont = new Font("SERIF", Font.PLAIN, 20 );

    Thread t = null;

    /**
     * Constructor
     */
    public LabelsAndFieldsPanel() {
        //super(new GridBagLayout());
        setLayout(new GridBagLayout());
        buildPanel();
        setVisible(true);

        firstNameBool = false;
        lastNameBool = false;
    }

    /**
     * Build panel method
     */
    private void buildPanel() {
        //Initializing thread for date and time
        DateAndTime r = new DateAndTime();

        Thread t = new Thread(r);
        t.start();
        GridBagConstraints grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.HORIZONTAL;
        
        
        
        //date
        date = new JLabel("Date");
        date.setFont(font);
        grid.gridx = GridBagConstraints.EAST;
        grid.gridy = 0;
        //grid.ipadx = 40;
        grid.ipady = 30;
        grid.gridwidth = 2;
        add(date, grid);

        //dateI
        dateI = new JLabel();
        grid.gridx = GridBagConstraints.RELATIVE;
        grid.gridy = 0;
        grid.gridwidth = 4;
        add(dateI, grid);

        //time
        time = new JLabel("Time");
        time.setFont(font);
        grid.gridx = GridBagConstraints.EAST;
        grid.gridy = 1;
        grid.gridwidth = 2;
        add(time, grid);

        //timeI
        timeI = new JLabel();
        grid.gridx = GridBagConstraints.RELATIVE;
        grid.gridy = 1;
        grid.gridwidth = 4;
        add(timeI, grid);
        
        cancelSpeech = new JCheckBox("Auto Speech", true);
        grid.gridx = 0;
        grid.gridy = 0;
        //grid.ipadx = 40;
        //grid.gridheight = 4;
        add(cancelSpeech, grid);
        
        //first
        firstButton = new JButton();
        firstButton.addActionListener(new firstButtonListener());
        firstButton.setIcon(microphone);
        first = new JLabel("First Name: ");
        first.setFont(font);
        grid.gridx = GridBagConstraints.RELATIVE;
        grid.gridy = 2;
        grid.gridwidth = 1;
        add(firstButton, grid);
        add(first, grid);

        //firstI
        firstI = new JPasswordField(CHAR_LENGTH);
        firstI.addFocusListener(new PasswordUtil(firstI));
        firstI.setFont(textFieldFont);
        firstI.addFocusListener(new MyFocusListener());
        grid.gridx = GridBagConstraints.RELATIVE;
        grid.gridy = 2;
        grid.gridwidth = 4;
        add(firstI, grid);

        //last
        lastButton = new JButton();
        lastButton.addActionListener(new lastButtonListener());
        lastButton.setIcon(microphone);
        last = new JLabel("Last Name: ");
        last.setFont(font);
        grid.gridx = GridBagConstraints.RELATIVE;
        grid.gridy = 3;
        grid.gridwidth = 1;
        add(lastButton, grid);
        add(last, grid);

        //lastI
        lastI = new JPasswordField(CHAR_LENGTH);
        lastI.addFocusListener(new PasswordUtil(lastI));
        lastI.setFont(textFieldFont);
        lastI.addFocusListener(new MyFocusListener());
        grid.gridx = GridBagConstraints.RELATIVE;
        grid.gridy = 3;
        grid.gridwidth = 4;
        add(lastI, grid);

        //role
        roleButton = new JButton();
        roleButton.addActionListener(new roleButtonListener());
        roleButton.setIcon(microphone);
        role = new JLabel("Role: ");
        role.setFont(font);
        grid.gridx = GridBagConstraints.RELATIVE;
        grid.gridy = 4;
        grid.gridwidth = 1;
        add(roleButton, grid);
        add(role, grid);

        //RoleI
        String[] roles = {"Student", "Teacher", "Parent", "Administrator"};
        roleI = new JComboBox(roles);
        roleI.setFont(textFieldFont);
        roleI.addFocusListener(new MyFocusListener());
        grid.gridx = GridBagConstraints.RELATIVE;
        grid.gridy = 4;
        grid.gridwidth = 2;
        add(roleI, grid);


        //email
        emailButton = new JButton();
        emailButton.addActionListener(new emailButtonListener());
        emailButton.setIcon(microphone);
        email = new JLabel("E-Mail: ");
        email.setFont(font);
        grid.gridx = GridBagConstraints.RELATIVE;
        grid.gridy = 5;
        grid.gridwidth = 1;
        add(emailButton, grid);
        add(email, grid);

        //emailI
        emailI = new JPasswordField(CHAR_LENGTH);
        emailI.addFocusListener(new PasswordUtil(emailI));
        emailI.setFont(textFieldFont);
        emailI.addFocusListener(new MyFocusListener());
        grid.gridx = GridBagConstraints.RELATIVE;
        grid.gridy = 5;
        grid.gridwidth = 4;
        add(emailI, grid);

        //phone
        phoneButton = new JButton();
        phoneButton.addActionListener(new phoneButtonListener());
        phoneButton.setIcon(microphone);
        phone = new JLabel("Phone #: ");
        phone.setFont(font);
        grid.gridx = GridBagConstraints.RELATIVE;
        grid.gridy = 6;
        grid.gridwidth = 1;
        add(phoneButton, grid);
        add(phone, grid);

        //phoneI
        phoneI = new JPasswordField(CHAR_LENGTH);
        phoneI.addFocusListener(new PasswordUtil(phoneI));
        phoneI.setFont(textFieldFont);
        phoneI.addFocusListener(new MyFocusListener());
        grid.gridx = GridBagConstraints.RELATIVE;
        grid.gridy = 6;
        grid.gridwidth = 4;
        add(phoneI, grid);

        //reason
        reasonButton = new JButton();
        reasonButton.addActionListener(new reasonButtonListener());
        reasonButton.setIcon(microphone);
        reason = new JLabel("Reason: ");
        reason.setFont(font);
        grid.gridx = GridBagConstraints.RELATIVE;
        grid.gridy = 7;
        grid.gridwidth = 1;
        add(reasonButton, grid);
        add(reason, grid);

        //reasonI
        String[] reasons = {"New, Prospective Student/Group", "Disclose and Document Disability (In-take)", "Placement Testing with Accommodation",
            "Schedule An Appointment with Disability Specialist", "Meet with a Disability Specialist", "Take Test with Accommodations", "Drop Off/Pick Up Notes",
            "Academic Advising (Course Selection, Add/Drop, Withdrawal)", "Fill Out Accommodation Forms", "Address Problems with Specific Accommodations",
            "Address Specific Course Assignment or Issue", "Alternative Format for Texts and Handouts",
            "Professional Consultation (Faculty, Staff, Administration, Department)", "All of the Above", "Other"};
        reasonI = new JComboBox(reasons);
        reasonI.addFocusListener(new MyFocusListener());
        reasonI.setFont(textFieldFont);
        grid.gridx = GridBagConstraints.RELATIVE;
        //grid.gridy = 7;
        grid.gridwidth = 4;
        add(reasonI, grid);

        //followUp
        followUpButton = new JButton();
        followUpButton.addActionListener(new followUpButtonListener());
        followUpButton.setIcon(microphone);
        followUp = new JLabel("Follow up: ");
        followUp.setFont(font);
        grid.gridx = GridBagConstraints.RELATIVE;
        grid.gridy = 8;
        grid.gridwidth = 1;
        add(followUpButton, grid);
        add(followUp, grid);

        //followUpI
        String[] returnVisit = {"Yes", "No"};
        followUpI = new JComboBox(returnVisit);
        followUpI.setFont(textFieldFont);
        followUpI.addFocusListener(new MyFocusListener());
        grid.gridx = GridBagConstraints.RELATIVE;
        grid.gridy = 8;
        grid.gridwidth = 1;
        add(followUpI, grid);


        //location
        locationButton = new JButton();
        JButton emptyButton = new JButton();
        locationButton.addActionListener(new locationButtonListener());
        locationButton.setIcon(microphone);
        location = new JLabel("Location: ");
        location.setFont(font);
        grid.gridx = GridBagConstraints.RELATIVE;
        grid.gridy = 9;
        grid.gridwidth = 1;
        add(locationButton, grid);
        add(location, grid);


        //LocationI
        String[] locations = {"Framingham", "Wellesley", "Ashland"};
        locationI = new JComboBox(locations);
        locationI.setFont(textFieldFont);
        locationI.addFocusListener(new MyFocusListener());
        grid.gridx = GridBagConstraints.RELATIVE;
        grid.gridy = 9;
        grid.gridwidth = 2;
        add(locationI, grid);
        

        //Text slider
        //textSlider = new JSlider(JSlider.VERTICAL, TEXT_MIN, TEXT_MAX, TEXT_INIT);
        //textSlider.addChangeListener((new SliderListener()));
        //textSlider.setMajorTickSpacing(2);

        //Create the label table
        //Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        //labelTable.put(new Integer(TEXT_MIN), new JLabel("Shrink text"));
        //labelTable.put(new Integer(TEXT_MAX), new JLabel("Enlarge text"));

        //textSlider.setLabelTable(labelTable);
        //textSlider.setPaintLabels(true);

        //grid.gridx = GridBagConstraints.RELATIVE;
        //grid.gridy = 0;
       // grid.ipadx = 40;
        //grid.gridheight = 4;
        //add(textSlider, grid);

        //cancelSpeech = new JCheckBox("Auto Speech", true);
        //grid.gridx = GridBagConstraints.WEST;
        //grid.gridy = 0;
        //grid.ipadx = 40;
        //grid.gridheight = 4;
        //add(cancelSpeech, grid);
    }//end of buildPanel

    //Getters
    public String getFirst() {
        return this.firstI.getText();
    }

    public String getLast() {
        return this.lastI.getText();
    }

    public String getRole() {
        return this.roleI.getSelectedItem().toString();
    }

    public String getEmail() {

        // regular expression
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(this.emailI.getText());

        // if valid, return e-mail as is
        if (matcher.matches()) {
            return this.emailI.getText();
        }

        // invalid e-mail
        return "";
    }

    /**
     * Validate and returns the phone number user inputted
     *
     * @return
     */
    public String getPhone() {
        // regular expression
        String regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(this.phoneI.getText());

        if (matcher.matches()) {
            return matcher.replaceFirst("$1$2$3");
        }

        // invalid phone number
        return "";
    }//end of getPhone

    public String getReason() {
        return this.reasonI.getSelectedItem().toString();
    }

    /**
     * Check if the the visit is a followup visit
     *
     * @return
     */
    public Boolean getFollowUp() {
        if (this.followUpI.getSelectedItem().toString() == "Yes") {
            return true;
        }
        return false;
    }

    public String getLocationInput() {
        return this.locationI.getSelectedItem().toString();
    }

    /**
     * TODO: remove the slider
     */
    /*private class SliderListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();

            if (!source.getValueIsAdjusting()) {
                date.setFont(new Font("SERIF", Font.BOLD, source.getValue()));
                time.setFont(new Font("SERIF", Font.BOLD, source.getValue()));
                first.setFont(new Font("SERIF", Font.BOLD, source.getValue()));
                last.setFont(new Font("SERIF", Font.BOLD, source.getValue()));
                role.setFont(new Font("SERIF", Font.BOLD, source.getValue()));
                email.setFont(new Font("SERIF", Font.BOLD, source.getValue()));
                phone.setFont(new Font("SERIF", Font.BOLD, source.getValue()));
                reason.setFont(new Font("SERIF", Font.BOLD, source.getValue()));
                followUp.setFont(new Font("SERIF", Font.BOLD, source.getValue()));
                location.setFont(new Font("SERIF", Font.BOLD, source.getValue()));
            }

        }
    }*/

    /**
     * Listener for Text to Speech fields and labels
     */
    public class MyFocusListener implements FocusListener {

        public void focusGained(FocusEvent e) {
            if (e.getSource() == firstI && cancelSpeech.isSelected() == true) {
                String text = "Please enter your first name in the text field";
                Speech s = new Speech(text);
                new Thread(s).start();
            }
            if (e.getSource() == lastI && cancelSpeech.isSelected() == true) {
                String text = "Please enter your last name in the text field";
                Speech s = new Speech(text);
                new Thread(s).start();
            }
            if (e.getSource() == emailI && cancelSpeech.isSelected() == true) {
                String text = "Please enter your email address in the text field";
                Speech s = new Speech(text);
                new Thread(s).start();
            }
            if (e.getSource() == phoneI && cancelSpeech.isSelected() == true) {
                String text = "Please enter your phone number in the text field";
                Speech s = new Speech(text);
                new Thread(s).start();
            }
            if (e.getSource() == reasonI && cancelSpeech.isSelected() == true) {

                String text = "Please enter your reason for being here in the drop down list";
                Speech s = new Speech(text);
                new Thread(s).start();
            }
            if (e.getSource() == followUpI && cancelSpeech.isSelected() == true) {
//             VoiceManager vm = VoiceManager.getInstance();
//
//            com.sun.speech.freetts.Voice voice = vm.getVoice("kevin16");
//            String text = "Please enter if this is a follow up visit. Yes or no";
//            String string[] = text.split("  ");
//            voice.allocate();
//            for (int i = 0; i < string.length; i++) {
//                voice.speak(text);
//
//       }
//       voice.deallocate();
                String text = "Please enter if this is a follow up visit. Yes or no";
                Speech s = new Speech(text);
                new Thread(s).start();
            }
            if (e.getSource() == roleI && cancelSpeech.isSelected() == true) {
                String text = "Please enter your role with the school in the drop down list";
                Speech s = new Speech(text);
                new Thread(s).start();
            }
            if (e.getSource() == locationI && cancelSpeech.isSelected() == true) {
                String text = "Please enter your location in the drop down list";
                Speech s = new Speech(text);
                new Thread(s).start();
            }
        }

        public void focusLost(FocusEvent e) {
        }
    }

    private class firstButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == firstButton) {
                String text = "Please enter your first name in the text field";
                Speech s = new Speech(text);
                new Thread(s).start();

            }
        }
    }

    private class lastButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == lastButton) {
                String text = "Please enter your last name in the text field";
                Speech s = new Speech(text);
                new Thread(s).start();
            }
        }
    }

    private class emailButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == emailButton) {
                String text = "Please enter your email address in the text field";
                Speech s = new Speech(text);
                new Thread(s).start();
            }

        }
    }

    private class phoneButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == phoneButton) {
                String text = "Please enter your phone number in the text field";
                Speech s = new Speech(text);
                new Thread(s).start();

            }
        }
    }

    private class reasonButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == reasonButton) {
                String text = "Please enter your reason for being here in the drop down list";
                Speech s = new Speech(text);
                t = new Thread(s);
                t.start();

            }
        }
    }

    private class followUpButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == followUpButton) {
                String text = "Please enter if this is a follow up visit. Yes or no";
                Speech s = new Speech(text);
                t = new Thread(s);
                t.start();

            }
        }
    }

    private class roleButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == roleButton) {

                String text = "Please enter your role with the school in the drop down list";
                Speech s = new Speech(text);
                new Thread(s).start();

            }
        }
    }

    private class locationButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == locationButton) {

                String text = "Please select your location from drop down list";
                Speech s = new Speech(text);
                new Thread(s).start();

            }
        }
    }


    /**
     * Setting up thread for time and date
     */
    private class DateAndTime implements Runnable {

        private static final int DELAY = 5000;//Sleep every 5 seconds

        @Override
        public void run() {
            /*
             * Update on 5/1/2014: had to add JLabel.setText method to a new Runnable
             * and set that inside the awt event queue so the event dispatch thread
             * doesn't crash like it was....I don't even know if I just said that right.
             */
            while (true) {
                try {
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            Date todaysDate = new Date();
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                            timeI.setText(timeFormat.format(todaysDate));
                            timeI.setFont(textFieldFont);
                            timeI.updateUI();

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyy/MM/dd");
                            dateI.setText(dateFormat.format(todaysDate));
                            dateI.setFont(textFieldFont);
                            dateI.updateUI();
                        }
                    });

                    Thread.sleep(DELAY);
                } catch (InterruptedException exception) {
                }
            }
        }

    }
}
