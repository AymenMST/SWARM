package neural_net;

/**
 * Linear Activation function for neurons.
 */
public class LinearActivation extends ActivationFunction {

	@Override
	public double fire(double input) {
		return input;
	}
	
	@Override
	public double gradient(double input, double output) {
		return 1;
	}

}
