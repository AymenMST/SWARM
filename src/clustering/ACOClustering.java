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
	private int xSpace = 800;
	private int ySpace = 800;
	

	public ACOClustering(List<DataPoint> data) {
		super(data);
		aco = new ACO(data, xSpace, ySpace);
		jungHandler.setDimensions(xSpace, ySpace);
	}

	@Override
	public void cluster() {
		for (int i = 0; i < 500000; i++) {
			if (visualize && !jungHandler.isSavingImages()) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {	e.printStackTrace();	}
			}
			aco.runIteration();
			if (visualize)
				drawGraph();
			if (i  > 30) {
				System.out.println("breakpoint");
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
