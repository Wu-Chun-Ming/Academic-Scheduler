package io.github.wcm.schedulemanager.domain;

public class Option {
	private String value;
	private String label;

	public Option() {
	}

	/**
	 * @param value
	 * @param label
	 */
	public Option(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}