package ui_graphlib;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.math.*;
import system_utils.DataStore;
import system_utils.EquationPlot;
import ui_framework.Refreshable;
import ui_stdlib.SystemThemes;
import system_utils.CorrelationInfo;;

@SuppressWarnings("serial")
public class CorrelationGraph implements Refreshable {
	//extends SystemPanel 
	private DataStore data_store;
	private GraphPanel graph;
	private CorrelationInfo data_to_plot;
	private HashMap<String, PointSet> data_sets;
	private EquationPlot eqn;

	private Point line_min;
	private Point line_max;
	
	private double x_ratio;
	private double y_ratio;
	
	private int draw_width;
	private int draw_height;

	private double bottom_buffer_x;
	private double bottom_buffer_y;
	
	public CorrelationGraph() {
		super();
	}
	
	private void set_line_endpoints() {
		double x = 0;
		double y = for_y(0);
		if (y < 0) {
			y = 0;
			x = for_x(0);
		}
		line_min = new Point(x, y);
		
		x = draw_width;
		y = for_y(x);
		if (y > draw_height) {
			y = draw_height;
			x = for_x(y);
		}
		line_max = new Point(x, y);
		
		graph.set_endpoints(line_min, line_max);
		
	}
	
	private double for_y(double x) {
		// This applies the algo to place points to the entire equation of the correlation model
		return (eqn.get_y(x) - bottom_buffer_x)*(x_ratio/y_ratio) + bottom_buffer_y; 
	}
	
	private double for_x(double y) {
		return eqn.get_linear_x((y - bottom_buffer_y) * (y_ratio / x_ratio) + bottom_buffer_x);
	}
	
	@Override
	public void refresh() {
		//data_sets = data_store.get_correlation_info();
		this.eqn = data_to_plot.get_equation();
		//ArrayList<PointSet> point_sets = new ArrayList<PointSet>()
		//point_sets.add(data_sets.get("standards"));
		//point_sets.add(data_sets.get("unknowns"));
		//this.graph.set_point_sets(point_sets);
		this.graph.refresh();
		set_vals();
		this.graph.refresh();
	}
	
	private void set_vals() {
		this.draw_width = graph.get_width();
		this.draw_height = graph.get_height();
		this.x_ratio = graph.get_x_r();
		this.y_ratio = graph.get_y_r();
		set_line_endpoints();
	}

	public void toggle_unknowns() {
		data_sets.get("unknowns").toggle_render();
		refresh();
	}
	
	@Override
	public void set_datastore(DataStore datastore) {
		// TODO Auto-generated method stub
		this.data_store = datastore;
	}

	@Override
	public void add_refreshable(Refreshable refreshable_window) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on_start() {
		this.graph = new GraphPanel(450, 250);
		this.refresh();
	}

}