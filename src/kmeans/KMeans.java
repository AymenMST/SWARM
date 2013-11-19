package kmeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import distance.Distance;
import distance.Euclidean;
import driver.DataPoint;
import driver.Simulator;

public class KMeans {
	
	// the number of clusters, or means
	private int k = 0;
	private int numFeatures = 0;
	// the amount of change allowable during an update of the algorithm for completion
	private double threshold = 0.001;
	// set the min and max values for the random center initialization
	private double initCenterMin = -0.3;
	private double initCenterMax =  0.3;
	// the actual centers found by the algorithm
	private List<List<Double>> centers;
	// the map that keeps track of which center datapoints are assigned to
	private Map<DataPoint, Integer> clusters = new HashMap<DataPoint, Integer>();
	
	Random random = new Random(11235);
	Distance dist = new Euclidean();
	
	public KMeans() {
		
	}
	
	/**
	 * @param data	The training data that will be used to identify clusters
	 */
	public void train(List<DataPoint> data) {
		// set k to the number of potential classes
		k = data.get(0).getOutputs().size();
		// get the size of the input space
		numFeatures = data.get(0).getFeatures().size();
		
		// set up the new list of centers
		initializeCenters();
		
		double change;
		do {
			change = trainIteration(data);
			//System.out.print(String.format("%20s  : ",String.valueOf(change)));
			//Simulator.printVector(centers.get(0));
		} while (change > threshold);
		
		orderCenters(data);
		
	}
	
	// This is a mess right now, but it seems to be doing the job.
	// It reorders the clusters that k-means found to match the correct centers.
	// Will clean up / optimize later
	private void orderCenters(List<DataPoint> data) {
		
		int size = data.get(0).getOutputs().size();
		List<List<Integer>> distribution = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			distribution.add(new ArrayList<Integer>(size));
			for (int j = 0; j < size; j++)
				distribution.get(i).add(0);
		}
		
		for (DataPoint datapoint : data) {
			int center = classify(datapoint);
			int oldValue = distribution.get(center).get(datapoint.getClassIndex());
			distribution.get(center).set(datapoint.getClassIndex(), oldValue + 1);
		}
			
		// reorder centers
		List<Integer> chosenIndexes = new ArrayList<Integer>(size);
		List<List<Double>> newCenters = new ArrayList<>();
		for (int center = 0; center < distribution.size(); center++)
			newCenters.add(new ArrayList<Double>());
		for (int center = 0; center < distribution.size(); center++) {
			int max = -1;
			int maxIndex = 0;
			for (int newCenter = 0; newCenter < distribution.get(center).size(); newCenter++) {
				int val = distribution.get(center).get(newCenter);
				if (val > max && !chosenIndexes.contains(newCenter)) {
					max = val;
					maxIndex = newCenter;
				}
			}
			chosenIndexes.add(maxIndex);
			newCenters.set(maxIndex, centers.get(center));
		}
		
		centers = newCenters;
	}
	
	public int classify(DataPoint datapoint) {
		double minDistance = Double.MAX_VALUE;
		int closestCenter = 0;
		for (int center = 0; center < k; center++) {
			double distance = dist.distance(datapoint.getFeatures(), centers.get(center));
			if (distance < minDistance) {
				minDistance = distance;
				closestCenter = center;
			}
		}
		return closestCenter;
	}
	
	public double trainIteration(List<DataPoint> data) {
		double change = 0.0;
		for (DataPoint datapoint : data) {
			int closestCenter = classify(datapoint);
			clusters.put(datapoint, closestCenter);
		}
		change = calculateCenters(data);
		return change;
	}
	
	private double calculateCenters(List<DataPoint> data) {
		double change = 0.0;
		// create points structure
		List<List<List<Double>>> points = new ArrayList<>(k);
		for (int cluster = 0; cluster < k; cluster++) {
			List<List<Double>> clust = new ArrayList<>();
			points.add(clust);
		}
		// add datapoints to points structure using clusters
		for (DataPoint datapoint : data) {
			// find the cluster index this point belongs to
			int cluster = clusters.get(datapoint);
			// get existing cluster
			List<List<Double>> updated = points.get(cluster);
			// add datapoint to cluster
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
				
				change = Math.max(change, Math.abs(average - centers.get(center).get(feature)));
				newCenter.add(average);
			}
			centers.set(center, newCenter);
		}
	    //System.out.println("CHANGE: "+change+"\n");
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
				newCenter.add(initCenterMin + (initCenterMax - initCenterMin) * random.nextDouble());
			}
			centers.add(newCenter);
		}
	}
	
	/**
	 * Retrieves the centers that were calculated by the algorithm.
	 * 
	 * @return	The centers as calculated by the algorithm.
	 */
	public List<List<Double>> getCenters() {
		return centers;
	}
	
	/**
	 * Describes the algorithm in the form "K-Means Clustering",
	 * where "K" is the number of clusters, assuming the algorithm
	 * has been trained.
	 */
	public String toString() {
		String clusters = "K";
		if (k > 0)
			clusters = Integer.toString(k);
		return clusters+"-Means Clustering";
	}

}
