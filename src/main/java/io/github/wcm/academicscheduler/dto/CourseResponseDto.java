package io.github.wcm.academicscheduler.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.github.wcm.academicscheduler.domain.Course;
import io.github.wcm.academicscheduler.domain.Timeslot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

	public CourseResponseDto(Course course) {
		this.code = course.getCode();
		this.name = course.getName();
		this.year = course.getYear();
		this.semester = course.getSemester();
		this.programmeType = course.getProgrammeType().name();
		this.lecture   = mapSlots(course.getTimeslots().getLecture());
		this.tutorial  = mapSlots(course.getTimeslots().getTutorial());
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
}
