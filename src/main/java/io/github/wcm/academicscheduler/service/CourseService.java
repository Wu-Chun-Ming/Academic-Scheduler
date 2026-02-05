package io.github.wcm.academicscheduler.service;

import java.util.List;

import io.github.wcm.academicscheduler.domain.Course;
import io.github.wcm.academicscheduler.dto.CourseRequestDto;

public interface CourseService {
	List<Course> getAllCourses();
	Course getCourseByCode(String code);
	Course createCourse(CourseRequestDto dto);
	Course updateCourse(CourseRequestDto dto, String code);
	void deleteCourse(String code);
	List<Course> getCurrentCourses();
}