package io.github.wcm.academicscheduler.schedule;

import java.util.List;

import io.github.wcm.academicscheduler.schedule.api.ScheduleRequestDto;

public interface ScheduleService {
	List<Schedule> getAllSchedules();
	Schedule getScheduleById(int id);
	Schedule createSchedule(ScheduleRequestDto dto);
	Schedule updateSchedule(ScheduleRequestDto dto, int id);
	void deleteSchedule(int id);
	List<Schedule> getCurrentSchedules();
}