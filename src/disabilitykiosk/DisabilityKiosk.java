/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package disabilitykiosk;

import GUI.loginwindow.*;
import GUI.teacherselectionwindow.*;
import Backend.*;
import com.sun.speech.freetts.VoiceManager;
//import disabilitykiosk.LabelsAndFieldsPanel;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.io.IOException;
import java.util.Hashtable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * DisabilityKiosk class is the main window of the Kiosk program.
 * It allows user to input information for their meeting with a supervisor.
 * It allows administrator to go to their login window.
 * @author Jacob Dwyer
 */
public class DisabilityKiosk extends JFrame
{
  //Utilizing Toolkit to retrieve screen width and height
  Toolkit tk = Toolkit.getDefaultToolkit();
  private final int WINDOW_WIDTH = ((int) tk.getScreenSize().getWidth()-100); //Width
  private final int WINDOW_HEIGHT = ((int) tk.getScreenSize().getHeight()-100); //Height
  //private final LoginWindow login_Window;
  private final GreetingsPanel greetings_Panel;
  //private final InfoPanel info_Panel;
  //private final LabelPanel label_Panel;
  private final LabelsAndFieldsPanel labelsAndFields;
  private final SubmitPanel submit_Panel;
  private Backend.User user;
  public static boolean isAdminWorking = false;
    /**
     * Constructor of DisabilityKiosk
     */
  public DisabilityKiosk()
  {
      if(AdminTable.isEmpty()){
            int submitted;
            JTextField unInput = new JTextField(10);
            JPasswordField pwInput = new JPasswordField(10);
            do {
                JPanel adminInput = new JPanel();
                adminInput.add(new JLabel("Username: "));
                adminInput.add(unInput);
                adminInput.add(Box.createHorizontalStrut(15));
                adminInput.add(new JLabel("Password: "));
                adminInput.add(pwInput);
                adminInput.setVisible(true);
                submitted = JOptionPane.showConfirmDialog(null, adminInput, "Please enter new Admin Username & Password.", JOptionPane.OK_CANCEL_OPTION);
                if (submitted == JOptionPane.OK_OPTION && !unInput.getText().equals("") && pwInput.getPassword().length != 0) {
                    String pw = new String(pwInput.getPassword());
                    AdminTable.addAdmin(unInput.getText(), pw);
                } else if (submitted != JOptionPane.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(null, "Please enter a Username and Password.", "Input Error", JOptionPane.ERROR_MESSAGE);

                }
            }
            while (submitted == JOptionPane.OK_OPTION && (unInput.getText().equals("") || pwInput.getPassword().length == 0));

            if (submitted == JOptionPane.CANCEL_OPTION) {
                System.exit(0);
            }
      }
      
      
    //Display a title
    setTitle("Disability Resource Kiosk");
    //set the window size
    //setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
    //setExtendedState(Frame.MAXIMIZED_vBOTH);
    //setUndecorated(true);
    //Specify an action on close
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    /*
     * TRYING TO GET RID OF TASK BAR
     */
                //--Below code code are different options for
                //--creating full screen effect minus taskbar
                //--no way of testing on my mac so whoever tests
                //--can delete the code that isn't needed
                //--don't forget to comment out the setSize method on line 45
                //--before trying these options though
                //--lastly, setVisible(true) is on line 107

                //--option 1--//
//    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//    if(gd.isFullScreenSupported()){
//        setUndecorated(true);
//        gd.setFullScreenWindow(this);
//    }else{
//        JOptionPane.showMessageDialog(rootPane, "Full screen not supported.");
//        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
//    }
                //--option 2--//
//    this.setSize(this.getToolkit().getScreenSize());
                //--option 3--//
    //setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
    setExtendedState(Frame.MAXIMIZED_BOTH);
    setUndecorated(true);
    
    /*
     * ABOVE CODE IS TRYING TO GET RID OF TASK BAR
     */
    //Create a Border Layout
    setLayout(new BorderLayout());

    //Create the custom panels
    greetings_Panel = new GreetingsPanel();
    /*
     * Commented out info_Panel and label_Panel to add the labelsAndFields panel
     * which was made using gridbaglayout
     */
    labelsAndFields = new LabelsAndFieldsPanel();
    submit_Panel = new SubmitPanel();
    submit_Panel.admin.addActionListener(new DisabilityKiosk.AdminButtonListener());
//    submit_Panel.close.addActionListener(new DisabilityKiosk.CloseButtonListener());
    submit_Panel.submit.addActionListener(new DisabilityKiosk.SubmitButtonListener());
//    submit_Panel.submitSpeech.addActionListener(new DisabilityKiosk.SubmitSpeechButtonListener());
//    submit_Panel.closeSpeech.addActionListener(new DisabilityKiosk.CloseSpeechButtonListener());
//    submit_Panel.adminSpeech.addActionListener(new DisabilityKiosk.AdminSpeechButtonListener());

    //Add the componets to the content pane
    add(greetings_Panel, BorderLayout.NORTH);
    add(labelsAndFields, BorderLayout.CENTER);
    //add(info_Panel, BorderLayout.CENTER);
    //add(label_Panel, BorderLayout.WEST);
    add(submit_Panel, BorderLayout.SOUTH);

    //set the windows position to the center of the screen
    setLocationRelativeTo(null);
    //Make the window visible
    setVisible(true);
  }


