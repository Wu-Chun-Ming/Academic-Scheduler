package io.github.wcm.schedulemanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.wcm.schedulemanager.domain.Course;
import io.github.wcm.schedulemanager.domain.Schedule;
import io.github.wcm.schedulemanager.domain.ScheduleType;
import io.github.wcm.schedulemanager.domain.Scope;
import io.github.wcm.schedulemanager.domain.Status;
import io.github.wcm.schedulemanager.dto.ScheduleRequestDto;
import io.github.wcm.schedulemanager.exception.CourseNotFoundException;
import io.github.wcm.schedulemanager.exception.ScheduleNotFoundException;
import io.github.wcm.schedulemanager.repository.CourseRepository;
import io.github.wcm.schedulemanager.repository.ScheduleRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ScheduleService {
	private final CourseRepository courseRepository;
	private final ScheduleRepository scheduleRepository;

	// Autowired constructor since only one constructor
	public ScheduleService(CourseRepository courseRepository, ScheduleRepository scheduleRepository) {
		this.courseRepository = courseRepository;
		this.scheduleRepository = scheduleRepository;
	}

	public List<Schedule> getAllSchedules() {
		return scheduleRepository.findAll();
	}

	public Schedule getScheduleById(int id) {
		return scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException(id));
	}

	public Schedule createSchedule(ScheduleRequestDto dto) {
		// Load course entity
		Course course = courseRepository.findByCode(dto.getCoursecode())
				.orElseThrow(() -> new CourseNotFoundException(dto.getCoursecode()));

		// Create entity
		Schedule schedule = new Schedule(dto, course);
		// Save entity
		return scheduleRepository.save(schedule);
	}

	public Schedule updateSchedule(ScheduleRequestDto dto, int id) {
		// Load schedule entity
		Schedule schedule = scheduleRepository.findById(id)
				.orElseThrow(() -> new ScheduleNotFoundException(id));

		// Load course entity
		Course course = courseRepository.findByCode(dto.getCoursecode())
				.orElseThrow(() -> new CourseNotFoundException(dto.getCoursecode()));

		// Validations
		if (dto.getStartDate().isAfter(dto.getEndDate())) {
			throw new IllegalArgumentException("Start date cannot be after end date");
		}
		if (dto.getStartTime().isAfter(dto.getEndTime())) {
			throw new IllegalArgumentException("Start time cannot be after end time");
		}

		// Update fields
		schedule.setCourse(course);
		schedule.setStartDate(dto.getStartDate());
		schedule.setEndDate(dto.getEndDate());
		schedule.setStartTime(dto.getStartTime());
		schedule.setEndTime(dto.getEndTime());
		schedule.setDetail(dto.getDetail());

		try {
			schedule.setType(ScheduleType.valueOf(dto.getType().toUpperCase()));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid schedule type: " + dto.getType());
		}
		try {
			schedule.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid status: " + dto.getStatus());
		}
		try {
			schedule.setScope(Scope.valueOf(dto.getScope().toUpperCase()));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid scope: " + dto.getScope());
		}

		return schedule;
	}

	public void deleteSchedule(int id) {
		scheduleRepository.deleteById(id);
	}
}
