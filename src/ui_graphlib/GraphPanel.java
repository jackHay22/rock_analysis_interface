package ui_graphlib;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.math.*;
import system_utils.DataStore;
import ui_framework.Refreshable;
import ui_stdlib.SystemThemes;
import ui_stdlib.components.PanelHeader;
import ui_stdlib.components.VerticalPanel;

@SuppressWarnings("serial")
public class GraphPanel extends ui_framework.SystemPanel implements DrawableManager, MouseListener {
	//extends SystemPanel 
	
	private ArrayList<PointSet> point_sets;
	private DrawablePanel points_panel;
	private PanelHeader header_panel;
	private PanelHeader r_sqrd_panel;
	private VerticalPanel y_label;
	private PanelHeader x_label;
	
	private GridBagConstraints constraints;
	private DataStore data_store;
	
	
	private int draw_width;
	private int draw_height;
	private double x_ratio;
	private double y_ratio;
	
	private int min_x = 0;
	private int max_x = 0;
	
	private int min_y = 0;
	private int max_y = 0;

	private double bottom_buffer_x;
	private double top_buffer_x;
	private double bottom_buffer_y;
	private double top_buffer_y;
	
	private Point left_point;
	private Point right_point;
	private boolean do_place_line;
	
	private int buffer_div = 8;
	
	public GraphPanel(int width, int height) {
		super();
		this.points_panel = new DrawablePanel(this, 450, 250);

		setLayout(new GridBagLayout());
		this.constraints = SystemThemes.get_grid_constraints();
		this.header_panel = new PanelHeader("GRAPH TITLE", SystemThemes.MAIN);
		this.r_sqrd_panel = new PanelHeader("R^2 EQUATION HERE", SystemThemes.MAIN);
		this.y_label = new VerticalPanel("y label", SystemThemes.MAIN);
		this.x_label = new PanelHeader("x label", SystemThemes.MAIN);
		
		this.draw_width = width;
		this.draw_height = height;
	}

	public void set_title(String s) {
		header_panel.set_text(s);
	}
	
	public void set_r2_eqn_label(String s) {
		r_sqrd_panel.set_text(s);
	}
	
	public void set_x_label(String s) {
		x_label.set_text(s);
	}
	
	public void set_y_label(String s) {
		y_label.set_text(s);
	}
	
	public double get_bbx() {
		return bottom_buffer_x;
	}
	
	public double get_bby() {
		return bottom_buffer_y;
	}
	
	public double get_x_r() {
		return x_ratio;
	}
	
	public double get_y_r() {
		return y_ratio;
	}
	
	public int get_width() {
		return draw_width;
	}
	
	public int get_height() {
		return draw_height;
	}
	
	public Point get_left_bottom() {
		Point p = new Point(min_x, min_y);
		return p;
	}
	
	public Point get_right_top() {
		Point p = new Point(max_x, max_y);
		return p;
	}
	
	public void set_endpoints(Point start, Point end) {
		left_point = start;
		right_point = end;
		do_place_line = true;
		points_panel.refresh();
	}
	
	private void draw_graph() {
		set_ratio();
	}
	
	public DrawablePanel get_points_panel() {
		return this.points_panel;
	}
	
	private void set_ratio() {

		for (int i = 0; i < point_sets.size(); i++) {
			
			ArrayList<Point> points = point_sets.get(i).get_points();

			if ((i == 0) && points.size() > 0) {
				min_x = (int)Math.floor(points.get(0).get_x());
				max_x = min_x;
				min_y = (int)Math.floor(points.get(0).get_y());
				max_y = min_y;
			}
			
			for (int j = 0; j < points.size(); j++) {
				if (points.get(j).get_x() < min_x) {
					min_x = (int)Math.floor(points.get(j).get_x());
				}
				if (points.get(j).get_y() < min_y) {
					min_y = (int)Math.floor(points.get(j).get_y());
				}
				if (points.get(j).get_x() > max_x) {
					max_x = (int)Math.ceil(points.get(j).get_x());
				}
				if (points.get(j).get_y() > max_y) {
					max_y = (int)Math.ceil(points.get(j).get_y());
				}
			}
		}

		// These are used to create a buffer around the points placed on the graphs
		bottom_buffer_x = bottom_buffer(min_x, max_x);
		top_buffer_x = top_buffer(min_x, max_x);
		bottom_buffer_y = bottom_buffer(min_y, max_y);
		top_buffer_y = top_buffer(min_y, max_y);
		
		x_ratio = draw_width/(top_buffer_x - bottom_buffer_x);
		y_ratio = draw_height/(top_buffer_y - bottom_buffer_y);
		
	}
	
	// The buffer is meant to be 5% on either side, thus / by 20
	private double bottom_buffer(int min, int max) {
		return min - (max - min)/buffer_div;
	}

	private double top_buffer(int min, int max) {
		return max + (max - min)/buffer_div;
	}
	
	private void place_line(Graphics2D g) {
		//System.out.println((int)this.left_point.get_x() + " " + (int)this.left_point.get_y()+ " " +(int)this.right_point.get_x()+ " " +(int)this.right_point.get_y());
		g.drawLine((int)this.left_point.get_x(), (int)this.left_point.get_y(), (int)this.right_point.get_x(), (int)this.right_point.get_y());
	}
	
