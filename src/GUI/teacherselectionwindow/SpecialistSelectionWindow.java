package GUI.teacherselectionwindow;

//import Backend.Data;
import Backend.SpecialistTable;
//import Backend.User;

import disabilitykiosk.DisabilityKiosk;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.*;

/**
 *
 * @author Sarah Ben-Kiki, Brendan
 */
public class SpecialistSelectionWindow extends JFrame {

    Toolkit tk = Toolkit.getDefaultToolkit();
    private final int WINDOW_WIDTH = ((int) tk.getScreenSize().getWidth());
    private final int WINDOW_HEIGHT = ((int) tk.getScreenSize().getHeight());
//    private Backend.User user;
    private JButton submit;
    private JPanel panel1, panel2; //panel3, panel4;
    private ArrayList<JRadioButtonMenuItem> radioButtons;
    private ButtonGroup bg = new ButtonGroup();
    private ArrayList<String> specName;
    private ArrayList<String> specPhoto;
    //private Border blackline = BorderFactory.createLineBorder(Color.black);
      //TODO: need to get information that user filled out in the form into the Constructor
    public SpecialistSelectionWindow() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        buildPanel1();
        buildPanel2();
        setLayout(new BorderLayout());
        add(panel1, BorderLayout.CENTER);
        add(panel2, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
//        this.user = user;
    }

    private void buildPanel1() {
        panel1 = new JPanel();
        panel1.setBorder(BorderFactory.createEmptyBorder(100, 300,60,300));
        
        radioButtons = new ArrayList<JRadioButtonMenuItem>();
        specPhoto = SpecialistTable.getPhotos();
        specName = SpecialistTable.getNames();
        
        for (int i = 0; i < specName.size(); i++) {
        	ImageIcon picture = new ImageIcon(specPhoto.get(i));
            String name = specName.get(i);
            JRadioButtonMenuItem rButton = new JRadioButtonMenuItem(name, picture, true);
            rButton.setFont(new Font("ariel",Font.BOLD, 100));
            rButton.isBorderPainted();
            radioButtons.add(rButton);
        }
        for (int i = 0; i < radioButtons.size(); i++) {
            bg.add((JRadioButtonMenuItem) radioButtons.get(i));
        }
        for (int i = 0; i < radioButtons.size(); i++) {
            panel1.add((JRadioButtonMenuItem)radioButtons.get(i));
        }
    }
    
    private void buildPanel2(){
        panel2 = new JPanel();
        panel2.setBorder(BorderFactory.createEmptyBorder(10, 100, 150, 100));

        submit = new JButton("Submit");
        submit.addActionListener(new SpecialistSelectionWindow.RadioButtonListener());
        submit.setPreferredSize(new Dimension(100, 50));
        panel2.add(submit);
    }

    public JRadioButtonMenuItem getSelection(ButtonGroup group) {
        for (Enumeration e = group.getElements(); e.hasMoreElements();) {
            JRadioButtonMenuItem b = (JRadioButtonMenuItem) e.nextElement();
            if (b.getModel() == group.getSelection()) {
                return b;
            }
        }
        return null;
    }

    public void submitted() {
        JRadioButtonMenuItem selected = getSelection(bg);
        String facultySelected[] = selected.getText().split(" ");
        //TODO: add the appropriate information to the VisitTable
//        user.setSpecialist(facultySelected[0], facultySelected[1]);
//        try {
//            Data.open();
//            user.newVisit(Data.chooseTable("visits"));
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
        JOptionPane.showMessageDialog(null, "Thank you for using the Disability Kiosk", "Thank You",
                JOptionPane.PLAIN_MESSAGE);
        new DisabilityKiosk();
        this.dispose();
    }
    private class RadioButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == submit) {
                submitted();
            }
        }
    }
}
