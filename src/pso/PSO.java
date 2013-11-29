package pso;

import java.util.List;

import driver.DataPoint;

public class PSO {
	
	Swarm swarm;
	protected static double momentum = 0.5;
	protected static double cognitiveDistribution = 2;			// phi_1
	protected static double socialDistribution = 2;				// phi_2
	
	public PSO(int swarmSize, List<DataPoint> data, int numClusters) {
		swarm = new Swarm(swarmSize, data, numClusters);
		swarm.evaluateParticles();
	}
	
	public double runIteration() {
		return swarm.runIteration();
	}
	
	public List<Double> getBestSolution() {
		return swarm.getBestParticle().getLocation();
	}
	
	public List<Double> getBestFeatures() {
		return swarm.getBestParticle().getLocation();
	}
	
	public double getBestFitness() {
		return swarm.getBestParticle().getFitness();
	}

}
