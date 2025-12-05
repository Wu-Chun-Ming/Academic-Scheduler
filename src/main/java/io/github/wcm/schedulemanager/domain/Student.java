package io.github.wcm.schedulemanager.domain;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicUpdate;

import io.github.wcm.schedulemanager.domain.enums.ProgrammeType;
import io.github.wcm.schedulemanager.dto.StudentRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Student {
	@Id
	private Long id;

	@Column(nullable = false)
	private String name;

	private String gender;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ProgrammeType programmeType;

	@Column(nullable = false)
	private String programme;

	@Column(nullable = false)
	private LocalDate enrollmentDate;
	@Column(nullable = false)
	private LocalDate graduationDate;

	@Column(nullable = false)
	private int currentYear;
	@Column(nullable = false)
	private int currentSemester;

	@Column(nullable = false)
	@Builder.Default
	private String status = "NORMAL";

	public Student(StudentRequestDto dto) {
		this.name = dto.getName();
		this.gender = dto.getGender();
		try {
			this.programmeType = ProgrammeType.valueOf(dto.getProgrammeType().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid programme type: " + dto.getProgrammeType());
		}
		this.programme = dto.getProgramme();
		this.enrollmentDate = dto.getEnrollmentDate();
		this.graduationDate = dto.getGraduationDate();
		this.currentYear = dto.getCurrentYear();
		this.currentSemester = dto.getCurrentSemester();
	}
}