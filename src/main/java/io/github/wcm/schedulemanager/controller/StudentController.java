package io.github.wcm.schedulemanager.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.wcm.schedulemanager.domain.Student;
import io.github.wcm.schedulemanager.dto.StudentRequestDto;
import io.github.wcm.schedulemanager.dto.StudentResponseDto;
import io.github.wcm.schedulemanager.service.StudentService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/students")
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping
	public ResponseEntity<List<StudentResponseDto>> getAllStudents() {
		List<Student> students = studentService.getAllStudents();
		List<StudentResponseDto> response = students.stream().map(StudentResponseDto::new).toList();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long id) {
		Student student = studentService.getStudentById(id);
		StudentResponseDto response = new StudentResponseDto(student);

		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<StudentResponseDto> createStudent(@Valid @RequestBody StudentRequestDto dto) {
		Student student = studentService.createStudent(dto);
		StudentResponseDto response = new StudentResponseDto(student);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<StudentResponseDto> updateStudent(@Valid @RequestBody StudentRequestDto dto, @PathVariable Long id) {
		Student student = studentService.updateStudent(dto, id);
		StudentResponseDto response = new StudentResponseDto(student);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
		studentService.deleteStudent(id);
		return ResponseEntity.noContent().build();
	}
}