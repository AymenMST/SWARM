package inputter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import driver.DataPoint;

public class InputterLetterRecognition extends Inputter {

	private final String filePath = "datasets/letter-recognition.data";

	public InputterLetterRecognition() {
		inputs = 16;
		outputs = 26;

	}

	@Override
	public void parseFile() {
		
		findClasses();

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
				for (int featureIterator = 1; featureIterator <= inputs; featureIterator++) {
					featureList.add(Double.valueOf(split[featureIterator]));
				}

				// get output. Inputs is a pointer to the point after
				// the last feature.
				// It is used questionably here, but it will work --
				// possibly consider something nicer
				List<Double> output = getOutputVector(split[0].toLowerCase());
				data.add(new DataPoint(featureList, output));
			}

			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found for yeast dataset.");
			e.printStackTrace();
		}

	}

	@SuppressWarnings("serial")
	@Override
	public void findClasses() {
		possibleClasses = new ArrayList<String>() {{
			add("a"); add("b"); add("c"); add("d"); add("e"); add("f"); add("g");
			add("h"); add("i"); add("j"); add("k"); add("l"); add("m"); add("n");
			add("o"); add("p"); add("q"); add("r"); add("s"); add("t"); add("u");
			add("v"); add("w"); add("x"); add("y"); add("z");
		}};
	}

}
