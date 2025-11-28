package io.github.wcm.schedulemanager.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import io.github.wcm.schedulemanager.domain.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleResponseDto {
	private int id;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime startDateTime;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime endDateTime;
	private DayOfWeek day;
	private String courseCode;
	private String courseName;
	private String type;
	private String description;
	private String venue;
	private String status;
	private String scope;
	private String timeLeft;

	public ScheduleResponseDto(Schedule schedule) {
		this.id = schedule.getId();
		if (schedule.getStartDate() != null && schedule.getStartTime() != null) {
	        this.startDateTime = LocalDateTime.of(schedule.getStartDate(), schedule.getStartTime());
	    }
	    if (schedule.getEndDate() != null && schedule.getEndTime() != null) {
	        this.endDateTime = LocalDateTime.of(schedule.getEndDate(), schedule.getEndTime());
	    }
		this.day = schedule.getStartDate().getDayOfWeek();
		this.courseCode = schedule.getCourse().getCode();
		this.courseName = schedule.getCourse().getName();
		this.type = schedule.getType().name();
		this.description = schedule.getDetail().getDescription();
		this.venue = schedule.getDetail().getVenue();
		this.status = schedule.getStatus().name();
		this.scope = schedule.getScope().name();
		this.timeLeft = schedule.calculateTimeLeft();
	}

	public LocalDate getStartDate() {
		return startDateTime.toLocalDate();
	}

	public LocalDate getEndDate() {
		return endDateTime.toLocalDate();
	}

	public LocalTime getStartTime() {
		return startDateTime.toLocalTime();
	}

	public LocalTime getEndTime() {
		return endDateTime.toLocalTime();
	}
}
