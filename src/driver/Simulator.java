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

	public Simulator(){
		int maxDataSetSize = 500;
		fitnessEvaluation = new DunnGraphFitness();
//		GraphFitness fitnessEvaluation = new DaviesBouldinGraphFitness();

		// get input data
		Inputter inputter;
		//inputter = new InputterLetterRecognition();
		// inputter = new InputterBlood();
		// inputter = new InputterGlass();
		// inputter = new InputterAbalone();
		
		//build a list of inputters
		
		// DR
		Inputter yeast = new InputterYeast();
		Inputter tictactoe = new InputterTicTacToe();
		Inputter seeds = new InputterSeeds();
		Inputter pen = new InputterPenDigits();
		Inputter optical = new InputterOpticalDigits();
		// LP
		Inputter banknote = new InputterBanknote();
		Inputter semeion = new InputterSemeion();
		Inputter eeg = new InputterEEGEyeState();
		Inputter car = new InputterCar();
		Inputter poker = new InputterPoker();
		
		List<Inputter> inputters = new ArrayList<>();
		inputters.add(yeast);
		inputters.add(tictactoe);
		inputters.add(seeds);
		inputters.add(pen);
		inputters.add(optical);
		
//		inputters.add(banknote);
//		inputters.add(semeion);
//		inputters.add(eeg);
//		inputters.add(car);
//		inputters.add(poker);

		
		for (Inputter in : inputters) {
			// parse data
			in.parseFile();
			in.truncate(maxDataSetSize);
			List<DataPoint> data = in.getData();
			
			PCA pca = new PCA(4);
			data = pca.runPCA(data);

			// Test K Means
			cluster = new KMeansClustering(data, fitnessEvaluation);
			
			// Test Competitive Learning
//			cluster = new CompetitiveLearningClustering(data, fitnessEvaluation);
			
			// Test ACO
//			cluster = new ACOClustering(data, fitnessEvaluation);
			
			// Test PSO
//			cluster = new PSOClustering(data, fitnessEvaluation);
			
			System.out.print(in+"\t");
			cluster.run();
		}
		
		//cluster.run();
	}
	
	public void runACOClustering(List<DataPoint> data){
		
	}
	
	
	public static void main(String[] args) {

		new Simulator();
	}

}
