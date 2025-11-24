package io.github.wcm.schedulemanager.exception;

public class ScheduleNotFoundException extends ResourceNotFoundException {
	public ScheduleNotFoundException(Integer id) {
		super("Schedule not found with id: " + id);
	}
}
