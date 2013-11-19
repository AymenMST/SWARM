package aco;

import graph.Edge;
import graph.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ant {
	
	Node location;
	Random random = new Random(11235);
	
	public Ant() {
		
	}
	
	/**
	 * Probabilistically choose and edge based on pheromones
	 * and a heuristic function.
	 * 
	 * @return	The edge that was chosen.
	 */
	public Edge selectEdge() {
		Edge selected = null;
		List<Double> selections = new ArrayList<Double>();
		double sum = 0.0;
		for (Edge edge : location.getEdges()) {
			double term1 = Math.pow(edge.pheromone, ACO.pheromoneInfluence);
			double term2 = Math.pow(ACO.heuristic.getValue(location, edge), ACO.attractivenessInfluence);
			double product = term1 * term2;
			selections.add(product);
			sum += product;
		}
		double randomPosition = random.nextDouble() * sum;
		double position = 0.0;
		for (int index = 0; index < selections.size(); index++) {
			if (position + selections.get(index) > randomPosition) {
				selected = location.getEdges().get(index);
				break;
			}
			position += selections.get(index);
		}
		return selected;
	}

}
