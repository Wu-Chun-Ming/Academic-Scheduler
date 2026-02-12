package io.github.wcm.academicscheduler.course.api;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.github.wcm.academicscheduler.course.internal.Timeslot;

public class TimeslotDto {
	private String day;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime start;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime end;

	public TimeslotDto() {
	}

	public TimeslotDto(String day, LocalTime start, LocalTime end) {
		this.day = day;
		this.start = start;
		this.end = end;
	}

	public TimeslotDto(Timeslot timeslot) {
		this.day = timeslot.getDay() != null ? timeslot.getDay().name() : null;
		this.start = timeslot.getStart();
		this.end = timeslot.getEnd();
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public LocalTime getStart() {
		return start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}

	public LocalTime getEnd() {
		return end;
	}

	public void setEnd(LocalTime end) {
		this.end = end;
	}
}