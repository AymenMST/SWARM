package neural_net;

/**
 * Layer implementation for input layer.
 */
public class InputLayer extends Layer{
	
	/**
	 * Creates a input layer of specified size.
	 * 
	 * @param size	The number of neurons to use in the layer.
	 */
	public InputLayer(int size) {
		super(size);
		// set activation function to linear
		layerActivation = new LinearActivation();
		// actually construct the layer
		buildLayer();
	}
	
}
