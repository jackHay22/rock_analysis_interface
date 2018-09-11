package system_utils;

public class DataStore {
	private ui_framework.SystemWindow window_parent;
	public DataStore() {
		
	}
	public void add_update_notify(ui_framework.SystemWindow window_parent) {
		this.window_parent = window_parent;
	}
	public void notify_update() {
		//on changes to data
		this.window_parent.refresh();
	}
}
