package io.github.wcm.academicscheduler.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.github.wcm.academicscheduler.dto.TimeslotDto;

public class Timeslot {
	private DayOfWeek day;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime start;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
	private LocalTime end;

	public Timeslot(TimeslotDto dto) {
		try {
			this.day = dto.getDay() != null ? DayOfWeek.valueOf(dto.getDay().toUpperCase()) : null;
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid day: " + dto.getDay());
		}
		this.start = dto.getStart();
		this.end = dto.getEnd();
	}

	public Timeslot() {
	}

	public Timeslot(DayOfWeek day, LocalTime start, LocalTime end) {
		this.day = day;
		this.start = start;
		this.end = end;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public void setDay(DayOfWeek day) {
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