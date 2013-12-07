package fitness;

import graph.Node;

import java.util.List;

public class DunnGraphFitness extends GraphFitness {
	
	/**
	 * http://en.wikipedia.org/wiki/Dunn_index
	 */
	public DunnGraphFitness() {
		
	}
	
	@Override
	public double getFitness(List<List<Node>> clusters) {
		
		this.clusters = clusters;
		double fitness = Double.MAX_VALUE;
		
		// calculate necessary information
		calculateCentersAndAverageDistances();
		
		// calculate max distance between centers
		double maxAvgDistance = 0.0;
		for (int k = 0; k < clusters.size(); k++)
			maxAvgDistance = Math.max(maxAvgDistance, avgDistances.get(k));
		
		// for every cluster
		for (int i = 0; i < clusters.size(); i++) {
			// compare to every other cluster
			for (int j = 0; j < clusters.size(); j++) {
				if (i != j) {
					// perform Dunn index calculation
					double value = distance.distance(centers.get(i), centers.get(j)) / maxAvgDistance;
					fitness = Math.min(fitness, value);
				}
			}
		}
		
		// reset centers in case called again
		centers = null;
		
		return fitness;
	}

}
