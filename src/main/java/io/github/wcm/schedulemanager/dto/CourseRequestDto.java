package io.github.wcm.schedulemanager.dto;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseRequestDto {
	@NotEmpty(message = "Course code is required")
	@Size(min = 7, max = 8, message = "Course code must be 7-8 characters")
	private String code;

	@NotBlank(message = "Course name is required")
	private String name;

	@Min(value = 1, message = "Year must be 1-3")
	@Max(value = 3, message = "Year must be 1-3")
	private Integer year;

	@Min(value = 1, message = "Semester must be 1-3")
	@Max(value = 3, message = "Semester must be 1-3")
	private Integer semester;

	@NotBlank(message = "Programme type is required")
	private String programmeType;

	// Time slots
	private List<TimeslotDto> lecture;
	private List<TimeslotDto> tutorial;
	private List<TimeslotDto> practical;
}
