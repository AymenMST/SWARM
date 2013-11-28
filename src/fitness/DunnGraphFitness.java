package fitness;

import graph.Node;

import java.util.ArrayList;
import java.util.List;

import distance.Distance;
import distance.Euclidean;

public class DunnGraphFitness extends GraphFitness {
	
	public DunnGraphFitness(List<List<Node>> clusters) {
		super(clusters);
	}
	
	// higher values are better
	public double getFitness() {
		double fitness = Double.MAX_VALUE;
		
		calculateCentersAndAverageDistances();
		
		// calculate max distance between centers
		double maxAvgDistance = 0.0;
		for (int k = 0; k < clusters.size(); k++)
			maxAvgDistance = Math.max(maxAvgDistance, avgDistances.get(k));
		
		for (int i = 0; i < clusters.size(); i++) {
			for (int j = 0; j < clusters.size(); j++) {
				if (i != j) {
					double value = distance.distance(centers.get(i), centers.get(j)) / maxAvgDistance;
					fitness = Math.min(fitness, value);
				}
			}
		}
		
		return fitness;
	}

}
