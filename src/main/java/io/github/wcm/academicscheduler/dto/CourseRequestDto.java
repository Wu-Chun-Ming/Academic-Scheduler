package io.github.wcm.academicscheduler.dto;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	public String getProgrammeType() {
		return programmeType;
	}

	public void setProgrammeType(String programmeType) {
		this.programmeType = programmeType;
	}

	public List<TimeslotDto> getLecture() {
		return lecture;
	}

	public void setLecture(List<TimeslotDto> lecture) {
		this.lecture = lecture;
	}

	public List<TimeslotDto> getTutorial() {
		return tutorial;
	}

	public void setTutorial(List<TimeslotDto> tutorial) {
		this.tutorial = tutorial;
	}

	public List<TimeslotDto> getPractical() {
		return practical;
	}

	public void setPractical(List<TimeslotDto> practical) {
		this.practical = practical;
	}
}
