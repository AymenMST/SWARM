package neural_net;

import java.util.ArrayList;
import java.util.List;

/**
 * Network class that contains entire structure of neural network.
 */
public class Network {
	private List<Layer> layers;
	private StructuralInfo structuralInfo;

	/**
	 * Initializes a new Neural Network using given structural information.
	 * 
	 * @param structuralInfo	The object containing all structural information required to build a neural network.
	 */
	public Network(StructuralInfo structuralInfo) {
		this.structuralInfo = structuralInfo;
	}

	/**
	 * Method to build a neural network. Each layer is constructed
	 * from the structural info object, and then the connections
	 * between layers are established.
	 */
	public void constructNetwork() {
		layers = new ArrayList<>();

		// build input layer
		Layer input = new InputLayer(structuralInfo.getNumInputs());
		layers.add(0, input);

		// build all hidden layers from structural info
		for (int hiddenLayerNum = 0; hiddenLayerNum < structuralInfo.getHiddenLayerInformation().length; hiddenLayerNum++) {
			Layer hiddenLayer = new HiddenLayer(structuralInfo.getHiddenLayerInformation()[hiddenLayerNum]);
			layers.add(hiddenLayerNum + 1, hiddenLayer);
		}

		// build output layer
		Layer outputLayer = new OutputLayer(structuralInfo.getNumOutputs());
		layers.add(outputLayer);

		// build all connections between layers
		buildConnections();
	}

	/**
	 * Method to build connections between layers. All layers are
	 * looped through, and all neurons from consecutive layers are
	 * created as Connection objects, which are stored in Layer.
	 * 
	 */
	private void buildConnections() {
		// loop through all layers, minus the output layer
		for (int layerNum = 0; layerNum < layers.size() - 1; layerNum++) {
			Layer currentLayer = layers.get(layerNum);
			Layer nextLayer = layers.get(layerNum + 1);

			// loop through all neurons in the current layer
			for (int neuronNum = 0; neuronNum < currentLayer.getNeurons().size(); neuronNum++) {

				Neuron neuron = currentLayer.getNeurons().get(neuronNum);
				List<Connection> outGoingConnections = new ArrayList<>();

				// loop through all neurons in the next layer, adding
				// them to a connection list.

				// The layer will eventually hold the connection list
				// as a hash map with the neuron being the key.
				for (int nextNeuronNum = 0; nextNeuronNum < nextLayer.getNeurons().size(); nextNeuronNum++) {
					outGoingConnections.add(new Connection(neuron, nextLayer.getNeurons().get(nextNeuronNum)));
				}

				// adds the list of connections stemming from a
				// neuron.
				currentLayer.addKeyValuePair(neuron, outGoingConnections);
			}
		}
	}
	
	/**
	 * @return	The total number of weights (connections) in the neural network.
	 */
	public int size() {
		int size = 0;
		// loop through all layers until the output is reached
		for (int layerIndex = 0; layerIndex < layers.size() - 1; layerIndex++) {
			Layer layer = layers.get(layerIndex);
			// loop through all neurons in the current layer
			for (Neuron neuron : layer.getNeurons()) {
				// add the amount of connections for the current neuron to the count of all connections
				if (layer.getOutGoingConnections() != null)
					size += layer.getOutGoingConnections().get(neuron).size();
			}
		}
		return size;
	}

	/**
	 * @return	The list of layer objects contained for the network.
	 */
	public List<Layer> getLayers() {
		return layers;
	}

	/**
	 * Sets the layers that will be used by the network.
	 * 
	 * @param layers	The list of layer objects to be used by the network.
	 */
	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}

}
