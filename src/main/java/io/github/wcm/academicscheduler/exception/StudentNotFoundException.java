package io.github.wcm.academicscheduler.exception;

public class StudentNotFoundException extends ResourceNotFoundException {
	public StudentNotFoundException(Long id) {
		super("Student not found with id: " + id);
	}
}
