package io.github.wcm.schedulemanager.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {
	@NotNull(message = "Start date is required")
	private LocalDate startDate;
	private LocalDate endDate;

	private LocalTime startTime;
	private LocalTime endTime;

	@NotBlank(message = "Course code is required")
	@Size(min = 7, max = 8, message = "Course code must be 7-8 characters")
	private String coursecode;

	@NotBlank(message = "Type is required")
	private String type;

	@NotBlank(message = "Detail is required")
	private String detail;

	@NotBlank(message = "Status is required")
	private String status;

	@NotBlank(message = "Scope is required")
	private String scope;
}
