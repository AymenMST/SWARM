package graph;

import java.util.ArrayList;
import java.util.List;

import distance.Distance;
import distance.Euclidean;
import edu.uci.ics.jung.graph.DelegateForest;

public class Graph extends DelegateForest<Node, Edge> {
	
	private static final long serialVersionUID = 1L;
	Distance dist = new Euclidean();

	public List<List<Node>> getClusters(List<List<Double>> centers) {
		List<List<Node>> clusters = new ArrayList<>();
		for (int i = 0; i < centers.size(); i++)
			clusters.add(new ArrayList<Node>());
		
		for (Node node : this.getVertices()) {
			int minIndex = 0;
			double minDistance = Double.MAX_VALUE;
			for (int i = 0; i < centers.size(); i++) {
				double distance = dist.distance(centers.get(i), node.getFeatureLocation());
				if (distance < minDistance) {
					minDistance = distance;
					minIndex = i;
				}
			}
			clusters.get(minIndex).add(node);
		}
		return clusters;
	}
	
}
