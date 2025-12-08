package io.github.wcm.academicscheduler.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.wcm.academicscheduler.domain.Student;
import io.github.wcm.academicscheduler.domain.enums.ProgrammeType;
import io.github.wcm.academicscheduler.repository.StudentRepository;

@Configuration
public class DataSeeder {

	@Bean
	CommandLineRunner seedDefaultStudent(StudentRepository studentRepository) {
		return args -> {
			String defaultName = "Default Student";
			// Check if student already exists
			if (studentRepository.existsByName(defaultName)) {
				System.out.println("Default student already exists, skipping seed.");
				return;
			}

			// Only insert if table is empty
			if (studentRepository.count() == 0) {
				Student student = new Student();
				student.setName(defaultName);
				student.setGender("M");
				student.setProgramme("Default Programme");
				student.setProgrammeType(ProgrammeType.UNDERGRADUATE);
				LocalDate today = LocalDate.now();
				student.setEnrollmentDate(LocalDate.of(today.getYear(), 1, 1));
				student.setGraduationDate(LocalDate.of(today.getYear() + 2, 1, 1));
				student.setCurrentYear(1);
				student.setCurrentSemester(1);
				student.setStatus("NORMAL");

				studentRepository.save(student);
				System.out.println("Default student seeded!");
			}
		};
	}
}
