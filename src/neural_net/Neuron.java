package neural_net;

/**
 * Neurons for use in a variety of neural networks.
 */
public class Neuron {

	private ActivationFunction neuronActivation;
	private double neuronInput;
	private double neuronOutput;
	private double neuronError;

	/**
	 * Creates a new neuron that uses the specified activation function.
	 * 
	 * @param neuronActivation	The activation function that should be used by the neuron.
	 */
	public Neuron(ActivationFunction neuronActivation) {
		this.neuronActivation = neuronActivation;
		neuronInput = 0;
		neuronOutput = 0;
		neuronError = 0;
	}

	/**
	 * @return	The last input that was sent to the neuron.
	 */
	public double getNeuronInput() {
		return neuronInput;
	}
	
	/**
	 * @return	The last output returned by the neuron's activation function.
	 */
	public double getNeuronOutput() {
		return neuronOutput;
	}

	/**
	 * Sets the value to be processed by the neuron.
	 * 
	 * @param neuronInput	The input that should be processed by the neuron's activation function.
	 */
	public void setNeuronInput(double neuronInput) {
		this.neuronInput = neuronInput;
	}

	/**
	 * @return	The error stored at this particular neuron.
	 */
	public double getNeuronError() {
		return neuronError;
	}

	/**
	 * Sets the neuron's individual contribution to the error of the entire network.
	 * This is used during the training process.
	 * 
	 * @param neuronError	The error that was calculated for this neuron.
	 */
	public void setNeuronError(double neuronError) {
		this.neuronError = neuronError;
	}

	/**
	 * Adds a value to the neuron's existing input.
	 * This is useful when summing all incoming inputs from connections.
	 * 
	 * @param value	The value that will be appended to the neuron's input.
	 */
	public void appendNeuronInput(double value) {
		this.neuronInput += value;
	}

	/**
	 * Runs the neuron's input value through its activation function.
	 * The result is stored in the neuron's output and also returned.
	 * 
	 * @return	The result of running the input through the activation function.
	 */
	public double fire() {
		this.neuronOutput = neuronActivation.fire(this.neuronInput);
		return this.neuronOutput;
	}
	
	/**
	 * @return	The output returned by the last activation firing.
	 */
	public double getOutput() {
		return this.neuronOutput;
	}
	
	/**
	 * Runs the neuron's input through the gradient function
	 * (derivative of the activation function).
	 * This value is not stored anywhere, but instead returned 
	 * directly by the function.
	 * 
	 * @return	The result of running the input through the gradient function.
	 */
	public double gradient() {
		return neuronActivation.gradient(this.neuronInput, this.neuronOutput);
	}
}
