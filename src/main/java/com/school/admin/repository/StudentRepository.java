package com.school.admin.repository;

import com.school.admin.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByClassNameAndSection(String className, String section);
}
