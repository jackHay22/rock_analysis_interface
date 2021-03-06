package ui_framework;

import java.awt.SplashScreen;
import javax.swing.UIManager;

public class WindowLoader implements Runnable {
	@Override
	public void run() {
		SplashScreen.getSplashScreen();
		try{
			//attempt to set native look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			if (System.getProperty("os.name").startsWith("Mac")) {
				//move menu bar to native mac bar
				System.setProperty("com.apple.mrj.application.apple.menu.about.name", "WikiTeX");
				System.setProperty("apple.laf.useScreenMenuBar", "true");
			}	
		}
		catch(Exception e){}
		
		//create a starting window from viewbuilder and schedule
		SystemWindow<system_utils.DataStore> starting_window = system_main.ViewBuilder.create_new_default_window();
		starting_window.on_start();
	}
}