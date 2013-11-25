package aco;

import graph.Node;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import distance.Distance;
import distance.Euclidean;

public class Ant {
	
	private Point2D.Double location;
	double lastMoveX = 0;
	double lastMoveY = 0;
	Node holding = null;
	Color colorEmpty = Color.CYAN;
	Color colorHolding = Color.BLUE;
	Color color = colorEmpty;
	Distance distance = new Euclidean();
	
	public Ant(Point2D.Double location) {
		this.location = location;
	}
	
	public Node toNode() {
		return new Node(null, location);
	}
	
	public double getX() {
		return location.x;
	}
	
	public double getY() {
		return location.y;
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
	
	public void move(double x, double y) {
		lastMoveX = x;
		lastMoveY = y;
		location.x += x;
		location.y += y;
	}
	
	public void act(double density, Node nearest) {
		pickup(density, nearest);
	}
	
	private void pickup(double density, Node nearest) {
		// with some probability, pick up
		double dist = distance.distance(nearest.getLocationVector(), this.getLocationVector());
		if (dist < 20) {
			holding = nearest;
			color = colorHolding;
		}
	}
	
	private void drop(double density) {
		// with some probability, drop
		holding = null;
		color = colorEmpty;
	}
	
	public boolean isHolding() {
		if (holding == null)
			return false;
		else
			return true;
	}
	
	public Node getHolding() {
		return holding;
	}
	
	public double getLastMoveX() {
		return lastMoveX;
	}
	
	public double getLastMoveY() {
		return lastMoveY;
	}
	
	public Color getColor() {
		return color;
	}

}
