package pso;

import fitness.DunnGraphFitness;
import fitness.GraphFitness;
import graph.Graph;
import graph.Node;

import java.util.ArrayList;
import java.util.List;

import driver.DataPoint;

public class Swarm {
	
	List<Particle> particles;
	GraphFitness fitness;
	List<List<Node>> clusters;
	Graph g = new Graph();
	int numClusters;
	double globalBestFitness = Double.MAX_VALUE;
	protected static List<Double> globalBest;
	double minValue = Double.MAX_VALUE;
	double maxValue = -Double.MAX_VALUE;
	
	public Swarm(int numParticles, List<DataPoint> data, int numClusters) {
		this.numClusters = numClusters;
		getMinMaxValues(data);
		for (DataPoint point : data) {
			g.addVertex(new Node(point));
		}
		int particleSize = numClusters * data.get(0).getFeatures().size();
		this.particles = new ArrayList<Particle>(numParticles);
		for (int i = 0; i < numParticles; i++) {
			particles.add(new Particle(particleSize, minValue, maxValue));
		}
	}
	
	private void getMinMaxValues(List<DataPoint> data) {
		for (DataPoint point : data) {
			for (Double value : point.getFeatures()) {
				minValue = Math.min(minValue, value);
				maxValue = Math.max(maxValue, value);
			}
		}
	}
	
	public double runIteration() {
		updateParticleVelocities();
		moveParticles();
		return evaluateParticles();
	}
	
	private void updateParticleVelocities() {
		for (Particle particle : particles) {
			particle.updateVelocity();
		}
	}
	
	private void moveParticles() {
		for (Particle particle : particles) {
			particle.move();
		}
	}
	
	public double evaluateParticles() {
		double bestFitness = Double.MAX_VALUE;
		Particle bestParticle = particles.get(0);
		for (Particle particle : particles) {
			
			int numFeatures = particle.size() / numClusters;
			List<List<Double>> centers = new ArrayList<>(numClusters);
			int i = 0;
			for (int clusterIndex = 0; clusterIndex < numClusters; clusterIndex++) {
				List<Double> center = new ArrayList<Double>(numFeatures);
				for (int featureIndex = 0; featureIndex < numFeatures; featureIndex++) {
					center.add(particle.getLocation().get(i++));
				}
				centers.add(center);
			}
			
			clusters = g.getClusters(centers);
			//fitness = new DaviesBouldinGraphFitness(clusters);
			fitness = new DunnGraphFitness(clusters);
			fitness.setCenters(centers);
			particle.setFitness(fitness.getFitness());
			if (fitness.getFitness() < bestFitness) {
				bestFitness = fitness.getFitness();
				bestParticle = particle;
			}
		}
		
		// update global best if changed
		if (bestFitness < globalBestFitness) {
			globalBestFitness = bestFitness;
			globalBest = bestParticle.getLocation();
		}
		
		return bestFitness;
	}
	
	public Particle getBestParticle() {
		Particle bestParticle = particles.get(0);
		double bestFitness = Double.MAX_VALUE;
		for (Particle particle : particles) {
			if (particle.getFitness() < bestFitness) {
				bestFitness = particle.getFitness();
				bestParticle = particle;
			}
		}
		return bestParticle;
	}

}
