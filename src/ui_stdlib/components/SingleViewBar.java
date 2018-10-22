package ui_stdlib.components;

import java.util.ArrayList;
import javax.swing.BoxLayout;

@SuppressWarnings("serial")
public abstract class SingleViewBar extends ui_framework.SystemPanel {
	private ArrayList<SingleViewPanel> panels;
	public SingleViewBar() {
		super();
		panels = new ArrayList<SingleViewPanel>();
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
	}
	
	protected void add_single_view(SingleViewPanel panel) {
		panels.add(panel);
	}
	
	protected void clear_views() {
		for (SingleViewPanel p : panels) {
			remove(p);
		}
		panels.clear();
	}
	
	protected void show_views() {
		setVisible(true);
		for (SingleViewPanel p : panels) {
			p.on_start();
			add(p);
		}
	}
}