package driver.inputter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import driver.DataPoint;

public class InputterTicTacToe extends Inputter {

	private final String filePath = "datasets/tic-tac-toe.data";

	/**
	 * Constructor for the TicTacToe input file. Data is of the form:
	 * <f1>, <f2> ... <f9>, where each f corresponds to a tile on a
	 * tic-tac-toe game board.
	 * 
	 * <f1> corresponds to the top-left square, <f2> to the
	 * top-middle-square, and <f4> to the middle-left square. I hope
	 * you can figure it out from there.... <3
	 * 
	 * Each feature can hold a single value of {x, o, b}, where x
	 * means the X player owns the spot, o means the O player owns the
	 * spot, and b means the spot is not yet taken.
	 */
	public InputterTicTacToe() {
		inputs = 9;
		outputs = 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Because the inputs are categorical, I am doing the following to
	 * make them numbers:
	 * 
	 * x -> 1; o -> -1; b -> 0
	 * 
	 * The outputs are binary, being 'positive' for a win, or
	 * 'negative' for a loss.
	 * 
	 * positive -> true; negative -> false
	 * 
	 * @see driver.Inputter#parseFile()
	 */
	@Override
	public void parseFile() {
		data = new ArrayList<>();
		try {
			Scanner in = new Scanner(new File(filePath));
			
			// find possible classes for dataset
			findClasses();

			// loop through entire data file.
			while (in.hasNext()) {
				String[] split = in.nextLine().split(",");
				List<Double> featureList = new ArrayList<>();

				// loop through all features, building up the
				// featureList list.
				// Data is split up according to mapping in header
				// comment.
				for (int featureIterator = 0; featureIterator < inputs; featureIterator++) {
					double featureValue = Double.MAX_VALUE;
					switch (split[featureIterator]) {
					case "x":
						featureValue = 1;
						break;
					case "o":
						featureValue = -1;
						break;
					case "b":
						featureValue = 0;
						break;
					default:
						System.out.println("Invalid character in parseFile of tic tac toe");
					}
					featureList.add(featureValue);
				}

				// get output. Inputs is a pointer to the point after
				// the last feature.
				// It is used questionably here, but it will work --
				// possibly consider something nicer
				List<Double> output = getOutputVector(split[inputs]);
				data.add(new DataPoint(featureList, output));
			}

			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found for poker dataset.");
			e.printStackTrace();
		} finally {

			// regardless of above, assign the possible classes to the
			// possible classes list.
			findClasses();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * This classification problem is a binary classification problem.
	 * We have two classes, and they are either 'positive' or
	 * 'negative'
	 * 
	 * @see driver.Inputter#findClasses()
	 */
	@Override
	public void findClasses() {
		possibleClasses = new ArrayList<>();
		possibleClasses.add("positive");
		possibleClasses.add("negative");
	}
}
