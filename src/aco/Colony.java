package aco;

import java.awt.Color;
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
	
	private int neighborhoodSize = 30;
	int maxMoveX = 40;
	int maxMoveY = 40;
	Distance distance = new Euclidean();
	List<Ant> ants = new ArrayList<Ant>();
	Random rand = new Random(11235);
	double momentum = 1.0;
	
	/**
	 * Creates a colony of ants.
	 * 
	 * @param size	The number of ants that will be put in the population.
	 */
	public Colony(int size) {
		initialize(size);
	}
	
	private void initialize(int size) {
		for (int i = 0; i < size; i++) {
			int x = rand.nextInt(ACO.xSpace);
			int y = rand.nextInt(ACO.ySpace);
			Ant ant = new Ant(new Point2D.Double(x, y));
			ants.add(ant);
		}
	}
	
	public void move() {
		
		for (Ant ant : ants) {
			
			// randomly get movement
			double moveX = (rand.nextDouble()*maxMoveX*2 - maxMoveX);
			double moveY = (rand.nextDouble()*maxMoveY*2 - maxMoveY);

			// calculate and bound X
			double newX = ant.getX() + moveX;
			if (newX < 0 || newX > ACO.xSpace) {
				moveX = -moveX;
			}
			// calculate and bound Y
			double newY = ant.getY() + moveY;
			if (newY < 0 || newY > ACO.ySpace) {
				moveY = -moveY;
			}
			
			// move ant
			ant.move(moveX, moveY);
			
		}
	}
	
	public void act(Forest<Node, Edge> g) {
		for (Ant ant : ants) {
			List<Node> neighborhood = new ArrayList<>();
		
			
			for (Node vertex : g.getVertices()) {
				double dist = distance.distance(vertex.getLocationVector(), ant.getLocationVector());
				if (dist < neighborhoodSize) {
					neighborhood.add(vertex);
				}
			}
			
			double density = (double)neighborhood.size() / g.getVertexCount();
			
			if (!ant.isHolding()){
				if (ant.pickup(neighborhood, density)){
				
					g.removeVertex(ant.getHolding());
				}
			}else{
				Node temp = ant.getHolding();
				if (ant.drop(neighborhood, density)){
					temp.setLocation(ant.getLocation());
					temp.setColor(Color.ORANGE);
					temp.setAlpha(0.2);
					g.addVertex(temp);
					
				}
			}
		}
	}
	
	public List<Ant> getAnts() {
		return ants;
	}

}
