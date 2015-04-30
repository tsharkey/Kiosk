package GUI.adddeletespec;

//import Backend.Specialist;
//import Backend.SpecialistList;
import Backend.SpecialistTable;
import GUI.loginwindow.AdminFrame;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.*;

/**
 * AddDeleteSpecFrame Class is part or the Kiosk implementation. it is a graphic
 * interface that allows to add or delete a specialist from the database The
 * frame will have three buttons to add, delete and edit the specialist
 *
 * @author Spconway 4/26/2014
 */
public class AddDeleteSpecFrame extends JFrame {

    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 500;
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

    public AddDeleteSpecFrame() {
        //Window title
        setTitle("ADD/DELETE SPECIALIST");
        setLayout(new BorderLayout());
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
    private void buildMainFrame() {
//        dlm = new DefaultListModel<String>();
//        updateList();
        listPanel = new ListPanel();
        listPanel.updateList();

        specInfoPanel = new SpecInfoPanel();
        updatePanel = new UpdatePanel();

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        add(listPanel);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4,0,0,10));

        Container c = this.getContentPane();


        //creating the Add button and adding the listener to it
        addBtn = new JButton("ADD NEW SPECIALIST");
        addBtn.setPreferredSize(new Dimension(150, 30));
        addBtn.addActionListener(new submitOrDeleteListener());
        buttonPanel.add(addBtn);

        //creating the Edit button and adding the listener to it
        editBtn = new JButton("EDIT SPECIALIST");
        editBtn.setPreferredSize(new Dimension(150, 30));
        editBtn.addActionListener(new submitOrDeleteListener());
        buttonPanel.add(editBtn);

        //creating the Delete button and adding the listener to it
        deleteBtn = new JButton("REMOVE SPECIALIST");
        deleteBtn.setPreferredSize(new Dimension(150, 30));
        deleteBtn.addActionListener(new submitOrDeleteListener());
        buttonPanel.add(deleteBtn);

        cancelBtn = new JButton("GO BACK");
        cancelBtn.setPreferredSize(new Dimension(150, 30));
        cancelBtn.addActionListener(new submitOrDeleteListener());
        buttonPanel.add(cancelBtn);

