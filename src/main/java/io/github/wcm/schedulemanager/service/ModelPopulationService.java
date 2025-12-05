package io.github.wcm.schedulemanager.service;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import io.github.wcm.schedulemanager.domain.Course;
import io.github.wcm.schedulemanager.domain.Option;
import io.github.wcm.schedulemanager.domain.ProgrammeType;
import io.github.wcm.schedulemanager.domain.Schedule;
import io.github.wcm.schedulemanager.dto.CourseResponseDto;
import io.github.wcm.schedulemanager.dto.ScheduleResponseDto;
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
				new Option<>("L", "Lecture"),
				new Option<>("T", "Tutorial"),
				new Option<>("P", "Practical"),
				new Option<>("ASGMT", "Assignment"),
				new Option<>("ASMT", "Assessment (Test, Quiz)"),
				new Option<>("FE", "Final Examination"),
				new Option<>("SP", "Special")
				);
		model.addAttribute("types", types);
		
		// Add scope options to model
		List<Option<String>> scopes = List.of(
				new Option<>("official", "Official"),
				new Option<>("personal", "Personal")
				);
		model.addAttribute("scopes", scopes);
	}

	// Populate course form data for form fields 
	public void populateCourseForm(Model model) {
		// Add programme type options to model
		List<Option<String>> programmeTypes = List.of(
			new Option<>(ProgrammeType.FOUNDATION.name(), "Foundation"),
			new Option<>(ProgrammeType.UNDERGRADUATE.name(), "Undergraduate")
		);
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
		List<Option<String>> dayOfWeek = List.of(
		    new Option<String>(DayOfWeek.MONDAY.name(), "Monday"),
		    new Option<String>(DayOfWeek.TUESDAY.name(), "Tuesday"),
		    new Option<String>(DayOfWeek.WEDNESDAY.name(), "Wednesday"),
		    new Option<String>(DayOfWeek.THURSDAY.name(), "Thursday"),
		    new Option<String>(DayOfWeek.FRIDAY.name(), "Friday"),
		    new Option<String>(DayOfWeek.SATURDAY.name(), "Saturday"),
		    new Option<String>(DayOfWeek.SUNDAY.name(), "Sunday")
		);
		model.addAttribute("dayOfWeek", dayOfWeek);
	}
}
