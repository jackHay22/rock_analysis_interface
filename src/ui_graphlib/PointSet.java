package ui_graphlib;

import java.util.ArrayList;

import system_utils.DataStore;

import java.awt.Color;
import ui_framework.Refreshable;

public class PointSet implements Refreshable {
	
	private ArrayList<Point> points;
	
	private double min_x;
	private double max_x;
	private double min_y;
	private double max_y;
	
	private String x_axis;
	private String y_axis;
	private String title;
	
	private Color color;
	
	public PointSet(ArrayList<Point> points, Color color, String x_axis, String y_axis, String title_label) {
		this.points = points;
		this.color = color;
		this.x_axis = x_axis;
		this.y_axis = y_axis;
		this.title = title_label;
		
		refresh();
	}
	
	private void edge_values() {
		// Finds the smallest and largest x and y values to be used to create an
		// appropriate scale for plotting the points
		int min_x_index = 0;
		int max_x_index = 0;
		int min_y_index = 0;
		int max_y_index = 0;
		
		for(int i = 0; i < points.size(); i++) {
			if (points.get(min_x_index).get_x() > points.get(i).get_x()) {
				min_x_index = i;
			}
			if (points.get(max_x_index).get_x() < points.get(i).get_x()) {
				max_x_index = i;
			}
			if (points.get(min_y_index).get_y() > points.get(i).get_y()) {
				min_y_index = i;
			}
			if (points.get(max_y_index).get_y() < points.get(i).get_y()) {
				max_y_index = i;
			}
		}
		min_x = points.get(min_x_index).get_x();
		max_x = points.get(max_x_index).get_x();
		min_y = points.get(min_y_index).get_y();
		max_y = points.get(max_y_index).get_y();
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
		edge_values();
		
	}

	@Override
	public void set_datastore(DataStore datastore) {
		// TODO Auto-generated method stub
		
	}
	
}