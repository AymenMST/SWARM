package neural_net;

/**
 * Class that stores all structural information needed to create a neural network.
 */
public class StructuralInfo {
	
	private int numInputs;
	private int numOutputs;
	private int[] hiddenLayerInformation;
	
	/**
	 * Initializes a set of information that can be used to create a neural network.
	 * 
	 * @param numInputs					The number of inputs that will be passed to a neural network.
	 * @param numOutputs				The number of outputs that are expected from a neural network.
	 * @param hiddenLayerInformation	The size of each hidden layer in array format.
	 */
	public StructuralInfo(int numInputs, int numOutputs, int[] hiddenLayerInformation) {
		this.numInputs = numInputs;
		this.numOutputs = numOutputs;
		this.hiddenLayerInformation = hiddenLayerInformation;
	}

	/**
	 * @return	The number of inputs that will be passed to a neural network.
	 */
	public int getNumInputs() {
		return numInputs;
	}

	/**
	 * Sets the number of inputs that will be passed to a neural network.
	 * 
	 * @param numInputs	The number of inputs that will be passed to a neural network.
	 */
	public void setNumInputs(int numInputs) {
		this.numInputs = numInputs;
	}

	/**
	 * @return	The number of outputs that will be expected from a neural network.
	 */
	public int getNumOutputs() {
		return numOutputs;
	}

	/**
	 * Sets the number of outputs that are expected from a neural network.
	 * 
	 * @param numOutputs	The number of outputs that are expected from a neural network.
	 */
	public void setNumOutputs(int numOutputs) {
		this.numOutputs = numOutputs;
	}

	/**
	 * @return	An array of the sizes of each hidden layer.
	 */
	public int[] getHiddenLayerInformation() {
		return hiddenLayerInformation;
	}

	/**
	 * Sets the size of each hidden layer in array format.
	 * 
	 * @param hiddenLayerInformation	An array of the sizes of each hidden layer.
	 */
	public void setHiddenLayerInformation(int[] hiddenLayerInformation) {
		this.hiddenLayerInformation = hiddenLayerInformation;
	}
	
	/**
	 * Prints a description of the neural network's structure to standard out.
	 */
	public void describe() {
		System.out.println(toString());
	}
	
	/**
	 * Constructs a string containing the size of each layer in the network.
	 */
	public String toString() {
		String description = "{";
		// add size of input layer
		description += getNumInputs() + ",";
		// for each hidden layer, add size of hidden layer
		for (int n : hiddenLayerInformation)
			description += n + ",";
		// add size of output layer
		description += getNumOutputs() + "}";
		return description;
	}

}
