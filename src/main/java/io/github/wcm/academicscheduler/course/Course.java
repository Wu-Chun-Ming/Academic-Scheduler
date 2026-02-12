package io.github.wcm.academicscheduler.course;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import io.github.wcm.academicscheduler.course.api.CourseRequestDto;
import io.github.wcm.academicscheduler.course.internal.CourseTimeslots;
import io.github.wcm.academicscheduler.course.internal.Timeslot;
import io.github.wcm.academicscheduler.common.api.ProgrammeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "courses")
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

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "json")
	private CourseTimeslots timeslots;

	public Course() {
	}

	public Course(String code, String name, int year, int semester, ProgrammeType programmeType, CourseTimeslots timeslots) {
		this.code = code;
		this.name = name;
		this.year = year;
		this.semester = semester;
		this.programmeType = programmeType;
		this.timeslots = timeslots;
	}

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
		this.timeslots = new CourseTimeslots(
			dto.getLecture().stream().map(Timeslot::new).toList(),
			dto.getTutorial().stream().map(Timeslot::new).toList(),
			dto.getPractical().stream().map(Timeslot::new).toList()
		);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public ProgrammeType getProgrammeType() {
		return programmeType;
	}

	public void setProgrammeType(ProgrammeType programmeType) {
		this.programmeType = programmeType;
	}

	public CourseTimeslots getTimeslots() {
		return timeslots;
	}

	public void setTimeslots(CourseTimeslots timeslots) {
		this.timeslots = timeslots;
	}
}
