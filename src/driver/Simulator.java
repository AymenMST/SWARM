package driver;

import graph.Edge;
import graph.Graph;
import graph.Node;
import inputter.Inputter;
import inputter.InputterLetterRecognition;

import java.awt.Point;
import java.util.List;
import java.util.Random;

import visualizer.JungHandler;
import edu.uci.ics.jung.graph.Forest;

/**
 * Main driver used to test various classifier training algorithms.
 */
public class Simulator {

	public static void main(String[] args) {

		int maxDataSetSize = 5;

		// get input data
		Inputter inputter;
		inputter = new InputterLetterRecognition();
		// inputter = new InputterPenDigits();
		// inputter = new InputterOpticalDigits();
		// inputter = new InputterSemeion();
		// inputter = new InputterAbalone();
		// inputter = new InputterBanknote();
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

		int xSpace = 100;
		int ySpace = 100;

		Random rand = new Random();

		for (DataPoint d : data) {
			int x = rand.nextInt(xSpace);
			int y = rand.nextInt(ySpace);
			g.addVertex(new Node(d, new Point(x, y)));
		}
		
		JungHandler jungHandler = new JungHandler(g);

		// TrainingMethod train;

		// Test K Means

		// train = new KMeansTraining(data);

		// Test Competitive Learning
		// train = new CompetitiveLearningTraining(data);

		// Test ACO
		// train = new ACOTraining(data);

		// Test PSO
		// train = new PSOTraining(data);

		// train.mainLoop(10);

	}

	// Utility function used in debugging to print vectors.
	public static void printVector(List<Double> vector) {
		for (Double value : vector)
			System.out.print(value + " ");
		System.out.println();
	}

}
