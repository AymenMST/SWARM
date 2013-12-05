package competitive_learning;

import java.util.ArrayList;
import java.util.List;

import neural_net.Connection;
import neural_net.Layer;
import neural_net.Network;
import neural_net.Neuron;
import driver.DataPoint;

/**
 * Performs operations on a constructed network.
 */
public class NetworkOperations {

	private Network neuralNetwork;

	/**
	 * Creates a new NetworkOperations class.
	 * 
	 * @param neuralNetwork	The network to perform operations on.
	 */
	public NetworkOperations(Network neuralNetwork) {
		this.neuralNetwork = neuralNetwork;
	}

	/**
	 * Feed forward method. First, we call the setInputs method to
	 * build inputs. Then, starting with the input layer, we loop
	 * through layers and feed the input values forward. The output is
	 * calculated in the end, and the values of each output neuron
	 * are returned.
	 * 
	 * @param dataPoint
	 *            data point we want to feed through the network,
	 *            getting output
	 * 
	 * @return vector of each output neuron's result.
	 */
	public List<Double> feedForward(DataPoint dataPoint) {
		
		List<Double> outputs = new ArrayList<>();

		// set the inputs according to the feature values of the data
		// point we are feeding forward.
		setInputs(dataPoint.getFeatures());

		// loop through all layers, multiplying neurons and weights,
		// and finally activating neurons. We do NOT loop through the
		// output layer
		for (int layerNum = 0; layerNum < neuralNetwork.getLayers().size() - 1; layerNum++) {
			Layer currentLayer = neuralNetwork.getLayers().get(layerNum);
			Layer nextLayer = neuralNetwork.getLayers().get(layerNum + 1);
			
			// reset the inputs for the next layer
			for (Neuron neuron : nextLayer.getNeurons())
				neuron.setNeuronInput(0.0);

			// loop through all neurons in the current layer. We first
			// fire the neuron, then we perform operations on
			// downstream neurons.
			for (Neuron currentNeuron : currentLayer.getNeurons()) {
				double value = currentNeuron.fire();

				// Loop through every outgoing connection from the
				// current neuron. Because of the map used to hold
				// this information, we build up neuron values at the
				// next layer. They are then activated in the next
				// layer loop.
				for (Connection c : currentLayer.getOutGoingConnections().get(currentNeuron)) {
					c.getToNeuron().appendNeuronInput(value * c.getWeight());
				}
			}
			
		}

		// output layer operations here
		Layer outputLayer = neuralNetwork.getLayers().get(neuralNetwork.getLayers().size() - 1);
		
		// Add each output neuron's value to the vector that will be returned
		for (Neuron currentNeuron : outputLayer.getNeurons())
			outputs.add(currentNeuron.fire());
		
		return outputs;
	}
	
	/**
	 * Returns the index of the largest output value,
	 * which equates to the class that should be chosen
	 * in classification problems.
	 * 
	 * @param outputs	The list of outputs returned by the network
	 * @return			The index of the largest output in the list
	 */
	public int getMaxIndex(List<Double> outputs) {
		
		int maxIndex = 0;
		Double max = Double.MIN_VALUE;
		
		// loop through output values, finding the index of the largest value
		for (int outputIndex = 0; outputIndex < outputs.size(); outputIndex++) {
			Double currentValue = outputs.get(outputIndex).doubleValue();
			// if current value is larger that previous max value, 
			// overwrite max value and store index
			if (currentValue > max) {
				maxIndex = outputIndex;
				max = currentValue;
			}
		}
		
		return maxIndex;
		
	}

	/**
	 * Method to set the values of the input neurons to the values of
	 * the features, from a data point.
	 * 
	 * @param features
	 *            list of all features in current data point
	 */
	public void setInputs(List<Double> features) {
		Layer inputLayer = neuralNetwork.getLayers().get(0);

		// ensure that the number of inputs matches the number of input neurons
		if (features.size() != inputLayer.getNeurons().size()) {
			throw new IllegalArgumentException("Number of features does not match input layer size");
		}

		// loop through all input neurons, assigning the corresponding
		// feature value to each neuron
		for (int neuronNum = 0; neuronNum < inputLayer.getNeurons().size(); neuronNum++) {
			inputLayer.getNeurons().get(neuronNum).setNeuronInput(features.get(neuronNum));
		}
	}

}
