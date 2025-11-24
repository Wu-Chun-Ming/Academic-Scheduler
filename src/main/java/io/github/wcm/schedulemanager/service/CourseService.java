package io.github.wcm.schedulemanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.wcm.schedulemanager.domain.Course;
import io.github.wcm.schedulemanager.domain.ProgrammeType;
import io.github.wcm.schedulemanager.dto.CourseRequestDto;
import io.github.wcm.schedulemanager.exception.CourseNotFoundException;
import io.github.wcm.schedulemanager.repository.CourseRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CourseService {
	private final CourseRepository courseRepository;

	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}

	public Course getCourseByCode(String code) {
		return courseRepository.findByCode(code).orElseThrow(() -> new CourseNotFoundException(code));
	}

	public Course createCourse(CourseRequestDto dto) {
		// Create entity from DTO
		Course course = new Course(dto);
		// Save entity
		return courseRepository.save(course);
	}

	public Course updateCourse(CourseRequestDto dto, String code) {
		// Load course entity
		Course course = courseRepository.findByCode(code).orElseThrow(() -> new CourseNotFoundException(code));

		// Update fields
		course.setCode(dto.getCode());
		course.setName(dto.getName());
		course.setYear(dto.getYear());
		course.setSemester(dto.getSemester());
		course.setTimeslots(dto.getTimeslots());

		try {
			course.setProgrammeType(ProgrammeType.valueOf(dto.getProgrammeType().toUpperCase()));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid programme type: " + dto.getProgrammeType());
		}

		return course;
	}

	public void deleteCourse(String code) {
		courseRepository.deleteByCode(code);
	}
}
