package io.github.wcm.schedulemanager.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.wcm.schedulemanager.domain.Course;
import io.github.wcm.schedulemanager.domain.Detail;
import io.github.wcm.schedulemanager.domain.Schedule;
import io.github.wcm.schedulemanager.domain.ScheduleType;
import io.github.wcm.schedulemanager.domain.Scope;
import io.github.wcm.schedulemanager.domain.Status;
import io.github.wcm.schedulemanager.dto.ScheduleRequestDto;
import io.github.wcm.schedulemanager.exception.CourseNotFoundException;
import io.github.wcm.schedulemanager.exception.ScheduleNotFoundException;
import io.github.wcm.schedulemanager.repository.CourseRepository;
import io.github.wcm.schedulemanager.repository.ScheduleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
@Transactional
public class ScheduleService {
	@PersistenceContext
	EntityManager entityManager;

	private final CourseRepository courseRepository;
	private final ScheduleRepository scheduleRepository;

	// Autowired constructor since only one constructor
	public ScheduleService(CourseRepository courseRepository, ScheduleRepository scheduleRepository) {
		this.courseRepository = courseRepository;
		this.scheduleRepository = scheduleRepository;
	}

	@Transactional(readOnly = true)
	@Cacheable("schedules")
	public List<Schedule> getAllSchedules() {
		List<Schedule> schedules = entityManager.createQuery(
		    "SELECT s FROM Schedule s JOIN FETCH s.course "
			+ "ORDER BY s.startDate DESC, s.startTime DESC",
			Schedule.class)
		.getResultList();

		return schedules;
	}

	@Transactional(readOnly = true)
	public Schedule getScheduleById(int id) {
		return scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException(id));
	}

	@CacheEvict(value = "schedules", allEntries = true)
	public Schedule createSchedule(ScheduleRequestDto dto) {
		// Load course entity
		Course course = courseRepository.findByCode(dto.getCourseCode())
				.orElseThrow(() -> new CourseNotFoundException(dto.getCourseCode()));

		// Create entity
		Schedule schedule = new Schedule(dto, course);
		// Save entity
		return scheduleRepository.save(schedule);
	}

	@CacheEvict(value = "schedules", allEntries = true)
	public Schedule updateSchedule(ScheduleRequestDto dto, int id) {
		// Load schedule entity
		Schedule schedule = scheduleRepository.findById(id)
				.orElseThrow(() -> new ScheduleNotFoundException(id));

		// Load course entity
		Course course = courseRepository.findByCode(dto.getCourseCode())
				.orElseThrow(() -> new CourseNotFoundException(dto.getCourseCode()));

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
		Detail detail = new Detail(dto.getDescription(), dto.getVenue());
		schedule.setDetail(detail);

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

		return scheduleRepository.save(schedule);
	}

	@CacheEvict(value = "schedules", allEntries = true)
	public void deleteSchedule(int id) {
		scheduleRepository.deleteById(id);
	}

	// Update schedule status
	private void updateScheduleStatus(Schedule schedule, Status newStatus) {
		schedule.setStatus(newStatus);
		scheduleRepository.save(schedule);
	}

	// Update expired schedule's status and get current schedules
	@Transactional(readOnly = true)
	public List<Schedule> getCurrentSchedules() {
		LocalDate today = LocalDate.now();
		LocalTime now = LocalTime.now();
		
		// Get pending schedules
		List<Schedule> pendingSchedules = entityManager.createQuery(
			"SELECT s FROM Schedule s " 
			+ "WHERE s.status = :pending",
			Schedule.class)
		.setParameter("pending", Status.PENDING)
		.getResultList();

		// Update status of expired schedules
		for (Schedule schedule : pendingSchedules) {
			LocalDateTime scheduleEndTime = schedule.getEndDate().atTime(schedule.getEndTime());
			if (LocalDateTime.now().isAfter(scheduleEndTime)) {
				updateScheduleStatus(schedule, Status.EXPIRED);
			}
		}

		// Get current schedules
		List<Schedule> currentSchedules = entityManager.createQuery(
			"SELECT s FROM Schedule s JOIN FETCH s.course " 
			+ "WHERE s.status = :pending " 
			+ "AND s.startDate <= :today AND s.endDate >= :today " 
			+ "AND s.startTime <= :now AND s.endTime >= :now",
			Schedule.class)
		.setParameter("pending", Status.PENDING)
		.setParameter("today", today)
		.setParameter("now", now)
		.getResultList();

		return currentSchedules;
	}
}
