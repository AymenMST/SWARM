package neural_net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract layer class. Holds neurons and connections to the next layer.
 */
public abstract class Layer {
	
	protected List<Neuron> neurons;
	protected int size;
	protected ActivationFunction layerActivation;
	protected ActivationFunction layerActivationGradient;
	protected Map<Neuron, List<Connection>> outGoingConnections;

	/**
	 * Initialize Layer to a specific size.
	 * 
	 * @param size	The number of neurons that are contained in this layer.
	 */
	public Layer(int size) {
		// set size
		this.size = size;
		// create list of neurons
		neurons = new ArrayList<>(size);
		// construct outgoing connections from this layer's neurons to the next layer's
		outGoingConnections = new HashMap<Neuron, List<Connection>>();
	}

	/**
	 * Adds neurons to the layer.
	 */
	public void buildLayer() {
		// add new neurons with the correct activation 
		// function to the internal list of neurons
		for (int i = 0; i < size; i++) {
			neurons.add(new Neuron(layerActivation));
		}
	}

	/**
	 * @return	The internal list of neurons that the layer contains.
	 */
	public List<Neuron> getNeurons() {
		return neurons;
	}

	/**
	 * Sets the internal list of neurons.
	 * 
	 * @param neurons	The list of neurons that the layer will now hold.
	 */
	public void setNeurons(List<Neuron> neurons) {
		this.neurons = neurons;
	}

	/**
	 * Retrieves the connections stored between the current layer's
	 * neurons and the next layer's neurons.
	 * 
	 * @return	The outgoing connections.
	 */
	public Map<Neuron, List<Connection>> getOutGoingConnections() {
		return outGoingConnections;
	}

	/**
	 * Sets the connections that are stored between the current
	 * layer's neurons and the next layer's neurons.
	 * 
	 * @param outGoingConnections	The outgoing connections.
	 */
	public void setOutGoingConnections(Map<Neuron, List<Connection>> outGoingConnections) {
		this.outGoingConnections = outGoingConnections;
	}

	/**
	 * Adds a key value pair for a neuron in the current layer
	 * and all connections contain that neuron as 'fromNeuron'.
	 * 
	 * @param key		A neuron in the current layer.
	 * @param value		The list of connections that contain this neuron in their 'fromNeuron' field.
	 */
	public void addKeyValuePair(Neuron key, List<Connection> value) {
		outGoingConnections.put(key, value);
	}

}
