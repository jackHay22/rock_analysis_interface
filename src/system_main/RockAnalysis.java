package system_main;

import ui_framework.SystemWindow;
import ui_graphlib.GraphPanel;
import ui_stdlib.SettingsPanel;
import system_utils.DataStore;

public class RockAnalysis {
	public static void main(String[] args) {
		int window_width = 1100;
		int window_height = 700;
		SystemWindow main_window = new SystemWindow("Ablation Analysis", window_width, window_height);
		main_window.set_minimum_size(window_width, window_height);
		
		DataStore loaded_datastore = new DataStore();
		
		SettingsPanel test_settings = new SettingsPanel();
		test_settings.set_datastore(loaded_datastore);
		main_window.add_system_panel(test_settings);
		
		main_window.add_system_panel(new GraphPanel());
		
		SettingsPanel test_settings_two = new SettingsPanel();
		test_settings_two.set_datastore(loaded_datastore);
		main_window.add_system_panel(test_settings_two);
		
		main_window.add_system_panel(new GraphPanel());
		
		main_window.start_window();
	}
}
