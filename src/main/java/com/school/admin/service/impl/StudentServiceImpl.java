package com.school.admin.service.impl;

import com.school.admin.dto.StudentDto;
import com.school.admin.entity.Staff;
import com.school.admin.entity.StaffClassSection;
import com.school.admin.entity.Student;
import com.school.admin.repository.StaffClassSectionRepository;
import com.school.admin.repository.StaffRepository;
import com.school.admin.repository.StudentRepository;
import com.school.admin.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private StaffRepository staffRepository;
    private StaffClassSectionRepository staffClassSectionRepository;

    @Override
    public StudentDto saveStudent(StudentDto studentDto) {

        // converting StudentDto to StudentJpaEntity
        Student student = new Student(
                studentDto.getId(),
                studentDto.getStudentName(),
                studentDto.getMotherName(),
                studentDto.getFatherName(),
                studentDto.getAddress(),
                studentDto.getClassName(),
                studentDto.getSection()
        );

        Student savedStudent = studentRepository.save(student);
        StudentDto savedStudentDto = new StudentDto(
                savedStudent.getId(),
                savedStudent.getStudentName(),
                savedStudent.getMotherName(),
                savedStudent.getFatherName(),
                savedStudent.getAddress(),
                savedStudent.getClassName(),
                savedStudent.getSection()
        );
        return savedStudentDto;
    }

    @Override
    public Staff saveStaff(Staff staff) {
        Staff savedStaff = staffRepository.save(staff);
        return savedStaff;
    }

    @Override
    public StaffClassSection saveStaffClassSection(StaffClassSection staffClassSection) {
        StaffClassSection savedStaffClassSection = staffClassSectionRepository.save(staffClassSection);
        return savedStaffClassSection;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> studentsList = studentRepository.findAll();
        return studentsList;
    }

    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    @Override
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getStudentsByClassAndSection(String className, String section) {
        List<Student> studentsList = studentRepository.findByClassNameAndSection(className, section);
        return studentsList;
    }

    @Override
    public List<Staff> getStaffListByStudentId(Long studentId) {
        Student student = getStudentById(studentId);
        String className = student.getClassName();
        String section = student.getSection();
        List<StaffClassSection> staffClassSectionList = staffClassSectionRepository.findByClassNameAndSection(className, section);
        return staffClassSectionList.stream()
                .map(StaffClassSection::getStaff)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Map<String, List<Student>>> getStudentListByStaffId(Long staffId) {
        Staff staff = staffRepository.findById(staffId).get();
        List<StaffClassSection> staffClassSectionList = staffClassSectionRepository.findAllByStaff(staff);
       List<Student> studentList = staffClassSectionList.stream()
               .map(staffClassSection -> studentRepository.findByClassNameAndSection(
                       staffClassSection.getClassName(),
                       staffClassSection.getSection()))
               .flatMap(List::stream).toList();

        Map<String, Map<String, List<Student>>> groupedStudents = studentList.stream()
                .collect(Collectors.groupingBy(Student::getClassName,
                        Collectors.groupingBy(Student::getSection)));
        return groupedStudents;
    }

  /*  @Override
    public Map<String, Map<String, List<Student>>> getStudentListByStaffId(Long staffId) {
        Staff staff = staffRepository.findById(staffId).orElseThrow(() -> new RuntimeException("Staff not found"));
        List<StaffClassSection> staffClassSectionList = staffClassSectionRepository.findAllByStaff(staff);
        return staffClassSectionList.stream()
                .flatMap(staffClassSection -> {
                    String className = staffClassSection.getClassName();
                    String section = staffClassSection.getSection();
                    List<Student> students = studentRepository.findByClassNameAndSection(className, section);
                    return students.stream()
                            .peek(student -> student.setClassName(className))
                            .peek(student -> student.setSection(section))
                            .map(student -> new AbstractMap.SimpleEntry<>(className, student));
                })
                .collect(Collectors.groupingBy(
                        AbstractMap.SimpleEntry::getKey,
                        Collectors.groupingBy(
                                AbstractMap.SimpleEntry::getValue,
                                LinkedHashMap::new,
                                Collectors.mapping(
                                        AbstractMap.SimpleEntry::getValue,
                                        Collectors.toList()))));
    }*/

    private Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).get();
    }
}
