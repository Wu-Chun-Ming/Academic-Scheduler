package io.github.wcm.academicscheduler.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.wcm.academicscheduler.domain.Schedule;
import io.github.wcm.academicscheduler.dto.ScheduleRequestDto;
import io.github.wcm.academicscheduler.dto.ScheduleResponseDto;
import io.github.wcm.academicscheduler.service.ScheduleService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

	private final ScheduleService scheduleService;

	public ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@GetMapping
	public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules() {
		List<Schedule> schedules = scheduleService.getAllSchedules();
		List<ScheduleResponseDto> response = schedules.stream().map(ScheduleResponseDto::new).toList();

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable int id) {
		Schedule schedule = scheduleService.getScheduleById(id);
		ScheduleResponseDto response = new ScheduleResponseDto(schedule);

		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleRequestDto dto) {
		Schedule schedule = scheduleService.createSchedule(dto);
		ScheduleResponseDto response = new ScheduleResponseDto(schedule);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ScheduleResponseDto> updateSchedule(@Valid @RequestBody ScheduleRequestDto dto, @PathVariable int id) {
		Schedule schedule = scheduleService.updateSchedule(dto, id);
		ScheduleResponseDto response = new ScheduleResponseDto(schedule);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSchedule(@PathVariable int id) {
		scheduleService.deleteSchedule(id);
		return ResponseEntity.noContent().build();
	}
}