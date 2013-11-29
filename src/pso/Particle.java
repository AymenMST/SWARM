package pso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tools.Tools;

public class Particle {
	
	Random random = new Random(11235);
	double minValue = 0;
	double maxValue = 10;
	double fitness;
	List<Double> velocity;
	List<Double> location;
	List<Double> personalBest;
	double bestFitness = Double.MAX_VALUE;
	
	public Particle(int size, double minValue, double maxValue) {
		location = new ArrayList<Double>(size);
		velocity = new ArrayList<Double>(size);
		for (int i = 0; i < size; i++) {
			location.add(Tools.getRandomDouble(minValue, maxValue));
			velocity.add(Tools.getRandomDouble(minValue, maxValue));
		}
		personalBest = location;
	}
	
	public void updateVelocity() {
		for (int i = 0; i < velocity.size(); i++) {
			double prevVelocity = PSO.momentum * velocity.get(i);
			double cognitive = (random.nextDouble() * PSO.cognitiveDistribution) * (personalBest.get(i) - location.get(i));
			double social = (random.nextDouble() * PSO.socialDistribution) * (Swarm.globalBest.get(i) - location.get(i));
			velocity.set(i, prevVelocity + cognitive + social);
		}
	}
	
	public void move() {
		for (int i = 0; i < location.size(); i++)
			location.set(i, location.get(i) + velocity.get(i));
	}
	
	public List<Double> getPersonalBest() {
		return personalBest;
	}
	
	public List<Double> getLocation() {
		return location;
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
		if (fitness < bestFitness) {
			bestFitness = fitness;
			personalBest = location;
		}
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public int size() {
		return location.size();
	}
	
	public String toString() {
		return "[" + location + "]";
	}

}
