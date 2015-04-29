package disabilitykiosk;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Tom Sharkey on 4/29/15.
 *
 * Will allow the text to be displayed from the password fields, unitl it loses focus.
 * Once focus is lost then all the characters are hidden. If the field regains focus the user will have
 * to re-enter the information as it will wipe all data in the field.
 */
public class PasswordUtil implements FocusListener{
    private Boolean bool;
    private JPasswordField jpf;

    public PasswordUtil(JPasswordField jpf){
        this.jpf = jpf;
        bool = false;
    }

    @Override
    //sets characters to be dsiplayed
    public void focusGained(FocusEvent e){
        if(bool == false){
            jpf.setEchoChar((char)0);
            System.out.println("has focus");
        }

        //if the user goes back to the field clear it and set displayed chars back to display
        if(jpf.getPassword().length >= 0){
            bool = false;
            jpf.setEchoChar((char)0);
            jpf.setText("");
        }
    }

    @Override
    //sets the characters to '*' upon lost focus
    public void focusLost(FocusEvent e){
        jpf.setEchoChar('*');
        bool = true;
    }
}
