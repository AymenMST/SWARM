package inputter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import driver.DataPoint;

public class InputterSemeion extends Inputter {

	private final String filePath = "datasets/optdigits.data";

	public InputterSemeion() {
		inputs = 256;
		outputs = 10;

	}

	@Override
	public void parseFile() {

		data = new ArrayList<>();
		try {
			Scanner in = new Scanner(new File(filePath));

			// loop through entire data file.
			while (in.hasNext()) {
				String[] split = in.nextLine().split(",");
				List<Double> featureList = new ArrayList<>();

				// loop through all features, building up the
				// featureList list.
				// Data is split up according to mapping in header
				// comment.
				for (int featureIterator = 0; featureIterator < inputs; featureIterator++) {
					featureList.add(Double.valueOf(split[featureIterator]));
				}

				// get output. Inputs is a pointer to the point after
				// the last feature.
				// It is used questionably here, but it will work --
				// possibly consider something nicer
				List<Double> output = getOutputVector(Integer.toString(getIndex(split, inputs)));
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
	
	private int getIndex(String[] split, int start) {
		int index = -1;
		for (int i = start; i < split.length; i++) {
			if (split[i] == "1") {
				if (index >= 0)
					System.out.println("ERROR: Only one class may be assigned to each example.");
				index = i - start;
			}
		}
		return index;
	}

	@Override
	public void findClasses() {
		possibleClasses = new ArrayList<String>() {{
			add("0"); add("1"); add("2"); add("3"); add("4");
			add("5"); add("6"); add("7"); add("8"); add("9");
		}};
	}

}
