package clustering;

import java.util.List;

import neural_net.Network;
import neural_net.StructuralInfo;
import driver.DataPoint;
import fitness.GraphFitness;

public class CompetitiveLearningClustering extends ClusteringMethod {
	
	int numInputs;
	int numOutputs = 15;
	int maxIterations = 100;

	public CompetitiveLearningClustering(List<DataPoint> data, GraphFitness fitnessEvaluation) {
		super(data, fitnessEvaluation);
        
        numInputs = data.get(0).getFeatures().size();
        
        StructuralInfo structuralInfo = new StructuralInfo(numInputs, numOutputs, new int[]{});
        Network neuralNetwork = new Network(structuralInfo);
        neuralNetwork.constructNetwork();
        structuralInfo.describe();
		
	}

	@Override
	public void cluster() {
		
		for (int i = 0; i < maxIterations; i++) {
			
		}

	}

}
