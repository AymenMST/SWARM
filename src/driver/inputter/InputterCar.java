package driver.inputter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import driver.DataPoint;

public class InputterCar extends Inputter {

	private final String filePath = "datasets/car.data";
	
	private final List<String> buying = new ArrayList<String>() {{
		add("vhigh"); add("high"); add("med"); add("low"); 
	}};
	
	private final List<String> maint = new ArrayList<String>() {{
		add("vhigh"); add("high"); add("med"); add("low"); 
	}};
	
	private final List<String> doors = new ArrayList<String>() {{
		add("2"); add("3"); add("4"); add("5more"); 
	}};
	
	private final List<String> persons = new ArrayList<String>() {{
		add("2"); add("4"); add("more"); 
	}};
	
	private final List<String> lug_boot = new ArrayList<String>() {{
		add("small"); add("med"); add("big");
	}};
	
	private final List<String> safety = new ArrayList<String>() {{
		add("low"); add("med"); add("high");
	}};

	/**
	 * Constructor for the Car input file. Data is of the form:
	 * <f1>, <f2> ... <f6>, where each f corresponds to a different
	 * attribute of the car.
	 * 
	 * <f1> = buying	(vhigh, high, med, low)
	 * <f2> = maint		(vhigh, high, med, low)
	 * <f3> = doors		(2, 3, 4, 5more)
	 * <f4> = persons	(2, 4, more)
	 * <f5> = lug_boot	(small, med, big)
	 * <f6> = safety	(low, med, high)
	 */
	public InputterCar() {
		inputs = 6;
		outputs = 4;
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
				
				// using the lists of input values, add each feature to the featurelist
				featureList.add(getFeatureValue(split[0],buying));
				featureList.add(getFeatureValue(split[1],maint));
				featureList.add(getFeatureValue(split[2],doors));
				featureList.add(getFeatureValue(split[3],persons));
				featureList.add(getFeatureValue(split[4],lug_boot));
				featureList.add(getFeatureValue(split[5],safety));

				List<Double> output = getOutputVector(split[inputs]);
				data.add(new DataPoint(featureList, output));
			}

			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found for tic-tac-toe dataset.");
			e.printStackTrace();
		} finally {

			// regardless of above, assign the possible classes to the
			// possible classes list.
			findClasses();
		}
	}
	
	private double getFeatureValue(String feature, List<String> list) {
		double featureValue = Double.MAX_VALUE;
		
		int index = list.indexOf(feature);
		if (index < 0)
			System.out.println("Invalid character in parseFile of car");
		else
			featureValue = (double) index;
		
		return featureValue;
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
		possibleClasses.add("unacc");
		possibleClasses.add("acc");
		possibleClasses.add("good");
		possibleClasses.add("vgood");
	}
}
