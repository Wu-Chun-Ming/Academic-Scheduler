package io.github.wcm.academicscheduler.course.exception;

import io.github.wcm.academicscheduler.common.exception.ResourceNotFoundException;

public class CourseNotFoundException extends ResourceNotFoundException {
	public CourseNotFoundException(String code) {
		super("Course not found with code: " + code);
	}
}
