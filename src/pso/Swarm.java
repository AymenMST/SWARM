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
	
	public Swarm(int numParticles, List<DataPoint> data, int numClusters) {
		this.numClusters = numClusters;
		for (DataPoint point : data) {
			g.addVertex(new Node(point));
		}
		int particleSize = numClusters * data.get(0).getFeatures().size();
		this.particles = new ArrayList<Particle>(numParticles);
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
			
			int numFeatures = particle.size() / numClusters;
			List<List<Double>> centers = new ArrayList<>(numClusters);
			int i = 0;
			for (int clusterIndex = 0; clusterIndex < numClusters; clusterIndex++) {
				List<Double> center = new ArrayList<Double>(numFeatures);
				for (int featureIndex = 0; featureIndex < numFeatures; featureIndex++) {
					center.add(particle.getRepresentation().get(i++));
				}
				centers.add(center);
			}
			
			clusters = g.getClusters(centers);
			//fitness = new DaviesBouldinGraphFitness(clusters);
			fitness = new DunnGraphFitness(clusters);
			fitness.setCenters(centers);
			particle.setFitness(fitness.getFitness());
		}
		
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
