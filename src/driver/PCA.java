package driver;

import java.util.ArrayList;
import java.util.List;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.EigenvalueDecomposition;

public class PCA {
	private int numPrincipleComponents;
	private List<DataPoint> data;
	private List<DataPoint> transformedData;
	private List<Double> featureMeans;

	private DoubleMatrix2D covarianceMatrix;
	private EigenvalueDecomposition eigenValueDecomp;
	private DoubleMatrix2D principleMatrix;
	private DoubleMatrix2D transformationMatrix;

	public PCA(int numPrincipleComponents) {
		this.numPrincipleComponents = numPrincipleComponents;
	}

	public List<DataPoint> runPCA(List<DataPoint> data) {
		this.data = data;
		centerAtZero();
		calculateCovarianceMatrix();
		calculateEigenVectors();
		findPrincipleComponents();
		constructTransformationMatrix();
		transformData();
		return transformedData;
	}
	
	private void transformData() {
		transformedData = new ArrayList<DataPoint>(data.size());
		for (DataPoint datapoint : data) {
			transformedData.add(transformDataPoint(datapoint));
		}
	}
	
	private DataPoint transformDataPoint(DataPoint datapoint) {
		
		List<Double> features = datapoint.getFeatures();
		double[][] difference = new double[1][features.size()];
		for (int i = 0; i < features.size(); i++) {
			difference[0][i] = features.get(i);
		}
		DoubleMatrix2D diff = new DenseDoubleMatrix2D(difference);
		DoubleMatrix2D newVector = null;
		
		newVector = diff.zMult(transformationMatrix, newVector);
		
		List<Double> newFeatures = new ArrayList<Double>(newVector.columns());
		for (int i = 0; i < newVector.columns(); i++) {
			newFeatures.add(newVector.get(0, i));
		}
		
		return new DataPoint(newFeatures, datapoint.getOutputs());
	}
	
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

	private void centerAtZero() {
		featureMeans = new ArrayList<>();
		for (int i = 0; i < data.get(0).getFeatures().size(); i++) {
			featureMeans.add(new Double(0.0));
		}
		for (DataPoint dataPoint : data) {
			for (int i = 0; i < dataPoint.getFeatures().size(); i++) {
				double currentSum = featureMeans.get(i);
				featureMeans.set(i, currentSum + dataPoint.getFeatures().get(i));
			}
		}
		for (int i = 0; i < featureMeans.size(); i++) {
			featureMeans.set(i, featureMeans.get(i) / data.size());
		}
		for (int i = 0; i < data.size(); i++) {
			for (int j = 0; j < data.get(i).getFeatures().size(); j++) {
				Double oldValue = data.get(i).getFeatures().get(j);
				data.get(i).getFeatures().set(j, oldValue - featureMeans.get(j));
			}
		}
	}

	private void calculateCovarianceMatrix() {
		int numDimensions = featureMeans.size();
		double[][] covarianceMatrix = new double[numDimensions][numDimensions];

		for (int i = 0; i < covarianceMatrix.length; i++) {
			for (int j = 0; j < covarianceMatrix[0].length; j++) {
				covarianceMatrix[i][j] = calculateCovariance(i, j);
			}
		}
		this.covarianceMatrix = new DenseDoubleMatrix2D(covarianceMatrix);

	}

	private Double calculateCovariance(int dimensionOneIndex, int dimensionTwoIndex) {

		Double covariance = 0.0;
		Double dimensionOneMean = featureMeans.get(dimensionOneIndex);
		Double dimensionTwoMean = featureMeans.get(dimensionTwoIndex);

		for (DataPoint dataPoint : data) {
			Double dimensionOneValue = dataPoint.getFeatures().get(
					dimensionOneIndex);
			Double dimensionTwoValue = dataPoint.getFeatures().get(
					dimensionTwoIndex);
			covariance += ((dimensionOneValue - dimensionOneMean) * (dimensionTwoValue - dimensionTwoMean));
		}
		covariance /= (featureMeans.size() - 1);
		return covariance;
	}

	private void calculateEigenVectors() {
		eigenValueDecomp = new EigenvalueDecomposition(covarianceMatrix);
	}

	private void findPrincipleComponents() {

		DoubleMatrix2D eigenVectors = eigenValueDecomp.getV();

		DoubleMatrix1D eigenValues = eigenValueDecomp.getRealEigenvalues();
		
		/*
		 * sort the eigenvalues and re-arrange the eigenvector matrix at the same time.
		 */
		for (int i = 0; i < eigenValues.size(); i++){
			for (int j = 0; j < eigenValues.size(); j++){
				if (eigenValues.get(j) < eigenValues.get(i)){
					double eigenValueTemp = eigenValues.get(i);
					eigenValues.set(i, eigenValues.get(j));
					eigenValues.set(j, eigenValueTemp);
				
					//swap values in the eigenvector matrix iff j's eigenvalues are smaller than i's.
					for (int k = 0; k < eigenVectors.columns(); k++){
						double eigenVectorTemp = eigenVectors.get(i, k);
						eigenVectors.set(i, k, eigenVectors.get(j, k));
						eigenVectors.set(j, k, eigenVectorTemp);
					}
				}
			}
		}
		

		principleMatrix = eigenVectors;
	}

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
