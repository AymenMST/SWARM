package clustering;

import java.util.List;

import roc.TunableParameter;
import tools.Tools;
import aco.ACO;
import aco.Ant;
import driver.DataPoint;
import fitness.GraphFitness;
import graph.Graph;
import graph.Node;

public class ACOClustering extends ClusteringMethod {
	
	private ACO aco;
	private KMeansClustering kmeans;
	
	private double maxPerformance;
	
	// initialize tunable params
	private int checks = 1000;
	private int maxIterations = 50000;
	// the number of times performance is allowed to 
	// be worse during checks before exiting the loop
	private int maxWorse = 10;	
	boolean echo = false;

	/**
	 * Creates the driver for the ACO clustering algorithm.
	 * 
	 * @param data					The data to run ACO on.
	 * @param fitnessEvaluation		The fitness evaluation to use for ACO.
	 */
	public ACOClustering(List<DataPoint> data, GraphFitness fitnessEvaluation) {
		super(data, fitnessEvaluation);
		aco = new ACO(data);
		jungHandler.setDimensions(aco.getXSpace(), aco.getYSpace());
	}

	@Override
	public void cluster() {
		
		// identify "clusters" in the 2D virtual space
		kmeans = new KMeansClustering(data, fitnessEvaluation);
		maxPerformance = 0;
		List<List<Node>> bestClustering = null;
		int worseCount = 0;
		// loop until converged, or max iterations reached
		for (int i = 0; worseCount < maxWorse && i < maxIterations; i++) {
			
			// run a single iteration of the ACO algorithm
			aco.runIteration();
			
			// draw the visualizer for the ACO
			if (visualize)
				drawGraph();
			
			// stop to evaluate fitness of the created graph
			if (i%checks == 0) {
				kmeans.setPseudoGraph(aco.getGraph());
				kmeans.cluster();
				this.clusters = kmeans.clusters;
				double fitness = fitnessEvaluation.getFitness(clusters);
				if (echo)
					System.out.println(Tools.round(fitness, 4));
				
				
				if (fitness > maxPerformance) {
					worseCount = 0;
					maxPerformance = fitness;
					bestClustering = clusters;
				} else {
					worseCount++;
				}
			}
			clusters = bestClustering;
			
			// TODO: stop when fitness is optimal
			
		}
		
	}
	
	/**
	 * Draw a graph of the ACO algorithm at the current time step.
	 */
	public void drawGraph() {
		
		g = new Graph();
		
		// add data points
		for (Node vertex : aco.getGraph().getVertices()) {
			g.addVertex(vertex);
		}
		
		// add ants
		for (Ant ant : aco.getAnts()) {
			Node node = ant.toNode();
			
			node.setColor(ant.getColor());
			g.addVertex(node);
		}
		
		// draw graph
		jungHandler.setGraph(g);
		jungHandler.draw();
		
	}

	@Override
	public void setTunableParameters(List<TunableParameter> tunableParameters) {
		// TODO Auto-generated method stub
		
	}

	
}
