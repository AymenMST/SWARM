package training;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import driver.DataPoint;

/**
 * Used to partition data into train and 
 * test sets that are then rotated k times.
 */
public class KFolds {
	
	int foldIndex = 0;
	int k;
	List<DataPoint> data;
	List<DataPoint> train = null;
	List<DataPoint> test = null;
	
	/**
	 * Constructs a KFold validation class that will
	 * rotate the train and test set through the data
	 * k times.
	 * 
	 * @param data	The data found by Inputter.
	 * @param k		The number of folds that will be processed.
	 */
	KFolds(List<DataPoint> data, int k) {
		this.data = shuffle(data);
		this.k = k;
	}
	
	/**
	 * Shuffles a copy of the data, leaving the original list untouched.
	 * 
	 * @param data	The list of data to be shuffled.
	 * @return		The newly shuffled version of the data.
	 */
	private List<DataPoint> shuffle(List<DataPoint> data) {
		// make copy of data
		List<DataPoint> randomData = new ArrayList<>();
		for (DataPoint datapoint : data)
			randomData.add(datapoint);
		// shuffle the copy and return
		Collections.shuffle(randomData, new Random(11235));
		return randomData;
	}
	
	/**
	 * Get the next fold, resetting the train and test sets
	 * in a rotational manner.
	 * 
	 * @return	True if another valid fold was found, False if all folds are completed.
	 */
	public boolean next() {
		// if done with k folds, stop iterations
		if (foldIndex >= k) {
			train = null;
			test = null;
			return false;
		}
		// continue on to next fold, rotating the partitions
		partition();
		foldIndex++;
		return true;
	}
	
	
	/**
	 * Partitions the data into train and test sets
	 * based on the current fold.
	 */
	private void partition() {
		// calculate test set size
		int testSetSize = data.size() / k;
		
		train = new ArrayList<>();
		test = new ArrayList<>();
		
		// loop through all datapoints
		for (int dataIndex = 0; dataIndex < data.size(); dataIndex++) {
			
			DataPoint datapoint = data.get(dataIndex);
			
			// calculate the min and max index values for the test set
			int minWindow = foldIndex * testSetSize;
			int maxWindow = minWindow + testSetSize;
			
			// if index falls within test window, add to test set
			// add everything else to train set
			if (dataIndex >= minWindow && dataIndex < maxWindow)
				test.add(datapoint);
			else
				train.add(datapoint);
		}
	}
	
	/**
	 * @return	The train set for the current fold.
	 */
	public List<DataPoint> getTrainSet() {
		return train;
	}
	
	/**
	 * @return	The test set for the current fold.
	 */
	public List<DataPoint> getTestSet() {
		return test;
	}

}
