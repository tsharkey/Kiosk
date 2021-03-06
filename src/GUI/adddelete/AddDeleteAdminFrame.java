package GUI.adddelete;

import Backend.AdminTable;
import GUI.loginwindow.AdminFrame;
import disabilitykiosk.DisabilityKiosk;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.*;


// Created by Pat and Cyrus
public class AddDeleteAdminFrame extends JFrame {

    private final int WINDOW_WIDTH = 300;
    private final int WINDOW_HEIGHT = 200;

    private JList<String> list;
    private DefaultListModel<String> listModel;
    private JScrollPane listScroller;

    private JPanel buttonPanel;
    private JButton addBtn, editBtn, deleteBtn, cancelBtn;

    public AddDeleteAdminFrame() {
        setTitle("ADD/DELETE ADMINISTRATORS");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(new BorderLayout());
        setLayout(new GridLayout(0, 1));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        buildMainFrame();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void buildMainFrame() {
        //listModel = new DefaultListModel<String>();
    	addWindowListener(new CloseListener());
        //list = new JList<String>(listModel);
        //list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        //list.setLayoutOrientation(JList.VERTICAL);
        //listScroller = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //add(listScroller, BorderLayout.CENTER);
       // buttonPanel = new JPanel();
        //buttonPanel.setLayout(new GridBagLayout());
        //GridBagConstraints c = new GridBagConstraints();

        Container c = this.getContentPane();
        c.setLayout(new GridLayout(0, 1));

        addBtn = new JButton("ADD NEW ADMIN");
        addBtn.addActionListener(new ButtonListener());
        c.add(addBtn);

        editBtn = new JButton("EDIT CURRENT ADMIN'S PASSWORD");
        editBtn.addActionListener(new ButtonListener());
        c.add(editBtn);

        deleteBtn = new JButton("REMOVE CURRENT ADMIIN");
        deleteBtn.addActionListener(new ButtonListener());
        c.add(deleteBtn);

        cancelBtn = new JButton("GO BACK");
        cancelBtn.addActionListener(new ButtonListener());
        c.add(cancelBtn);
    }


    private class CloseListener implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			new AdminFrame();
		}

		@Override
		public void windowClosed(WindowEvent e) {
			//
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
    	
    }
    

    /**
     * ButtonListener
     */
    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //if Add button pressed
            if (e.getSource() == addBtn) {
                JPanel adminInput = new JPanel();
                JTextField unInput = new JTextField(10);
                JPasswordField pwInput = new JPasswordField(10);
                JPasswordField confirmPW = new JPasswordField(10);
                adminInput.add(new JLabel("Username: "));
                adminInput.add(unInput);
                adminInput.add(Box.createHorizontalStrut(15));
                adminInput.add(new JLabel("Password: "));
                adminInput.add(pwInput);
                adminInput.add(new JLabel("Confirm Password:"));
                adminInput.add(confirmPW);
                int submitted = JOptionPane.showConfirmDialog(null, adminInput, "Please enter admin information", JOptionPane.OK_CANCEL_OPTION);

                //when Ok is pressed
                if (submitted == JOptionPane.OK_OPTION) {
                    String un = unInput.getText();
                    String pw = new String(pwInput.getPassword());
                    String cfpw = new String(confirmPW.getPassword());
                    if (!un.equals("") && !pw.equals("") && !cfpw.equals("")) {
                        if (AdminTable.admin_exist(un)) {
                            JOptionPane.showMessageDialog(null, "An account with this username already exits.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else if(pw.length()<6){
                                JOptionPane.showMessageDialog(null, "Password too short", "Error", JOptionPane.ERROR_MESSAGE);
                        } else if (!pw.equals(cfpw)) {
                            JOptionPane.showMessageDialog(null, "Passwords do not match", "Error ", JOptionPane.ERROR_MESSAGE);
                        } else {
                            AdminTable.addAdmin(un, pw);
                            JOptionPane.showMessageDialog(null, "Admin added successful ", "Notice", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Missing info ", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                dispose();
                new AdminFrame();
            } else if (e.getSource() == editBtn) {
                JPanel passChange = new JPanel();
                passChange.add(new JLabel("Current Password: "));
                JPasswordField oldPassInput = new JPasswordField(20);
                passChange.add(oldPassInput);
                passChange.add(new JLabel("New Password:"));
                JPasswordField newPassInput = new JPasswordField(20);
                passChange.add(newPassInput);
                passChange.add(new JLabel("Confirm New Password:"));
                JPasswordField cfPassInput = new JPasswordField(20);
                passChange.add(cfPassInput);
                int entered = JOptionPane.showConfirmDialog(null, passChange, "Please enter information", JOptionPane.OK_CANCEL_OPTION);

                if (entered == JOptionPane.OK_OPTION) {
                    String opw = new String(oldPassInput.getPassword());
                    String npw = new String(newPassInput.getPassword());
                    String cfpw = new String(cfPassInput.getPassword());
                    if (!opw.equals("") && !npw.equals("") && !cfpw.equals("")) {
                        if (!AdminTable.verifyPassword(DisabilityKiosk.workingAdmin, opw)) {
                            JOptionPane.showMessageDialog(null, "Wrong old password", "Error", JOptionPane.ERROR_MESSAGE);
                        } else if(npw.length() < 6) {
                            JOptionPane.showMessageDialog(null, "Password too short", "Error", JOptionPane.ERROR_MESSAGE);
                        } else if (!npw.equals(cfpw)) {
                            JOptionPane.showMessageDialog(null, "New Passwords do not match", "Error ", JOptionPane.ERROR_MESSAGE);
                        } else {
                            AdminTable.updatePassword(DisabilityKiosk.workingAdmin, npw);
                            JOptionPane.showMessageDialog(null, "password updated successful ", "Notice", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Missing info ", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                dispose();
                new AdminFrame();
            } else if (e.getSource() == deleteBtn) {
                if (AdminTable.getAdmins().size() == 1) {
                    JOptionPane.showMessageDialog(null, "You cannot delete the last Administrator.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JPanel passCheck = new JPanel();
                    JPasswordField passInput = new JPasswordField(20);
                    passCheck.add(new JLabel("Password: "));
                    passCheck.add(passInput);
                    int entered = JOptionPane.showConfirmDialog(null, passCheck, "Please enter the current password for this account.", JOptionPane.OK_CANCEL_OPTION);

                    if (entered == JOptionPane.OK_OPTION) {
                        String pw = new String(passInput.getPassword());
                        if (AdminTable.verifyPassword(DisabilityKiosk.workingAdmin, pw)) {
                            int entered2 = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this account?", "Verification", JOptionPane.YES_NO_OPTION);

                            if (entered2 == JOptionPane.YES_OPTION) {
                                AdminTable.deleteAdmin(DisabilityKiosk.workingAdmin);
                                DisabilityKiosk.workingAdmin = "";
                                DisabilityKiosk.isAdminWorking = false;
                                JOptionPane.showMessageDialog(null, "Admin account deleted", "Notice", JOptionPane.INFORMATION_MESSAGE);
                                new DisabilityKiosk();
                            }
                            //Brendan S
                            else if (entered2 == JOptionPane.NO_OPTION){
                            	dispose();
                                new AdminFrame();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect Password.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else if (entered == JOptionPane.CANCEL_OPTION){
                    	dispose();
                        new AdminFrame();
                    }
                }
                dispose();
            } else if (e.getSource() == cancelBtn) {
            	dispose();
                new AdminFrame();
            }
        }
    }
}
