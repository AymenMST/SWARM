package graph;

import java.util.ArrayList;
import java.util.List;

import distance.Distance;
import distance.Euclidean;
import edu.uci.ics.jung.graph.DelegateForest;

public class Graph extends DelegateForest<Node, Edge> {
	
	private static final long serialVersionUID = 1L;
	Distance dist = new Euclidean();

	/**
	 * Given a list of centers, retrieves clusters where
	 * each node belongs to the closest center.
	 * 
	 * @param centers	The centers of each cluster.
	 * @return			The cluster of nodes corresponding to each center.
	 */
	public List<List<Node>> getClusters(List<List<Double>> centers) {
		// construct clusters structure
		List<List<Node>> clusters = new ArrayList<>();
		for (int i = 0; i < centers.size(); i++)
			clusters.add(new ArrayList<Node>());
		
		// for each node in the graph
		for (Node node : this.getVertices()) {
			int minIndex = 0;
			double minDistance = Double.MAX_VALUE;
			// for each center
			for (int i = 0; i < centers.size(); i++) {
				// calculate the distance between the node and the center
				double distance = dist.distance(centers.get(i), node.getFeatureLocation());
				// place node in the closest center
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
