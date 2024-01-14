package com.school.admin.service;

import com.school.admin.dto.StudentDto;
import com.school.admin.entity.Staff;
import com.school.admin.entity.StaffClassSection;
import com.school.admin.entity.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {

    StudentDto saveStudent(StudentDto studentDto);
    Staff saveStaff(Staff staff);
    StaffClassSection saveStaffClassSection(StaffClassSection staffClassSection);
    List<Student> getStudentsByClassAndSection(String className, String section);
    List<Staff> getStaffListByStudentId(Long studentId);
    Map<String, Map<String, List<Student>>> getStudentListByStaffId(Long staffId);
    List<Student> getAllStudents();
    List<Staff> getAllStaff();
    void deleteStudentById(Long id);
}
