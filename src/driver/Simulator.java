package driver;

import inputter.Inputter;
import inputter.InputterBanknote;

import java.util.ArrayList;
import java.util.List;

import roc.ROC;
import roc.Range;
import roc.TunableParameter;
import clustering.ClusteringMethod;
import clustering.KMeansClustering;
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
		
		PCA pca = new PCA(1);
		//data = pca.runPCA(data);


		// Test K Means
		//cluster.setVisualize(true);
		runKMeansClustering(data);
		
//		cluster = new CompetitiveLearningClustering(data, fitnessEvaluation);
//		cluster.run();
		
		// Test ACO
<<<<<<< HEAD
		cluster = new ACOClustering(data);
		cluster.setVisualize(true);
		cluster.setStartVisualize(50000);
=======
//		cluster = new ACOClustering(data, fitnessEvaluation);
//		//cluster.setVisualize(true);
//		//cluster.setStartVisualize(5000);
		
		// Test PSO
//		cluster = new PSOClustering(data, fitnessEvaluation);
		
>>>>>>> 55c3a34eb5144b17528a3c420e3d00269949909d
		cluster.run();
	}
	
	public void roc(){
		
	}

	
	public void runKMeansClustering(List<DataPoint> data){
		
		
		
		List<TunableParameter> tunableParameters = new ArrayList<>();
		
		tunableParameters.add(new TunableParameter<Integer>("k", 2, new Range<Integer>(2, 10)));
		
		ROC roc = new ROC(cluster, tunableParameters);
		
		cluster = new KMeansClustering(data, fitnessEvaluation);
	}
	
	
	public static void main(String[] args) {

		new Simulator();
	}

}
