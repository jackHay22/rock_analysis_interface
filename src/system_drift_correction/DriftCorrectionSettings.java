package system_drift_correction;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import system_utils.Element;
import ui_framework.Refreshable;
import ui_stdlib.SystemThemes;
import ui_stdlib.components.PanelHeader;

@SuppressWarnings("serial")
public class DriftCorrectionSettings extends ui_framework.SystemPanel<DriftCorrectionDS> implements Refreshable<DriftCorrectionDS> {
	private GridBagConstraints constraints;
	private JComboBox<Element> element_selection;
	private JComboBox<Integer> degree_selection;
	private int max_degree = 10;
	private int default_degree = 3;
	private int current_elem_index;
	private int total_elems;
	private JButton next_element;
	private JButton prev_element;
	private int static_button_width;
	
	private PanelHeader<DriftCorrectionDS> panel_header;
	private PanelHeader<DriftCorrectionDS> element_label;
	private PanelHeader<DriftCorrectionDS> degree_label;
	
	private PanelHeader<DriftCorrectionDS> eqn_label; //TODO: rename
	private PanelHeader<DriftCorrectionDS> rsqrd_label; //TODO: rename
	
	private boolean backend_loaded;
	private DriftCorrectionDS datastore;
	
	
	public DriftCorrectionSettings() {
		super();
		backend_loaded = false;
		setLayout(new GridBagLayout());
		constraints = SystemThemes.get_grid_constraints();
		
		element_selection = new JComboBox<Element>(Element.values());
		
		degree_selection = new JComboBox<Integer>();
		current_elem_index = 0;
		total_elems = SystemThemes.TOTAL_ELEMENTS;
		
		next_element = new JButton("Next Element");
		prev_element = new JButton("Previous Element");
		prev_element.setEnabled(false);
		
		panel_header = new PanelHeader<DriftCorrectionDS>("Drift Correction", SystemThemes.MAIN);
		element_label = new PanelHeader<DriftCorrectionDS>("Current Element: ", SystemThemes.MAIN, SystemThemes.INSET);
		degree_label = new PanelHeader<DriftCorrectionDS>("Degree: ", SystemThemes.MAIN, SystemThemes.INSET);
	
		eqn_label = new PanelHeader<DriftCorrectionDS>(SystemThemes.superscript("---"), SystemThemes.MAIN);
		eqn_label.set_font_size(SystemThemes.LARGE_TEXT_FONT_SIZE);
		
		rsqrd_label = new PanelHeader<DriftCorrectionDS>("---", SystemThemes.MAIN);
		rsqrd_label.set_font_size(SystemThemes.LARGE_TEXT_FONT_SIZE);
		
		static_button_width = next_element.getPreferredSize().width;
		
		element_selection.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
				current_elem_index = element_selection.getSelectedIndex();
				update_button_state();
				if (backend_loaded) {
					datastore.set_element((Element)element_selection.getSelectedItem());
					datastore.notify_update();
				}
		    }
		});
		
		degree_selection.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
				if (backend_loaded) {
					datastore.set_degree((int)degree_selection.getSelectedItem());
					datastore.notify_update();
				}
		    }
		});
	}

	@Override
	public void refresh() {
		if (backend_loaded) {
			eqn_label.set_text(datastore.get_eqn());
			rsqrd_label.set_text(datastore.get_rsqrd());
		}
	}
	
	private void get_next_element() {
		if (current_elem_index + 1 < total_elems) {
			current_elem_index++;
			element_selection.setSelectedIndex(current_elem_index);
		}
		update_button_state();
	}
	
	private void update_button_state() {
		//update datastore
		datastore.set_element((Element)element_selection.getSelectedItem());
		datastore.notify_update();
		
		if (current_elem_index == total_elems - 1) {
			next_element.setEnabled(false);
		} else {
			next_element.setEnabled(true);
		}
		if (current_elem_index == 0) {
			prev_element.setEnabled(false);
		} else {
			prev_element.setEnabled(true);
		}
	}
	
	private void get_prev_element() {
		if (current_elem_index - 1 >= 0) {
			current_elem_index--;
			element_selection.setSelectedIndex(current_elem_index);
		}
		
		update_button_state();
	}

	@Override
	public void set_datastore(DriftCorrectionDS datastore) {
		this.datastore = datastore;
		backend_loaded = true;
	}

	@Override
	public void add_refreshable(Refreshable<DriftCorrectionDS> refreshable_component) {

	}

	@Override
	public void on_start() {

		panel_header.on_start();
		
		//constraints.gridy = 0;
		//enforce side column widths:
		//constraints.gridwidth = 1;
		//constraints.gridx = 0;
		//add(javax.swing.Box.createHorizontalStrut(static_button_width), constraints);
		//constraints.gridx = 3;
		//add(javax.swing.Box.createHorizontalStrut(static_button_width), constraints);
		
		constraints.gridwidth = 4;
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.ipady = SystemThemes.HEADER_PADDING;
		
		//add panel title
		add(panel_header, constraints);
		
		element_label.on_start();
		element_selection.setSelectedIndex(current_elem_index);
		
		//add element selection dropdown to label
		element_label.add(element_selection);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.weighty = 0;
		constraints.weightx = 0.5;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		
		//add element panel to main panel
		add(element_label, constraints);
		
		for (int i = 0; i < max_degree; i++) {
			degree_selection.addItem((Integer) i + 1);
		}
		degree_selection.setSelectedIndex(default_degree);
			
		
		degree_label.on_start();
		degree_label.add(degree_selection);
		
		constraints.gridx = 2;
		constraints.weightx = 0.5;
		constraints.gridwidth = 2;
		constraints.weighty = 0.05;
		
		//add degree label
		add(degree_label, constraints);
		
		prev_element.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
				get_prev_element();
		    }
		});
		
		constraints.gridx = 0;
		constraints.gridwidth = 4;
		constraints.gridy++;
		constraints.weighty = 0;
		constraints.weighty = 0.05;
		
		//add spanning label
		add(SystemThemes.get_horiz_scrollable_panel(eqn_label), constraints);
		constraints.weighty = 0.9;
		
		//add second spanning label
		constraints.gridy++;
		add(rsqrd_label, constraints);
		
		constraints.gridy++;
		constraints.gridx = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		
		add(prev_element, constraints);
		
		constraints.weightx = 1;
		constraints.gridx = 1;
		constraints.gridwidth = 2;
		
		next_element.addActionListener(new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
				get_next_element();
		    }
		});
		
		constraints.gridx = 3;
		constraints.weightx = 0;
		constraints.gridwidth = 1;
		add(next_element, constraints);
		
		setVisible(true);
	}
}
