package neural_net;

/**
 * Logistic Activation function (Sigmoidal) for neurons.
 */
public class LogisticActivation extends ActivationFunction {
	
	private double stretch = 0.5;

	@Override
	public double fire(double input) {
		return (stretch / (stretch + Math.exp(-input)));
	}
	
	@Override
	public double gradient(double input, double output) {
		return output * (1 - output);
	}
	

}
