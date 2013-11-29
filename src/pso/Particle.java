package pso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tools.Tools;

public class Particle {
	
	List<Double> representation;
	Random random = new Random(11235);
	double minInitValue = 0;
	double maxInitValue = 10;
	double fitness;
	double bestFitness = Double.MAX_VALUE;
	List<Double> bestRepresentation;
	
	public Particle(int size) {
		representation = new ArrayList<Double>(size);
		for (int i = 0; i < size; i++) {
			representation.add(Tools.getRandomDouble(minInitValue, maxInitValue));
		}
	}
	
	public void move() {
		
	}
	
	public List<Double> getBestRepresentation() {
		return bestRepresentation;
	}
	
	public List<Double> getRepresentation() {
		return representation;
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
		if (fitness < bestFitness) {
			bestFitness = fitness;
			bestRepresentation = representation;
		}
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public int size() {
		return representation.size();
	}
	
	public String toString() {
		return "[" + representation + "]";
	}

}
