package io.github.wcm.schedulemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import io.github.wcm.schedulemanager.domain.Schedule;
import io.github.wcm.schedulemanager.dto.ScheduleRequestDto;
import io.github.wcm.schedulemanager.dto.ScheduleResponseDto;
import io.github.wcm.schedulemanager.service.ScheduleService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api")
public class ScheduleController {
	@Autowired
	private ScheduleService scheduleService;

	public ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@GetMapping("/schedules")
	public List<Schedule> getAllSchedules() {
		return scheduleService.getAllSchedules();
	}

	@GetMapping("/schedule/{id}")
	public Schedule getScheduleById(@PathVariable int id) {
		return scheduleService.getScheduleById(id);
	}

	@PostMapping("/schedule/{id}")
	public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleRequestDto dto) {
		Schedule schedule = scheduleService.createSchedule(dto);
		ScheduleResponseDto response = new ScheduleResponseDto(schedule);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/schedule/{id}")
	public ResponseEntity<ScheduleResponseDto> updateSchedule(@Valid @RequestBody ScheduleRequestDto dto, @PathVariable int id) {
		Schedule schedule = scheduleService.updateSchedule(dto, id);
		ScheduleResponseDto response = new ScheduleResponseDto(schedule);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/schedule/{id}")
	public ResponseEntity<Void> deleteSchedule(@PathVariable int id) {
		scheduleService.deleteSchedule(id);
		return ResponseEntity.noContent().build();
	}
}