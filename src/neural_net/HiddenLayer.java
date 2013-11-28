package neural_net;

/**
 * Layer implementation for hidden layers.
 */
public class HiddenLayer extends Layer{

	/**
	 * Creates a hidden layer of specified size.
	 * 
	 * @param size	The number of neurons to use in the layer.
	 */
	public HiddenLayer(int size) {
		super(size);
		// set activation function to sigmoidal
		layerActivation = new TanhActivation();
		// actually construct the layer
		buildLayer();
	}

}
