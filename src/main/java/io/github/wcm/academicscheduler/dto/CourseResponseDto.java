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

		List<Timeslot> lectureSlots = course.getTimeslots().getLecture();
		// Provide a default empty timeslot if none exist
		if (lectureSlots.isEmpty()) {
			lectureSlots.add(new Timeslot());
		}
		this.lecture = lectureSlots.stream().map(TimeslotDto::new).collect(Collectors.toCollection(ArrayList::new));

		List<Timeslot> tutorialSlots = course.getTimeslots().getTutorial();
		if (tutorialSlots.isEmpty()) {
			tutorialSlots.add(new Timeslot());
		}
		this.tutorial = tutorialSlots.stream().map(TimeslotDto::new).collect(Collectors.toCollection(ArrayList::new));

		List<Timeslot> practicalSlots = course.getTimeslots().getPractical();
		if (practicalSlots.isEmpty()) {
			practicalSlots.add(new Timeslot());
		}
		this.practical = practicalSlots.stream().map(TimeslotDto::new).collect(Collectors.toCollection(ArrayList::new));
	}
}
