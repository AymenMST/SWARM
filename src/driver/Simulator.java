package driver;

import graph.Edge;
import graph.Graph;
import graph.Node;
import inputter.Inputter;
import inputter.InputterBanknote;

import java.util.List;

import clustering.ACOClustering;
import clustering.ClusteringMethod;
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

		ClusteringMethod cluster;

		// Test K Means
//		cluster = new KMeansClustering(data);
//		cluster.setVisualize(true);
//		cluster.run();
		
		// Test ACO
		cluster = new ACOClustering(data);
		cluster.setVisualize(true);
		cluster.run();
		
	}

}
