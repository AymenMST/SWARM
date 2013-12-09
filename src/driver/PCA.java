package driver;

import java.util.ArrayList;
import java.util.List;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.EigenvalueDecomposition;

public class PCA {
	private int numPrincipleComponents;

	// original list of data points
	private List<DataPoint> data;

	// post-PCA list of data points.
	private List<DataPoint> transformedData;

	// means of original data points
	private List<Double> featureMeans;

	// cern.colt.matrix.* library classes
	private DoubleMatrix2D covarianceMatrix;
	private EigenvalueDecomposition eigenValueDecomp;
	private DoubleMatrix2D principleMatrix;
	private DoubleMatrix2D transformationMatrix;

	/**
	 * constructor to perform principle component analysis on a set of
	 * datapoints.
	 * 
	 * @param numPrincipleComponents
	 */
	public PCA(int numPrincipleComponents) {
		this.numPrincipleComponents = numPrincipleComponents;
	}

	/**
	 * Driver of the PCA class. Performs all operations necessary to
	 * PCA in order
	 * 
	 * @param data
	 *            list of data to perform PCA on
	 * @return list of data with dimensions reduced.
	 */
	public List<DataPoint> runPCA(List<DataPoint> data) {
		this.data = data;

		// center all original data points at (expected-actual)
		centerAtZero();

		// build covariance matrix
		calculateCovarianceMatrix();

		// find the eigenvectors of the covariance matrix
		calculateEigenVectors();

		// find the n-largest eigenvalues and keep the eigenvectors of
		// those eigenvalues
		findPrincipleComponents();

		// transform matrix of eigenvectors to make it compatible with
		// multiplication of original data
		constructTransformationMatrix();

		// transform original data by multiplying it with the
		// eigenvectors.
		transformData();
		return transformedData;
	}

	/**
	 * Transform original data by multiplying it with the eigenvectors
	 */
	private void transformData() {
		transformedData = new ArrayList<DataPoint>(data.size());

		// transform all data points by removing unimportant features
		for (DataPoint datapoint : data) {
			transformedData.add(transformDataPoint(datapoint));
		}
	}

	/**
	 * Individually remove unimportant features from original
	 * datapoints
	 * 
	 * @param datapoint
	 *            data point with full set of features
	 * @return new data point with unimportant features removed
	 */
	private DataPoint transformDataPoint(DataPoint datapoint) {

		List<Double> features = datapoint.getFeatures();
		double[][] difference = new double[1][features.size()];

		// build matrix consisting of original features
		for (int i = 0; i < features.size(); i++) {
			difference[0][i] = features.get(i);
		}
		DoubleMatrix2D diff = new DenseDoubleMatrix2D(difference);
		DoubleMatrix2D newVector = null;

		// multiply the original feature set by the eigenvector matrix
		newVector = diff.zMult(transformationMatrix, newVector);

		// arrange the result from the multiplication in correct
		// matrix form.
		List<Double> newFeatures = new ArrayList<Double>(newVector.columns());
		for (int i = 0; i < newVector.columns(); i++) {
			newFeatures.add(newVector.get(0, i));
		}

		// return a new datapoint of the result. This datapoint has
		// unimportant features removed.
		return new DataPoint(newFeatures, datapoint.getOutputs());
	}

