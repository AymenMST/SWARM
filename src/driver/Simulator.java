package driver;

import inputter.Inputter;
import inputter.InputterBanknote;

import java.util.List;

import clustering.*;
import fitness.*;

/**
 * Main driver used to test various classifier training algorithms.
 */
public class Simulator {

	public static void main(String[] args) {

		int maxDataSetSize = 500;
//		GraphFitness fitnessEvaluation = new DunnGraphFitness();
		GraphFitness fitnessEvaluation = new DaviesBouldinGraphFitness();

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

		ClusteringMethod cluster;

		// Test K Means
//		cluster = new KMeansClustering(data, fitnessEvaluation);
////		cluster.setVisualize(true);
//		cluster.run();
		
		cluster = new CompetitiveLearningClustering(data, fitnessEvaluation);
		//cluster.run();
		
		// Test ACO
//		cluster = new ACOClustering(data, fitnessEvaluation);
//		cluster.setVisualize(true);
//		cluster.setStartVisualize(5000);
//		cluster.run();
		
		// Test PSO
//		cluster = new PSOClustering(data, fitnessEvaluation);
//		cluster.run();
		
	}

}
