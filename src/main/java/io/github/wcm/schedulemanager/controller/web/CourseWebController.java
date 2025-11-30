package io.github.wcm.schedulemanager.controller.web;

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
import io.github.wcm.schedulemanager.dto.CourseRequestDto;
import io.github.wcm.schedulemanager.dto.CourseResponseDto;
import io.github.wcm.schedulemanager.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/course")
public class CourseWebController {
	@Autowired
	private CourseService courseService;

	// Add courses to model attribute
	private void addCoursesToModel(Model model) {
		if (!model.containsAttribute("courses")) {
			List<Course> courses = courseService.getAllCourses();
			List<CourseResponseDto> coursesDto = courses.stream().map(CourseResponseDto::new).toList();
			model.addAttribute("courses", coursesDto);
		}
	}

	// Populate form data for form fields 
	private void populateFormData(Model model) {
		if (!model.containsAttribute("courses")) {
			addCoursesToModel(model);
		}

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

	// View current courses
	@GetMapping({ "", "/" })
	public String index(Model model) {
		// Remove previousUrl from model
		if (model.containsAttribute("previousUrl")) {
			model.asMap().remove("previousUrl");
		}
		// Create empty ScheduleRequestDto as command object
		if (!model.containsAttribute("course")) {
			model.addAttribute("course", new CourseRequestDto());
		}
        populateFormData(model);
		addCoursesToModel(model);

		return "course/index";
	}

	// Store new course
	@PostMapping("/create")
	public String store(
		@Valid @ModelAttribute("course") CourseRequestDto dto, 
		BindingResult bindingResult, 
		Model model, 
		RedirectAttributes redirectAttributes
	) {
		if (bindingResult.hasErrors()) {
			populateFormData(model);
			model.addAttribute("course", dto);
			return "course/index";
		}
		courseService.createCourse(dto);
		redirectAttributes.addFlashAttribute("successMessage", "Course created successfully!");

		return "redirect:/course"; // Redirect to course page
	}

	// Display course edit form
	@GetMapping("/{code}/edit")
	public String edit(
		@PathVariable String code, 
		Model model,
		HttpServletRequest request, 
		HttpSession session
	) {
		CourseResponseDto courseDto = new CourseResponseDto(courseService.getCourseByCode(code));
		model.addAttribute("course", courseDto);
		model.addAttribute("code", courseDto.getCode());
		populateFormData(model);
		addPreviousUrlToModel(model, request);

		return "course/edit";
	}
	
	// Update course
	@PutMapping("/{code}")
	public String update(
		@PathVariable String code, 
		@Valid @ModelAttribute("course") CourseRequestDto dto, 
		BindingResult bindingResult, 
		Model model, 
		RedirectAttributes redirectAttributes
	) {
		if (bindingResult.hasErrors()) {
			populateFormData(model);
			model.addAttribute("course", dto);
			return "course/edit";
		}
		courseService.updateCourse(dto, code);
		redirectAttributes.addFlashAttribute("successMessage", "Course updated successfully!");

		return "redirect:/course/" + code + "/edit"; // Redirect to course edit page
	}
	
	// Delete course
	@DeleteMapping("/{code}")
	public String destroy(
		@PathVariable String code, 
		RedirectAttributes redirectAttributes
	) {
		courseService.deleteCourse(code);
		redirectAttributes.addFlashAttribute("successMessage", "Course deleted successfully!");

		return "redirect:/course"; // Redirect to course page
	}

	// Manage courses
	@GetMapping("/manage")
	public String manage(Model model) {
		// Remove previousUrl from model
		if (model.containsAttribute("previousUrl")) {
			model.asMap().remove("previousUrl");
		}
		addCoursesToModel(model);

		return "course/manage";
	}
}