package io.github.wcm.academicscheduler.schedule.exception;

import io.github.wcm.academicscheduler.common.exception.ResourceNotFoundException;

public class ScheduleNotFoundException extends ResourceNotFoundException {
	public ScheduleNotFoundException(Integer id) {
		super("Schedule not found with id: " + id);
	}
}
