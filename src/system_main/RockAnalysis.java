package system_main;

import ui_framework.SystemWindow;
import ui_graphlib.GraphPanel;
import ui_framework.SystemSubPanel;

public class RockAnalysis {

	public static void main(String[] args) {
		int window_width = 1400;
		int window_height = 700;
		SystemWindow test = new SystemWindow("Test", window_width, window_height);
		test.set_minimum_size(window_width, window_height);
		for (int i = 0; i < 4; i++) {
			test.add_subwindow(new SystemSubPanel());
		}
		int ß = 20; 
		test.start_window();
	}

}
