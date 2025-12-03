package io.github.wcm.schedulemanager.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.github.wcm.schedulemanager.domain.Timeslot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeslotDto {
    private String day;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime start;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime end;
    
    public TimeslotDto(Timeslot timeslot) {
    	this.day = timeslot.getDay() != null ? timeslot.getDay().name() : null;
    	this.start = timeslot.getStart();
    	this.end = timeslot.getEnd();
    }
}