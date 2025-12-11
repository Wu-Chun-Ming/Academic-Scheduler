package io.github.wcm.academicscheduler.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.DockerClientFactory;

import io.github.wcm.academicscheduler.MySQLContainerTest;
import io.github.wcm.academicscheduler.TestDataFactory;
import io.github.wcm.academicscheduler.domain.Course;
import io.github.wcm.academicscheduler.domain.enums.ProgrammeType;
import jakarta.persistence.EntityManager;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@EnabledIf("io.github.wcm.academicscheduler.repository.CourseRepositoryTest#isDockerAvailable")
public class CourseRepositoryTest extends MySQLContainerTest {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private EntityManager entityManager;

	// Check if Docker is available for Testcontainers
	public static boolean isDockerAvailable() {
		return DockerClientFactory.instance().isDockerAvailable();
	}

	// Assert that scalar fields are equal
	private void assertCourseScalarFieldsEqual(Course actual, Course expected) {
		assertThat(actual.getCode()).isEqualTo(expected.getCode());
		assertThat(actual.getName()).isEqualTo(expected.getName());
		assertThat(actual.getYear()).isEqualTo(expected.getYear());
		assertThat(actual.getSemester()).isEqualTo(expected.getSemester());
		assertThat(actual.getProgrammeType()).isEqualTo(expected.getProgrammeType());
	}

	// Assert that timeslots are equal
	private void assertTimeslotsEqual(Course actual, Course expected) {
		assertThat(actual.getTimeslots()).usingRecursiveComparison().isEqualTo(expected.getTimeslots());
	}

	// Create a new sample course
	private Course newSampleCourse() {
		return TestDataFactory.sampleCourse();
	}

	// -------------------------------
	// save()
	// -------------------------------
	@Test
	void shouldSaveCourse() {
		Course mockCourse = newSampleCourse();

		Course result = courseRepository.save(mockCourse);

		assertCourseScalarFieldsEqual(result, mockCourse);
		assertTimeslotsEqual(result, mockCourse);
	}

	// -------------------------------
	// existsByCode()
	// -------------------------------
	@Test
	void shouldCheckExistsByCode() {
		Course mockCourse = newSampleCourse();
		courseRepository.save(mockCourse);

		boolean exists = courseRepository.existsByCode(mockCourse.getCode());

		assertThat(exists).isTrue();
	}

	// -------------------------------
	// findByCode()
	// -------------------------------
	@Test
	void shouldFindCourseByCode() {
		Course mockCourse = newSampleCourse();
		courseRepository.save(mockCourse);

		Course result = courseRepository.findByCode(mockCourse.getCode()).orElseThrow();

		assertCourseScalarFieldsEqual(result, mockCourse);
		assertTimeslotsEqual(result, mockCourse);
	}

	// -------------------------------
	// deleteByCode()
	// -------------------------------
	@Test
	void shouldDeleteCourse() {
		Course mockCourse = newSampleCourse();
		courseRepository.save(mockCourse);

		courseRepository.deleteByCode(mockCourse.getCode());

		assertThat(courseRepository.existsByCode(mockCourse.getCode())).isFalse();
	}

	// ===== Custom Queries =====
	// -------------------------------
	// findByProgrammeTypeAndYearAndSemester()
	// -------------------------------
	@Test
	void shouldFindCoursesByYearAndSemester() {
		List<Course> mockCourses = TestDataFactory.sampleCourseList();

		courseRepository.saveAll(mockCourses);

		List<Course> results = courseRepository.findByProgrammeTypeAndYearAndSemester(ProgrammeType.UNDERGRADUATE, 1,
				1);

		List<Course> expectedCourses = mockCourses.stream().filter(
				c -> c.getProgrammeType() == ProgrammeType.UNDERGRADUATE && c.getYear() == 1 && c.getSemester() == 1)
				.toList();

		assertThat(results).hasSize(expectedCourses.size());
		results.forEach(course -> {
			Course expected = expectedCourses.stream().filter(c -> c.getCode().equals(course.getCode())).findFirst()
					.orElseThrow(() -> new AssertionError("Unexpected course found: " + course.getCode()));

			assertCourseScalarFieldsEqual(course, expected);
			assertTimeslotsEqual(course, expected);
		});
	}

	// ===== Constraint Tests =====
	// -------------------------------
	// Database Level Constraints
	// -------------------------------
	@Test
	void shouldThrowExceptionWhenCourseAlreadyExists() {
		Course course1 = newSampleCourse();
		Course course2 = newSampleCourse();
		entityManager.persist(course1);
		entityManager.flush();
		
		entityManager.clear();

		assertThatThrownBy(() -> {
			entityManager.persist(course2);
			entityManager.flush(); // forces real SQL INSERT
		}).isInstanceOf(org.hibernate.exception.ConstraintViolationException.class);
	}

	@Test
	void shouldNotAllowDuplicateCourseCode() {
		Course course1 = newSampleCourse();
		entityManager.persist(course1);
		entityManager.flush();

		// Clear the persistence context to avoid caching effects
		entityManager.clear();

		Course duplicate = newSampleCourse();
		duplicate.setCode(course1.getCode());

		assertThatThrownBy(() -> {
			entityManager.persist(duplicate);
			entityManager.flush(); // forces real SQL INSERT
		}).isInstanceOf(org.hibernate.exception.ConstraintViolationException.class);
	}

	// -------------------------------
	// Bean Validation Constraints
	// -------------------------------
	@Test
	void shouldFailWhenCodeTooShort() {
		Course course = newSampleCourse();
		course.setCode("ABC");

		assertThatThrownBy(() -> courseRepository.saveAndFlush(course))
				.isInstanceOf(jakarta.validation.ConstraintViolationException.class);
	}

	@Test
	void shouldFailWhenYearOutOfRange() {
		Course invalid = newSampleCourse();
		invalid.setYear(0);

		assertThatThrownBy(() -> courseRepository.saveAndFlush(invalid))
				.isInstanceOf(jakarta.validation.ConstraintViolationException.class);
	}

	@Test
	void shouldFailCheckConstraintForYearAndSemester() {
		Course invalid = newSampleCourse();
		invalid.setYear(0);
		invalid.setSemester(1);

		assertThatThrownBy(() -> courseRepository.saveAndFlush(invalid))
				.isInstanceOf(jakarta.validation.ConstraintViolationException.class);
	}
}
