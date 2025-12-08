package io.github.wcm.academicscheduler.exception;

public class CourseNotFoundException extends ResourceNotFoundException {
	public CourseNotFoundException(String code) {
		super("Course not found with code: " + code);
	}
}
