package ui_stdlib.views;

import system_utils.DataStore;
import ui_framework.Refreshable;
import ui_stdlib.components.ListingSet;

@SuppressWarnings("serial")
public class CalculatedValuesPanel extends ui_framework.SystemPanel {
	private ListingSet<CalculationListElement> calculated_elements_list;
	private DataStore datastore;
	
	public CalculatedValuesPanel() {
		super();
		calculated_elements_list = new ListingSet<CalculationListElement>(CalculationListElement.class);
	}
	
	@Override
	public void refresh() {
		calculated_elements_list.refresh();
	}

	@Override
	public void set_datastore(DataStore datastore) {
		this.datastore = datastore;
		
	}

	@Override
	public void add_refreshable(Refreshable refreshable_component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on_start() {
		// TODO Auto-generated method stub
		
	}

}
