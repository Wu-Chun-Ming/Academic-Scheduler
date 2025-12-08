package io.github.wcm.academicscheduler.controller;

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

import io.github.wcm.academicscheduler.domain.Course;
import io.github.wcm.academicscheduler.dto.CourseRequestDto;
import io.github.wcm.academicscheduler.dto.CourseResponseDto;
import io.github.wcm.academicscheduler.service.CourseService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {

	private final CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping
	public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
		List<Course> courses = courseService.getAllCourses();
		List<CourseResponseDto> response = courses.stream().map(CourseResponseDto::new).toList();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{code}")
	public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable String code) {
		Course course = courseService.getCourseByCode(code);
		CourseResponseDto response = new CourseResponseDto(course);

		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<CourseResponseDto> createCourse(@Valid @RequestBody CourseRequestDto dto) {
		Course course = courseService.createCourse(dto);
		CourseResponseDto response = new CourseResponseDto(course);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{code}")
	public ResponseEntity<CourseResponseDto> updateCourse(@Valid @RequestBody CourseRequestDto dto, @PathVariable String code) {
		Course course = courseService.updateCourse(dto, code);
		CourseResponseDto response = new CourseResponseDto(course);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{code}")
	public ResponseEntity<Void> deleteCourse(@PathVariable String code) {
		courseService.deleteCourse(code);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/current")
	public ResponseEntity<List<CourseResponseDto>> getCurrentCourses() {
		List<Course> courses = courseService.getCurrentCourses();
		List<CourseResponseDto> response = courses.stream().map(CourseResponseDto::new).toList();

		return ResponseEntity.ok(response);
	}
}