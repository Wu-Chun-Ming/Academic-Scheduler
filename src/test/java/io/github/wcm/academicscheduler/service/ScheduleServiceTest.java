package io.github.wcm.academicscheduler.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.wcm.academicscheduler.TestDataFactory;
import io.github.wcm.academicscheduler.domain.Course;
import io.github.wcm.academicscheduler.domain.Schedule;
import io.github.wcm.academicscheduler.dto.ScheduleRequestDto;
import io.github.wcm.academicscheduler.exception.ScheduleNotFoundException;
import io.github.wcm.academicscheduler.repository.CourseRepository;
import io.github.wcm.academicscheduler.repository.ScheduleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private CourseRepository courseRepository;
    
    @Mock
    private EntityManager entityManager;
    
    @Mock
    private TypedQuery<Schedule> typedQuery;

    @InjectMocks
    private ScheduleService scheduleService;

    // Test data
    private Schedule mockSchedule;
    private Course mockCourse;
    private ScheduleRequestDto validRequest;
    private static final int VALID_ID = 123;
    private static final int INVALID_ID = 999;
    
    @BeforeEach
    void setUp() {
        mockSchedule = TestDataFactory.sampleSchedule();
        mockCourse = mockSchedule.getCourse();
        validRequest = TestDataFactory.validScheduleRequest();
    }

    // -------------------------------
    // getAllSchedules()
    // -------------------------------
	@Test
	void shouldReturnAllSchedules() {
		// Arrange
		List<Schedule> mockSchedules = TestDataFactory.sampleScheduleList();
		when(entityManager.createQuery(anyString(), eq(Schedule.class))).thenReturn(typedQuery);
		when(typedQuery.getResultList()).thenReturn(mockSchedules);

		// Act
		List<Schedule> result = scheduleService.getAllSchedules();

		// Assert
		assertThat(result)
			.hasSize(2)
			.containsExactlyElementsOf(mockSchedules);
		verify(entityManager).createQuery(anyString(), eq(Schedule.class));
	}

    // -------------------------------
    // getScheduleById()
    // -------------------------------
	@Test
	void shouldReturnScheduleWhenIdExists() {
		int id = mockSchedule.getId();
		when(scheduleRepository.findById(id)).thenReturn(Optional.of(mockSchedule));

		Schedule result = scheduleService.getScheduleById(id);

		assertThat(result)
			.usingRecursiveComparison()
			.isEqualTo(mockSchedule);
		verify(scheduleRepository).findById(id);
	}

	@Test
	void shouldThrowExceptionWhenScheduleNotFound() {
		when(scheduleRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> scheduleService.getScheduleById(INVALID_ID))
			.isInstanceOf(ScheduleNotFoundException.class)
			.hasMessage("Schedule not found with id: " + INVALID_ID);

		verify(scheduleRepository).findById(INVALID_ID);
	}

    // -------------------------------
    // createSchedule()
    // -------------------------------
	@Test
	void shouldCreateScheduleSuccessfully() {
		when(courseRepository.findByCode(validRequest.getCourseCode())).thenReturn(Optional.of(mockCourse));
		when(scheduleRepository.save(any(Schedule.class))).thenReturn(mockSchedule);

		Schedule result = scheduleService.createSchedule(validRequest);

		assertThat(result)
			.usingRecursiveComparison()
			.isEqualTo(mockSchedule);
		verify(courseRepository).findByCode(validRequest.getCourseCode());
		verify(scheduleRepository).save(any(Schedule.class));
	}

    // -------------------------------
    // updateSchedule()
    // -------------------------------
	@Test
	void shouldUpdateScheduleSuccessfully() {
		int id = mockSchedule.getId();
		when(scheduleRepository.findById(id)).thenReturn(Optional.of(mockSchedule));
		when(courseRepository.findByCode(validRequest.getCourseCode())).thenReturn(Optional.of(mockCourse));
		when(scheduleRepository.save(any(Schedule.class))).thenReturn(mockSchedule);

		Schedule result = scheduleService.updateSchedule(validRequest, id);

		assertThat(result)
			.usingRecursiveComparison()
			.isEqualTo(mockSchedule);
		verify(scheduleRepository).findById(id);
		verify(courseRepository).findByCode(validRequest.getCourseCode());
		verify(scheduleRepository).save(any(Schedule.class));
	}

	@Test
	void shouldThrowExceptionWhenUpdatingNonExistingSchedule() {
		when(scheduleRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> scheduleService.updateSchedule(validRequest, INVALID_ID))
			.isInstanceOf(ScheduleNotFoundException.class)
			.hasMessage("Schedule not found with id: " + INVALID_ID);

		verify(scheduleRepository).findById(INVALID_ID);
		verify(courseRepository, never()).findByCode(anyString());
        verify(scheduleRepository, never()).save(any(Schedule.class));
	}

    // -------------------------------
    // deleteSchedule()
    // -------------------------------
	@Test
	void shouldDeleteScheduleSuccessfully() {
		when(scheduleRepository.existsById(VALID_ID)).thenReturn(true);

		scheduleService.deleteSchedule(VALID_ID);

		verify(scheduleRepository).existsById(VALID_ID);
		verify(scheduleRepository).deleteById(VALID_ID);
	}

	@Test
	void shouldThrowExceptionWhenDeletingNonExistingSchedule() {
		when(scheduleRepository.existsById(INVALID_ID)).thenReturn(false);

		assertThatThrownBy(() -> scheduleService.deleteSchedule(INVALID_ID))
			.isInstanceOf(ScheduleNotFoundException.class)
			.hasMessage("Schedule not found with id: " + INVALID_ID);

		verify(scheduleRepository).existsById(INVALID_ID);
		verify(scheduleRepository, never()).deleteById(INVALID_ID);
	}
}