package aco;

import graph.Edge;
import graph.Graph;
import graph.Heuristic;
import graph.HeuristicConstant;
import graph.Node;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import driver.DataPoint;
import edu.uci.ics.jung.graph.Forest;

public class ACO {
	
	static Heuristic heuristic = new HeuristicConstant();
	static double pheromoneInfluence = 1.0;
	static double attractivenessInfluence = 1.0;
	private List<DataPoint> data;
	Random rand = new Random(11235);
	Forest<Node, Edge> g = new Graph();
	Colony colony;
	
	int numAnts = 5;
	int xSpace = 1000;
	int ySpace = 1000;
	
	public ACO(List<DataPoint> data) {
		this.data = data;
		initialize();
	}
	
	public void initialize() {
		// place data points
		for (DataPoint d : data) {
			int x = rand.nextInt(xSpace);
			int y = rand.nextInt(ySpace);
			double point1 = d.getFeatures().get(1);
			double point2 = d.getFeatures().get(2);
			Node vertex = new Node(d, new Point2D.Double((point1 + 20) * 20, (point2 + 20) * 20));
			vertex.setAlpha(0.1);
			g.addVertex(vertex);
		}
		// place ants
		colony = new Colony(xSpace, ySpace, numAnts);
	}
	
	public double runIteration() {
		colony.move();
		colony.act(g);
		return 0.0;
	}
	
	public List<Ant> getAnts() {
		return colony.getAnts();
	}
	
	public Forest<Node, Edge> getGraph() {
		return g;
	}

}
