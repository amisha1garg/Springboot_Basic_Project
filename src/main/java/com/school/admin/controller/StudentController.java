package com.school.admin.controller;

import com.school.admin.dto.StudentDto;
import com.school.admin.entity.Staff;
import com.school.admin.entity.StaffClassSection;
import com.school.admin.entity.Student;
import com.school.admin.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/students", produces = "application/json")
public class StudentController {

    private StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDto> saveStudent(@Validated @RequestBody StudentDto studentDto) {
        try {
            StudentDto savedStudent = studentService.saveStudent(studentDto);
            return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Request", ex);
        }
    }

    @PostMapping("/staff")
    public ResponseEntity<Staff> saveStaff(@RequestBody Staff staff) {
        try {
            Staff savedStaff = studentService.saveStaff(staff);
            return new ResponseEntity<>(savedStaff, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Request", ex);
        }
    }

    @PostMapping("/staffClassSection")
    public ResponseEntity<StaffClassSection> saveStaffClassAndSection(@RequestBody StaffClassSection staffClassSection) {
        try {
            StaffClassSection savedStaffClassSection = studentService.saveStaffClassSection(staffClassSection);
            return new ResponseEntity<>(savedStaffClassSection, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Request", ex);
        }
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> studentsList = studentService.getAllStudents();
        if (studentsList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No records found");
        }
        return new ResponseEntity<>(studentsList, HttpStatus.OK);
    }

    @GetMapping("/staff")
    public ResponseEntity<List<Staff>> getAllStaff() {
        List<Staff> staffList = studentService.getAllStaff();
        if (staffList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No records found");
        }
        return new ResponseEntity<>(staffList, HttpStatus.OK);
    }

    @GetMapping("/student/{studId}")
    public ResponseEntity<List<Staff>> getStaffListByStudentId(@PathVariable("studId") Long id) {
        List<Staff> staffList = studentService.getStaffListByStudentId(id);
        if (staffList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No records found");
        }
        return new ResponseEntity<>(staffList, HttpStatus.OK);
    }

    @GetMapping("/staff/{staffId}")
    public ResponseEntity<Map<String, Map<String, List<Student>>>> getStudentListByStaffId(@PathVariable("staffId") Long id) {
        Map<String, Map<String, List<Student>>> studentList = studentService.getStudentListByStaffId(id);
        if (studentList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No records found");
        }
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    /**
     * values of className - 1st, 2nd, 3rd, 4th, 5th, 6th, 7th, 8th
     * Possible values of section - A, B, C
     * Staff id range from 1-16
     * and student id range from - 1-41 out of which 1,2 were deleted
     */
    @GetMapping("/{className}/{section}")
    public ResponseEntity<List<Student>> getStudentByClassAndSection(@PathVariable("className") String className, @PathVariable("section") String section) {
        List<Student> studentsList = studentService.getStudentsByClassAndSection(className, section);
        if (studentsList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No records found");
        }
        return new ResponseEntity<>(studentsList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteTopic(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return HttpStatus.OK;
    }
}
