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
		return kmeans.assign(datapoint);
	}

	public void train(List<DataPoint> trainSet) {
		kmeans.train(trainSet);
	}
	
}
