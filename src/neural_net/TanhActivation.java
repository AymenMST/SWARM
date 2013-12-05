package neural_net;

/**
 * Hyperbolic Tangent Activation function (Sigmoidal) for neurons.
 */
public class TanhActivation extends ActivationFunction {
	
	double scale = 1.0;

	@Override
	public double fire(double input) {
		input /= scale;
		return ((Math.pow(Math.E, input) - Math.pow(Math.E, -input)) / (Math.pow(Math.E, input) + Math.pow(Math.E, -input)));
	}
	
	@Override
	public double gradient(double input, double output) {
		return (1.0 - Math.pow(output, 2)) / scale;
	}
	

}
