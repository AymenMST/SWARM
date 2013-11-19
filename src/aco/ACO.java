package aco;

import graph.Heuristic;
import graph.HeuristicConstant;

import java.util.List;

import driver.DataPoint;

public class ACO {
	
	static Heuristic heuristic = new HeuristicConstant();
	static double pheromoneInfluence = 1.0;
	static double attractivenessInfluence = 1.0;
	
	public ACO() {
		
	}
	
	/**
	 * @param data	The training data that will be used to identify clusters
	 */
	public void train(List<DataPoint> data) {
		
	}
	
	public int classify(DataPoint datapoint) {
		
		
		return 0;
	}
	
	public double trainIteration(List<DataPoint> data) {
		
		
		return 0.0;
	}

}
