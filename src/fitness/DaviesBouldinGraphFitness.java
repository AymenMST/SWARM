package fitness;

import graph.Node;

import java.util.List;

public class DaviesBouldinGraphFitness extends GraphFitness {
	
	public DaviesBouldinGraphFitness() {
		
	}
	
	// smaller values are better
	public double getFitness(List<List<Node>> clusters) {
		
		this.clusters = clusters;
		double fitness = 0.0;
		
		calculateCentersAndAverageDistances();
			
		for (int i = 0; i < clusters.size(); i++) {
			double maxValue = 0.0;
			for (int j = 0; j < clusters.size(); j++) {
				if (i != j) {
					double value = avgDistances.get(i) + avgDistances.get(j) / distance.distance(centers.get(i), centers.get(j));
					maxValue = Math.max(maxValue, value);
				}
			}
			fitness += maxValue;
		}
		fitness /= clusters.size();
		
		// inverse to make larger values better
		fitness = 1.0 / fitness;
		
		return fitness;
	}

}
