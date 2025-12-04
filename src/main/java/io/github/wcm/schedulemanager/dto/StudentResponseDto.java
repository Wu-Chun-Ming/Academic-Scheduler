package io.github.wcm.schedulemanager.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import io.github.wcm.schedulemanager.domain.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentResponseDto {
	private Long id;

	private String name;

	private String gender;

	private String programmeType;

	private String programme;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate enrollmentDate;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate graduationDate;

	private int currentYear;
	private int currentSemester;

	private String status;

	public StudentResponseDto(Student student) {
		this.id = student.getId();
		this.name = student.getName();
		this.gender = student.getGender();
		this.programmeType = student.getProgrammeType().name();
		this.programme = student.getProgramme();
		this.enrollmentDate = student.getEnrollmentDate();
		this.graduationDate = student.getGraduationDate();
		this.currentYear = student.getCurrentYear();
		this.currentSemester = student.getCurrentSemester();
		this.status = student.getStatus();
	}
}