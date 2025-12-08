package io.github.wcm.academicscheduler.domain;

public class Option<T> {
	private T value;
	private T label;

	public Option() {
	}

	/**
	 * @param value
	 * @param label
	 */
	public Option(T value, T label) {
		this.value = value;
		this.label = label;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public T getLabel() {
		return label;
	}

	public void setLabel(T label) {
		this.label = label;
	}
}