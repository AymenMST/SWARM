package fitness;

import graph.Node;

import java.util.ArrayList;
import java.util.List;

import distance.Distance;
import distance.Euclidean;

public abstract class GraphFitness extends Fitness {
	
	Distance distance = new Euclidean();
	List<List<Node>> clusters;
	List<Double> avgDistances;
	List<List<Double>> centers;
	
	public GraphFitness(List<List<Node>> clusters) {
		this.clusters = clusters;
	}
	
	public abstract double getFitness();
	
	protected void calculateCentersAndAverageDistances() {
		// calculate centers and average distances
		avgDistances = new ArrayList<Double>(clusters.size());
		centers = new ArrayList<>(clusters.size());
		for (List<Node> cluster : clusters) {
			// get center
			List<Double> center = new ArrayList<>();
			for (int nodeIndex = 0; nodeIndex < cluster.size(); nodeIndex++) {
				Node node = cluster.get(nodeIndex);
				for (int i = 0; i < node.getFeatureLocation().size(); i++) {
					double newValue = node.getFeatureLocation().get(i);
					if (nodeIndex == 0)
						center.add(newValue);
					else
						center.set(i, center.get(i) + newValue);
				}
			}
			for (int i = 0; i < center.size(); i++)
				center.set(i, center.get(i) / cluster.size());
			
			centers.add(center);
			// get average distance
			avgDistances.add(getAverageClusterDistance(cluster, center));
		}
	}
	
	private double getAverageClusterDistance(List<Node> cluster, List<Double> center) {
		double avgDistance = 0.0;
		for (Node node : cluster) {
			avgDistance += distance.distance(center, node.getFeatureLocation());
		}
		avgDistance /= cluster.size();
		return avgDistance;
	}

}
