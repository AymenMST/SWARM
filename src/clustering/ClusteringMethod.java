package clustering;

import java.util.List;

import visualizer.JungHandler;
import driver.DataPoint;
import edu.uci.ics.jung.graph.Forest;
import fitness.DunnGraphFitness;
import fitness.Fitness;
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
	protected Forest<Node, Edge> g = new Graph();
	protected JungHandler jungHandler = new JungHandler();
	protected List<List<Node>> clusters;
	Fitness fitness;

	/**
	 * Constructs a generic TrainingMethod class.
	 * 
	 * @param neuralNetwork
	 *            The neural network that the algorithm will train.
	 * @param data
	 *            The data that will be used to train the network.
	 */
	public ClusteringMethod(List<DataPoint> data) {
		this.data = data;
	}

	/**
	 * The main loop that performs all work related to training and testing the
	 * network.
	 * 
	 */
	public void run() {

		// used for timing the training algorithm
		long startTime, elapsedTime;
		
		
		startTime = System.currentTimeMillis();
		cluster();
		elapsedTime = System.currentTimeMillis() - startTime;

		double performance = evaluate();

		System.out.println();
		System.out.println(performance + "\t" + elapsedTime);

	}

	/**
	 * The train method will be different for each implementation.
	 * 
	 */
	public abstract void cluster();


	public double evaluate() {
		//fitness = new DaviesBouldinGraphFitness(clusters);
		fitness = new DunnGraphFitness(clusters);
		return fitness.getFitness();
	}
	
	public abstract List<List<Double>> getCenters();
	
	public void setVisualize(boolean visualize) {
		this.visualize = visualize;
	}

	public void setStartVisualize(int startVisualize) {
		jungHandler.setStartVisualize(startVisualize);
	}
	
	public void setVisualizeDirectory(String directory) {
		jungHandler.saveImagesTo(directory);
	}
	
	public List<List<Node>> getClusters() {
		return clusters;
	}

}
