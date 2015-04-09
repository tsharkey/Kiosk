package GUI.adddeletespec;

import Backend.Specialist;
import Backend.SpecialistList;
import Backend.SpecialistTable;

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
 *
 * @author Spconway 4/26/2014
 */
public class AddDeleteSpecFrame extends JFrame{
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 500;
    private ArrayList<String> specNames;
    private SpecInfoPanel specInfoPanel;
    private ListPanel listPanel;
    private JPanel buttonPanel;
    private JButton addBtn, editBtn, deleteBtn;
    private JList<String> list;
    private DefaultListModel<String> dlm;
    private JScrollPane scroll;
    
    SpecialistTable spec;
    
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
        dlm = new DefaultListModel<String>();
        updateList();
        listPanel = new ListPanel();
        specInfoPanel = new SpecInfoPanel();
        //listPanel = new ListPanel();
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        
        setLayout(new BorderLayout());

        add(listPanel);
        //add(specInfoPanel, BorderLayout.EAST);
//        add(submitPanel, BorderLayout.SOUTH);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        addBtn = new JButton("Add New Specialist");
        addBtn.addActionListener(new submitOrDeleteListener());
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.05;
        buttonPanel.add(addBtn, c);
        
        editBtn = new JButton("Edit Specialist");
        editBtn.addActionListener(new submitOrDeleteListener());
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.05;
        buttonPanel.add(editBtn, c);
        
        deleteBtn = new JButton("Remove Specialist");
        deleteBtn.addActionListener(new submitOrDeleteListener());
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.05;
        buttonPanel.add(deleteBtn, c);
        add(buttonPanel, BorderLayout.EAST);
    }
       public void updateList()
    {
        dlm.clear();
        for (Specialist a : Backend.SpecialistList.specs)
        {
            dlm.addElement(a.getFullName());
        }
    }
       private Specialist getSpecialist()
    {
        if (list.getSelectedValue() != null)
        {
            String target = list.getSelectedValue();

            for (Specialist a : SpecialistList.specs)
            {
                if (target.equals(a.getFullName()))
                {
                    return a;
                }
            }
        }
        return null;
    }
    //LISTENERS???
    private class submitOrDeleteListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if (e.getSource() == addBtn){
                int submitted = JOptionPane.showConfirmDialog(null, specInfoPanel, "Please enter a username and password for the new account.", JOptionPane.OK_CANCEL_OPTION);
                boolean flag = true;
                if (submitted == JOptionPane.OK_OPTION){
                        boolean exists = false;
                        for (Specialist a : SpecialistList.specs)
                        {
                            if (a.getFullName().equals(specInfoPanel.getFullName()))
                            {
                                exists = true;
                            }
                        }
                    if (exists)
                    {
                        JOptionPane.showInternalMessageDialog(null, "An account with this username already exits.", "Existing Account", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(specInfoPanel.getFirstName().length() == 0 || specInfoPanel.getLastName().length() == 0
                            || specInfoPanel.getRoleText().length() == 0){
                        JOptionPane.showMessageDialog(null, "Fail to create a Specialist.", "Incomplete", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(specInfoPanel.getPhoto().length() == 0)
                    {
                        JOptionPane.showMessageDialog(null, "Fail to create a Specialist.", "Image Missing", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(specInfoPanel.getPhoneText().length() == 0){
                        JOptionPane.showMessageDialog(null, "Fail to create a Specialist.", "Invalid Phone", JOptionPane.ERROR_MESSAGE);
                    }
                    else if(specInfoPanel.getEmailText().length() == 0) {
                        JOptionPane.showMessageDialog(null, "Fail to create a Specialist.", "Invalid E-mail", JOptionPane.ERROR_MESSAGE);
                    }
                    else
                            {
                                ImageIcon i = new ImageIcon(specInfoPanel.getPhoto());
                                if(i.getIconHeight()>250||i.getIconWidth()>250){
                                    
                                      JOptionPane.showInternalMessageDialog(null, "Image too Large. Maximum size is 250px by 250px.", "Image Error", JOptionPane.ERROR_MESSAGE);

                                }
                                else{
                                SpecialistList.specs.add(new Specialist(specInfoPanel.getPhoto(), specInfoPanel.getEmailText(),specInfoPanel.getFirstName(),specInfoPanel.getLastName(),specInfoPanel.getRoleText(),specInfoPanel.getPhoneText()));
                                updateList();
                                specInfoPanel.clear();
                                }
                    }
                   }
            }
            if (e.getSource() == editBtn){
                if(getSpecialist()!=null){
//                specInfoPanel.setEditUser(getSpecialist());
                int submitted = JOptionPane.showConfirmDialog(null, specInfoPanel, "Please enter a username and password for the new account.", JOptionPane.OK_CANCEL_OPTION);
                if (submitted == JOptionPane.OK_OPTION){
                    if(specInfoPanel.getPhoto().length() == 0){
                     SpecialistList.specs.set(SpecialistList.specs.indexOf(getSpecialist()),new Specialist(specInfoPanel.getEmailText(),specInfoPanel.getFirstName(),specInfoPanel.getLastName(),specInfoPanel.getRoleText(),specInfoPanel.getPhoneText()));
                     updateList();
                     specInfoPanel.clear();
                    }
                    else{
                        ImageIcon j = new ImageIcon(specInfoPanel.getPhoto());
                                if(j.getIconHeight()>250||j.getIconWidth()>250){
                                    
                                      JOptionPane.showMessageDialog(null, "Image too Large.", "Image Error", JOptionPane.ERROR_MESSAGE);

                                }
                                else{
                                    SpecialistList.specs.set(SpecialistList.specs.indexOf(getSpecialist()),new Specialist(specInfoPanel.getPhoto(),specInfoPanel.getEmailText(),specInfoPanel.getFirstName(),specInfoPanel.getLastName(),specInfoPanel.getRoleText(),specInfoPanel.getPhoneText()));
                                }
                    }
                }
            }
        }
            if(e.getSource() == deleteBtn){
                if(getSpecialist()!=null){
                     SpecialistList.specs.remove(SpecialistList.specs.indexOf(getSpecialist()));
                     updateList();
                }
            }
    }
}
}
