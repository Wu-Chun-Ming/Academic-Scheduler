package io.github.wcm.academicscheduler.student.exception;

import io.github.wcm.academicscheduler.common.exception.ResourceNotFoundException;

public class StudentNotFoundException extends ResourceNotFoundException {
	public StudentNotFoundException(Long id) {
		super("Student not found with id: " + id);
	}
}
