package io.github.wcm.schedulemanager.controller.web;

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

import io.github.wcm.schedulemanager.dto.CourseRequestDto;
import io.github.wcm.schedulemanager.dto.CourseResponseDto;
import io.github.wcm.schedulemanager.service.CourseService;
import io.github.wcm.schedulemanager.service.ModelPopulationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/course")
public class CourseWebController {

	private final CourseService courseService;
	private final ModelPopulationService modelPopulationService;

	public CourseWebController(CourseService courseService, ModelPopulationService modelPopulationService) {
		this.courseService = courseService;
		this.modelPopulationService = modelPopulationService;
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
		modelPopulationService.populateCourseForm(model);
		modelPopulationService.addCurrentYearAndSemesterToModel(model);
		modelPopulationService.addCurrentCoursesToModel(model);

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
			modelPopulationService.populateCourseForm(model);
			modelPopulationService.addCurrentYearAndSemesterToModel(model);
			modelPopulationService.addCurrentCoursesToModel(model);
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
		modelPopulationService.populateCourseForm(model);
		modelPopulationService.addPreviousUrlToModel(model, request);

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
			modelPopulationService.populateCourseForm(model);
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
		modelPopulationService.addCoursesToModel(model);

		return "course/manage";
	}
}