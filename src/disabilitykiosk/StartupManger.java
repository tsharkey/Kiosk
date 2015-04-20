package disabilitykiosk;

import GUI.loginwindow.DatabaseInitFrame;

/**
 *
 * @author Eric Sullivan
 */

public class StartupManger {
	public static boolean initalizeDB = false;

	public StartupManger() {
		
		System.setProperty("freetts.voices", 
		        "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

		@SuppressWarnings("unused")
		DatabaseInitFrame dbcf = new DatabaseInitFrame();
	}

	public static void main(String[] args) {
		
		if (args.length != 0) {
			if (args[0].equals("initalizeDatabase")) {
				initalizeDB = true;
			}
		}
		new StartupManger();
	}

}