package io.github.wcm.academicscheduler.student;

import java.util.List;

import io.github.wcm.academicscheduler.student.api.StudentRequestDto;

public interface StudentService {
	List<Student> getAllStudents();
	Student getStudentById(Long id);
	Student createStudent(StudentRequestDto dto);
	Student updateStudent(StudentRequestDto dto, Long id);
	void deleteStudent(Long id);
	public Student getCurrentStudent();
}