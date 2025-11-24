package io.github.wcm.schedulemanager.domain;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.DynamicUpdate;

import io.github.wcm.schedulemanager.dto.CourseRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "courses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Check(constraints = "(year BETWEEN 1 AND 3 AND semester BETWEEN 1 AND 3) OR (year = 0 AND semester = 0)")
public class Course {
	@Id
	@Column(unique = true, nullable = false)
	@Size(min = 7, max = 8)
	private String code;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Min(1)
	@Max(3)
	private int year;

	@Column(nullable = false)
	@Min(1)
	@Max(3)
	private int semester;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ProgrammeType programmeType;

	@Column(columnDefinition = "json")
	private String timeslots;

	public Course(CourseRequestDto dto) {
		this.code = dto.getCode();
		this.name = dto.getName();
		this.year = dto.getYear();
		this.semester = dto.getSemester();
		try {
			this.programmeType = ProgrammeType.valueOf(dto.getProgrammeType().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid programme type: " + dto.getProgrammeType());
		}
		this.timeslots = dto.getTimeslots();
	}
}
