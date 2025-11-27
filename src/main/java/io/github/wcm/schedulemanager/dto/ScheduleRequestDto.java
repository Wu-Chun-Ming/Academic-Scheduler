package io.github.wcm.schedulemanager.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {
	@NotNull(message = "Start time is required")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime startDateTime;

	@NotNull(message = "End time is required")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime endDateTime;

	@NotBlank(message = "Course code is required")
	@Size(min = 7, max = 8, message = "Course code must be 7-8 characters")
	private String coursecode;

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
}
