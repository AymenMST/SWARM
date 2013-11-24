package graph;

import java.awt.Color;
import java.awt.geom.Point2D;

import driver.DataPoint;

public class Node {

	private DataPoint dataPoint;
	private Point2D.Double location;
	private Color color = Color.RED;
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

	public Point2D.Double getLocation() {
		return location;
	}

	public void setLocation(Point2D.Double location) {
		this.location = location;
	}
	
	public void setColor(Color color) {
		this.color = color;
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
	
	public double getAlpha() {
		return (double) color.TRANSLUCENT / 255;
	}

}
