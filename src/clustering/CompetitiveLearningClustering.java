package clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import roc.TunableParameter;
import neural_net.Network;
import neural_net.StructuralInfo;
import competitive_learning.CompetitiveLearning;
import driver.DataPoint;
import fitness.GraphFitness;
import graph.Node;

public class CompetitiveLearningClustering extends ClusteringMethod {
	
	CompetitiveLearning competitiveLearning;
	int numInputs;
	int numOutputs = 10;
	Map<Integer, List<DataPoint>> clusterMap = new HashMap<Integer, List<DataPoint>>();

	public CompetitiveLearningClustering(List<DataPoint> data, GraphFitness fitnessEvaluation) {
		super(data, fitnessEvaluation);
        
        numInputs = data.get(0).getFeatures().size();
        
        StructuralInfo structuralInfo = new StructuralInfo(numInputs, numOutputs, new int[]{});
        Network neuralNetwork = new Network(structuralInfo);
        neuralNetwork.constructNetwork();
        structuralInfo.describe();
        
        competitiveLearning = new CompetitiveLearning(neuralNetwork);
		
	}

	@Override
	public void cluster() {
		
		// train network
		for (DataPoint datapoint : data) {
			competitiveLearning.trainIteration(datapoint);
		}
		
		// assign clusters
		for (DataPoint datapoint : data) {
			int cluster = competitiveLearning.assign(datapoint);
			if (!clusterMap.containsKey(cluster))
				clusterMap.put(cluster, new ArrayList<DataPoint>());
			clusterMap.get(cluster).add(datapoint);
		}
		
		Set<Integer> clusterKeys = clusterMap.keySet();
		clusters = new ArrayList<>(clusterKeys.size());
		for (Integer cluster : clusterKeys) {
			List<DataPoint> datapoints = clusterMap.get(cluster);
			List<Node> group = new ArrayList<Node>(datapoints.size());
			for (DataPoint datapoint : datapoints) {
				group.add(new Node(datapoint));
			}
			clusters.add(group);
		}

	}

	@Override
	public void setTunableParameters(List<TunableParameter> tunableParameters) {
		// TODO Auto-generated method stub
		
	}

}
