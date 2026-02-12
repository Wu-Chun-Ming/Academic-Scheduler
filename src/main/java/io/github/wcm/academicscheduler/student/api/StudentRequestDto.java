package io.github.wcm.academicscheduler.student.api;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

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

	public StudentRequestDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getProgrammeType() {
		return programmeType;
	}

	public void setProgrammeType(String programmeType) {
		this.programmeType = programmeType;
	}

	public String getProgramme() {
		return programme;
	}

	public void setProgramme(String programme) {
		this.programme = programme;
	}

	public LocalDate getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(LocalDate enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public LocalDate getGraduationDate() {
		return graduationDate;
	}

	public void setGraduationDate(LocalDate graduationDate) {
		this.graduationDate = graduationDate;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	public int getCurrentSemester() {
		return currentSemester;
	}

	public void setCurrentSemester(int currentSemester) {
		this.currentSemester = currentSemester;
	}
}