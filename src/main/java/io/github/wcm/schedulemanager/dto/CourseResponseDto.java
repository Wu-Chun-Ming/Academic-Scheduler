package io.github.wcm.schedulemanager.dto;

import io.github.wcm.schedulemanager.domain.Course;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseResponseDto {
	private String code;
	private String name;
	private Integer year;
	private Integer semester;
	private String programmeType;
	private String timeslots;

	public CourseResponseDto(Course course) {
		this.code = course.getCode();
		this.name = course.getName();
		this.year = course.getYear();
		this.semester = course.getSemester();
		this.programmeType = course.getProgrammeType().name();
		this.timeslots = course.getTimeslots();
	}
}
