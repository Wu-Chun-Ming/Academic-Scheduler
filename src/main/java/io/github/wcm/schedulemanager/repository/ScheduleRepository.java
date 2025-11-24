package io.github.wcm.schedulemanager.repository;

import io.github.wcm.schedulemanager.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
	
}