package io.github.wcm.schedulemanager.controller.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.wcm.schedulemanager.domain.Option;
import io.github.wcm.schedulemanager.domain.ProgrammeType;
import io.github.wcm.schedulemanager.dto.StudentRequestDto;
import io.github.wcm.schedulemanager.dto.StudentResponseDto;
import io.github.wcm.schedulemanager.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping({"", "/"})
public class GlobalWebController {
	@Autowired
	private StudentService studentService;

    // Populate form data for form fields
	@ModelAttribute
	private void populateProfileFormFields(Model model) {
		// Add gender options to model
		List<Option<String>> genders = List.of(
			new Option<>("M", "Male"),
			new Option<>("F", "Female")
		);
		model.addAttribute("genders", genders);
		
		// Add programme type options to model
		List<Option<String>> programmeTypes = List.of(
			new Option<>(ProgrammeType.FOUNDATION.name(), "Foundation"),
			new Option<>(ProgrammeType.UNDERGRADUATE.name(), "Undergraduate")
		);
		model.addAttribute("programmeTypes", programmeTypes);

		// Add programme options to model
		List<Option<String>> programmes = List.of(
			// Example programmes
			new Option<>("Computer Science", "Computer Science"),
			new Option<>("Information Systems", "Information Systems"),
			new Option<>("Software Engineering", "Software Engineering"),
			new Option<>("Data Science", "Data Science")
		);
		model.addAttribute("programmes", programmes);

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

	@GetMapping({"", "/"})
	public String home(Model model) {
		return "index";
	}

    @GetMapping("/profile/modal")
    public String getProfileModal(
		Model model,
		HttpServletRequest request
	) {
    	StudentResponseDto studentDto = new StudentResponseDto(studentService.getCurrentStudent());
		model.addAttribute("student", studentDto);
		addPreviousUrlToModel(model, request);

        return "fragments/profile-modal :: profileModal";
    }

    @PostMapping("/profile/update")
    @ResponseBody
    public Map<String, Object> updateProfile(
        @Valid @ModelAttribute("student") StudentRequestDto dto,
        BindingResult bindingResult,
        @RequestParam String previousUrl,
        Model model
    ) {
        Map<String, Object> response = new LinkedHashMap<>();

        if (bindingResult.hasErrors()) {
            // Include error messages in the response
            List<String> errors = bindingResult.getAllErrors().stream()
            		.map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            response.put("status", "error");
            response.put("errors", errors);

            return response;
        }

        studentService.updateStudent(dto, studentService.getCurrentStudent().getId());

        response.put("status", "success");
        response.put("redirectUrl", previousUrl != null ? previousUrl : "/");

        return response;
    }
}
