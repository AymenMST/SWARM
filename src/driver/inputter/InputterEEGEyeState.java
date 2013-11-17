package driver.inputter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import driver.DataPoint;

public class InputterEEGEyeState extends Inputter {

	private final String filePath = "datasets/EEGEyeState.data";

	/**
	 * Doesn't work again. I'm now thinking backprop doesn't work.
	 */
	public InputterEEGEyeState() {
		inputs = 14;
		outputs = 2;
	}

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
				for (int featureIterator = 0; featureIterator < inputs; featureIterator++)
					featureList.add(Double.valueOf(split[featureIterator]));

				// get output. Inputs is a pointer to the point after
				// the last feature.
				// It is used questionably here, but it will work --
				// possibly consider something nicer
				List<Double> output = getOutputVector(split[inputs]);
				data.add(new DataPoint(featureList, output));
			}

			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found for banknote dataset.");
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
	 * We have two classes, and they are either '0' or
	 * '1'
	 * 
	 * @see driver.Inputter#findClasses()
	 */
	@Override
	public void findClasses() {
		possibleClasses = new ArrayList<>();
		possibleClasses.add("0");
		possibleClasses.add("1");
	}
}
