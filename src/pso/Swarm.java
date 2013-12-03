package pso;

import java.util.ArrayList;
import java.util.List;

import driver.DataPoint;
import graph.Graph;
import graph.Node;

public class Swarm {
	
	// initialize params
	List<Particle> particles;
	List<List<Node>> clusters;
	Graph g = new Graph();
	int numClusters;
	double globalBestFitness = -Double.MAX_VALUE;
	protected static List<Double> globalBest;
	double minValue = Double.MAX_VALUE;
	double maxValue = -Double.MAX_VALUE;
	
	/**
	 * Creates a new swarm of particles.
	 * 
	 * @param numParticles	The number of particles in the swarm.
	 * @param data			The data that the swarm should be clustering on.
	 * @param numClusters	The number of clusters to be identified by the algorithm.
	 */
	public Swarm(int numParticles, List<DataPoint> data, int numClusters) {
		// set the number of clusters
		this.numClusters = numClusters;
		// find range of all features in data
		getMinMaxValues(data);
		// construct graph
		for (DataPoint point : data) {
			g.addVertex(new Node(point));
		}
		// determine the search space dimensions based on 
		// the size of the features and the number of clusters.
		int particleSize = numClusters * data.get(0).getFeatures().size();
		// add the necessary number of particles to the swarm
		this.particles = new ArrayList<Particle>(numParticles);
		for (int i = 0; i < numParticles; i++) {
			particles.add(new Particle(particleSize, minValue, maxValue));
		}
	}
	
	/**
	 * Get the minimum and maximum value of any datapoint feature.
	 * 
	 * @param data	The data to scan for min and max values.
	 */
	private void getMinMaxValues(List<DataPoint> data) {
		// for each datapoint
		for (DataPoint point : data) {
			// for each feature in the current datapoint
			for (Double value : point.getFeatures()) {
				// store min and max seen for all features for all datapoints
				minValue = Math.min(minValue, value);
				maxValue = Math.max(maxValue, value);
			}
		}
	}
	
	/**
	 * Runs a single iteration of updating velocities 
	 * and locations for every particle in the swarm.
	 * 
	 * @return	The fitness of the clusters after iteration.
	 */
	public double runIteration() {
		// update velocities
		updateParticleVelocities();
		// update particle locations
		moveParticles();
		// evaluate the fitness for each particle
		return evaluateParticles();
	}
	
	/**
	 * Update the particle velocities for every particle in the swarm.
	 */
	private void updateParticleVelocities() {
		for (Particle particle : particles) {
			particle.updateVelocity();
		}
	}
	
	/**
	 * Update the particle locations for every particle in the swarm.
	 */
	private void moveParticles() {
		for (Particle particle : particles) {
			particle.move();
		}
	}
	
	/**
	 * Evaluate the fitness of each particle by running a measuring
	 * index on the clusters the particle creates.
	 * 
	 * @return	The fitness of the best particle.
	 */
	public double evaluateParticles() {
		double bestFitness = -Double.MAX_VALUE;
		Particle bestParticle = particles.get(0);
		
		// for each particle
		for (Particle particle : particles) {
			
			int numFeatures = particle.size() / numClusters;
			List<List<Double>> centers = new ArrayList<>(numClusters);
			
			// for each cluster represented by the particle
			int i = 0;
			for (int clusterIndex = 0; clusterIndex < numClusters; clusterIndex++) {
				List<Double> center = new ArrayList<Double>(numFeatures);
				// for each feature in the particle's datapoint
				for (int featureIndex = 0; featureIndex < numFeatures; featureIndex++) {
					// store particle location as an actual center in the search space
					center.add(particle.getLocation().get(i++));
				}
				centers.add(center);
			}
			
			// get the clusters corresponding to the centers
			clusters = g.getClusters(centers);
			// calculate fitness of the clusters that were found
			PSO.fitness.setCenters(centers);
			double fitnessValue = PSO.fitness.getFitness(clusters);
			// store calculated fitness value in particle
			particle.setFitness(fitnessValue);
			// keep track of the best particle seen so far
			if (fitnessValue > bestFitness) {
				bestFitness = fitnessValue;
				bestParticle = particle;
			}
		}
		
		// update global best if changed
		if (bestFitness > globalBestFitness) {
			globalBestFitness = bestFitness;
			globalBest = bestParticle.getLocation();
		}
		
		return bestFitness;
	}
	
	/**
	 * Retrieves the best particle seen by the algorithm so far.
	 * Note: the particle may not currently be the best, but at
	 * some stage it found a location that is dominant. (It's 
	 * personal best is the highest).
	 * 
	 * @return	The best particle seen by the algorithm so far.
	 */
	public Particle getBestParticle() {
		Particle bestParticle = particles.get(0);
		double bestFitness = Double.MAX_VALUE;
		// for each particle
		for (Particle particle : particles) {
			// store best particle
			if (particle.getFitness() > bestFitness) {
				bestFitness = particle.getFitness();
				bestParticle = particle;
			}
		}
		return bestParticle;
	}

}
