package tools;

import java.util.Random;

public class Tools {

	public static Random random = new Random(11235);
	
	public static double getRandomDouble(double minValue, double maxValue) {
		return minValue + (maxValue - minValue) * random.nextDouble();
	}
	
	public static double round(double value, int decimals) {
		int pow = (int) Math.pow(10, decimals);
		double val = Math.round(value * pow);
		return (double) Math.round(val) / pow;
	}
	
}
