package io.github.wcm.academicscheduler.repository;

import io.github.wcm.academicscheduler.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
	
}