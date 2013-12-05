package competitive_learning;

import java.util.List;
import java.util.Map;

import neural_net.Connection;
import neural_net.Layer;
import neural_net.Network;
import neural_net.Neuron;
import driver.DataPoint;

public class CompetitiveLearning {
	
	Network network;
	NetworkOperations operations;
	double learningRate = 0.8;
	Layer inputLayer, outputLayer;
	Map<Neuron, List<Connection>> weights;
	
	public CompetitiveLearning(Network network) {
		this.network = network;
		operations = new NetworkOperations(network);
		// set up some convenience variables
		inputLayer = network.getLayers().get(0);
		outputLayer = network.getLayers().get(1);
		weights = inputLayer.getOutGoingConnections();
	}
	
	public void trainIteration(DataPoint datapoint) {
		List<Double> inputs = datapoint.getFeatures();
		List<Double> outputs = operations.feedForward(datapoint);
		int winnerIndex = operations.getMaxIndex(outputs);
		
		// perform weight update
		for (int i = 0; i < inputs.size(); i++) {
			Neuron currentInputNeuron = inputLayer.getNeurons().get(i);
			// get the link to update
			Connection c = weights.get(currentInputNeuron).get(winnerIndex);
			// calculate weight change
			double updateValue =  learningRate * (inputs.get(i) - c.getWeight());
			// store and apply weight change
			c.setWeightChange(updateValue);
			c.setWeight(c.getWeight() + updateValue);
		}
	}
	
	public int assign(DataPoint datapoint) {
		return operations.getMaxIndex(operations.feedForward(datapoint));
	}

}
