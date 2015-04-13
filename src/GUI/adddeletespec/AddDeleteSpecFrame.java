package GUI.adddeletespec;

//import Backend.Specialist;
//import Backend.SpecialistList;
import Backend.SpecialistTable;
import GUI.loginwindow.AdminFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 * AddDeleteSpecFrame Class is part or the Kiosk implementation.
 * it is a graphic interface that allows to add or delete a specialist from the database
 * The frame will have three buttons to add, delete and edit the specialist
 * @author Spconway 4/26/2014
 */
public class AddDeleteSpecFrame extends JFrame{

    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 500;
    //declaring an array that holds the names of the specialists
    private ArrayList<String> specNames;
    //declaring fields for the panels
    private SpecInfoPanel specInfoPanel;
    private UpdatePanel updatePanel;
    private ListPanel listPanel;
    private JPanel buttonPanel;
    //declaring fields for the buttons
    private JButton addBtn, editBtn, deleteBtn, cancelBtn;
//    private JList<String> list;
//    private DefaultListModel<String> dlm;
    private JScrollPane scroll;
    
    public AddDeleteSpecFrame(){
        //Window title
        setTitle("Add/Delete Specialists");
        //Set default close operation
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Build the main frame and add components
        buildMainFrame();
        //Set location
        setLocationRelativeTo(null);
        //Set visible
        setVisible(true);
        //Block resizing
        setResizable(false);
    }

    /**
     *
     */
    private void buildMainFrame(){
//        dlm = new DefaultListModel<String>();
//        updateList();
        listPanel = new ListPanel();
        listPanel.updateList();
        specInfoPanel = new SpecInfoPanel();
        updatePanel = new UpdatePanel();
        //listPanel = new ListPanel();
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        
        setLayout(new BorderLayout());

        add(listPanel);
        //add(specInfoPanel, BorderLayout.EAST);
//        add(submitPanel, BorderLayout.SOUTH);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        //creating the Add button and adding the listener to it
        addBtn = new JButton("Add New Specialist");
        addBtn.addActionListener(new submitOrDeleteListener());
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.05;
        buttonPanel.add(addBtn, c);

        //creating the Edit button and adding the listener to it
        editBtn = new JButton("Edit Specialist");
        editBtn.addActionListener(new submitOrDeleteListener());
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.05;
        buttonPanel.add(editBtn, c);

        //creating the Delete button and adding the listener to it
        deleteBtn = new JButton("Remove Specialist");
        deleteBtn.addActionListener(new submitOrDeleteListener());
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.05;
        buttonPanel.add(deleteBtn, c);
        add(buttonPanel, BorderLayout.EAST);
        
        cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(new submitOrDeleteListener());
        c.gridx = 0;
        c.gridy = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.05;
        buttonPanel.add(cancelBtn, c);
        add(buttonPanel, BorderLayout.EAST);
    }

    /**
     * Return the email of a specialist
     * @return String
     */
	private String getSpecialist() {
		if (listPanel.getSelectedSpec() != null) {
            String[] nameArray = listPanel.getSelectedSpec();
            String fname = nameArray[0];
            String lname = nameArray[1];

			return SpecialistTable.getEmailFromName(fname,lname);
		}
		return null;
	}

