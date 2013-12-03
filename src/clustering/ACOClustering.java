package clustering;

import java.util.List;

import tools.Tools;
import aco.ACO;
import aco.Ant;
import driver.DataPoint;
import fitness.GraphFitness;
import graph.Graph;
import graph.Node;

public class ACOClustering extends ClusteringMethod {
	
	private ACO aco;
	private int xSpace = 800;
	private int ySpace = 800;
	private KMeansClustering kmeans;
	private int checks = 100;
	

	public ACOClustering(List<DataPoint> data, GraphFitness fitnessEvaluation) {
		super(data, fitnessEvaluation);
		aco = new ACO(data, xSpace, ySpace);
		jungHandler.setDimensions(xSpace, ySpace);
	}

	@Override
	public void cluster() {
		
		kmeans = new KMeansClustering(data, fitnessEvaluation);
		
		for (int i = 0; i < 50000; i++) {
			if (visualize && !jungHandler.isSavingImages()) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {	e.printStackTrace();	}
			}
			aco.runIteration();
			if (visualize)
				drawGraph();
			
			if (i%checks == 0) {
				kmeans.setPseudoGraph(aco.getGraph());
				kmeans.cluster();
				this.clusters = kmeans.clusters;
				double fitness = fitnessEvaluation.getFitness(clusters);
				System.out.println(Tools.round(fitness, 4));
			}
		}
		
	}
	
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
			node.setLayer(1);
			g.addVertex(node);
		}
		
		jungHandler.setGraph(g);
		jungHandler.draw();
		
	}

	@Override
	public List<List<Double>> getCenters() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
