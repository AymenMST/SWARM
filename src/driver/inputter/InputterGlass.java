package driver.inputter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import driver.DataPoint;

public class InputterGlass extends Inputter {

	private final String filePath = "datasets/glass.data";

	public InputterGlass() {
		inputs = 9;
		outputs = 6;
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
				for (int featureIterator = 1; featureIterator < inputs + 1; featureIterator++) {
					featureList.add(Double.valueOf(split[featureIterator]));
				}

				// get output. Inputs is a pointer to the point after
				// the last feature.
				// It is used questionably here, but it will work --
				// possibly consider something nicer
				List<Double> output = getOutputVector(split[inputs + 1]);
				data.add(new DataPoint(featureList, output));
			}

			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found for glass dataset.");
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
		possibleClasses.add("1");
		possibleClasses.add("2");
		possibleClasses.add("3");
		possibleClasses.add("4");
		possibleClasses.add("5");
		possibleClasses.add("6");

	}

}
