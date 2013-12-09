package distance;

import java.util.List;

public class Euclidean extends Distance {

	/**
	 * Calculates Euclidean distance between two vectors.
	 */
	@Override
	public Double distance(List<Double> vector1, List<Double> vector2) {
		Double distance = 0.0;
		for (int i = 0; i < vector1.size(); i++) {
			distance += Math.pow((vector2.get(i) - vector1.get(i)), 2);
		}
		distance = Math.sqrt(distance);
		return distance;
	}
	
}
