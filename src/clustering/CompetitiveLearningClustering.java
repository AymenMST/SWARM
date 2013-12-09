package clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import neural_net.Network;
import neural_net.StructuralInfo;

import competitive_learning.CompetitiveLearning;

import driver.DataPoint;
import fitness.GraphFitness;
import graph.Node;

public class CompetitiveLearningClustering extends ClusteringMethod {
	
	private CompetitiveLearning competitiveLearning;
	private int numInputs;
	private int numOutputs = 10;
	private Map<Integer, List<DataPoint>> clusterMap = new HashMap<Integer, List<DataPoint>>();

	/**
	 * Creates the driver for the competitive clustering algorithm.
	 * 
	 * @param data					The data to run the competitive learning clustering algorithm on.
	 * @param fitnessEvaluation		The fitness evaluation to use for this clustering method.
	 */
	public CompetitiveLearningClustering(List<DataPoint> data, GraphFitness fitnessEvaluation) {
		super(data, fitnessEvaluation);
        
		//build the neural network
        numInputs = data.get(0).getFeatures().size();
        StructuralInfo structuralInfo = new StructuralInfo(numInputs, numOutputs, new int[]{});
        Network neuralNetwork = new Network(structuralInfo);
        neuralNetwork.constructNetwork();
        
        //instantiate competitive learning with the neural network just built.
        competitiveLearning = new CompetitiveLearning(neuralNetwork);
		
	}

	@Override
	public void cluster() {
		
		// train network
		for (DataPoint datapoint : data) {
			competitiveLearning.trainIteration(datapoint);
		}
		
		// assign datapoints to clusters
		for (DataPoint datapoint : data) {
			
			//get highest output node index 
			int cluster = competitiveLearning.assign(datapoint);
			if (!clusterMap.containsKey(cluster))
				clusterMap.put(cluster, new ArrayList<DataPoint>());
			clusterMap.get(cluster).add(datapoint);
		}
		
		//build a set containing the keys to the n-largest clusters
		Set<Integer> clusterKeys = clusterMap.keySet();
		
		clusters = new ArrayList<>(clusterKeys.size());
		
		//loop through all cluster keys to convert each neural network output to a standard cluster object.
		for (Integer cluster : clusterKeys) {
			List<DataPoint> datapoints = clusterMap.get(cluster);
			List<Node> group = new ArrayList<Node>(datapoints.size());
			for (DataPoint datapoint : datapoints) {
				group.add(new Node(datapoint));
			}
			//add the datapoint to the converted cluster.
			clusters.add(group);
		}

	}

}
