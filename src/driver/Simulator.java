package driver;

import graph.Edge;
import graph.Graph;
import graph.Node;
import inputter.Inputter;
import inputter.InputterBanknote;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;

import visualizer.JungHandler;
import clustering.ClusteringMethod;
import clustering.KMeansClustering;
import edu.uci.ics.jung.graph.Forest;

/**
 * Main driver used to test various classifier training algorithms.
 */
public class Simulator {

	public static void main(String[] args) {

		int maxDataSetSize = 1000;

		// get input data
		Inputter inputter;
		//inputter = new InputterLetterRecognition();
		// inputter = new InputterPenDigits();
		// inputter = new InputterOpticalDigits();
		// inputter = new InputterSemeion();
		// inputter = new InputterAbalone();
		 inputter = new InputterBanknote();
		// inputter = new InputterBlood();
		// inputter = new InputterCar();
		// inputter = new InputterEEGEyeState();
		// inputter = new InputterGlass();
		// inputter = new InputterPoker();
		// inputter = new InputterSeeds();
		// inputter = new InputterTicTacToe();
		// inputter = new InputterYeast();

		// parse data
		inputter.parseFile();
		inputter.truncate(maxDataSetSize);
		List<DataPoint> data = inputter.getData();

		Forest<Node, Edge> g = new Graph();

		int xSpace = 500;
		int ySpace = 500;

		Random rand = new Random();

		//Banknote = (point1 + 20) * 20
		for (DataPoint d : data) {
			int x = rand.nextInt(xSpace);
			int y = rand.nextInt(ySpace);
			double point1 = d.getFeatures().get(0);
			double point2 = d.getFeatures().get(3);
			g.addVertex(new Node(d, new Point2D.Double((point1 + 20) * 20, (point2 + 20) * 20)));
		}

		JungHandler jungHandler = new JungHandler(g);

		ClusteringMethod cluster;

		// Test K Means

		cluster = new KMeansClustering(data);
		cluster.run();
	}

	// Utility function used in debugging to print vectors.
	public static void printVector(List<Double> vector) {
		for (Double value : vector)
			System.out.print(value + " ");
		System.out.println();
	}

}
