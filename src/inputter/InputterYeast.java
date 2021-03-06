package inputter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import driver.DataPoint;

public class InputterYeast extends Inputter {

	private final String filePath = "datasets/yeast.data";

	public InputterYeast() {
		inputs = 8;
		outputs = 10;
		name = "Yeast";
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
			System.out.println("File not found for yeast dataset.");
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
		possibleClasses.add("CYT");
		possibleClasses.add("NUC");
		possibleClasses.add("MIT");
		possibleClasses.add("ME3");
		possibleClasses.add("ME2");
		possibleClasses.add("ME1");
		possibleClasses.add("EXC");
		possibleClasses.add("VAC");
		possibleClasses.add("POX");
		possibleClasses.add("ERL");
	}

}
