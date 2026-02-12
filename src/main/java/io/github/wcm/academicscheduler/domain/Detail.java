package io.github.wcm.academicscheduler.domain;

public class Detail {
	private String description;

	private String venue;

	public Detail() {
	}

	public Detail(String description, String venue) {
		this.description = description;
		this.venue = venue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}
}