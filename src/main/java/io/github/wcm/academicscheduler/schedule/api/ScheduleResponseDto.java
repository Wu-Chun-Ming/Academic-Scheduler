package io.github.wcm.academicscheduler.schedule.api;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import io.github.wcm.academicscheduler.schedule.Schedule;

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

	public ScheduleResponseDto() {
	}

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public void setDay(DayOfWeek day) {
		this.day = day;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(String timeLeft) {
		this.timeLeft = timeLeft;
	}
}
