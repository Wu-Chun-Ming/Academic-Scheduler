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

import io.github.wcm.schedulemanager.dto.ScheduleRequestDto;
import io.github.wcm.schedulemanager.dto.ScheduleResponseDto;
import io.github.wcm.schedulemanager.service.ModelPopulationService;
import io.github.wcm.schedulemanager.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/schedule")
public class ScheduleWebController {

	private final ScheduleService scheduleService;
	private final ModelPopulationService modelPopulationService;
	
	public ScheduleWebController(ScheduleService scheduleService, ModelPopulationService modelPopulationService) {
		this.scheduleService = scheduleService;
		this.modelPopulationService = modelPopulationService;
	}

	// View current schedules
	@GetMapping({ "", "/" })
	public String index(Model model) {
		// Remove previousUrl from model
		if (model.containsAttribute("previousUrl")) {
			model.asMap().remove("previousUrl");
		}
		modelPopulationService.addCurrentSchedulesToModel(model);

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
		modelPopulationService.populateScheduleForm(model);
		modelPopulationService.addPreviousUrlToModel(model, request);

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
			modelPopulationService.populateScheduleForm(model);
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
		modelPopulationService.populateScheduleForm(model);
		modelPopulationService.addPreviousUrlToModel(model, request);

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
			modelPopulationService.populateScheduleForm(model);
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
		modelPopulationService.addSchedulesToModel(model);

		return "schedule/manage";
	}
}