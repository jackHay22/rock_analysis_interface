package ui_stdlib.views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import system_utils.DataStore;
import ui_framework.Refreshable;
import ui_stdlib.SystemThemes;
import ui_stdlib.components.SingleViewBar;
import ui_stdlib.components.SingleViewPanel;

@SuppressWarnings("serial")
public class StdsListElement extends SingleViewBar {
	private SingleViewPanel weighted_mean;
	private SingleViewPanel actual;
	private DataStore datastore;
	private String standard;
	private GridBagConstraints constraints;
	
	public StdsListElement(String standard) {
		super();
		setLayout(new GridBagLayout());
		
		this.standard = standard;
	}

	@Override
	public void refresh() {
		
	}

	@Override
	public void set_datastore(DataStore datastore) {
		this.datastore = datastore;
	}

	@Override
	public void add_refreshable(Refreshable refreshable_component) {
		
	}

	@Override
	public void on_start() {
		constraints = SystemThemes.get_grid_constraints();
		
		double wm_val = 0;
		double actual_val = 0;
		weighted_mean = new SingleViewPanel(SystemThemes.get_display_number(wm_val), 
											SystemThemes.HIGHLIGHT, SystemThemes.BACKGROUND);
		
		actual = new SingleViewPanel(SystemThemes.get_display_number(actual_val), 
									 SystemThemes.HIGHLIGHT, SystemThemes.BACKGROUND);	
		
		add_single_view(new SingleViewPanel("Placeholder", SystemThemes.MAIN, SystemThemes.BACKGROUND));
		add_single_view(new SingleViewPanel("Placeholder", SystemThemes.MAIN, SystemThemes.BACKGROUND));
		add_single_view(new SingleViewPanel("Placeholder", SystemThemes.MAIN, SystemThemes.BACKGROUND));
		
		constraints.weightx = 0;
//		constraints.ipadx = SystemThemes.HEADER_PADDING;
		JLabel standards = new JLabel(standard);
		standards.setPreferredSize(new Dimension(70, 10));
		add(standards, constraints);
		
		constraints.ipadx = 0;
		constraints.weightx = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		show_views();
		constraints.weightx = 0;
		constraints.fill = 0;
		
		actual.on_start();
		weighted_mean.on_start();
		
		add(weighted_mean, constraints);
		add(actual, constraints);
		
	}

}
