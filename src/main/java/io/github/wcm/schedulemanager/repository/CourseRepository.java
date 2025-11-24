package io.github.wcm.schedulemanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.wcm.schedulemanager.domain.Course;

public interface CourseRepository extends JpaRepository<Course, String> {
	boolean existsByCode(String code);
	Optional<Course> findByCode(String code);
	void deleteByCode(String code);
}