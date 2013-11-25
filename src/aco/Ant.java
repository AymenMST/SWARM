package aco;

import graph.Node;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Point2d;

public class Ant {
	
	private Point2D.Double location;
	double lastMoveX = 0;
	double lastMoveY = 0;
	Node holding = null;
	Color colorEmpty = Color.CYAN;
	Color colorHolding = Color.BLUE;
	Color color = colorEmpty;
	Random rand = new Random(11235);
	double pickupProbability = 0.5;
	
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
	
	
	public boolean pickup(List<Node> neighborhood) {
		
		boolean didPickUp = false;
		double greatestError = 0;
		double runningError = 0;
		double avgError = 0;
		
		Node toPickUp = null;
		
		// with some probability, pick up
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
			avgError += runningError;
		}

		avgError /= (neighborhood.size());
		
	
		double probability = (avgError / greatestError);
		
		System.out.println(("Neighborhood size: " + neighborhood.size() + " Probability: " + probability));
		//TODO remove this threshold, instead choose a value probabilistically.
		if (probability > pickupProbability) {
			holding = toPickUp;
			color = colorHolding;
			didPickUp = true;
		}
		
		return didPickUp;
		
	}
	
	public boolean drop(List<Node> neighbors) {
		
		boolean didDrop = true;
		// with some probability, drop
		
		//holding.setLocation(this.location);
		holding = null;
		color = colorEmpty;
		return didDrop;
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
