package GUI.verifywindow;

import javax.swing.*;

/**
 *
 * @author Jacob
 */

@SuppressWarnings("serial")
public class SubmitPanel extends JPanel {
	public JButton submit, cancel;

	public SubmitPanel() {
		buildPanel();
	}

	public void buildPanel() {
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		add(submit);
		add(cancel);
	}
}