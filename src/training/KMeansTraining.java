package training;

import java.util.List;

import kmeans.KMeans;
import driver.DataPoint;

public class KMeansTraining extends TrainingMethod {
	
	KMeans kmeans = new KMeans();

	public KMeansTraining(List<DataPoint> data) {
		super(data);
	}

	public int classify(DataPoint datapoint) {
		int outputClass = 0;
		// TODO: use code from non-existent KMeans package to classify
		return outputClass;
	}

	public void train(List<DataPoint> trainSet) {
		
		int classFound, classExpected;
		for (DataPoint datapoint : trainSet) {
			classFound = classify(datapoint);
			classExpected = datapoint.getClassIndex();
			kmeans.train(trainSet);
		}
		
	}
	
}
