package io.github.wcm.academicscheduler.controller.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import io.github.wcm.academicscheduler.domain.Course;
import io.github.wcm.academicscheduler.domain.Option;
import io.github.wcm.academicscheduler.domain.enums.ProgrammeType;
import io.github.wcm.academicscheduler.dto.CourseResponseDto;
import io.github.wcm.academicscheduler.dto.StudentRequestDto;
import io.github.wcm.academicscheduler.dto.StudentResponseDto;
import io.github.wcm.academicscheduler.service.CourseService;
import io.github.wcm.academicscheduler.service.ModelPopulationService;
import io.github.wcm.academicscheduler.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping({"", "/"})
public class GlobalWebController {

	private final StudentService studentService;
	private final CourseService courseService;
    private final ModelPopulationService modelPopulationService;

	public GlobalWebController(StudentService studentService, CourseService courseService, ModelPopulationService modelPopulationService) {
		this.studentService = studentService;
		this.courseService = courseService;
		this.modelPopulationService = modelPopulationService;
	}

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

	// Add current courses to model attribute
	private void addCurrentCoursesToModel(Model model) {
		if (!model.containsAttribute("courses")) {
			List<Course> courses = courseService.getCurrentCourses();
			List<CourseResponseDto> coursesDto = courses.stream().map(CourseResponseDto::new).toList();
			model.addAttribute("courses", coursesDto);
		}
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
		modelPopulationService.addCurrentYearAndSemesterToModel(model);
		addCurrentCoursesToModel(model);
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
