package clustering;

import java.util.List;

import visualizer.JungHandler;
import driver.DataPoint;
import edu.uci.ics.jung.graph.Forest;
import fitness.GraphFitness;
import graph.Edge;
import graph.Graph;
import graph.Node;

/**
 * Abstract Training Method class. Implementations are capable of training a
 * neural network using various algorithms.
 */
public abstract class ClusteringMethod {

	protected int outIndex;
	protected List<DataPoint> data;
	protected boolean visualize = false;
	protected Graph g = new Graph();
	protected JungHandler jungHandler = new JungHandler();
	protected List<List<Node>> clusters;
	GraphFitness fitnessEvaluation;

	/**
	 * Constructs a generic TrainingMethod class.
	 * 
	 * @param neuralNetwork
	 *            The neural network that the algorithm will train.
	 * @param data
	 *            The data that will be used to train the network.
	 */
	public ClusteringMethod(List<DataPoint> data, GraphFitness fitnessEvaluation) {
		this.data = data;
		this.fitnessEvaluation = fitnessEvaluation;
	}

	/**
	 * The main loop that performs all work related to training and testing the
	 * network.
	 * 
	 */
	public void run() {

		// used for timing the training algorithm
		long startTime, elapsedTime;
		
		// perform clustering algorithm and record runtime
		startTime = System.currentTimeMillis();
		cluster();
		elapsedTime = System.currentTimeMillis() - startTime;

		// evaluate the perfomance of the clustering algorithm
		double performance = evaluate();

		// print results
		System.out.println();
		
		System.out.println("   Clusters: "+clusters.size());
		System.out.println("Performance: "+performance);
		System.out.println("       Time: "+elapsedTime);

	}

	/**
	 * The train method will be different for each implementation.
	 */
	public abstract void cluster();


	/**
	 * @return	A fitness value of the clusters.
	 */
	public double evaluate() {
		return fitnessEvaluation.getFitness(clusters);
	}
	
	/**
	 * @param visualize	Whether or not visualization should be used for the algorithm.
	 */
	public void setVisualize(boolean visualize) {
		this.visualize = visualize;
	}

	/**
	 * @param startVisualize	At what time step visualization should start occurring.
	 */
	public void setStartVisualize(int startVisualize) {
		jungHandler.setStartVisualize(startVisualize);
	}
	
	/**
	 * @param directory	The directory to write images from the visualizer to.
	 */
	public void setVisualizeDirectory(String directory) {
		jungHandler.saveImagesTo(directory);
	}
	
	/**
	 * @return	The clusters returned by the clustering method.
	 */
	public List<List<Node>> getClusters() {
		return clusters;
	}

}
