package io.github.wcm.academicscheduler.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import io.github.wcm.academicscheduler.domain.Student;

public class StudentResponseDto {
	private Long id;

	private String name;

	private String gender;

	private String programmeType;

	private String programme;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate enrollmentDate;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate graduationDate;

	private int currentYear;
	private int currentSemester;

	private String status;

	public StudentResponseDto() {
	}

	public StudentResponseDto(Student student) {
		this.id = student.getId();
		this.name = student.getName();
		this.gender = student.getGender();
		this.programmeType = student.getProgrammeType().name();
		this.programme = student.getProgramme();
		this.enrollmentDate = student.getEnrollmentDate();
		this.graduationDate = student.getGraduationDate();
		this.currentYear = student.getCurrentYear();
		this.currentSemester = student.getCurrentSemester();
		this.status = student.getStatus();
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}