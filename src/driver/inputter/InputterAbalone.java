package driver.inputter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import driver.DataPoint;

public class InputterAbalone extends Inputter {

	private final String filePath = "datasets/abalone.txt";

	public InputterAbalone() {
		inputs = 8;
		outputs = 29;
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
				for (int featureIterator = 0; featureIterator < inputs; featureIterator++) {
					if (featureIterator == 0) {
						String firstIndex = split[featureIterator];
						if (firstIndex.equals("M")) {
							split[featureIterator] = -1.0 + "";
						} else if (firstIndex.equals("F")) {
							split[featureIterator] = 0.0 + "";
						} else if (firstIndex.equals("I")) {
							split[featureIterator] = 1.0 + "";
						} else {
							System.out.println("Should not be here. Abalone.");
						}
					}

					featureList.add(Double.valueOf(split[featureIterator]));
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
			System.out.println("File not found for abalone dataset.");
			e.printStackTrace();
		} finally {

			// regardless of above, assign the possible classes to the
			// possible classes list.
			findClasses();
		}
	}

	@Override
	public void findClasses() {
		possibleClasses = new ArrayList<>();

		for (int i = 0; i < outputs; i++) {
			possibleClasses.add("" + i);
		}
	}

}