        add(buttonPanel, BorderLayout.EAST);

    }

    /**
     * Return the email of a specialist
     *
     * @return String
     */
    private String getSpecialist() {
        if (listPanel.getSelectedSpec() != null) {
            String[] nameArray = listPanel.getSelectedSpec();
            String fname = nameArray[0];
            String lname = nameArray[1];

            return SpecialistTable.getEmailFromName(fname, lname);
        }
        return null;
    }

    public void copyFile(File sourceFile, File destFile)
            throws IOException {
        if (!sourceFile.exists()) {
            return;
        }
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }

    public String getFileExtension(File file){
         String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf("."));
        else return "";
    }
    
    /**
     * ActionListener for Add, Edit, and Delete Specialist
     */
    private class submitOrDeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //If Add Specialist is pressed
            if (e.getSource() == addBtn) {
                int submitted = JOptionPane.showConfirmDialog(null, specInfoPanel, "Please enter the following information to create a specialist", JOptionPane.OK_CANCEL_OPTION);
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
                    } else if (specInfoPanel.getFirstName().length() == 0 || specInfoPanel.getLastName().length() == 0) {
                        JOptionPane.showMessageDialog(null, "Fail to create a Specialist.", "Incomplete Information", JOptionPane.ERROR_MESSAGE);
                    } else if (specInfoPanel.getPhoneText().length() == 0) {
                        JOptionPane.showMessageDialog(null, "Fail to create a Specialist.", "Invalid Phone", JOptionPane.ERROR_MESSAGE);
                    } else if (specInfoPanel.getEmailText().length() == 0) {
                        JOptionPane.showMessageDialog(null, "Fail to create a Specialist.", "Invalid E-mail", JOptionPane.ERROR_MESSAGE);
                    } else if (specInfoPanel.getPassword().length() == 0 && specInfoPanel.getCPassword().length() == 0) {
                        JOptionPane.showMessageDialog(null, "No Password Input", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (specInfoPanel.getPassword().length() == 0) {
                        JOptionPane.showMessageDialog(null, "Password should include 1 Upper Case, 1 Lower Case,1 number, 1 of @#$% and 6-20 letters.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (specInfoPanel.getPassword().length() < 6) {
                        JOptionPane.showMessageDialog(null, "Password too Short", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (!specInfoPanel.confirmPassword()) {
                        JOptionPane.showMessageDialog(null, "Password doesn't match", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        ImageIcon i = new ImageIcon(specInfoPanel.getPhoto());
                        if (i.getIconHeight() > 250 || i.getIconWidth() > 250) {

                            JOptionPane.showMessageDialog(null, "Image too Large. Maximum size is 250px by 250px.", "Image Error", JOptionPane.ERROR_MESSAGE);

                        } else if (specInfoPanel.getPhoto().length() > 100) {

                            JOptionPane.showMessageDialog(null, "Photo file name is too large. Please rename it and try again.", "Image Error", JOptionPane.ERROR_MESSAGE);

                        } else {
                            //copy the image to the project folder for using later
                            //save the relative path to the image into the db
                            File dir = new File("specialist_images");
                            if (!dir.exists() || !dir.isDirectory()) {
                                dir.mkdir();
                            }
                            File scr = new File(specInfoPanel.getPhoto());
                            File dest = new File("specialist_images/" + specInfoPanel.getFirstName() + "_" + specInfoPanel.getLastName() + getFileExtension(scr));
                            try {
                                copyFile(scr, dest);
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                            SpecialistTable.addSpecialist(
                                    specInfoPanel.getFirstName(),
                                    specInfoPanel.getLastName(),
                                    specInfoPanel.getEmailText(),
                                    specInfoPanel.getPhoneText(),
                                    specInfoPanel.getPassword(),
                                    "specialist_images/" + specInfoPanel.getFirstName() + "_" + specInfoPanel.getLastName() + ".jpg");
                            listPanel.updateList();
                            specInfoPanel.clear();
                        }
                    }
                }
            } //If Edit button is pressed
            else if (e.getSource() == editBtn) {
                String[] temp = listPanel.getSelectedSpec();
                if (getSpecialist() != null) {
                    updatePanel.setEditUser(temp);
                    int submitted = JOptionPane.showConfirmDialog(null, updatePanel, "Please enter the updated information.", JOptionPane.OK_CANCEL_OPTION);

                    //update specialist info
                    if (submitted == JOptionPane.OK_OPTION) {
                        //if no photo
                        if (updatePanel.getPhoto().length() == 0) {
                            SpecialistTable.updateEmail(getSpecialist(), updatePanel.getEmailText());
                            //if new password inputted
                            if (updatePanel.getPassword().length() != 0 || updatePanel.getCPassword().length() != 0) {
                                if (updatePanel.getPassword().length() < 6) {
                                    JOptionPane.showMessageDialog(null, "Password too Short", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                if (updatePanel.getCPassword().length() == 0) {
                                    JOptionPane.showMessageDialog(null, "Password should include 1 Upper Case, 1 Lower Case, 1 number, 1 of @#$% and 6-20 letters.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                if (updatePanel.confirmPassword()) {
                                    SpecialistTable.updatePassword(getSpecialist(), updatePanel.getPassword());
                                } else {
                                    JOptionPane.showMessageDialog(null, "Passwords Not Matched", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            if (updatePanel.getPhoneText().length() != 0) {
                                SpecialistTable.updatePhone(getSpecialist(), updatePanel.getPhoneText());
                            }
                            listPanel.updateList();
                            updatePanel.clear();
                        } else {
                            ImageIcon j = new ImageIcon(updatePanel.getPhoto());
                            if (j.getIconHeight() > 250 || j.getIconWidth() > 250) {

                                JOptionPane.showMessageDialog(null, "Image too Large. Maximum size is 250px by 250px.", "Image Error", JOptionPane.ERROR_MESSAGE);

                            } else if (updatePanel.getPhoto().length() > 100) {

                                JOptionPane.showMessageDialog(null, "Photo file name is too large. Please rename it and try again.", "Image Error", JOptionPane.ERROR_MESSAGE);

                            } else {
                                SpecialistTable.updateEmail(getSpecialist(), updatePanel.getEmailText());
                                SpecialistTable.updatePassword(getSpecialist(), updatePanel.getPassword());
                                //create folder if neccessary
                                File dir = new File("specialist_images");
                                if (!dir.exists() || !dir.isDirectory()) {
                                    dir.mkdir();
                                }
                                //delete old picture
                                File oldFile = new File("specialist_images/" + listPanel.getSelectedSpec()[0] + "_" + listPanel.getSelectedSpec()[1] + ".jpg");
                                if (oldFile.exists()) {
                                    oldFile.delete();
                                }
                                
                                File oldFile1 = new File("specialist_images/" + listPanel.getSelectedSpec()[0] + "_" + listPanel.getSelectedSpec()[1] + ".png");
                                if (oldFile1.exists()) {
                                    oldFile1.delete();
                                }
                                
                                //replace with new picture
                                File src = new File(updatePanel.getPhoto());
                                File dest = new File("specialist_images/" + listPanel.getSelectedSpec()[0] + "_" + listPanel.getSelectedSpec()[1] + getFileExtension(src));
                                try {
                                    copyFile(src, dest);
                                } catch (IOException ioe) {
                                    ioe.printStackTrace();
                                }
                                //no need to update relative picture's path
                                //SpecialistTable.updatePhoto(getSpecialist(), updatePanel.getPhoto());
                                if (updatePanel.getPhoneText().length() != 0) {
                                    SpecialistTable.updatePhone(getSpecialist(), updatePanel.getPhoneText());
                                }

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
            } //If Delete button is pressed
            else if (e.getSource() == deleteBtn) {
                if (getSpecialist() != null) {
                    String[] name = listPanel.getSelectedSpec();
                    String fname = name[0];
                    String lname = name[1];
                    int submitted = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + fname + " " + lname, "Delete Warning", JOptionPane.OK_CANCEL_OPTION);
                    if (submitted == JOptionPane.OK_OPTION) {
                        SpecialistTable.deleteSpecialist(getSpecialist());
                        listPanel.updateList();
                        listPanel.repaint();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a specialist", "No Selection", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == cancelBtn) {
                dispose();
                new AdminFrame();
            }
        }
    }
}
