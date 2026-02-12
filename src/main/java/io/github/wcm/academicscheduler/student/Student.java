package io.github.wcm.academicscheduler.student;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicUpdate;

import io.github.wcm.academicscheduler.common.api.ProgrammeType;
import io.github.wcm.academicscheduler.student.api.StudentRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
@DynamicUpdate
public class Student {
	@Id
	private Long id;

	@Column(nullable = false)
	private String name;

	private String gender;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ProgrammeType programmeType;

	@Column(nullable = false)
	private String programme;

	@Column(nullable = false)
	private LocalDate enrollmentDate;
	@Column(nullable = false)
	private LocalDate graduationDate;

	@Column(nullable = false)
	private int currentYear;
	@Column(nullable = false)
	private int currentSemester;

	@Column(nullable = false)
	private String status = "NORMAL";

	public Student() {
	}

	public Student(Long id, String name, String gender, ProgrammeType programmeType, String programme,
			LocalDate enrollmentDate, LocalDate graduationDate, int currentYear, int currentSemester, String status) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.programmeType = programmeType;
		this.programme = programme;
		this.enrollmentDate = enrollmentDate;
		this.graduationDate = graduationDate;
		this.currentYear = currentYear;
		this.currentSemester = currentSemester;
		this.status = status;
	}

	public Student(StudentRequestDto dto) {
		this.name = dto.getName();
		this.gender = dto.getGender();
		try {
			this.programmeType = ProgrammeType.valueOf(dto.getProgrammeType().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid programme type: " + dto.getProgrammeType());
		}
		this.programme = dto.getProgramme();
		this.enrollmentDate = dto.getEnrollmentDate();
		this.graduationDate = dto.getGraduationDate();
		this.currentYear = dto.getCurrentYear();
		this.currentSemester = dto.getCurrentSemester();
	}

	public Long getId() {
		return id;
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

	public ProgrammeType getProgrammeType() {
		return programmeType;
	}

	public void setProgrammeType(ProgrammeType programmeType) {
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