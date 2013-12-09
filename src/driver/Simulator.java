package driver;

import inputter.*;

import java.util.ArrayList;
import java.util.List;

import clustering.*;
import fitness.DunnGraphFitness;
import fitness.GraphFitness;

/**
 * Main driver used to test various classifier training algorithms.
 */
public class Simulator {

	private ClusteringMethod cluster;
	private GraphFitness fitnessEvaluation;

	public Simulator() {
		int maxDataSetSize = 500;
		fitnessEvaluation = new DunnGraphFitness();
		// GraphFitness fitnessEvaluation = new
		// DaviesBouldinGraphFitness();

		// get input data
		Inputter inputter;
		// inputter = new InputterLetterRecognition();
		// inputter = new InputterBlood();
		// inputter = new InputterGlass();
		// inputter = new InputterAbalone();

		// build a list of inputters

		// DR
		inputter = new InputterYeast();

		// Inputter tictactoe = new InputterTicTacToe();
		// Inputter seeds = new InputterSeeds();
		// Inputter pe  = new InputterPenDigits();
		// Inputter optical = new InputterOpticalDigits();
		// // LP
		// Inputter banknote = new InputterBanknote();
		// Inputter semeion = new InputterSemeion();
		// Inputter eeg = new InputterEEGEyeState();
		// Inputter car = new InputterCar();
		// Inputter poker = new InputterPoker();

		// for (Inputter in : inputters) {
		// parse data
		inputter.parseFile();
		inputter.truncate(maxDataSetSize);
		List<DataPoint> data = inputter.getData();

		PCA pca = new PCA(1);
		data = pca.runPCA(data);

		// Test K Means
		System.out.println("KMeans");
		cluster = new KMeansClustering(data, fitnessEvaluation);
		System.out.print(inputter + "\t");
		cluster.run();

		// Test Competitive Learning

		System.out.println("\nCompetitive Learning");
		cluster = new CompetitiveLearningClustering(data, fitnessEvaluation);
		System.out.print(inputter + "\t");
		cluster.run();

		// Test ACO

		System.out.println("\nACO");
		cluster = new ACOClustering(data, fitnessEvaluation);
		System.out.print(inputter + "\t");
		cluster.run();

		// Test PSO

		System.out.println("\nPSO");
		cluster = new PSOClustering(data, fitnessEvaluation);
		System.out.print(inputter + "\t");
		cluster.run();

	}

	public void runACOClustering(List<DataPoint> data) {

	}

	public static void main(String[] args) {

		new Simulator();
	}

}
