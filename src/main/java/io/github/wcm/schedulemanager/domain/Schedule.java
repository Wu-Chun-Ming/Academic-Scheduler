package io.github.wcm.schedulemanager.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.DynamicUpdate;

import io.github.wcm.schedulemanager.dto.ScheduleRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Data
@Entity
@Table(name = "schedules")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Schedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private LocalDate startDate;
	private LocalDate endDate;

	private LocalTime startTime;
	private LocalTime endTime;

	@ManyToOne(optional = false)
	@JoinColumn(name = "coursecode", referencedColumnName = "code")
	private Course course;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ScheduleType type;

	@Column(columnDefinition = "json", nullable = false)
	private String detail;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private Scope scope = Scope.OFFICIAL;

	public Schedule(ScheduleRequestDto dto, Course course) {
		this.course = course; // resolved from repository

		this.startDate = dto.getStartDate();
		this.endDate = dto.getEndDate();
		this.startTime = dto.getStartTime();
		this.endTime = dto.getEndTime();
		try {
			this.type = ScheduleType.valueOf(dto.getType().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid schedule type: " + dto.getType());
		}

		this.detail = dto.getDetail();

		try {
			this.status = Status.valueOf(dto.getStatus().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid status: " + dto.getStatus());
		}

        try {
            this.scope = Scope.valueOf(dto.getScope().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid scope: " + dto.getScope());
        }
	}
}
