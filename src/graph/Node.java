package graph;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import driver.DataPoint;

public class Node {

	// initialize parameters
	private DataPoint dataPoint;
	private Point2D.Double location;
	private Color color = Color.BLUE;
	private int layer = 0;
	private double error = 0;

	/**
	 * Create a new node representing the specified datapoint.
	 * 
	 * @param dataPoint	The datapoint to be represented by the node.
	 */
	public Node(DataPoint dataPoint) {
		this.dataPoint = dataPoint;
		location = new Point2D.Double(0, 0);
	}

	/**
	 * Create a new node representing the specified 
	 * datapoint at a specific location in virtual space.
	 * 
	 * @param dataPoint	The datapoint to be represented by the node.
	 * @param location	The 2D location in virtual space.
	 */
	public Node(DataPoint dataPoint, Point2D.Double location) {
		this.dataPoint = dataPoint;
		this.location = location;
	}

	/**
	 * @return	The datapoint that the node represents.
	 */
	public DataPoint getDataPoint() {
		return dataPoint;
	}

	/**
	 * @param dataPoint	The datapoint that the node represents.
	 */
	public void setDataPoint(DataPoint dataPoint) {
		this.dataPoint = dataPoint;
	}
	
	/**
	 * @return	The location of the node in terms of the datapoint's features.
	 */
	public List<Double> getFeatureLocation() {
		return this.dataPoint.getFeatures();
	}

	/**
	 * @return	The location of the node in virtual 2D space.
	 */
	public Point2D.Double getLocation() {
		return location;
	}
	
	/**
	 * @return	The virtual location of the node in list form.
	 */
	public List<Double> getLocationVector() {
		List<Double> vector = new ArrayList<Double>(2);
		vector.add(location.x);
		vector.add(location.y);
		return vector;
	}

	/**
	 * @param location	The location of the node in virtual 2D space.
	 */
	public void setLocation(Point2D.Double location) {
		this.location = location;
	}
	
	/**
	 * @param color	The color to display the node as in the visualizer.
	 */
	public void setColor(Color color) {
		double alpha = getAlpha();
		this.color = color;
		setAlpha(alpha);
	}
	
	/**
	 * @return	The color that the node is displayed with in the visualizer.
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * @param alpha	The transparency of the node in the visualizer.
	 */
	public void setAlpha(double alpha) {
		color = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (alpha * 255));
	}
	
	/**
	 * @return	The transparency of the node in the visualizer.
	 */
	public double getAlpha() {
		return (double)Math.round(((double)color.getAlpha()/255) * 100) / 100;
	}
	
	/**
	 * @param error	The error associated with the node by a clustering algorithm.
	 */
	public void setError(double error) {
		this.error = error;
	}
	
	/**
	 * @return	The error associated with the node by a clustering algorithm.
	 */
	public double getError() {
		return error;
	}

}
