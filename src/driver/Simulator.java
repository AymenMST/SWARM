package driver;

import graph.Edge;
import graph.Graph;
import graph.Node;
import inputter.Inputter;
import inputter.InputterBanknote;

import java.awt.Color;
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
			double point1 = d.getFeatures().get(1);
			double point2 = d.getFeatures().get(2);
			Node vertex = new Node(d, new Point2D.Double((point1 + 20) * 20, (point2 + 20) * 20));
			vertex.setAlpha(0.2);
			g.addVertex(vertex);
		}

		ClusteringMethod cluster;

		// Test K Means
		cluster = new KMeansClustering(data);
		cluster.run();
		
		Forest<Node, Edge> centers = new Graph();
		double point1 = cluster.getCenters().get(0).get(1);
		double point2 = cluster.getCenters().get(0).get(2);
		Node center1 = new Node(null, new Point2D.Double((point1 + 20) * 20, (point2 + 20) * 20));
		center1.setColor(Color.GREEN);
		center1.setLayer(1);
		g.addVertex(center1);
		
		point1 = cluster.getCenters().get(1).get(1);
		point2 = cluster.getCenters().get(1).get(2);
		Node center2 = new Node(null, new Point2D.Double((point1 + 20) * 20, (point2 + 20) * 20));
		center2.setColor(Color.GREEN);
		center2.setLayer(1);
		g.addVertex(center2);
		
		JungHandler jungHandler = new JungHandler(g);
		jungHandler.draw();
		
	}

}
