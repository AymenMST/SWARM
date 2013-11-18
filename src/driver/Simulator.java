package driver;

import java.util.List;

import training.*;
import driver.inputter.*;

/**
 * Main driver used to test various classifier training algorithms.
 */
public class Simulator {

	public static void main(String[] args) {
		
		int maxDataSetSize = 1000;

		// get input data
		Inputter inputter;
		inputter = new InputterLetterRecognition();
		inputter = new InputterPenDigits();
		inputter = new InputterOpticalDigits();
		inputter = new InputterSemeion();
//		inputter = new InputterAbalone();
//		inputter = new InputterBanknote();
//		inputter = new InputterBlood();
//		inputter = new InputterCar();
//		inputter = new InputterEEGEyeState();
//		inputter = new InputterGlass();
//		inputter = new InputterPoker();
//		inputter = new InputterSeeds();
//		inputter = new InputterTicTacToe();
//		inputter = new InputterYeast();
		
		
		// parse data
		inputter.parseFile();
		inputter.truncate(maxDataSetSize);
		List<DataPoint> data = inputter.getData();
		
		TrainingMethod train;

		// Test K Means
		train = new KMeansTraining(data);

		// Test Competitive Learning
//		train = new CompetitiveLearningTraining(data);
		
		//Test DE
//		train = new ACOTraining(data);
		
		// Test ES
//		train = new PSOTraining(data);
		
		train.mainLoop(10);

	}

	// Utility function used in debugging to print vectors.
	public static void printVector(List<Double> vector) {
		for (Double value : vector)
			System.out.print(value + " ");
		System.out.println();
	}

}
