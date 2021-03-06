package system_utils;

import java.util.HashMap;
import java.io.Serializable;
import java.util.ArrayList;

public class DataTable implements Serializable {
	private static final long serialVersionUID = 2;
	
	// DataTable holds both doubles and strings, needed to have one data structure to support this
	private HashMap<TableKey, Data> data;
	private HashMap<TableKey, ArrayList<String>> string_data;
	private String table_name;
	
	public DataTable(String table_name) {
		this.table_name = table_name;
		this.data = new HashMap<TableKey, Data>();
		this.string_data = new HashMap<TableKey, ArrayList<String>>();
	}
	
	public void put_data(TableKey key, ArrayList<Double> a) {
		Data entry = new Data(a);
		this.data.put(key, entry);
	}
	
	public void put_info(TableKey key, ArrayList<String> a) {
		this.string_data.put(key, a);
	}
	
	public Data get_data(TableKey key) {
		return this.data.get(key);
	}
	
	public Data get_data(Element elem) {
		for (TableKey key : this.data.keySet()) {
			if (key.get_val() != null && key.get_val().equals(elem)) {
				return this.data.get(key);
			}
		}
		
		return null;
	}
	
	public ArrayList<String> get_info(TableKey key) {
		return this.string_data.get(key);
	}

	public boolean contains_data(TableKey key) {
		return this.data.containsKey(key);
	}
	
	public HashMap<TableKey, Data> get_data() {
		return this.data;
	}
	
	public String name() {
		return table_name;
	}
	
}

