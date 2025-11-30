package io.github.wcm.schedulemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.wcm.schedulemanager.domain.Course;
import io.github.wcm.schedulemanager.domain.Option;
import io.github.wcm.schedulemanager.domain.Schedule;
import io.github.wcm.schedulemanager.dto.CourseResponseDto;
import io.github.wcm.schedulemanager.dto.ScheduleRequestDto;
import io.github.wcm.schedulemanager.dto.ScheduleResponseDto;
import io.github.wcm.schedulemanager.service.CourseService;
import io.github.wcm.schedulemanager.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/schedule")
public class ScheduleWebController {
	@Autowired
	private CourseService courseService;

	@Autowired
	private ScheduleService scheduleService;

	// Add courses to model attribute
	private void addCoursesToModel(Model model) {
		if (!model.containsAttribute("courses")) {
			List<Course> courses = courseService.getAllCourses();
			List<CourseResponseDto> coursesDto = courses.stream().map(CourseResponseDto::new).toList();
			model.addAttribute("courses", coursesDto);
		}
	}

	// Add schedules to model attribute
	private void addSchedulesToModel(Model model) {
		if (!model.containsAttribute("schedules")) {
			List<Schedule> schedules = scheduleService.getAllSchedules();
			List<ScheduleResponseDto> schedulesDto = schedules.stream().map(ScheduleResponseDto::new).toList();
			model.addAttribute("schedules", schedulesDto);
		}
	}

	// Populate form data for form fields 
	private void populateFormData(Model model) {
		if (!model.containsAttribute("courses")) {
			addCoursesToModel(model);
		}

		// Add types to model
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

		// Add scopes to model
		List<Option<String>> scopes = List.of(
			new Option<>("official", "Official"),
			new Option<>("personal", "Personal")
		);
		model.addAttribute("scopes", scopes);
	}

	// Add previousUrl to model
	private void addPreviousUrlToModel(
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

	// View current schedules
	@GetMapping({ "", "/" })
	public String index(Model model) {
		// Remove previousUrl from model
		if (model.containsAttribute("previousUrl")) {
			model.asMap().remove("previousUrl");
		}
		addSchedulesToModel(model);

		return "schedule/index";
	}

	// Display schedule form
	@GetMapping("/create")
	public String create(
		Model model,
		HttpServletRequest request, 
		HttpSession session
	) {
		// Create empty ScheduleRequestDto as command object
		if (!model.containsAttribute("schedule")) {
			model.addAttribute("schedule", new ScheduleRequestDto());
		}
		populateFormData(model);
		addPreviousUrlToModel(model, request);

		return "schedule/create";
	}

	// Store new schedule
	@PostMapping("/create")
	public String store(
		@Valid @ModelAttribute("schedule") ScheduleRequestDto dto, 
		BindingResult bindingResult, 
		Model model, 
		RedirectAttributes redirectAttributes
	) {
		if (bindingResult.hasErrors()) {
			populateFormData(model);
			model.addAttribute("schedule", dto);
			return "schedule/create";
		}

		scheduleService.createSchedule(dto);
		redirectAttributes.addFlashAttribute("successMessage", "Schedule created successfully!");

		return "redirect:/schedule"; // Redirect to schedule page
	}

	// Display schedule edit form
	@GetMapping("/{id}/edit")
	public String edit(
		@PathVariable int id, 
		Model model,
		HttpServletRequest request, 
		HttpSession session
	) {
		ScheduleResponseDto scheduleDto = new ScheduleResponseDto(scheduleService.getScheduleById(id));
		model.addAttribute("schedule", scheduleDto);
		model.addAttribute("id", scheduleDto.getId());
		populateFormData(model);
		addPreviousUrlToModel(model, request);

		return "schedule/edit";
	}
	
	// Update schedule
	@PutMapping("/{id}")
	public String update(@PathVariable int id, 
		@Valid @ModelAttribute("schedule") ScheduleRequestDto dto, 
		BindingResult bindingResult, 
		Model model, 
		RedirectAttributes redirectAttributes
	) {
		if (bindingResult.hasErrors()) {
			populateFormData(model);
			model.addAttribute("schedule", dto);
			return "schedule/edit";
		}

		scheduleService.updateSchedule(dto, id);
		redirectAttributes.addFlashAttribute("successMessage", "Schedule updated successfully!");

		return "redirect:/schedule/" + id + "/edit"; // Redirect to schedule page
	}
	
	// Delete schedule
	@DeleteMapping("/{id}")
	public String destroy(
		@PathVariable int id, 
		RedirectAttributes redirectAttributes
	) {
		scheduleService.deleteSchedule(id);
		redirectAttributes.addFlashAttribute("successMessage", "Schedule deleted successfully!");

		return "redirect:/schedule"; // Redirect to schedule page
	}

	// Manage schedules
	@GetMapping("/manage")
	public String manage(Model model) {
		// Remove previousUrl from model
		if (model.containsAttribute("previousUrl")) {
			model.asMap().remove("previousUrl");
		}
		addSchedulesToModel(model);

		return "schedule/manage";
	}
}