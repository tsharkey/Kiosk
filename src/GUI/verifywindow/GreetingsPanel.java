package GUI.verifywindow;

import javax.swing.*;

/**
 *
 * @author Jacob
 */

@SuppressWarnings("serial")
public class GreetingsPanel extends JPanel {
	public GreetingsPanel() {
		JLabel greetings = new JLabel("Please enter a user name and password");
		add(greetings);

	}
}
