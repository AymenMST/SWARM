package distance;

import java.util.List;

public abstract class Distance {

	public Distance() {
		
	}
	
	public abstract Double distance(List<Double> vector1, List<Double> vector2);
	
}
