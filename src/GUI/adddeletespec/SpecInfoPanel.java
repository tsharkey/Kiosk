package GUI.adddeletespec;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Spconway 4/26/2014
 */
public class SpecInfoPanel extends JPanel{
    
    private final int CHAR_LENGTH = 30;
    private Font font = new Font("MONOSPACED", Font.PLAIN, 18);
    //Need to make passT a JPassword field but after that we need to add another
    //JPassword field to confirm password and check that both JPassword fields match
    private JTextField firstT, lastT, emailT, photoT, phoneT;
    private JPasswordField passwordText, cpasswordText;
    private JButton fileselector;
   // private JComboBox locationI;
    
    public SpecInfoPanel(){
        super(new GridBagLayout());
        buildPanel();
        setVisible(true);
    }
    
    private void buildPanel(){
        GridBagConstraints grid = new GridBagConstraints();
        
        grid.fill = GridBagConstraints.HORIZONTAL;
        
        /*
         * Begin initialization and add contents to grid
         */
        
        //First name 
        JLabel first = new JLabel("First Name");
        first.setFont(font);
        grid.gridx = 0;
        grid.gridy = 0;
        grid.gridwidth = 1;
        add(first, grid);
        
        //input field for first name
        firstT = new JTextField(CHAR_LENGTH);
     
        grid.gridx = 1;
        grid.gridy = 0;
        grid.gridwidth = 2;
        add(firstT, grid);
        
        //Last name
        JLabel last = new JLabel("Last Name");
        last.setFont(font);
        grid.gridx = 0;
        grid.gridy = 1;
        grid.gridwidth = 1;
        add(last, grid);
        
        //input field for last name
        lastT = new JTextField(CHAR_LENGTH);
     
        grid.gridx = 1;
        grid.gridy = 1;
        grid.gridwidth = 2;
        add(lastT, grid);
        
        //Photo
        JLabel photo = new JLabel("Photo");
        photo.setFont(font);
        grid.gridx = 0;
        grid.gridy = 2;
        grid.gridwidth = 1;
        add(photo, grid);
        
        photoT = new JTextField(CHAR_LENGTH);
     
        grid.gridx = 1;
        grid.gridy = 2;
        grid.gridwidth = 1;
        add(photoT, grid);
        
        fileselector = new JButton("...");
        
        grid.gridx = 3;
        grid.gridy = 2;
        grid.gridwidth = 1;
        add(fileselector,grid);
        fileselector.addActionListener(new Listener());
        
        //Email
        JLabel email = new JLabel("E-Mail");
        email.setFont(font);
        grid.gridx = 0;
        grid.gridy = 3;
        grid.gridwidth = 1;
        add(email, grid);
        
        emailT = new JTextField(CHAR_LENGTH);
     
        grid.gridx = 1;
        grid.gridy = 3;
        grid.gridwidth = 2;
        add(emailT, grid);
        
        //Phone
        JLabel phone = new JLabel("Phone");
        phone.setFont(font);
        grid.gridx = 0;
        grid.gridy = 5;
        grid.gridwidth = 1;
        add(phone, grid);
        
        phoneT = new JTextField(CHAR_LENGTH);
     
        grid.gridx = 1;
        grid.gridy = 5;
        grid.gridwidth = 2;
        add(phoneT, grid);

        //password label and field
        JLabel password = new JLabel("Password");
        password.setFont(font);
        grid.gridx = 0;
        grid.gridy = 6;
        grid.gridwidth = 1;
        add(password, grid);

        passwordText = new JPasswordField(CHAR_LENGTH);
        grid.gridx = 1;
        grid.gridy = 6;
        grid.gridwidth = 2;
        add(passwordText,grid);

        //confirm password label and field
        JLabel cpassword = new JLabel("Confirm");
        cpassword.setFont(font);
        grid.gridx = 0;
        grid.gridy = 7;
        grid.gridwidth = 1;
        add(cpassword, grid);

        cpasswordText = new JPasswordField(CHAR_LENGTH);
        grid.gridx = 1;
        grid.gridy = 7;
        grid.gridwidth = 2;
        add(cpasswordText, grid);
        
    }
    
    //getters
    public String getFirstName(){
        return firstT.getText();
    }
    
    public String getLastName(){
        return lastT.getText();
    }
    public String getFullName(){
        return firstT.getText()+" "+lastT.getText();
    }
    
    public String getEmailText(){
        //regular expression
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(this.emailT.getText());

        //if input is an email
        if(match.matches()){
            return emailT.getText();
        }

        //if false email
        return "";

    }
    
    public String getPhoneText(){
        String regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(this.phoneT.getText());


        if(matcher.matches())
        {
            return matcher.replaceFirst("$1$2$3");
        }

        // invalid phone number
        return "";
    }

    /**
     * Returns the file name of the photo
     * @return
     */
    public String getPhoto(){
        String temp = photoT.getText();
        String[] tempList = temp.split("/", -2);
        //TODO: Only pass on the file name, once the SpecitalistTable's phone var length is fix, change to pass on the whole path
        return tempList[tempList.length-1];
    }

    /**
     * Validate and return the password
     * @return
     */
    public String getPassword() {
        String pw = new String(passwordText.getPassword());
        //regular expression
        String regex = "((?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pw);

        if(matcher.matches()) {
            return pw;
        }

        return "";
    }

    public String getCPassword() {
        String cpw = new String(cpasswordText.getPassword());
        return cpw;
    }

    public boolean confirmPassword() {
        return getPassword().equals(getCPassword());
    }
    
    /**
     * Clear all text fields
     */
    public void clear(){
        firstT.setText("");
        lastT.setText("");
        emailT.setText("");
        phoneT.setText("");
        photoT.setText("");
        passwordText.setText("");
        cpasswordText.setText("");
    }

    /**
     * Select photo from file as icon
     */
    private class Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == fileselector){
                JFileChooser fc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Img File", "jpg", "png"); // create a filter
                fc.setFileFilter(filter);
                //Brendan S
                int f = fc.showDialog(SpecInfoPanel.this,"OK");
                if(f == JFileChooser.APPROVE_OPTION){
                    photoT.setText(fc.getSelectedFile().getAbsolutePath());
                }
            }
        }
    }
}
