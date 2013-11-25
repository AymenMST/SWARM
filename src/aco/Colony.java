package aco;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import distance.Distance;
import distance.Euclidean;
import edu.uci.ics.jung.graph.Forest;
import graph.Edge;
import graph.Node;

public class Colony {
	
	int xSpace;
	int ySpace;
	List<Ant> ants = new ArrayList<Ant>();
	Random rand = new Random(11235);
	double momentum = 0.1;
	int maxMoveX = 10;	// TODO: change ant position to double
	int maxMoveY = 10;
	double moveX = 0;
	double moveY = 0;
	Distance distance = new Euclidean();
	
	/**
	 * Creates a colony of ants.
	 * 
	 * @param size	The number of ants that will be put in the population.
	 */
	public Colony(int xSpace, int ySpace, int size) {
		this.xSpace = xSpace;
		this.ySpace = ySpace;
		initialize(size);
	}
	
	private void initialize(int size) {
		for (int i = 0; i < size; i++) {
			int x = rand.nextInt(xSpace);
			int y = rand.nextInt(ySpace);
			Ant ant = new Ant(new Point2D.Double(x, y));
			ants.add(ant);
		}
	}
	
	public void move() {
		for (Ant ant : ants) {
			// randomly get movement
			moveX = (rand.nextDouble()*maxMoveX*2 - maxMoveX) + ant.getLastMoveX() * momentum;
			if (moveX > maxMoveX)
				moveX = maxMoveX;
			else if (moveX < -maxMoveX)
				moveX = -maxMoveX;
			moveY = (rand.nextDouble()*maxMoveY*2 - maxMoveY) + ant.getLastMoveY() * momentum;
			if (moveY > maxMoveY)
				moveY = maxMoveY;
			else if (moveY < -maxMoveY)
				moveY = -maxMoveY;
			// calculate and bound X
			double newX = ant.getX() + moveX;
			if (newX < 0 || newX > xSpace)
				moveX = -moveX;
			// calculate and bound Y
			double newY = ant.getY() + moveY;
			if (newY < 0 || newY > ySpace)
				moveY = -moveY;
			// move ant
			ant.move(moveX, moveY);
		}
	}
	
	public void act(Forest<Node, Edge> g) {
		for (Ant ant : ants) {
			double minDistance = Double.MAX_VALUE;
			Node nearest = null;
			for (Node vertex : g.getVertices()) {
				double dist = distance.distance(vertex.getLocationVector(), ant.getLocationVector());
				if (dist < minDistance) {
					minDistance = dist;
					nearest = vertex;
				}
			}
			ant.act(0.0, nearest);
		}
	}
	
	public List<Ant> getAnts() {
		return ants;
	}

}
