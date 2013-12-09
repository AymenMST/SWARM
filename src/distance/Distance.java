package distance;

import java.util.List;

public abstract class Distance {

	
	/**
	 * A distance measure between two vectors of the same size.
	 * 
	 * @param vector1	The first vector used in comparison.
	 * @param vector2	The second vector used in comparison.
	 * @return			The distance as determined by the specific implementation.
	 */
	public abstract Double distance(List<Double> vector1, List<Double> vector2);
	
}
