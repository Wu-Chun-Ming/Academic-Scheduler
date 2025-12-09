package io.github.wcm.academicscheduler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.wcm.academicscheduler.domain.Course;
import io.github.wcm.academicscheduler.domain.CourseTimeslots;
import io.github.wcm.academicscheduler.domain.Student;
import io.github.wcm.academicscheduler.domain.Timeslot;
import io.github.wcm.academicscheduler.domain.enums.ProgrammeType;
import io.github.wcm.academicscheduler.dto.CourseRequestDto;
import io.github.wcm.academicscheduler.exception.CourseNotFoundException;
import io.github.wcm.academicscheduler.repository.CourseRepository;
import jakarta.persistence.EntityManager;

@Service
@Transactional
public class CourseService {

	private final EntityManager entityManager;

	private final CourseRepository courseRepository;

	@Autowired
	private StudentService studentService;

	public CourseService(CourseRepository courseRepository, EntityManager entityManager) {
		this.courseRepository = courseRepository;
		this.entityManager = entityManager;
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
		if (!courseRepository.existsByCode(code)) {
			throw new CourseNotFoundException(code);
		}
		courseRepository.deleteByCode(code);
	}

	@Transactional(readOnly = true)
	public List<Course> getCurrentCourses() {
		Student currentStudent = studentService.getCurrentStudent();
		ProgrammeType programmeType = currentStudent.getProgrammeType();
		int currentYear = currentStudent.getCurrentYear();
		int currentSemester = currentStudent.getCurrentSemester();

		return courseRepository.findByProgrammeTypeAndYearAndSemester(programmeType, currentYear, currentSemester);
	}
}
