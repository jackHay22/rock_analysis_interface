package ui_stdlib.dialogwindows;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import system_utils.DataStore;
import system_utils.io_tools.SystemFileDialog;
import ui_framework.SystemWindow;
import ui_framework.ScheduledState;

public class OpenDialog implements ScheduledState<DataStore> {
	private SystemWindow<DataStore> main_window;
	private SystemFileDialog<DataStore> file_dialog;
	
	public OpenDialog(String title, SystemWindow<DataStore> main_window) {
		this.main_window = main_window;
		file_dialog = new SystemFileDialog<DataStore>(main_window, "Open...", "ds");
	}
	
	private boolean is_datastore_file(String file) {
		int pos = file.indexOf('.');
		if (pos < 0) {
			return false;
		}
		
		// Verify that the selected file is a DataStore file with '.ds' extension
		String extension = file.substring(pos + 1);
		if (extension.equals("ds")) {
			return true;
		}
		return false;
	}

	@Override
	public void on_scheduled(DataStore backend) {
		
		boolean status = file_dialog.init_backend_on_path(backend);
		
		String file_path = backend.get_path();
		
		if (status && is_datastore_file(file_path)) {
			try {
				
				// Create object from path
				FileInputStream fileInputStream = new FileInputStream(file_path);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
				
				// Cast the objectInputStream as a DataStore object that will be used in the system
				DataStore ds = (DataStore) objectInputStream.readObject();
				objectInputStream.close();
				
				// Assign applications' window to the DataStore object
				ds.set_window(main_window);
				
				main_window.on_scheduled(ds);
				
			} catch (IOException | ClassNotFoundException e) {
				new ErrorDialog<DataStore>("Loading Error", "Unable to load selected file. Please check that the "
											   + "selected file is a valid .ds file "
											   + "or that you have selected the right file.").show_dialog();
			}
		}
	}

}
