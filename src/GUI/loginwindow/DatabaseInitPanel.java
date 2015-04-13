package GUI.loginwindow;

import javax.swing.*;
import java.awt.*;

/**
 * DatabaseInitPanel class creates a window that will allow the Kiosk application to connect to the database,
 * using the host, user name and password
 * it has two buttons one to login in the database 'Login' and one to exit 'cancel'
 * Created by catherinehuang on 4/6/15.
 */
public class DatabaseInitPanel extends JPanel{

    private final int CHAR_LENGTH = 20;  //userName, passWord, and
    private JLabel userName; //label for username
    private JLabel passWord; //label for password
    private JButton loginButton; //button for login
    private JButton cancelButton; //button for cancel, exit out of the application
    private Font font = new Font("PLAIN", Font.PLAIN, 18);
    private JTextField userNameText; //text field to enter the user name
    private JPasswordField passWordText; //text field to enter the password
    private JTextField hostText; // text field to enter the host

    /**
     * Constructor
     */
    public DatabaseInitPanel() {
        super(new GridBagLayout());
        loginPanel();
        setVisible(true);
    }

    /**
     * Creates the labels and buttons for the login panel
     */
    private void loginPanel() {

        userNameText = new JTextField(CHAR_LENGTH);
        passWordText = new JPasswordField(CHAR_LENGTH);
        hostText = new JTextField(CHAR_LENGTH);
        loginButton = new JButton("LOGIN");
        cancelButton = new JButton("CANCEL");


        GridBagConstraints grid = new GridBagConstraints();

        grid.fill = GridBagConstraints.HORIZONTAL;

        //creates the label host
        JLabel host = new JLabel("Host: ");
        host.setFont(font);
        grid.gridx = 0;
        grid.gridy = 0;
        grid.gridwidth = 1;

        add(host,grid);

        grid.gridx = 1;
        grid.gridy = 0;
        grid.gridwidth = 1;

        add(hostText, grid);

        //creates the label fo the userName
        userName = new JLabel("Username: ");
        userName.setFont(font);
        grid.gridx = 0;
        grid.gridy = 1;
        grid.gridwidth = 1;

        add(userName, grid);

        grid.gridx = 1;
        grid.gridy = 1;
        grid.gridwidth = 2;

        add(userNameText, grid);

        //Creates the label for the password
        passWord = new JLabel("Password: ");
        passWord.setFont(font);
        grid.gridx = 0;
        grid.gridy = 2;
        grid.gridwidth = 1;

        add(passWord, grid);

        grid.gridx = 1;
        grid.gridy = 2;
        grid.gridwidth = 2;

        add(passWordText, grid);

        //login button
        grid.gridx = 1;
        grid.gridy = 3;
        grid.gridwidth = 1;

        add(loginButton, grid);

        //cancel button
        grid.gridx = 2;
        grid.gridy = 3;
        grid.gridwidth = 1;

        add(cancelButton, grid);


    }

    // getters

    public final JButton getConnectButton() {
        return loginButton;
    }

    public final JButton getCancelButton() {
        return cancelButton;
    }

    public String getPasswordText() {
        return String.valueOf(passWordText.getPassword());
    }

    public String getUsernameText() {
        return userNameText.getText();
    }

    public String getHostText() {
        return hostText.getText();
    }

}//End of class
