package io.github.wcm.academicscheduler.domain;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.github.wcm.academicscheduler.dto.TimeslotDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}