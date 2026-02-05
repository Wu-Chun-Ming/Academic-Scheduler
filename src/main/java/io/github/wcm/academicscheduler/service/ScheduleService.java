package io.github.wcm.academicscheduler.service;

import java.util.List;

import io.github.wcm.academicscheduler.domain.Schedule;
import io.github.wcm.academicscheduler.dto.ScheduleRequestDto;

public interface ScheduleService {
	List<Schedule> getAllSchedules();
	Schedule getScheduleById(int id);
	Schedule createSchedule(ScheduleRequestDto dto);
	Schedule updateSchedule(ScheduleRequestDto dto, int id);
	void deleteSchedule(int id);
	List<Schedule> getCurrentSchedules();
}