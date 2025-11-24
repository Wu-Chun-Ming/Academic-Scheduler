package io.github.wcm.schedulemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import io.github.wcm.schedulemanager.domain.Course;
import io.github.wcm.schedulemanager.dto.CourseRequestDto;
import io.github.wcm.schedulemanager.dto.CourseResponseDto;
import io.github.wcm.schedulemanager.service.CourseService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api")
public class CourseController {
	@Autowired
	private CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping("/courses")
	public List<Course> getAllCourses() {
		return courseService.getAllCourses();
	}

	@GetMapping("/course/{code}")
	public Course getCourseById(@PathVariable String code) {
		return courseService.getCourseByCode(code);
	}

	@PostMapping("/course/{code}")
	public ResponseEntity<CourseResponseDto> createCourse(@Valid @RequestBody CourseRequestDto dto) {
		Course course = courseService.createCourse(dto);
		CourseResponseDto response = new CourseResponseDto(course);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/course/{code}")
	public ResponseEntity<CourseResponseDto> updateCourse(@Valid @RequestBody CourseRequestDto dto, @PathVariable String code) {
		Course course = courseService.updateCourse(dto, code);
		CourseResponseDto response = new CourseResponseDto(course);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/course/{code}")
	public ResponseEntity<Void> deleteCourse(@PathVariable String code) {
		courseService.deleteCourse(code);
		return ResponseEntity.noContent().build();
	}
}