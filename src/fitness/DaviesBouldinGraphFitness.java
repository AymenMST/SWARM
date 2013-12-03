package fitness;

import graph.Node;

import java.util.List;

public class DaviesBouldinGraphFitness extends GraphFitness {
	
	/**
	 * http://en.wikipedia.org/wiki/Davies%E2%80%93Bouldin_index
	 */
	public DaviesBouldinGraphFitness() {
		
	}
	
	@Override
	public double getFitness(List<List<Node>> clusters) {
		
		this.clusters = clusters;
		double fitness = 0.0;
		
		// calculate necessary information
		calculateCentersAndAverageDistances();
			
		// for each cluster
		for (int i = 0; i < clusters.size(); i++) {
			double maxValue = 0.0;
			// compare to every other cluster
			for (int j = 0; j < clusters.size(); j++) {
				if (i != j) {
					// perform Davies-Bouldin index calculation
					double value = avgDistances.get(i) + avgDistances.get(j) / distance.distance(centers.get(i), centers.get(j));
					maxValue = Math.max(maxValue, value);
				}
			}
			fitness += maxValue;
		}
		fitness /= clusters.size();
		
		// by default, min indicates better
		// inverse to make it a maximization problem
		fitness = 1.0 / fitness;
		
		return fitness;
	}

}
