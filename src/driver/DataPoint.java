package driver;

import java.util.List;

/**
 * Structure that contains a datapoint example,
 * matching inputs to outputs.
 */
public class DataPoint {

	private List<Double> features;
	private List<Double> outputs;
	private int classIndex = -1;

	/**
	 * Constructs a datapoint with features and outputs.
	 * 
	 * @param features	The inputs to the datapoint
	 * @param outputs	The outputs to the datapoint
	 */
	public DataPoint(List<Double> features, List<Double> outputs) {
		setFeatures(features);
		setOutputs(outputs);
	}
	
	/**
	 * Prints the inputs to the datapoint.
	 */
	public void printFeatures() {
		for (Double value : features)
			System.out.print(value+" ");
		System.out.println();
	}
	
	/**
	 * Prints the outputs to the datapoint.
	 */
	public void printOutputs() {
		for (Double value : outputs)
			System.out.print(value+" ");
		System.out.println();
	}

	/**
	 * Retrieves the inputs of the datapoint.
	 * 
	 * @return	The inputs of the datapoint.
	 */
	public List<Double> getFeatures() {
		return features;
	}

	/**
	 * Sets the inputs of the datapoint.
	 * 
	 * @param features	The inputs of the datapoint.
	 */
	public void setFeatures(List<Double> features) {
		this.features = features;
	}
	
	/**
	 * Sets the outputs of the datapoint.
	 * 
	 * @param outputs	The outputs of the datapoint
	 */
	public void setOutputs(List<Double> outputs) {
		this.outputs = outputs;
	}
	
	/**
	 * Retrieves the outputs of the datapoint.
	 * 
	 * @return	The outputs of the datapoint.
	 */
	public List<Double> getOutputs() {
		return outputs;
	}
	
	/**
	 * If the datapoint is used for classification,
	 * returns the index of the class that should be chosen.
	 * 
	 * @return	The index of the target class.
	 */
	public int getClassIndex() {
		// find class index if not previously found
		if (classIndex < 0) {
			boolean found = false;
			// loop through all outputs of the datapoint
			for (int output = 0; output < outputs.size(); output++) {
				// if the output is not zero, mark as target class
				if (outputs.get(output) > 0.0) {
					// if there is more than one target class, throw error
					if (found)
						throw new IllegalArgumentException("Output vector must have only one nonzero value for classification.");
					// set target class
					classIndex = output;
					found = true;
				}
			}
		}
		// return the class index
		return classIndex;
	}

}
