package clustering;

import graph.Graph;
import graph.Node;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.List;

import aco.ACO;
import aco.Ant;
import driver.DataPoint;

public class ACOClustering extends ClusteringMethod {
	
	private ACO aco;

	public ACOClustering(List<DataPoint> data) {
		super(data);
		aco = new ACO(data);
		if (visualize)
			drawGraph();
	}

	@Override
	public void cluster() {
		for (int i = 0; i < 100; i++) {
			if (visualize) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {	e.printStackTrace();	}
			}
			aco.runIteration();
			if (visualize)
				drawGraph();
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
			Node node = new Node(null, ant.getLocation());
			
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
