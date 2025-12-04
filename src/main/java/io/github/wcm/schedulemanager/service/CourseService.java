package io.github.wcm.schedulemanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.wcm.schedulemanager.domain.Course;
import io.github.wcm.schedulemanager.domain.CourseTimeslots;
import io.github.wcm.schedulemanager.domain.ProgrammeType;
import io.github.wcm.schedulemanager.domain.Timeslot;
import io.github.wcm.schedulemanager.dto.CourseRequestDto;
import io.github.wcm.schedulemanager.exception.CourseNotFoundException;
import io.github.wcm.schedulemanager.repository.CourseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class CourseService {
	
	@PersistenceContext
	EntityManager entityManager;

	private final CourseRepository courseRepository;

	@Autowired
	private StudentService studentService;

	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	@Transactional(readOnly = true)
	@Cacheable("courses")
	public List<Course> getAllCourses() {
		List<Course> courses = entityManager.createQuery(
		    "SELECT c FROM Course c "
			+ "ORDER BY c.year DESC, c.semester DESC",
			Course.class)
		.getResultList();

		return courses;
	}

	@Transactional(readOnly = true)
	public Course getCourseByCode(String code) {
		return courseRepository.findByCode(code).orElseThrow(() -> new CourseNotFoundException(code));
	}

	@CacheEvict(value = "courses", allEntries = true)
	public Course createCourse(CourseRequestDto dto) {
		// Create entity from DTO
		Course course = new Course(dto);
		// Save entity
		return courseRepository.save(course);
	}

	@CacheEvict(value = "courses", allEntries = true)
	public Course updateCourse(CourseRequestDto dto, String code) {
		// Load course entity
		Course course = courseRepository.findByCode(code).orElseThrow(() -> new CourseNotFoundException(code));

		// Update fields
		course.setCode(dto.getCode());
		course.setName(dto.getName());
		course.setYear(dto.getYear());
		course.setSemester(dto.getSemester());
		course.setTimeslots(new CourseTimeslots(
			dto.getLecture().stream().map(Timeslot::new).toList(),
			dto.getTutorial().stream().map(Timeslot::new).toList(),
			dto.getPractical().stream().map(Timeslot::new).toList()
		));

		try {
			course.setProgrammeType(ProgrammeType.valueOf(dto.getProgrammeType().toUpperCase()));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid programme type: " + dto.getProgrammeType());
		}

		return courseRepository.save(course);
	}

	@CacheEvict(value = "courses", allEntries = true)
	public void deleteCourse(String code) {
		courseRepository.deleteByCode(code);
	}

	@Transactional(readOnly = true)
	public List<Course> getCurrentCourses() {
		int currentYear = studentService.getCurrentStudent().getCurrentYear();
		int currentSemester = studentService.getCurrentStudent().getCurrentSemester();

		return courseRepository.findByYearAndSemester(currentYear, currentSemester);
	}
}
