package training;

import java.util.List;

import driver.DataPoint;

public class ACOTraining extends TrainingMethod {

	public ACOTraining(List<DataPoint> data) {
		super(data);
	}

	public int classify(DataPoint datapoint) {
		int outputClass = 0;
		// TODO: use code from non-existent ACO package to classify
		return outputClass;
	}

	public void train(List<DataPoint> trainSet) {
		
		int classFound, classExpected;
		for (DataPoint datapoint : trainSet) {
			classFound = classify(datapoint);
			classExpected = datapoint.getClassIndex();
			// TODO: perform training operations
		}
		
	}
	
}
