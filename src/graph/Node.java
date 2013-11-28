package graph;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import driver.DataPoint;

public class Node {

	private DataPoint dataPoint;
	private Point2D.Double location;
	private Color color = Color.BLUE;
	private int layer = 0;

	public Node(DataPoint dataPoint) {
		this.dataPoint = dataPoint;
		location = new Point2D.Double(0, 0);
	}

	public Node(DataPoint dataPoint, Point2D.Double location) {
		this.dataPoint = dataPoint;
		this.location = location;
	}

	public DataPoint getDataPoint() {
		return dataPoint;
	}

	public void setDataPoint(DataPoint dataPoint) {
		this.dataPoint = dataPoint;
	}
	
	public List<Double> getFeatureLocation() {
		return this.dataPoint.getFeatures();
	}

	public Point2D.Double getLocation() {
		return location;
	}
	
	public List<Double> getLocationVector() {
		List<Double> vector = new ArrayList<Double>(2);
		vector.add(location.x);
		vector.add(location.y);
		return vector;
	}

	public void setLocation(Point2D.Double location) {
		this.location = location;
	}
	
	public void setColor(Color color) {
		double alpha = getAlpha();
		this.color = color;
		setAlpha(alpha);
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setLayer(int layer) {
		this.layer = layer;
	}
	
	public int getLayer() {
		return layer;
	}
	
	public void setAlpha(double alpha) {
		color = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (alpha * 255));
	}
	
	@SuppressWarnings("static-access")
	public double getAlpha() {
		return (double)Math.round(((double)color.getAlpha()/255) * 100) / 100;
	}

}
