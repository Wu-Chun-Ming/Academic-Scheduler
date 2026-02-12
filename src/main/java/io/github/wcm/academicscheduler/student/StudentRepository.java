package io.github.wcm.academicscheduler.student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
	boolean existsByName(String name);
	Optional<Student> findByName(String name);
}
