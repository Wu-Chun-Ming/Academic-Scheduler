package io.github.wcm.academicscheduler.service;

import java.util.List;

import io.github.wcm.academicscheduler.domain.Student;
import io.github.wcm.academicscheduler.dto.StudentRequestDto;

public interface StudentService {
	List<Student> getAllStudents();
	Student getStudentById(Long id);
	Student createStudent(StudentRequestDto dto);
	Student updateStudent(StudentRequestDto dto, Long id);
	void deleteStudent(Long id);
	public Student getCurrentStudent();
}