	private void place_point(Point p, Graphics2D g, Color c) {
		double point_x = p.get_x();
		double draw_x = (point_x - bottom_buffer_x)*x_ratio;
		
		double point_y = p.get_y();
		double draw_y = draw_height - (point_y - bottom_buffer_y)*y_ratio;

		p.set_draw_values((int)draw_x, (int)draw_y);
		
		//TODO: draw x and draw y aren't numbers
		// Place the actual point with coords (draw_x, draw_y)
		Color graph_color = g.getColor();
		g.setColor(SystemThemes.MAIN);
		if (p.in_use()) {
			g.setColor(c);
		}
		g.fillOval((int)draw_x, (int)draw_y, 5,5);
		g.setColor(graph_color);
	}
	
	private void plot_points(Graphics2D g) {
		for (int i = 0; i < point_sets.size(); i++) {
			if (point_sets.get(i).do_render()) {
				Color c = point_sets.get(i).get_color();
				ArrayList<Point> points = point_sets.get(i).get_points();
				for (int point_index = 0; point_index < points.size(); point_index++) {
					place_point(points.get(point_index), g, c);
				}
			}
		}
	}
	
	
	
	private void set_labels(Graphics2D g) {
		
		g.drawString(Integer.toString(min_x), draw_width/10, draw_height*39/40);
		g.drawLine(draw_width/buffer_div, draw_height, draw_width/buffer_div, draw_height-(draw_height*1/40));
		
		g.drawString(Integer.toString(min_y), draw_width*1/40, draw_height*9/10);
		g.drawLine(0, draw_height*7/buffer_div, draw_width/60, draw_height*7/buffer_div);
		
		g.drawString(Integer.toString(max_x), draw_width*7/8, draw_height*39/40);
		g.drawLine(draw_width*7/buffer_div, draw_height, (int) (draw_width*7/buffer_div), draw_height-(draw_height*1/40));
		
		g.drawString(Integer.toString(max_y), draw_width*1/40, draw_height*1/10);
		g.drawLine(0, draw_height*1/buffer_div, draw_width/60, draw_height*1/buffer_div);
	}
	
	private void point_selected(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Point closest = find_closest_point(x,y);
		double distance_to_point = distance(closest.get_draw_x(), closest.get_draw_y(), x, y);
		// If the mouse click was within 4% of the screen diagonal from the point
		
		if (distance_to_point < distance(draw_width, draw_height, 0, 0)*0.06) {
			closest.toggle();
			data_store.notify_update();
		}

	}
	
	private Point find_closest_point(int x, int y) {
		
		// This is only initialized to silence warnings
		Point closest = new Point(0,0);
		
		// This will always be larger than the distance btwn the mouse click and a point
		double distance = draw_width + draw_height;

		// Sort through all the points on the graph and find the closest one
		for (int i = 0; i < point_sets.size(); i++) {
			if (point_sets.get(i).do_render()) {
				ArrayList<Point> points = point_sets.get(i).get_points();
				for (int point_index = 0; point_index < points.size(); point_index++) {
					Point point = points.get(point_index);
					double new_d = distance(point.get_draw_x(), point.get_draw_y(), x, y);

					if (new_d < distance) {
						closest = point;
						distance = new_d;
					}
				}
			}
		}
		return closest;
	}
	
	private double distance(int x_1, int y_1, int x_2, int y_2) {

		return Math.sqrt(Math.pow((x_1 - x_2), 2) + Math.pow((y_1 - y_2), 2));
	}
	
	@Override
	public void refresh() {
		Dimension d = this.points_panel.get_drawable_size();
		this.draw_height = d.height;
		this.draw_width = d.width;
		this.draw_graph();
		this.points_panel.refresh();
		this.revalidate();
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
	public void draw_components(Graphics2D g) {
		if (point_sets != null) {
			plot_points(g);
			if (do_place_line) {
				place_line(g);
			}
			set_labels(g);
		}
	}

	@Override
	public void handle_mouse_event(MouseEvent e) {
		point_selected(e);
	}

	public void set_point_sets(ArrayList<PointSet> pnts) {
		point_sets = pnts;
	}
	
	private void set_constraints() {
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.gridwidth = 2;
		constraints.ipady = SystemThemes.HEADER_PADDING;
		this.add(this.header_panel, constraints);

		constraints.gridy = 1;
		constraints.gridx = 1;
		constraints.gridwidth = 1;
		this.add(this.r_sqrd_panel, constraints);
		
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.weightx = 0.0;
		constraints.weighty = 1.0;
		this.add(this.y_label, constraints);
		
		constraints.gridx = 1;
		constraints.ipady = 0;
		constraints.weightx = 1.0;
		constraints.fill = GridBagConstraints.BOTH;
		this.add(this.points_panel, constraints);
		
		constraints.gridy = 3;
		constraints.weighty = 0.0;
		constraints.ipady = SystemThemes.HEADER_PADDING;
		this.add(this.x_label, constraints);
	}
	
	@Override
	public void on_start() {
		
		set_constraints();
		
		points_panel.on_start();
		header_panel.on_start();
		r_sqrd_panel.on_start();
		y_label.on_start();
		x_label.on_start();
		
		points_panel.addComponentListener(new ComponentAdapter() {
		    @Override
		    public void componentResized(ComponentEvent e) {
		        if (data_store != null) {
		        	data_store.notify_update();
		        }
		    }
		});
		
		points_panel.setVisible(true);
		header_panel.setVisible(true);
		r_sqrd_panel.setVisible(true);
		y_label.setVisible(true);
		x_label.setVisible(true);
		this.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}