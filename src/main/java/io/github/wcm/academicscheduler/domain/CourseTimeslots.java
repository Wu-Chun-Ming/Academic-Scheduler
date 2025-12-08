package io.github.wcm.academicscheduler.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseTimeslots {
    private List<Timeslot> lecture = new ArrayList<>();
    private List<Timeslot> tutorial = new ArrayList<>();
    private List<Timeslot> practical = new ArrayList<>();
}