    /**
     * Administer button
     * goes to admin login window
     */
  private class AdminButtonListener implements ActionListener
  {
      public void actionPerformed(ActionEvent e)
      {
          if (e.getSource() == submit_Panel.admin)
          {
              //if there is no admin logging in, call the login window
              if(!DisabilityKiosk.isAdminWorking){
                  new LoginFrame();
              }
              //else, call the admin window
              else{
                  AdminFrame adminPanel = new AdminFrame();
                  adminPanel.setVisible(true);
              }
              setVisible(true);
              dispose();
          }
      }
    }

    /**
     * ActionListener for the close button
     * goes to admin login window
     */
  private class CloseButtonListener implements ActionListener
  {
      public void actionPerformed(ActionEvent e)
      {
          if (e.getSource() == submit_Panel.close)
          {
              new LoginFrame();
              setVisible(false);
              dispose();
          }
      }
    }

    /**
     * ActionListener for the Submit button
     * validates all inputs
     */
  private class SubmitButtonListener implements ActionListener
  {
      public void actionPerformed(ActionEvent e)
      {
          if(!SpecialistTable.isEmpty()){
          //if(!SpecialistList.getSpecialList().isEmpty()){
          boolean flag = true;
            if( labelsAndFields.getFirst().length() == 0 || labelsAndFields.getLast().length() == 0 )
            {
              flag = false;
              JOptionPane.showMessageDialog(null,"Please Enter the Correct Information","Incomplete",JOptionPane.ERROR_MESSAGE);
            } else if (labelsAndFields.getEmail().length() == 0)
            {
              flag = false; 
              JOptionPane.showMessageDialog(null,"Please Enter a Valid E-mail Address","Invalid E-mail",JOptionPane.ERROR_MESSAGE);
            }
            else if (labelsAndFields.getPhone().length() == 0)
            {
              flag = false; 
              JOptionPane.showMessageDialog(null,"Please Enter a Valid Phone Number","Invalid Phone",JOptionPane.ERROR_MESSAGE);
            } else if( flag = true)
            {
              boolean temp;
                if(labelsAndFields.followUpI.getSelectedItem() == "Yes")
                temp = true;
              else
                temp = false;

                //adding the data to the database
                UserTable ut = new UserTable();
                VisitsTable vt = new VisitsTable();
                ut.addUser(labelsAndFields.getFirst(), labelsAndFields.getLast(), labelsAndFields.getEmail(), labelsAndFields.getPhone(), labelsAndFields.getRole());
                VisitsTable.addVisit(labelsAndFields.getReason(), labelsAndFields.getFollowUp(), labelsAndFields.getEmail(), "fix this", labelsAndFields.getLocationInput());
              setVisible(false);
              new GUI.teacherselectionwindow.SpecialistSelectionWindow(user);
            }
          }
          else{
              JOptionPane.showMessageDialog(null,"No Specialist in List","Incomplete",JOptionPane.ERROR_MESSAGE);
          }
}
  }
}
//           private class SubmitSpeechButtonListener implements ActionListener
//{
//    public void actionPerformed(ActionEvent e)
//    {
//        if (e.getSource() == submit_Panel.submitSpeech)
//        {
//             VoiceManager vm = VoiceManager.getInstance();
//
//            com.sun.speech.freetts.Voice voice = vm.getVoice("kevin16");
//            String text = "Submit your answers";
//            String string[] = text.split("  ");
//            voice.allocate();
//            for (int i = 0; i < string.length; i++) {
//                voice.speak(text);
//
//       }
//       voice.deallocate();
//        }
//
//    }
//  }
//           
//        private class CloseSpeechButtonListener implements ActionListener
//{
//    public void actionPerformed(ActionEvent e)
//    {
//        if (e.getSource() == submit_Panel.closeSpeech)
//        {
//             VoiceManager vm = VoiceManager.getInstance();
//
//            com.sun.speech.freetts.Voice voice = vm.getVoice("kevin16");
//            String text = "Close the window";
//            String string[] = text.split("  ");
//            voice.allocate();
//            for (int i = 0; i < string.length; i++) {
//                voice.speak(text);
//
//       }
//       voice.deallocate();
//        }
//
//    }
//  }
//      private class AdminSpeechButtonListener implements ActionListener
//{
//    public void actionPerformed(ActionEvent e)
//    {
//        if (e.getSource() == submit_Panel.adminSpeech)
//        {
//             VoiceManager vm = VoiceManager.getInstance();
//
//            com.sun.speech.freetts.Voice voice = vm.getVoice("kevin16");
//            String text = "Admins only.";
//            String string[] = text.split("  ");
//            voice.allocate();
//            for (int i = 0; i < string.length; i++) {
//                voice.speak(text);
//
//       }
//       voice.deallocate();
//        }
//
//    }
//  }  
//}
