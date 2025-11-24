package io.github.wcm.schedulemanager.exception;

public class CourseNotFoundException extends ResourceNotFoundException {
	public CourseNotFoundException(String code) {
		super("Course not found with code: " + code);
	}
}
