package io.github.wcm.academicscheduler.course.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.github.wcm.academicscheduler.course.Course;
import io.github.wcm.academicscheduler.course.internal.Timeslot;

public class CourseResponseDto {
	private String code;
	private String name;
	private Integer year;
	private Integer semester;
	private String programmeType;
	// Time slots
	private List<TimeslotDto> lecture = new ArrayList<>();
	private List<TimeslotDto> tutorial = new ArrayList<>();
	private List<TimeslotDto> practical = new ArrayList<>();

	public CourseResponseDto() {
	}

	public CourseResponseDto(Course course) {
		this.code = course.getCode();
		this.name = course.getName();
		this.year = course.getYear();
		this.semester = course.getSemester();
		this.programmeType = course.getProgrammeType().name();
		this.lecture = mapSlots(course.getTimeslots().getLecture());
		this.tutorial = mapSlots(course.getTimeslots().getTutorial());
		this.practical = mapSlots(course.getTimeslots().getPractical());
	}

	private List<TimeslotDto> mapSlots(List<Timeslot> slots) {
		// Provide a default empty timeslot if none exist
		if (slots == null || slots.isEmpty()) {
			return List.of(new TimeslotDto());
		}
		return slots.stream()
				.map(TimeslotDto::new)
				.collect(Collectors.toCollection(ArrayList::new));
	}

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
