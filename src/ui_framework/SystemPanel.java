package ui_framework;

import java.awt.Dimension;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class SystemPanel<Backend extends DataBackend> extends JPanel implements Refreshable<Backend> {
	public SystemPanel() {
		super();
	}

	public void set_minimum_dimension(int width, int height) {
		super.setMinimumSize(new Dimension(width, height));
	}
}
