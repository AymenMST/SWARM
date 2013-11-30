package clustering;

import fitness.GraphFitness;
import graph.Graph;
import graph.Node;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import tools.Tools;

import distance.Distance;
import distance.Euclidean;
import driver.DataPoint;

public class KMeansClustering extends ClusteringMethod {

	private Graph graph;
	// the number of clusters, or means
	private int k = 0;
	private int numFeatures = 0;
	// the amount of change allowable during an update of the algorithm for
	// completion
	private double threshold = 0.001;
	// set the min and max values for the random center initialization
	private double initCenterMin = -0.3;
	private double initCenterMax = 0.3;
	// the actual centers found by the algorithm
	private List<List<Double>> centers;
	// the map that keeps track of which center datapoints are assigned to
	private Map<DataPoint, Integer> clustersMap = new HashMap<DataPoint, Integer>();
	// whether or not to use the datapoints' virtual locations
	private boolean useVirtualLocations = false;

	private Random random = new Random(11235);
	private Distance dist = new Euclidean();

	public KMeansClustering(List<DataPoint> data, GraphFitness fitnessEvaluation) {
		super(data, fitnessEvaluation);
	}

	/**
	 * @param data
	 *            The training data that will be used to identify clusters
	 */
	public void cluster() {
		
		// set k to the number of potential classes
		k = data.get(0).getOutputs().size();
		// the size of the input space
		numFeatures = 0;
		for (Node node : graph.getVertices()) {
			DataPoint datapoint = node.getDataPoint();
			if (useVirtualLocations)
				numFeatures = node.getLocationVector().size();
			else
				numFeatures = datapoint.getFeatures().size();
			break;
		}
		
		// build graph from datapoints if not set
		if (graph == null) {
			this.graph = new Graph();
			for (DataPoint point : data) {
				graph.addVertex(new Node(point));
			}
		}

		// set up the new list of centers
		initializeCenters();
			
		if (visualize)
			drawGraph();

		double change;
		do {
			change = trainIteration();
			//System.out.print(String.format("%20s  : ",String.valueOf(change)));
			//System.out.println(centers.get(0));
			if (visualize)
				drawGraph();
		} while (change > threshold);
		
		// get clusters
		clusters = new ArrayList<>(k);
		for (int i = 0; i < k; i++) {
			clusters.add(new ArrayList<Node>());
		}
		for (Node node : graph.getVertices()) {
			DataPoint datapoint = node.getDataPoint();
			clusters.get(clustersMap.get(datapoint)).add(node);
		}

	}

	public int assignCluster(Node node) {
		DataPoint datapoint = node.getDataPoint();
		double minDistance = Double.MAX_VALUE;
		int closestCenter = 0;
		for (int center = 0; center < k; center++) {
			double distance = 0.0;
			if (useVirtualLocations)
				distance = dist.distance(node.getLocationVector(), centers.get(center));
			else
				distance = dist.distance(datapoint.getFeatures(), centers.get(center));
			if (distance < minDistance) {
				minDistance = distance;
				closestCenter = center;
			}
		}
		return closestCenter;
	}

	public double trainIteration() {
		double change = 0.0;
		for (Node node : graph.getVertices()) {
			DataPoint datapoint = node.getDataPoint();
			int closestCenter = assignCluster(node);
			clustersMap.put(datapoint, closestCenter);
		}
		change = calculateCenters();
		return change;
	}

	private double calculateCenters() {
		double change = 0.0;
		// create points structure
		List<List<List<Double>>> points = new ArrayList<>(k);
		for (int cluster = 0; cluster < k; cluster++) {
			List<List<Double>> clust = new ArrayList<>();
			points.add(clust);
		}
		// add datapoints to points structure using clusters
		for (Node node : graph.getVertices()) {
			DataPoint datapoint = node.getDataPoint();
			// find the cluster index this point belongs to
			int cluster = clustersMap.get(datapoint);
			// get existing cluster
			List<List<Double>> updated = points.get(cluster);
			// add datapoint to cluster
			if (useVirtualLocations)
				updated.add(node.getLocationVector());
			else
				updated.add(datapoint.getFeatures());
			// update the cluster to use the new cluster
			points.set(cluster, updated);
		}
		// update centers
		for (int center = 0; center < k; center++) {
			List<Double> newCenter = new ArrayList<Double>();
			for (int feature = 0; feature < numFeatures; feature++) {
				double average = 0.0;
				for (List<Double> point : points.get(center)) {
					average += point.get(feature);
				}

				if (points.get(center).size() != 0)
					average /= points.get(center).size();
				else
					average = centers.get(center).get(feature);

				change = Math.max(change,
						Math.abs(average - centers.get(center).get(feature)));
				newCenter.add(average);
			}
			centers.set(center, newCenter);
		}
		// System.out.println("CHANGE: "+change+"\n");
		return change;
	}

	/**
	 * Initializes the centers to random variables within a specified range.
	 */
	private void initializeCenters() {
		centers = new ArrayList<>(k);
		for (int center = 0; center < k; center++) {
			ArrayList<Double> newCenter = new ArrayList<Double>(numFeatures);
			for (int feature = 0; feature < numFeatures; feature++) {
				newCenter.add(Tools.getRandomDouble(initCenterMin, initCenterMax));
			}
			centers.add(newCenter);
		}
	}

	/**
	 * Retrieves the centers that were calculated by the algorithm.
	 * 
	 * @return The centers as calculated by the algorithm.
	 */
	public List<List<Double>> getCenters() {
		return centers;
	}
	
	public void drawGraph() {
		
		g = new Graph();
		
		int dim1 = 1;
		int dim2 = 2;
		
		// add data points
		for (Node node : graph.getVertices()) {
			DataPoint d = node.getDataPoint();
			double point1 = d.getFeatures().get(dim1);
			double point2 = d.getFeatures().get(dim2);
			Node vertex = new Node(d, new Point2D.Double((point1 + 20) * 20, (point2 + 20) * 20));
			
			//Add color to nodes here
			if (d.getClassIndex() == 0){
				vertex.setColor(Color.CYAN);
			}
			vertex.setAlpha(0.1);
			g.addVertex(vertex);
		}
		
		// add centers
		for (List<Double> center : centers) {
			double point1 = center.get(dim1);
			double point2 = center.get(dim2);
			Node node = new Node(null, new Point2D.Double((point1 + 20) * 20, (point2 + 20) * 20));
			node.setColor(Color.GREEN);
			node.setLayer(1);
			g.addVertex(node);
		}
		
		jungHandler.setGraph(g);
		jungHandler.draw();
		
	}
	
	public void setPseudoGraph(Graph graph) {
		this.graph = graph;
		this.useVirtualLocations = true;
	}
	
	public void setUseVirtualLocations(boolean useVirtualLocations) {
		this.useVirtualLocations = useVirtualLocations;
	}

	/**
	 * Describes the algorithm in the form "K-Means Clustering", where "K" is
	 * the number of clusters, assuming the algorithm has been trained.
	 */
	public String toString() {
		String clusters = "K";
		if (k > 0)
			clusters = Integer.toString(k);
		return clusters + "-Means Clustering";
	}

}
