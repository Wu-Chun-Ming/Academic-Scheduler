package io.github.wcm.academicscheduler.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentRequestDto {

	@NotNull(message = "Student ID is required")
	private Long id;

	@NotEmpty(message = "Name is required")
	private String name;

	@NotEmpty(message = "Gender is required")
	private String gender;

	@NotEmpty(message = "Programme type is required")
	private String programmeType;

	@NotEmpty(message = "Programme is required")
	private String programme;

	@NotNull(message = "Enrollment date is required")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate enrollmentDate;
	@NotNull(message = "Graduation date is required")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate graduationDate;

	@NotNull(message = "Current year is required")
	private int currentYear;
	@NotNull(message = "Current semester is required")
	private int currentSemester;
}