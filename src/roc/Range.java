package roc;


public class Range<T> {
	private T lowerRange;
	private T upperRange;
	private final int rangeDivider = 2;
	private int stepCounter = 0;

	public Range(T lowerRange, T upperRange) {
		this.lowerRange = lowerRange;
		this.upperRange = upperRange;
	}

	public T getLowerRange() {
		return lowerRange;
	}

	public void setLowerRange(T lowerRange) {
		this.lowerRange = lowerRange;
	}

	public T getUpperRange() {
		return upperRange;
	}

	public void setUpperRange(T upperRange) {
		this.upperRange = upperRange;
	}
	


}
