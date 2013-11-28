package pso;

import java.util.ArrayList;
import java.util.List;

import fitness.DunnGraphFitness;
import fitness.Fitness;
import graph.Node;

public class Swarm {
	
	List<Particle> particles;
	Fitness fitness;
	
	public Swarm(int numParticles, int particleSize) {
		particles = new ArrayList<Particle>(numParticles);
		for (int i = 0; i < numParticles; i++) {
			particles.add(new Particle(particleSize));
		}
	}
	
	public void runIteration() {
		moveParticles();
		evaluateParticles();
	}
	
	private void moveParticles() {
		for (Particle particle : particles) {
			particle.move();
		}
	}
	
	private void evaluateParticles() {
		for (Particle particle : particles) {
			// TODO: create cluster representation
			List<List<Node>> clusters = new ArrayList<>();
			fitness = new DunnGraphFitness(clusters);
			particle.setFitness(fitness.getFitness());
		}
		
	}
	
	// TODO
	public Particle getBestParticle() {
		return particles.get(0);
	}

}