    /**
     * ActionListener for Add, Edit, and Delete Specialist
     */
    private class submitOrDeleteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            //If Add Specialist is pressed
            if (e.getSource() == addBtn) {
                int submitted = JOptionPane.showConfirmDialog(null, specInfoPanel, "Please enter the following information to create a specialist", JOptionPane.OK_CANCEL_OPTION);
                boolean flag = true;
                //ok pressed
                if (submitted == JOptionPane.OK_OPTION) {
                    boolean exists = false;
                    ArrayList<String> names = SpecialistTable.getNames();
                    for (String name : names) {
                        if (name.equals(specInfoPanel.getFullName())) {
                            exists = true;
                        }
                    }
                    //check for error in input information
                    if (exists) {
                        JOptionPane.showMessageDialog(null, "An account with this username already exits.", "Existing Account", JOptionPane.ERROR_MESSAGE);
                    } else if (specInfoPanel.getFirstName().length() == 0 || specInfoPanel.getLastName().length() == 0
                            || specInfoPanel.getRoleText().length() == 0) {
                        JOptionPane.showMessageDialog(null, "Fail to create a Specialist.", "Incomplete Information", JOptionPane.ERROR_MESSAGE);
                    } else if (specInfoPanel.getPhoneText().length() == 0) {
                        JOptionPane.showMessageDialog(null, "Fail to create a Specialist.", "Invalid Phone", JOptionPane.ERROR_MESSAGE);
                    } else if (specInfoPanel.getEmailText().length() == 0) {
                        JOptionPane.showMessageDialog(null, "Fail to create a Specialist.", "Invalid E-mail", JOptionPane.ERROR_MESSAGE);
                    } else if (specInfoPanel.getPassword().length() == 0 || specInfoPanel.getCPassword().length() == 0) {
                        JOptionPane.showMessageDialog(null, "No Password Input", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (specInfoPanel.getPassword().length() < 6) {
                        JOptionPane.showMessageDialog(null, "Password too Short", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (!specInfoPanel.confirmPassword()) {
                        JOptionPane.showMessageDialog(null, "Password doesn't match", "Error", JOptionPane.ERROR_MESSAGE);
                    }else {
                        ImageIcon i = new ImageIcon(specInfoPanel.getPhoto());
                        if (i.getIconHeight() > 250 || i.getIconWidth() > 250) {

                            JOptionPane.showMessageDialog(null, "Image too Large. Maximum size is 250px by 250px.", "Image Error", JOptionPane.ERROR_MESSAGE);

                        } else if (specInfoPanel.getPhoto().length() > 100) {
                        	
                        	JOptionPane.showMessageDialog(null, "Photo file name is too large. Please rename it and try again.", "Image Error", JOptionPane.ERROR_MESSAGE);
                        	
                        } else {
                            SpecialistTable.addSpecialist(
                                    specInfoPanel.getFirstName(),
                                    specInfoPanel.getLastName(),
                                    specInfoPanel.getPhoneText(),
                                    specInfoPanel.getEmailText(),
                                    specInfoPanel.getPassword(),
                                    specInfoPanel.getPhoto());
                            listPanel.updateList();
                            specInfoPanel.clear();
                        }
                    }
                }
            }

            //If Edit button is pressed
            else if (e.getSource() == editBtn) {
                String[] temp = listPanel.getSelectedSpec();
                if (getSpecialist() != null) {
                    updatePanel.setEditUser(temp);
                    int submitted = JOptionPane.showConfirmDialog(null, updatePanel , "Please enter the updated information.", JOptionPane.OK_CANCEL_OPTION);

                    //update specialist info
                    if (submitted == JOptionPane.OK_OPTION) {
                        //if no photo
                        if (updatePanel.getPhoto().length() == 0) {
                            SpecialistTable.updateEmail(getSpecialist(), updatePanel.getEmailText());
                            //if new password inputted
                            if (specInfoPanel.getPassword().length() < 6) {
                                JOptionPane.showMessageDialog(null, "Password too Short", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            if (updatePanel.getPassword().length() != 0 || updatePanel.getCPassword().length() != 0) {
                                if(updatePanel.confirmPassword())
                                    SpecialistTable.updatePassword(getSpecialist(), updatePanel.getPassword());
                                else
                                    JOptionPane.showMessageDialog(null, "Fails to update password", "Passwords Not Matched", JOptionPane.ERROR_MESSAGE);
                            }
                            if (updatePanel.getPhoneText().length() != 0)
                                SpecialistTable.updatePhone(getSpecialist(), updatePanel.getPhoneText());
                            listPanel.updateList();
                            updatePanel.clear();
                        } else {
                            ImageIcon j = new ImageIcon(updatePanel.getPhoto());
                            if (j.getIconHeight() > 250 || j.getIconWidth() > 250) {

                                JOptionPane.showMessageDialog(null, "Image too Large. Maximum size is 250px by 250px.", "Image Error", JOptionPane.ERROR_MESSAGE);

                            }  else if (updatePanel.getPhoto().length() > 100) {
                            	
                            	JOptionPane.showMessageDialog(null, "Photo file name is too large. Please rename it and try again.", "Image Error", JOptionPane.ERROR_MESSAGE);
                            	
                            } else {
                                SpecialistTable.updateEmail(getSpecialist(), updatePanel.getEmailText());
                                SpecialistTable.updatePassword(getSpecialist(), updatePanel.getPassword());
                                SpecialistTable.updatePhoto(getSpecialist(), updatePanel.getPhoto());
                                if (updatePanel.getPhoneText().length() != 0)
                                    SpecialistTable.updatePhone(getSpecialist(), updatePanel.getPhoneText());

                                listPanel.updateList();
                                updatePanel.clear();
                            }

                            listPanel.updateList();
                            specInfoPanel.clear();

                        } 
                    } 
                } else {
                        JOptionPane.showMessageDialog(null, "Please select a specialist first", "Selection", JOptionPane.ERROR_MESSAGE);
                  }
            } 
            //If Delete button is pressed
            else if (e.getSource() == deleteBtn) {
                if (getSpecialist() != null) {
                    String[] name = listPanel.getSelectedSpec();
                    String fname = name[0];
                    String lname = name[1];
                    int submitted = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " +fname+" "+lname  , "Delete Warning", JOptionPane.OK_CANCEL_OPTION);
                    if(submitted == JOptionPane.OK_OPTION) {
                        SpecialistTable.deleteSpecialist(getSpecialist());
                        listPanel.updateList();
                        listPanel.repaint();
                    }

                }
                else{
                    JOptionPane.showMessageDialog(null, "Please select a specialist", "No Selection", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == cancelBtn) {
            	dispose();
                new AdminFrame();
            }
        }
    }
}
