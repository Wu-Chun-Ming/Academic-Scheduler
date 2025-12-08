package io.github.wcm.academicscheduler.exception;

public class ScheduleNotFoundException extends ResourceNotFoundException {
	public ScheduleNotFoundException(Integer id) {
		super("Schedule not found with id: " + id);
	}
}
