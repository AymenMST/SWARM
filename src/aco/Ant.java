package aco;

import graph.Node;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ant {
	
	private Point2D.Double location;
	double lastMoveX = 0;
	double lastMoveY = 0;
	Node holding = null;
	Color colorEmpty = Color.MAGENTA;
	Color colorHolding = Color.RED;
	Color color = colorEmpty;
	Random rand = new Random(11235);
	double pickupGain = 0.05;
	double dropGain = 0.00005;
	double lastError;
	
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
		return new Point2D.Double(location.x, location.y);
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
	
	
	public boolean pickup(List<Node> neighborhood, double density) {
		
//		System.out.println(pickupProbability(density));
		
		if (rand.nextDouble() > pickupProbability(density))
			return false;
		
		double greatestError = 0;
		double runningError = 0;
		
		Node toPickUp = null;
		
		// calculate most dis-similar
		for (Node current : neighborhood) {
			runningError = 0;
			for (Node neighbor : neighborhood){
				if (current != neighbor){
					for (int featureNum = 0; featureNum < current.getDataPoint().getFeatures().size(); featureNum++){
						Double currentFeature = current.getDataPoint().getFeatures().get(featureNum);
						Double neighborFeature = neighbor.getDataPoint().getFeatures().get(featureNum);
						runningError += Math.abs(currentFeature - neighborFeature);
					}
				}
			}
			if (runningError > greatestError){
				greatestError = runningError;
				toPickUp = current;
			}
		}
		
		lastError = greatestError / (neighborhood.size() - 1);
		holding = toPickUp;
		color = colorHolding;
		
		return true;
		
	}
	
	public boolean drop(List<Node> neighborhood, double density) {
		
		if (rand.nextDouble() > dropProbability(density))
			return false;
		
		double runningError = 0;
		for (Node neighbor : neighborhood){
			if (holding != neighbor){
				for (int featureNum = 0; featureNum < holding.getDataPoint().getFeatures().size(); featureNum++){
					Double currentFeature = holding.getDataPoint().getFeatures().get(featureNum);
					Double neighborFeature = neighbor.getDataPoint().getFeatures().get(featureNum);
					runningError += Math.abs(currentFeature - neighborFeature);
				}
			}
		}
		
		double newError = runningError / (neighborhood.size() - 1);
		// if error has been reduced from when the node was picked up, drop node
		if (newError < lastError){
			holding = null;
			color = colorEmpty;
			return true;
		} else {
			lastError *= 1.05;
			return false;
		}
		
		
	}
	
	public double pickupProbability(double density) {
		return Math.pow((pickupGain / (pickupGain + density)), 2);
	}
	
	public double dropProbability(double density) {
		return Math.pow((density / (dropGain + density)), 2);
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
