package disabilitykiosk;

import GUI.loginwindow.DatabaseInitFrame;

/**
 *
 * @author Eric Sullivan
 */

public class StartupManger {
	public static boolean initalizeDB = false;

	public StartupManger() {

		@SuppressWarnings("unused")
		DatabaseInitFrame dbcf = new DatabaseInitFrame();
	}

	public static void main(String[] args) {
		if (args.length != 0) {
			if (args[0].equals("initDatabase")) {
				initalizeDB = true;
			}
		}
		new StartupManger();
	}

}