package io.github.wcm.academicscheduler.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.wcm.academicscheduler.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	boolean existsByName(String name);
	Optional<Student> findByName(String name);
}
