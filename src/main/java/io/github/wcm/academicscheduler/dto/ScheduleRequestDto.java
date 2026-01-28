package io.github.wcm.academicscheduler.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ScheduleRequestDto {
	@NotNull(message = "Start time is required")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime startDateTime;

	@NotNull(message = "End time is required")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime endDateTime;

	@NotBlank(message = "Course code is required")
	@Size(min = 7, max = 8, message = "Course code must be 7-8 characters")
	private String courseCode;

	@NotBlank(message = "Type is required")
	private String type;

	@NotBlank(message = "Description is required")
	private String description;

	private String venue;

	@NotBlank(message = "Status is required")
	private String status;

	@NotBlank(message = "Scope is required")
	private String scope;

	public LocalDate getStartDate() {
		return startDateTime.toLocalDate();
	}

	public LocalTime getStartTime() {
		return startDateTime.toLocalTime();
	}

	public LocalDate getEndDate() {
		return endDateTime.toLocalDate();
	}

	public LocalTime getEndTime() {
		return endDateTime.toLocalTime();
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
}
