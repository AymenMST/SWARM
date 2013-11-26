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
	
	int numAnts = 25;
	int xSpace = 500;
	int ySpace = 500;
	
	public ACO(List<DataPoint> data, int xSpace, int ySpace) {
		this.xSpace = xSpace;
		this.ySpace = ySpace;
		initialize(data);
	}
	
	public ACO(List<DataPoint> data) {
		initialize(data);
	}
	
	public void initialize(List<DataPoint> data) {
		this.data = data;
		// place data points
		for (DataPoint d : data) {
			int x = rand.nextInt(xSpace);
			int y = rand.nextInt(ySpace);
			Node vertex = new Node(d, new Point2D.Double(x, y));
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
