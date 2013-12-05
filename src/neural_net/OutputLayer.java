package neural_net;

/**
 * Layer implementation for output layer.
 */
public class OutputLayer extends Layer {

	/**
	 * Creates a output layer of specified size.
	 * 
	 * @param size	The number of neurons to use in the layer.
	 */
	public OutputLayer(int size) {
		super(size);
		// set activation function to linear
		layerActivation = new LinearActivation();
		// actually construct the layer
		buildLayer();
	}

}
