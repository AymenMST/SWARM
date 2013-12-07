package clustering;

import java.util.List;

import pso.PSO;
import roc.TunableParameter;
import driver.DataPoint;
import fitness.GraphFitness;
import graph.Node;

public class PSOClustering extends ClusteringMethod {
	
	PSO pso;
	int swarmSize = 50;
	int numClusters = 2;
	int maxNumClusters = 15;
	int maxIterations = 100;
	int maxWorseIterations = 20;
	boolean echo = false;
	
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
		
		double bestFitness = 0.0;
		int bestNumClusters = 0;
		List<List<Node>> bestClusters = null;
		PSO bestPSO = null;
		
		for (int numClusters = 2; numClusters < maxNumClusters; numClusters++) {
		
			// create a new PSO algorithm instance, passing in relevant params
			pso = new PSO(swarmSize, data, numClusters, fitnessEvaluation);
			
			double maxFitness = 0.0;
			int countWorseIterations = 0;
			
			// loop until stopping criteria
			for (int i = 0; countWorseIterations < maxWorseIterations && i < maxIterations; i++) {
				// run an iteration of the PSO algorithm
				double fitness = 0.0;
				try {
					fitness = pso.runIteration();
				} catch (Exception e) {
					// number of clusters is too large for data
					break;
				}
				// evaluate the fitness of the resulting particles
				String fitnessDisplay = String.valueOf(fitness);
				
				// display fitness results for debugging
				if (echo)
					System.out.println("FITNESS: "+fitnessDisplay);
				
				if (fitness > maxFitness) {
					maxFitness = fitness;
					countWorseIterations = 0;
					if (fitness > bestFitness) {
						bestPSO = pso;
					}
				} else {
					countWorseIterations++;
				}
				
			}
			
			if (maxFitness > bestFitness) {
				bestFitness = maxFitness;
				bestNumClusters = numClusters;
			}
			
		}
		
		pso = bestPSO;
		
		this.clusters = pso.getClusters();
		
	}

	@Override
	public double evaluate() {
		return pso.getBestFitness();
	}

	@Override
	public void setTunableParameters(List<TunableParameter> tunableParameters) {
		// TODO Auto-generated method stub
		
	}

}
