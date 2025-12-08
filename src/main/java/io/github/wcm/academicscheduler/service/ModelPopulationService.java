package io.github.wcm.academicscheduler.service;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import io.github.wcm.academicscheduler.domain.Course;
import io.github.wcm.academicscheduler.domain.Option;
import io.github.wcm.academicscheduler.domain.Schedule;
import io.github.wcm.academicscheduler.domain.enums.ProgrammeType;
import io.github.wcm.academicscheduler.domain.enums.ScheduleType;
import io.github.wcm.academicscheduler.domain.enums.Scope;
import io.github.wcm.academicscheduler.dto.CourseResponseDto;
import io.github.wcm.academicscheduler.dto.ScheduleResponseDto;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class ModelPopulationService {

    private final CourseService courseService;
    private final ScheduleService scheduleService;
	private final StudentService studentService;
    
    public ModelPopulationService(CourseService courseService, ScheduleService scheduleService, StudentService studentService) {
    	this.courseService = courseService;
    	this.scheduleService = scheduleService;
    	this.studentService = studentService;
    }

	// Add courses to model attribute
	public void addCoursesToModel(Model model) {
		if (!model.containsAttribute("courses")) {
			List<Course> courses = courseService.getAllCourses();
			List<CourseResponseDto> coursesDto = courses.stream().map(CourseResponseDto::new).toList();
			model.addAttribute("courses", coursesDto);
		}
	}

	// Add current courses to model attribute
	public void addCurrentCoursesToModel(Model model) {
		if (!model.containsAttribute("courses")) {
			List<Course> courses = courseService.getCurrentCourses();
			List<CourseResponseDto> coursesDto = courses.stream().map(CourseResponseDto::new).toList();
			model.addAttribute("courses", coursesDto);
		}
	}

	// Add schedules to model attribute
	public void addSchedulesToModel(Model model) {
		if (!model.containsAttribute("schedules")) {
			List<Schedule> schedules = scheduleService.getAllSchedules();
			List<ScheduleResponseDto> schedulesDto = schedules.stream().map(ScheduleResponseDto::new).toList();
			model.addAttribute("schedules", schedulesDto);
		}
	}

	// Add current schedules to model attribute
	public void addCurrentSchedulesToModel(Model model) {
		if (!model.containsAttribute("schedules")) {
			List<Schedule> schedules = scheduleService.getCurrentSchedules();
			List<ScheduleResponseDto> schedulesDto = schedules.stream().map(ScheduleResponseDto::new).toList();
			model.addAttribute("schedules", schedulesDto);
		}
	}

	// Add previousUrl to model
	public void addPreviousUrlToModel(
		Model model, 
		HttpServletRequest request
	) {
		String currentUrl = request.getRequestURL().toString();
        String previousUrl = request.getHeader("Referer");

		// If previousUrl exists and different from currentUrl, add it to model
        if (previousUrl != null && !currentUrl.equals(previousUrl)) {
        	model.addAttribute("previousUrl", previousUrl);
        }
	}

	// Add current year and semester to model
	public void addCurrentYearAndSemesterToModel(Model model) {
		if (!model.containsAttribute("currentYear") && !model.containsAttribute("currentSemester")) {
			model.addAttribute("currentYear", studentService.getCurrentStudent().getCurrentYear());
			model.addAttribute("currentSemester", studentService.getCurrentStudent().getCurrentSemester());
		}
	}
	
	// Populate schedule form data for form fields 
	public void populateScheduleForm(Model model) {
		if (!model.containsAttribute("courses")) {
			addCurrentCoursesToModel(model);
		}
		
		// Add type options to model
		List<Option<String>> types = List.of(
				new Option<>(ScheduleType.L.name(), "Lecture"),
				new Option<>(ScheduleType.T.name(), "Tutorial"),
				new Option<>(ScheduleType.P.name(), "Practical"),
				new Option<>(ScheduleType.ASGMT.name(), "Assignment"),
				new Option<>(ScheduleType.ASMT.name(), "Assessment (Test, Quiz)"),
				new Option<>(ScheduleType.FE.name(), "Final Examination"),
				new Option<>(ScheduleType.SP.name(), "Special")
				);
		model.addAttribute("types", types);
		
		// Add scope options to model
		List<Option<String>> scopes = Arrays.stream(Scope.values())
			.map(scope -> new Option<>(
				scope.name(),
				scope.getLabel()
			))
			.toList();
		model.addAttribute("scopes", scopes);
	}

	// Populate course form data for form fields 
	public void populateCourseForm(Model model) {
		// Add programme type options to model
		List<Option<String>> programmeTypes = Arrays.stream(ProgrammeType.values())
			.map(programmeType -> new Option<>(
				programmeType.name(),
				programmeType.getLabel()
			))
			.toList();
		model.addAttribute("programmeTypes", programmeTypes);

		// Add year options to model
		List<Option<Integer>> years = List.of(
			new Option<>(1, 1),
			new Option<>(2, 2),
			new Option<>(3, 3)
		);
		model.addAttribute("years", years);

		// Add semester options to model
		List<Option<Integer>> semesters = List.of(
			new Option<>(1, 1),
			new Option<>(2, 2),
			new Option<>(3, 3)
		);
		model.addAttribute("semesters", semesters);

		// Add timeslot types to model
		List<String> timeslotTypes = Arrays.asList("lecture", "tutorial", "practical");
		model.addAttribute("timeslotTypes", timeslotTypes);

		// Add dayOfWeek options to model
		List<Option<String>> dayOfWeek = Arrays.stream(DayOfWeek.values())
			.map(day -> new Option<>(
				day.name(),
				day.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
			))
			.toList();
		model.addAttribute("dayOfWeek", dayOfWeek);
	}
}
