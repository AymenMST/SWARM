package driver;

public class ConfusionMatrix {
	
	private int size;
	private int[][] matrix;
	
	/**
	 * Creates a zeroed confusion matrix of specified size nxn.
	 * 
	 * @param size	The width/height of the confusion matrix.
	 */
	public ConfusionMatrix(int size) {
		this.size = size;
		matrix = new int[size][size];
	}
	
	/**
	 * @return	The width/height of the confusion matrix.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Increment the specified element matrix[expected][actual]
	 * 
	 * @param expected	The expected class for the example.
	 * @param actual	The actual class returned by the classifier.
	 */
	public void increment(int expected, int actual) {
		this.matrix[expected][actual]++;
	}
	
	/**
	 * Prints a graphical representation of the matrix to standard out.
	 */
	public void printMatrix() {
		System.out.println(this);
	}
	
	/**
	 * Returns a graphical representation of the matrix in string format.
	 */
	public String toString() {
		String value = "";
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				value += String.format("%4s", this.matrix[i][j]);
			}
			value += "\n";
		}
		return value;
	}

}
