package io.github.wcm.schedulemanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.wcm.schedulemanager.domain.Course;
import io.github.wcm.schedulemanager.domain.ProgrammeType;

public interface CourseRepository extends JpaRepository<Course, String> {
	boolean existsByCode(String code);
	Optional<Course> findByCode(String code);
	void deleteByCode(String code);
	List<Course> findByProgrammeTypeAndYearAndSemester(ProgrammeType programmeType, int year, int semester);
}