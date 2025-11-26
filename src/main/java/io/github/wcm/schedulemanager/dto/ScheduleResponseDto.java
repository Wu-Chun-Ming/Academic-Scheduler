package io.github.wcm.schedulemanager.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import io.github.wcm.schedulemanager.domain.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleResponseDto {
	private int id;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private String courseCode;
	private String courseName;
	private String type;
	private String description;
	private String venue;
	private String status;
	private String scope;

	public ScheduleResponseDto(Schedule schedule) {
		this.id = schedule.getId();
		this.startDate = schedule.getStartDate();
		this.endDate = schedule.getEndDate();
		this.startTime = schedule.getStartTime();
		this.endTime = schedule.getEndTime();
		this.courseCode = schedule.getCourse().getCode();
		this.courseName = schedule.getCourse().getName();
		this.type = schedule.getType().name();
		this.description = schedule.getDetail().getDescription();
		this.venue = schedule.getDetail().getVenue();
		this.status = schedule.getStatus().name();
		this.scope = schedule.getScope().name();
	}
}
