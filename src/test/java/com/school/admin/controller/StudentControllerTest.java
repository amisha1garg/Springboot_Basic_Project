package com.school.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.admin.dto.StudentDto;
import com.school.admin.entity.Student;
import com.school.admin.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@EnableWebMvc
@AutoConfigureMockMvc
@WebAppConfiguration
public class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @Mock
    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testSaveStudent() throws Exception {
        StudentDto studentDto = new StudentDto();
        studentDto.setStudentName("John Doe");
        studentDto.setMotherName("Seeta");
        studentDto.setFatherName("Santosh");
        studentDto.setAddress("GT road Delhi");
        studentDto.setClassName("1st");
        studentDto.setSection("A");

        StudentDto savedStudent = new StudentDto();
        savedStudent.setId(1L);
        savedStudent.setStudentName("John Doe");
        savedStudent.setClassName("1st");
        savedStudent.setSection("A");
        when(studentService.saveStudent(any(StudentDto.class))).thenReturn(savedStudent);

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.studentName").value("John Doe"))
                .andExpect(jsonPath("$.className").value("1st"))
                .andExpect(jsonPath("$.section").value("A"))
                .andReturn();
    }

    @Test
    public void testGetAllStudents() throws Exception {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setStudentName("John Doe");
        student1.setClassName("1st");
        student1.setSection("A");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setStudentName("Jane Doe");
        student2.setClassName("2nd");
        student2.setSection("B");

        List<Student> studentList = Arrays.asList(student1, student2);
        when(studentService.getAllStudents()).thenReturn(studentList);

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].studentName").value("John Doe"))
                .andExpect(jsonPath("$[0].className").value("1st"))
                .andExpect(jsonPath("$[0].section").value("A"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].studentName").value("Jane Doe"))
                .andExpect(jsonPath("$[1].className").value("2nd"))
                .andExpect(jsonPath("$[1].section").value("B"));
    }

    @Test
    public void testGetStudentByClassAndSection() throws Exception {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setStudentName("John Doe");
        student1.setClassName("1st");
        student1.setSection("A");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setStudentName("Jane Doe");
        student2.setClassName("1st");
        student2.setSection("A");

        List<Student> studentList = Arrays.asList(student1, student2);
        when(studentService.getStudentsByClassAndSection(eq("1st"), eq("A"))).thenReturn(studentList);

        this.mockMvc.perform(get("/api/students/1st/A")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].studentName").value("John Doe"))
                .andExpect(jsonPath("$[0].className").value("1st"))
                .andExpect(jsonPath("$[0].section").value("A"))
                .andReturn();
    }
}

