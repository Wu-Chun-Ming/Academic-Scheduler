package io.github.wcm.academicscheduler;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.github.wcm.academicscheduler.domain.Course;
import io.github.wcm.academicscheduler.domain.CourseTimeslots;
import io.github.wcm.academicscheduler.domain.Detail;
import io.github.wcm.academicscheduler.domain.Schedule;
import io.github.wcm.academicscheduler.domain.Timeslot;
import io.github.wcm.academicscheduler.domain.enums.ProgrammeType;
import io.github.wcm.academicscheduler.domain.enums.ScheduleStatus;
import io.github.wcm.academicscheduler.domain.enums.ScheduleType;
import io.github.wcm.academicscheduler.domain.enums.Scope;
import io.github.wcm.academicscheduler.dto.CourseRequestDto;
import io.github.wcm.academicscheduler.dto.ScheduleRequestDto;
import io.github.wcm.academicscheduler.dto.TimeslotDto;

public class TestDataFactory {

    public static Course sampleCourse() {
        return new Course(
            "ABCDEFG",
            "Course A",
            1,
            1,
            ProgrammeType.UNDERGRADUATE,
            new CourseTimeslots(Collections.emptyList(), Collections.emptyList(), Collections.emptyList())  // or some sample list of lectures
        );
    }

    public static Course sampleCourse2() {
        return new Course(
            "HIJKLMN",
            "Course B",
            2,
            1,
            ProgrammeType.UNDERGRADUATE,
            new CourseTimeslots(Collections.emptyList(), Collections.emptyList(), Collections.emptyList())
        );
    }

    public static Course sampleCourseWithTimeslots() {
        List<Timeslot> lectures = new ArrayList<>();
        lectures.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.parse("10:00"), LocalTime.parse("12:00")));
        
        List<Timeslot> tutorials = new ArrayList<>();
        tutorials.add(new Timeslot(DayOfWeek.WEDNESDAY, LocalTime.parse("14:00"), LocalTime.parse("15:00")));

        List<Timeslot> practicals = new ArrayList<>();
        practicals.add(new Timeslot(DayOfWeek.FRIDAY, LocalTime.parse("09:00"), LocalTime.parse("12:00")));

        Course course = sampleCourse();
        course.setTimeslots(new CourseTimeslots(lectures, tutorials, practicals));
        
        return course;
    }
	
	public static List<Course> sampleCourseList() {
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(sampleCourse());
        courses.add(sampleCourse2());
        courses.add(sampleCourseWithTimeslots());

        return courses;
    }

    public static CourseRequestDto validCourseRequest() {
        CourseRequestDto dto = new CourseRequestDto();
        Course course = sampleCourseWithTimeslots();
        dto.setCode(course.getCode());
        dto.setName(course.getName());
        dto.setYear(course.getYear());
        dto.setSemester(course.getSemester());
        dto.setProgrammeType(course.getProgrammeType().name());
        List<TimeslotDto> lectureTimeslots = course.getTimeslots().getLecture().stream().map(TimeslotDto::new).collect(Collectors.toCollection(ArrayList::new));
        dto.setLecture(lectureTimeslots);     // ensure non-null list
        List<TimeslotDto> tutorialSlots = course.getTimeslots().getTutorial().stream().map(TimeslotDto::new).collect(Collectors.toCollection(ArrayList::new));
        dto.setTutorial(tutorialSlots);
        List<TimeslotDto> practicalSlots = new ArrayList<TimeslotDto>();
        dto.setPractical(practicalSlots);

        return dto;
    }

    public static CourseRequestDto invalidCourseRequest() {
        CourseRequestDto dto = new CourseRequestDto();
        dto.setCode(null);
        dto.setName(null);
        dto.setYear(-1);
        dto.setSemester(0);
        dto.setProgrammeType("FOUNDATION");
        dto.setLecture(null);
        dto.setTutorial(null);
        dto.setPractical(null);

        return dto;
    }    

    public static Schedule sampleSchedule() {
        return new Schedule(
        	123,
        	LocalDate.parse("2025-01-01"),
        	LocalDate.parse("2025-02-01"),
        	LocalTime.parse("00:00"),
        	LocalTime.parse("23:59"),
        	sampleCourse(),
        	ScheduleType.L,
        	new Detail("Description", ""),
        	ScheduleStatus.PENDING,
        	Scope.OFFICIAL
        );
    }

    public static Schedule sampleSchedule2() {
        return new Schedule(
        	124,
        	LocalDate.parse("2025-03-01"),
        	LocalDate.parse("2025-04-01"),
        	LocalTime.parse("09:00"),
        	LocalTime.parse("11:00"),
        	sampleCourse2(),
        	ScheduleType.T,
        	new Detail("Description for schedule 2", "Room 101"),
        	ScheduleStatus.PENDING,
        	Scope.OFFICIAL
        );
    }

    public static List<Schedule> sampleScheduleList() {
        List<Schedule> schedules = new ArrayList<>();
        schedules.add(sampleSchedule());
        schedules.add(sampleSchedule2());

        return schedules;
    }

    public static ScheduleRequestDto validScheduleRequest() {
    	ScheduleRequestDto dto = new ScheduleRequestDto();
        Schedule schedule = sampleSchedule();
    	dto.setStartDateTime(LocalDateTime.of(schedule.getStartDate(), schedule.getStartTime()));
    	dto.setEndDateTime(LocalDateTime.of(schedule.getEndDate(), schedule.getEndTime()));
        dto.setCourseCode(schedule.getCourse().getCode());
        dto.setType(schedule.getType().name());
        dto.setDescription(schedule.getDetail().getDescription());
        dto.setVenue(schedule.getDetail().getVenue());
        dto.setStatus(schedule.getStatus().name());
        dto.setScope(schedule.getScope().name());

        return dto;
    }

    public static ScheduleRequestDto invalidScheduleRequest() {
    	ScheduleRequestDto dto = new ScheduleRequestDto();
    	dto.setStartDateTime(null);
    	dto.setEndDateTime(null);
        dto.setCourseCode(null);
        dto.setType("INVALID_TYPE");
        dto.setDescription(null);
        dto.setVenue(null);
        dto.setStatus("INVALID_STATUS");
        dto.setScope("INVALID_SCOPE");

        return dto;
    }
}
