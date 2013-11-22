package graph;

import java.awt.Point;
import java.awt.geom.Point2D;

import driver.DataPoint;

public class Node {

	private DataPoint dataPoint;
	private Point2D location;

	public Node(DataPoint dataPoint) {
		this.dataPoint = dataPoint;
		location = new Point(0, 0);
	}

	public Node(DataPoint dataPoint, Point2D location) {
		this.dataPoint = dataPoint;
		this.location = location;
	}

	public DataPoint getDataPoint() {
		return dataPoint;
	}

	public void setDataPoint(DataPoint dataPoint) {
		this.dataPoint = dataPoint;
	}

	public Point2D getLocation() {
		return location;
	}

	public void setLocation(Point2D location) {
		this.location = location;
	}

}
