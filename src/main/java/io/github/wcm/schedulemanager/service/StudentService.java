package io.github.wcm.schedulemanager.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.wcm.schedulemanager.domain.ProgrammeType;
import io.github.wcm.schedulemanager.domain.Student;
import io.github.wcm.schedulemanager.dto.StudentRequestDto;
import io.github.wcm.schedulemanager.exception.StudentNotFoundException;
import io.github.wcm.schedulemanager.repository.StudentRepository;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable("students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    @CacheEvict(value = "students", allEntries = true)
    public Student createStudent(StudentRequestDto dto) {
        // Create student entity from DTO
        Student student = new Student(dto);
        // Save entity
        return studentRepository.save(student);
    }

    @CacheEvict(value = "students", allEntries = true)
    public Student updateStudent(StudentRequestDto dto, Long id) {
        // Load student entity
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));

        // Update fields
        student.setName(dto.getName());
        student.setGender(dto.getGender());
        student.setProgramme(dto.getProgramme());
		try {
			student.setProgrammeType(ProgrammeType.valueOf(dto.getProgrammeType().toUpperCase()));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid programme type: " + dto.getProgrammeType());
		}
        student.setEnrollmentDate(dto.getEnrollmentDate());
        student.setGraduationDate(dto.getGraduationDate());
        student.setCurrentYear(dto.getCurrentYear());
        student.setCurrentSemester(dto.getCurrentSemester());

        return studentRepository.save(student);
    }

    @CacheEvict(value = "students", allEntries = true)
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
