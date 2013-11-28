package pso;

import java.util.List;

import fitness.Fitness;

public class PSO {
	
	Swarm swarm;
	
	public PSO(int swarmSize, int particleSize) {
		swarm = new Swarm(swarmSize, particleSize);
	}
	
	public void runIteration() {
		swarm.runIteration();
	}
	
	public List<Double> getBestSolution() {
		return swarm.getBestParticle().getRepresentation();
	}

}
