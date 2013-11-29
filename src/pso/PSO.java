package pso;

import java.util.List;

import driver.DataPoint;

public class PSO {
	
	Swarm swarm;
	
	public PSO(int swarmSize, List<DataPoint> data, int numClusters) {
		swarm = new Swarm(swarmSize, data, numClusters);
	}
	
	public void runIteration() {
		swarm.runIteration();
	}
	
	public List<Double> getBestSolution() {
		return swarm.getBestParticle().getRepresentation();
	}
	
	public List<Double> getBestFeatures() {
		return swarm.getBestParticle().getRepresentation();
	}
	
	public double getBestFitness() {
		return swarm.getBestParticle().getFitness();
	}

}
