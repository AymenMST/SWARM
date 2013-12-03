package pso;

import java.util.List;

import driver.DataPoint;
import fitness.GraphFitness;

public class PSO {
	
	Swarm swarm;
	protected static GraphFitness fitness;
	
	// initialize tunable parameters
	protected static double momentum = 0.5;
	protected static double cognitiveDistribution = 2;			// phi_1
	protected static double socialDistribution = 2;				// phi_2
	protected static double minValue = 0;
	protected static double maxValue = 10;
	
	/**
	 * Creates an instance of the PSO algorithm.
	 * 
	 * @param swarmSize				The number of particles in a swarm.
	 * @param data					The data to cluster using PSO.
	 * @param numClusters			The number of clusters to identify.
	 * @param fitnessEvaluation		The fitness evaluation to use for the clusters.
	 */
	@SuppressWarnings("static-access")
	public PSO(int swarmSize, List<DataPoint> data, int numClusters, GraphFitness fitnessEvaluation) {
		this.fitness = fitnessEvaluation;
		swarm = new Swarm(swarmSize, data, numClusters);
		swarm.evaluateParticles();
	}
	
	/**
	 * Run a single iteration of the PSO algorithm.
	 * 
	 * @return	fitness of the clusters after the iteration has run.
	 */
	public double runIteration() {
		return swarm.runIteration();
	}
	
	/**
	 * @return	The best solution to the clustering problem seen by any particle.
	 */
	public List<Double> getBestSolution() {
		return swarm.getBestParticle().getLocation();
	}
	
	/**
	 * @return	The fitness of the best solution to the clustering problem seen by any particle.
	 */
	public double getBestFitness() {
		return swarm.getBestParticle().getFitness();
	}

}
