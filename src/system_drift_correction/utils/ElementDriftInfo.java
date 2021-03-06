package system_drift_correction.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import system_drift_correction.DriftCorrectionDS;
import system_formulas.Formulas;
import system_utils.Element;
import system_utils.EquationPlot;
import ui_framework.Refreshable;
import ui_graphlib.Point;
import ui_graphlib.PointSet;
import ui_stdlib.SystemThemes;

public class ElementDriftInfo implements Refreshable<DriftCorrectionDS> {
	
	// This is the element in question
	private Element element;
	// Holds the equation fit to the drift points
	private EquationPlot equation;
	// This holds the drift points from the raw data
	private PointSet<DriftCorrectionDS> points_to_plot;
	// Our databackend, giving us access to important information (such as the degree)
	private DriftCorrectionDS datastore;
	// The degree of the requested fit equation
	private Integer degree_for_fit;
	
	// We set things up by normalizing the drift info, and creating the points_to_plot
	// pointset with that data
	public ElementDriftInfo(PointSet<DriftCorrectionDS> points_data, Element element) {
		
		this.element = element;
		
		ArrayList<Double> time_vals = points_data.get_x_vals();
		ArrayList<Double> drift_CPS_vals = points_data.get_y_vals();
		
		// Choose a value out of the middle of the data set to use to normalize the data
		Double middle = drift_CPS_vals.get((int)drift_CPS_vals.size()/2);
		
		ArrayList<Double> normed_CPS_vals = Formulas.normalize(drift_CPS_vals, middle);
		ArrayList<Point> points = new ArrayList<Point>();
		
		for (int i = 0; i < time_vals.size(); i++) {
			
			points.add(new Point(time_vals.get(i), normed_CPS_vals.get(i)));
		}
		
		points_to_plot = new PointSet<DriftCorrectionDS>(points, SystemThemes.HIGHLIGHT, "Time", "counts", "", true);
		
	}
	
	// Used to give databacked access to pertinent information regarding the drift
	// and allow the equation to be displayed
	public EquationPlot get_equation() {
		return this.equation;
	}
	
	// Returns the element
	public Element get_element() {
		return element;
	}
	
	// Gives the point set that gets plotted on the graph
	public ArrayList<PointSet<DriftCorrectionDS>> get_point_sets() {
		ArrayList<PointSet<DriftCorrectionDS>> sets = new ArrayList<PointSet<DriftCorrectionDS>>();
		sets.add(this.points_to_plot);
		return sets;
	}
	
	// Sets this.equation to be a curve fit to the drift points for this element
	private void create_fit() {
		// uses the apache library to fit a polynomial equation to the set of points
		PolynomialCurveFitter reg_object = PolynomialCurveFitter.create(this.degree_for_fit);
		ArrayList<Point> points = this.points_to_plot.get_points();
		WeightedObservedPoints new_points = new WeightedObservedPoints();
		for (int i = 0; i < points.size(); i++) {
			Point point = points.get(i);
			if (point.in_use()) {
				double x = point.get_x();
				double y = point.get_y();
				new_points.add(x, y);
			}
		}
		
		// Get relevant info from the regression object
		double[] coefficients = reg_object.fit(new_points.toList());
		
		double r_2 = get_r_square(coefficients, this.points_to_plot);
		
		this.equation = new EquationPlot(r_2, this.degree_for_fit, coefficients);
	}

	// Returns the r^2 value of the fit equation
	private Double get_r_square(double[] coefficients, PointSet<DriftCorrectionDS> points_data) {

		// This gets the x and y values in sep lists
		
		EquationPlot temp_eqn = new EquationPlot(0.0, degree_for_fit, coefficients);
		
		ArrayList<Point> points = this.points_to_plot.get_points();
		ArrayList<Double> time_vals = new ArrayList<Double>();
		ArrayList<Double> drift_CPS_vals = new ArrayList<Double>();
		for (int i = 0; i < points.size(); i++) {
			Point point = points.get(i);
			if (point.in_use()) {
				time_vals.add(point.get_x());
				drift_CPS_vals.add(point.get_y());
			}
		}
		
		ArrayList<Double> y_hat = new ArrayList<Double>();
		for (Double x : time_vals) {
			y_hat.add(temp_eqn.get_y(x));
		}
		Double y_bar = Formulas.mean_of_array(drift_CPS_vals);
		Double ssreg = Formulas.sum_mean_diff_squared(y_hat, y_bar);
		
		Double sstot = Formulas.sum_mean_diff_squared(drift_CPS_vals);
		
		return ssreg/sstot;
	}
	
	// This divides the data points in point_set by f(x) 
	public void apply_correction(PointSet<DriftCorrectionDS> point_set) {
		
		ArrayList<Point> points = point_set.get_points();
		ArrayList<Point> new_points = new ArrayList<Point>();
		
		for (Point pt : points) {
			new_points.add(apply_correction_point(pt));
		}
		
		point_set.set_points(new_points);
		
	}
	
	// Applies the correction equation to a single point
	private Point apply_correction_point(Point point) {
		Double x = point.get_x();
		Double y = point.get_y();
		Point pt = new Point(x, y/this.equation.get_y(x));
		return pt;
	}
	
	// Corrects all of the samples for a given element
	public void correct_map(HashMap<String, PointSet<DriftCorrectionDS>> sample_map) {
		for (String s : this.datastore.get_sample_list()) {
			PointSet<DriftCorrectionDS> set = sample_map.get(s);
			if (set == null) {
				continue;
			}
			this.apply_correction(set);
		}
	}
	
	@Override
	// In this refresh, we recreate our fit equation using the degree from
	// our databackend, and the points from the graph. This allows us to 
	// exclude points which were toggled via clicking on the graph. 
	public void refresh() {
		this.degree_for_fit = datastore.get_degree();
		
		points_to_plot.refresh();
		
		create_fit();
	}

	@Override
	public void set_datastore(DriftCorrectionDS datastore) {
		// TODO Auto-generated method stub
		this.datastore = datastore;
	}

	@Override
	public void add_refreshable(Refreshable<DriftCorrectionDS> refreshable_component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void on_start() {
		// TODO Auto-generated method stub
		
	}
}