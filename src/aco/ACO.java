package aco;

import graph.Graph;
import graph.Node;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;

import driver.DataPoint;

public class ACO {
	
	Random rand = new Random(11235);
	Graph g = new Graph();
	Colony colony;
	
	// initialize tunable parameters
	static int numAnts = 20;
	static double pickupGain = 0.05;
	static double dropGain = 0.00005;
	static double worseDropoffProbability = 0.05;
	static int xSpace = 400;
	static int ySpace = 400;
	static int neighborhoodSize = 35;
	static int maxMove = 40;
	
	/**
	 * Creates a new ACO algorithm to be run on specified data.
	 * 
	 * @param data		The data to run the ACO clustering on.
	 * @param xSpace	The width of the virtual space.
	 * @param ySpace	The height of the virtual space.
	 */
	public ACO(List<DataPoint> data, int xSpace, int ySpace) {
		this.xSpace = xSpace;
		this.ySpace = ySpace;
		initialize(data);
	}
	
	/**
	 * Creates a new ACO algorithm to be run on specified data.
	 * 
	 * @param data		The data to run the ACO clustering on.
	 */
	public ACO(List<DataPoint> data) {
		initialize(data);
	}
	
	/**
	 * Places datapoints and ants randomly in virtual space.
	 * 
	 * @param data
	 */
	public void initialize(List<DataPoint> data) {
		// place data points
		for (DataPoint d : data) {
			int x = rand.nextInt(xSpace);
			int y = rand.nextInt(ySpace);
			Node vertex = new Node(d, new Point2D.Double(x, y));
			vertex.setAlpha(0.1);
			g.addVertex(vertex);
		}
		// place ants
		colony = new Colony(numAnts);
	}
	
	/**
	 * Runs a single iteration of the ACO algorithm.
	 */
	public void runIteration() {
		colony.move();
		colony.act(g);
	}
	
	/**
	 * @return	A list of the ants being used by the algorithm.
	 */
	public List<Ant> getAnts() {
		return colony.getAnts();
	}
	
	/**
	 * @return	The graph being modified by the ACO algorithm.
	 */
	public Graph getGraph() {
		return g;
	}
	
	/**
	 * @return	The width of the virtual space.
	 */
	public int getXSpace() {
		return xSpace;
	}
	
	/**
	 * @return	The height of the virtual space.
	 */
	public int getYSpace() {
		return ySpace;
	}

}
