package io.github.wcm.schedulemanager.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import io.github.wcm.schedulemanager.domain.Schedule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleResponseDto {
	private int id;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private String coursecode;
	private String coursename;
	private String type;
	private String detail;
	private String status;
	private String scope;

	public ScheduleResponseDto(Schedule schedule) {
		this.id = schedule.getId();
		this.startDate = schedule.getStartDate();
		this.endDate = schedule.getEndDate();
		this.startTime = schedule.getStartTime();
		this.endTime = schedule.getEndTime();
		this.coursecode = schedule.getCourse().getCode();
		this.coursename = schedule.getCourse().getName();
		this.type = schedule.getType().name();
		this.detail = schedule.getDetail();
		this.status = schedule.getStatus().name();
		this.scope = schedule.getScope().name();
	}
}
