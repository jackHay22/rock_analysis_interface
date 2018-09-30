package system_utils;

import java.util.HashMap; 
import java.util.ArrayList;

public class ElementCorrelationInfo {
	private Element element;
	private HashMap<Element, CorrelationInfo> all_correlations;
	private ArrayList<CorrelationInfo> selected_elements;
	
	public ElementCorrelationInfo(Element element, HashMap<Element, CorrelationInfo> all_correlations) {
		this.element = element;
		this.all_correlations = all_correlations;
		this.selected_elements = new ArrayList<CorrelationInfo>();
	}
	
	public Element get_element() {
		return this.element;
	}
	
	public double get_r2(Element element) {
		return all_correlations.get(element).get_r2();
	}
	
	public CorrelationInfo get_corr(Element y) {
		return this.all_correlations.get(y);
	}
	
	public HashMap<Element, CorrelationInfo> get_all_corr() {
		return this.all_correlations;
	}
	
	public void add_selected(Element secondary) {
		CorrelationInfo corr = this.all_correlations.get(secondary);
		corr.toggle();
		this.selected_elements.add(corr);
	}
	
	public void remove_selected(Element secondary) {
		CorrelationInfo corr = this.all_correlations.get(secondary);
		corr.toggle();
		this.selected_elements.remove(corr);
	}
	
	public boolean is_selected(Element secondary) {
		CorrelationInfo corr = this.all_correlations.get(secondary);
		return corr.in_use();
	}
	
	// More to come
	
}