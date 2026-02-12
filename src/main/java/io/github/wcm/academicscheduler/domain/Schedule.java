package io.github.wcm.academicscheduler.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import io.github.wcm.academicscheduler.domain.enums.ScheduleStatus;
import io.github.wcm.academicscheduler.domain.enums.ScheduleType;
import io.github.wcm.academicscheduler.domain.enums.Scope;
import io.github.wcm.academicscheduler.dto.ScheduleRequestDto;
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

//@Data
@Entity
@Table(name = "schedules")
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
	@JoinColumn(name = "courseCode", referencedColumnName = "code")
	private Course course;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ScheduleType type;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "json", nullable = false)
	private Detail detail;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ScheduleStatus status;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Scope scope = Scope.OFFICIAL;

	public Schedule() {
	}

	public Schedule(int id, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime,
			Course course, ScheduleType type, Detail detail, ScheduleStatus status, Scope scope) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.course = course;
		this.type = type;
		this.detail = detail;
		this.status = status;
		this.scope = scope;
	}

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

		this.detail = new Detail(dto.getDescription(), dto.getVenue());

		try {
			this.status = ScheduleStatus.valueOf(dto.getStatus().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid status: " + dto.getStatus());
		}

        try {
            this.scope = Scope.valueOf(dto.getScope().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid scope: " + dto.getScope());
        }
	}

	public int getId() {
		return id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public ScheduleType getType() {
		return type;
	}

	public void setType(ScheduleType type) {
		this.type = type;
	}

	public Detail getDetail() {
		return detail;
	}

	public void setDetail(Detail detail) {
		this.detail = detail;
	}

	public ScheduleStatus getStatus() {
		return status;
	}

	public void setStatus(ScheduleStatus status) {
		this.status = status;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	// Calculate time left
	public String calculateTimeLeft() {
		LocalDate today = LocalDate.now();
		LocalTime now = LocalTime.now();

		// If the schedule has ended
		if (today.isAfter(endDate)) {
			return "Ended";
		}
		// If the schedule is ongoing
		else if (today.isAfter(startDate)) {
			return "Ongoing";
		}
		// If the schedule is yet to start but starts today
		else if (today.isEqual(startDate)) {
			long hoursLeft = ChronoUnit.HOURS.between(now, startTime);
			long minutesLeft = ChronoUnit.MINUTES.between(now, startTime);

			if (now.isAfter(endTime)) {
				return "Ended";
			}
			else if (now.isAfter(startTime)) {
				return "Ongoing";
			}
			else {
				return (hoursLeft > 0 ? hoursLeft + " hours" : "") + (hoursLeft <= 0 ? minutesLeft + " minutes" : "");
			}
		}
		// If the schedule is yet to start and starts in future
		else {
			long daysLeft = ChronoUnit.DAYS.between(today, startDate);
			long hoursLeft = ChronoUnit.HOURS.between(now, startTime);
			return (daysLeft > 0 ? daysLeft + " days " : "") + (hoursLeft > 0 ? hoursLeft + " hours" : "");
		}
	}
}
