package ui_stdlib;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;

public class SystemThemes {
	public static final Color BACKGROUND = new Color(39, 47, 50);
	public static final Color MAIN = new Color(157, 189, 198);
	public static final Color WHITE = new Color(255, 255, 255);
	public static final Color HIGHLIGHT = new Color(255, 61, 46);
	public static final Color HIGHLIGHT2 = new Color(255, 176, 59);
	public static final Color HIGHLIGHT3 = new Color(202, 41, 62);
	
	private static final int INSET = 5;
	public static final int HEADER_PADDING = 5;
	
	public static final String COPYRIGHT = "© 2018 Ben Parfitt, Jack Hay, and Oliver Keh";
	
	public static final int DIALOG_WINDOW_WIDTH = 500;
	public static final int DIALOG_WINDOW_HEIGHT = 350;
	
	public static final int MAIN_WINDOW_WIDTH = 1200;
	public static final int MAIN_WINDOW_HEIGHT = 750;
	public static KeyStroke SAVE_BINDING = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
	
	public static GridBagConstraints get_grid_constraints() {
		GridBagConstraints constraints =  new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1.0;
		constraints.insets = new Insets(INSET, INSET, INSET, INSET);
		return constraints;
	}
}