	/**
	 * Build the matrix used to transform ori
	 */
	private void constructTransformationMatrix() {
		double[][] transformationMatrix = new double[principleMatrix.rows()][numPrincipleComponents];
		int rows = transformationMatrix.length;
		int columns = transformationMatrix[0].length;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				transformationMatrix[i][j] = principleMatrix.get(i, j + (columns - numPrincipleComponents));
			}
		}
		this.transformationMatrix = new DenseDoubleMatrix2D(transformationMatrix);
	}

	/**
	 * Center all data points at zero. This is done through (actual -
	 * expected), where expected is the mean of that feature.
	 */
	private void centerAtZero() {
		featureMeans = new ArrayList<>();

		// initiate list of feature means
		for (int i = 0; i < data.get(0).getFeatures().size(); i++) {
			featureMeans.add(new Double(0.0));
		}

		// append array of feature means
		for (DataPoint dataPoint : data) {
			for (int i = 0; i < dataPoint.getFeatures().size(); i++) {
				double currentSum = featureMeans.get(i);
				featureMeans.set(i, currentSum + dataPoint.getFeatures().get(i));
			}
		}

		// find means of data points by dividing sum by size
		for (int i = 0; i < featureMeans.size(); i++) {
			featureMeans.set(i, featureMeans.get(i) / data.size());
		}

		// shift original features by subtracting them from the mean
		for (int i = 0; i < data.size(); i++) {
			for (int j = 0; j < data.get(i).getFeatures().size(); j++) {
				Double oldValue = data.get(i).getFeatures().get(j);
				data.get(i).getFeatures().set(j, oldValue - featureMeans.get(j));
			}
		}
	}

	/**
	 * Build the covariance matrix by finding the variance of every
	 * feature to every other feature.
	 */
	private void calculateCovarianceMatrix() {
		int numDimensions = featureMeans.size();
		double[][] covarianceMatrix = new double[numDimensions][numDimensions];

		// construct the covariance matrix by calling the method
		// calculatecovariance.
		// This is done for every combination of data points.
		for (int i = 0; i < covarianceMatrix.length; i++) {
			for (int j = 0; j < covarianceMatrix[0].length; j++) {
				covarianceMatrix[i][j] = calculateCovariance(i, j);
			}
		}
		this.covarianceMatrix = new DenseDoubleMatrix2D(covarianceMatrix);

	}

	/**
	 * find the variance of any two feature dimensions.
	 * 
	 * @param dimensionOneIndex
	 *            index of first feature
	 * @param dimensionTwoIndex
	 *            index of second feature
	 * @return variance of two features.
	 */
	private Double calculateCovariance(int dimensionOneIndex, int dimensionTwoIndex) {

		Double covariance = 0.0;
		Double dimensionOneMean = featureMeans.get(dimensionOneIndex);
		Double dimensionTwoMean = featureMeans.get(dimensionTwoIndex);

		// for each feature index in each data point, find the
		// variance with the second feature index
		for (DataPoint dataPoint : data) {
			Double dimensionOneValue = dataPoint.getFeatures().get(dimensionOneIndex);
			Double dimensionTwoValue = dataPoint.getFeatures().get(dimensionTwoIndex);
			covariance += ((dimensionOneValue - dimensionOneMean) * (dimensionTwoValue - dimensionTwoMean));
		}

		// average covariance
		covariance /= (featureMeans.size() - 1);
		return covariance;
	}

	/**
	 * Use the cern.colt.matrix.* library call to get a matrix of
	 * eigenvectors from the covariance matrix
	 */
	private void calculateEigenVectors() {
		eigenValueDecomp = new EigenvalueDecomposition(covarianceMatrix);
	}

	/**
	 * find the n-largest eigenvalues and the eigenvectors
	 * corresponding to them.
	 */
	private void findPrincipleComponents() {
		DoubleMatrix2D eigenVectors = eigenValueDecomp.getV();
		DoubleMatrix1D eigenValues = eigenValueDecomp.getRealEigenvalues();

		// sort the eigenvalues and re-arrange the eigenvector matrix
		// at the same time.
		for (int i = 0; i < eigenValues.size(); i++) {
			for (int j = 0; j < eigenValues.size(); j++) {
				if (eigenValues.get(j) < eigenValues.get(i)) {
					double eigenValueTemp = eigenValues.get(i);
					eigenValues.set(i, eigenValues.get(j));
					eigenValues.set(j, eigenValueTemp);

					// swap values in the eigenvector matrix iff j's
					// eigenvalues are smaller than i's.
					for (int k = 0; k < eigenVectors.columns(); k++) {
						double eigenVectorTemp = eigenVectors.get(i, k);
						eigenVectors.set(i, k, eigenVectors.get(j, k));
						eigenVectors.set(j, k, eigenVectorTemp);
					}
				}
			}
		}

		principleMatrix = eigenVectors;
	}

	/**
	 * 
	 * Inputs a matrix and prints it in a nice form
	 * 
	 * @param mat
	 */
	public void printMatrix(DoubleMatrix2D mat) {
		for (int i = 0; i < mat.rows(); i++) {
			for (int j = 0; j < mat.columns(); j++) {
				System.out.print(mat.get(i, j) + " ");
			}
			System.out.println();
		}
	}

	public List<DataPoint> getData() {
		return data;
	}

	public void setData(List<DataPoint> data) {
		this.data = data;
	}

}
