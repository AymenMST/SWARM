package roc;

public class TunableParameter<T> {
	private String parameterName;
	private T value;
	private Range<T> range;

	public TunableParameter(String parameterName, T value, Range<T> range) {

		this.parameterName = parameterName;
		this.value = value;
		this.range = range;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Range<T> getRange() {
		return range;
	}

	public void setRange(Range<T> range) {
		this.range = range;
	}

}
