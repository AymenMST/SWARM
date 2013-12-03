package pso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tools.Tools;

public class Particle {
	
	// initialize params
	Random random = new Random(11235);
	double fitness;
	List<Double> velocity;
	List<Double> location;
	List<Double> personalBest;
	double bestFitness = 0.0;
	double minValue, maxValue;
	
	/**
	 * Create a new particle 
	 * 
	 * @param size		The search space for the particle.
	 * @param minValue	The minimum value accessible by the particle.
	 * @param maxValue	The maximum value accessible by the particle.
	 */
	public Particle(int size, double minValue, double maxValue) {
		location = new ArrayList<Double>(size);
		velocity = new ArrayList<Double>(size);
		this.minValue = minValue;
		this.maxValue = maxValue;
		for (int i = 0; i < size; i++) {
			location.add(Tools.getRandomDouble(minValue, maxValue));
			velocity.add(Tools.getRandomDouble(minValue, maxValue));
		}
		personalBest = location;
	}
	
	/**
	 * Update the velocity of the particle.
	 */
	public void updateVelocity() {
		// for each element in the velocity vector
		for (int i = 0; i < velocity.size(); i++) {
			// momentum term
			double prevVelocity = PSO.momentum * velocity.get(i);
			// cognitive term (personal best)
			double cognitive = (random.nextDouble() * PSO.cognitiveDistribution) * (personalBest.get(i) - location.get(i));
			// social term (global best)
			double social = (random.nextDouble() * PSO.socialDistribution) * (Swarm.globalBest.get(i) - location.get(i));
			// set new velocity to the sum of the terms
			velocity.set(i, prevVelocity + cognitive + social);
		}
	}
	
	/**
	 * Move the particle through the search space based on the velocity vector.
	 */
	public void move() {
		// for each element in the location vector
		for (int i = 0; i < location.size(); i++) {
			// update position
			location.set(i, location.get(i) + velocity.get(i));
		}
	}
	
	/**
	 * @return	The best position seen by the particle.
	 */
	public List<Double> getBestLocation() {
		return personalBest;
	}
	
	/**
	 * @return	The current location of the particle.
	 */
	public List<Double> getLocation() {
		return location;
	}
	
	/**
	 * @param fitness	The fitness of the particle based on an external evaluation.
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
		// if best, store
		if (fitness > bestFitness) {
			bestFitness = fitness;
			personalBest = location;
		}
	}
	
	/**
	 * @return	The current fitness of the particle.
	 */
	public double getFitness() {
		return fitness;
	}
	
	/**
	 * @return	The best fitness the particle has encountered.
	 */
	public double getBestFitness() {
		return bestFitness;
	}
	
	/**
	 * @return	The size of the search space.
	 */
	public int size() {
		return location.size();
	}
	
	/**
	 * A string representation of the particle's location.
	 */
	public String toString() {
		return "[" + location + "]";
	}

}
