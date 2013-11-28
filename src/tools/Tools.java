package tools;

import java.util.Random;

public class Tools {

	public static Random random = new Random(11235);
	
	public static double getRandomDouble(double minValue, double maxValue) {
		return minValue + (maxValue - minValue) * random.nextDouble();
	}
	
}
