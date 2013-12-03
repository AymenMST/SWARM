package clustering;

import java.util.List;

import pso.PSO;
import driver.DataPoint;
import fitness.GraphFitness;

public class PSOClustering extends ClusteringMethod {
	
	PSO pso;
	int swarmSize = 3;
	int numClusters = 2;
	int maxIterations = 1000;
	
	/**
	 * Creates a driver for the PSO clustering algorithm.
	 * 
	 * @param data				The data to cluster with PSO.
	 * @param fitnessEvaluation	The fitness evaluation to use with PSO.
	 */
	public PSOClustering(List<DataPoint> data, GraphFitness fitnessEvaluation) {
		super(data, fitnessEvaluation);
	}

	@Override
	public void cluster() {
		// create a new PSO algorithm instance, passing in relevent params
		pso = new PSO(swarmSize, data, numClusters, fitnessEvaluation);
		
		// loop until stopping criteria
		int i = 0;
		do {
			// run an iteration of the PSO algorithm
			double fitness = pso.runIteration();
			// evaluate the fitness of the resulting particles
			String fitnessDisplay = String.valueOf(fitness);
			
			// display fitness results for debugging
			if (fitness > 10000000)
				fitnessDisplay = "infinity";
			System.out.println("FITNESS: "+fitnessDisplay);
			
			i++;
		} while (i < maxIterations);
		
	}

	@Override
	public double evaluate() {
		return pso.getBestFitness();
	}

}
