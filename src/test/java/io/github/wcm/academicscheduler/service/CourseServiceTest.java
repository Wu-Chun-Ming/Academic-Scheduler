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
import io.github.wcm.academicscheduler.dto.CourseRequestDto;
import io.github.wcm.academicscheduler.exception.CourseNotFoundException;
import io.github.wcm.academicscheduler.repository.CourseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

	@Mock
	private CourseRepository courseRepository;

	@Mock
	private EntityManager entityManager;

	@Mock
	private TypedQuery<Course> typedQuery;

	@InjectMocks
	private CourseService courseService;

    // Test data
    private Course mockCourse;
    private CourseRequestDto validRequest;
    private static final String VALID_CODE = "ABC1234";
    private static final String INVALID_CODE = "XYZ9999";
    
    @BeforeEach
    void setUp() {
        mockCourse = TestDataFactory.sampleCourse();
        validRequest = TestDataFactory.validCourseRequest();
    }

    // -------------------------------
    // getAllCourses()
    // -------------------------------
	@Test
	void shouldReturnAllCourses() {
		// Arrange
		List<Course> mockCourses = TestDataFactory.sampleCourseList();
		when(entityManager.createQuery(anyString(), eq(Course.class))).thenReturn(typedQuery);
		when(typedQuery.getResultList()).thenReturn(mockCourses);

		// Act
		List<Course> result = courseService.getAllCourses();

		// Assert
		assertThat(result)
			.hasSize(3)
			.containsExactlyElementsOf(mockCourses);
		verify(entityManager).createQuery(anyString(), eq(Course.class));
	}

	// -------------------------------
	// getCourseByCode()
	// -------------------------------
	@Test
	void shouldReturnCourseWhenCodeExists() {
		// Arrange
		String code = mockCourse.getCode();
		when(courseRepository.findByCode(code)).thenReturn(Optional.of(mockCourse));

		// Act
		Course result = courseService.getCourseByCode(code);

		// Assert
		assertThat(result)
			.usingRecursiveComparison()
			.isEqualTo(mockCourse);
		verify(courseRepository).findByCode(code);
	}

	@Test
	void shouldThrowExceptionWhenCourseNotFound() {
		when(courseRepository.findByCode(INVALID_CODE)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> courseService.getCourseByCode(INVALID_CODE))
			.isInstanceOf(CourseNotFoundException.class)
			.hasMessage("Course not found with code: " + INVALID_CODE);

		verify(courseRepository).findByCode(INVALID_CODE);
	}

	// -------------------------------
	// createCourse()
	// -------------------------------
	@Test
	void shouldCreateCourseSuccessfully() {
		when(courseRepository.save(any(Course.class))).thenReturn(mockCourse);

		Course result = courseService.createCourse(validRequest);

		assertThat(result)
			.usingRecursiveComparison()
			.isEqualTo(mockCourse);
		verify(courseRepository).save(any(Course.class));
	}

	// -------------------------------
	// updateCourse()
	// -------------------------------
	@Test
	void shouldUpdateCourseSuccessfully() {
		String code = mockCourse.getCode();
		when(courseRepository.findByCode(code)).thenReturn(Optional.of(mockCourse));
		when(courseRepository.save(any(Course.class))).thenReturn(mockCourse);

		Course result = courseService.updateCourse(validRequest, code);

		assertThat(result)
			.usingRecursiveComparison()
			.isEqualTo(mockCourse);
		verify(courseRepository).findByCode(code);
		verify(courseRepository).save(any(Course.class));
	}

	@Test
	void shouldThrowExceptionWhenUpdatingNonExistingCourse() {
		when(courseRepository.findByCode(INVALID_CODE)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> courseService.updateCourse(validRequest, INVALID_CODE))
			.isInstanceOf(CourseNotFoundException.class)
			.hasMessage("Course not found with code: " + INVALID_CODE);

		verify(courseRepository).findByCode(INVALID_CODE);
		verify(courseRepository, never()).save(any(Course.class));
	}

	// -------------------------------
	// deleteCourse()
	// -------------------------------
	@Test
	void shouldDeleteCourseSuccessfully() {
		when(courseRepository.existsByCode(VALID_CODE)).thenReturn(true);

		courseService.deleteCourse(VALID_CODE);

		verify(courseRepository).existsByCode(VALID_CODE);
		verify(courseRepository).deleteByCode(VALID_CODE);
	}

	@Test
	void shouldThrowExceptionWhenDeletingNonExistingCourse() {
		when(courseRepository.existsByCode(INVALID_CODE)).thenReturn(false);

		assertThatThrownBy(() -> courseService.deleteCourse(INVALID_CODE))
			.isInstanceOf(CourseNotFoundException.class)
			.hasMessage("Course not found with code: " + INVALID_CODE);

		verify(courseRepository).existsByCode(INVALID_CODE);
		verify(courseRepository, never()).deleteByCode(anyString());
	}
}