package graph;

import java.awt.geom.Point2D;

import driver.DataPoint;

public class Node {

	private DataPoint dataPoint;
	private Point2D.Double location;

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

}
