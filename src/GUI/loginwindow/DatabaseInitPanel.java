package GUI.loginwindow;

import javax.swing.*;
import java.awt.*;

/**
 * Created by catherinehuang on 4/6/15.
 */
public class DatabaseInitPanel extends JPanel{

    private final int CHAR_LENGTH = 20;  //userName, passWord, and
    private JLabel userName;
    private JLabel passWord;
    private JButton loginButton;
    private JButton cancelButton;
    private Font font = new Font("PLAIN", Font.PLAIN, 18);
    private JTextField userNameText;
    private JPasswordField passWordText;
    private JTextField hostText;

    /**
     * Constructor
     */
    public DatabaseInitPanel() {
        super(new GridBagLayout());
        loginPanel();
        setVisible(true);
    }

    /**
     * Creates the login panel
     */
    private void loginPanel() {

        userNameText = new JTextField(CHAR_LENGTH);
        passWordText = new JPasswordField(CHAR_LENGTH);
        hostText = new JTextField(CHAR_LENGTH);
        loginButton = new JButton("LOGIN");
        cancelButton = new JButton("CANCEL");


        GridBagConstraints grid = new GridBagConstraints();

        grid.fill = GridBagConstraints.HORIZONTAL;

        //host
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

        //userName
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

        //password
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
