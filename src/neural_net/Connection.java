package neural_net;

import java.util.Random;

/**
 * The connection between neurons in a neural network (synapses)
 */
public class Connection {

	private Neuron fromNeuron;
	private Neuron toNeuron;
	private double weight;
	private double weightChange;
	private Random rand;
	private double minWeight = -0.3;
	private double maxWeight = 0.3;
	private static int seed = 1;

	/**
	 * Creates a connection from one neuron to another.
	 * 
	 * @param fromNeuron	The neuron that passes information to the connection during feedforward process.
	 * @param toNeuron		The neuron that receives information from the connection during feedforward process.
	 */
	public Connection(Neuron fromNeuron, Neuron toNeuron) {
		// create random object with new seed for each connection
		rand = new Random(11235 * seed++);
		// set linked neurons
		this.fromNeuron = fromNeuron;
		this.toNeuron = toNeuron;
		// randomly initialize weight of connection
		weight = getRandomWeight();
		// set weight change to 0 by default
		weightChange = 0;
	}

	/**
	 * @return	A random value in the range (minWeight , maxWeight)
	 */
	private double getRandomWeight() {
		return minWeight + (rand.nextDouble() * (maxWeight - minWeight));
	}
	
	/**
	 * Add value to existing weight.
	 */
	public void appendWeight(){
		this.weight += weightChange;
	}

	/**
	 * @return	The neuron that passes information to the connection during feedforward process.
	 */
	public Neuron getFromNeuron() {
		return fromNeuron;
	}

	/**
	 * @return	The neuron that receives information from the connection during feedforward process.
	 */
	public Neuron getToNeuron() {
		return toNeuron;
	}

	/**
	 * @return	The value of the weight of the connection.
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Sets the weight of the connection to a new value.
	 * 
	 * @param weight	The new weight for the connection.
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return	The value of the last weight change.
	 */
	public double getWeightChange() {
		return weightChange;
	}

	/**
	 * Sets the amount by which the weight of the connection was changed.
	 * 
	 * @param weightChange	The amount by which the weight was changed.
	 */
	public void setWeightChange(double weightChange) {
		this.weightChange = weightChange;
	}

}
